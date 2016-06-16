package hello.businessLogic.document;

import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.MatchLocation;
import com.marklogic.client.query.MatchSnippet;
import hello.StringResources.MarkLogicStrings;
import hello.StringResources.TipIzmene;
import hello.businessLogic.core.BeanManager;
import hello.entity.gov.gradskaskupstina.*;
import hello.security.EncryptKEK;
import hello.security.CRLVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.SerializationUtils;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.cert.Certificate;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by milan on 4.6.2016..
 * Class that handles all file related operations.
 */
public class AktManager extends BeanManager<Akt> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    /********************************************************************/
    private String  archive_app_api="http://localhost:9090/api/testx";
    /********************************************************************/


    private CRLVerifier crlVerifier = new CRLVerifier();
    /**
     * Initializes a new intance of AktManager.
     */
    public AktManager(){
        super();
    }

    /**
     * Returns all files that are proposed from database.
     *
     * @return List of files in database. <code>NULL</code> if not successful.
     */
    public ArrayList<Akt> getAllFilesProposed() {
        StringBuilder query = new StringBuilder();
        query.append("fn:collection(\"");
        query.append(MarkLogicStrings.AKTOVI_PREDLOZEN_COL_ID);
        query.append("\")");
        return queryManager.executeQuery(query.toString());
    }

    /**
     * Return all files that are approved from database.
     * @return List of files in database. <code>NULL</code> if not successful.
     */
    public ArrayList<Akt> getAllFilesApproved(){
        StringBuilder query = new StringBuilder();
        query.append("fn:collection(\"");
        query.append(MarkLogicStrings.AKTOVI_USVOJENI_COL_ID);
        query.append("\")");
        return queryManager.executeQuery(query.toString());
    }

    /**
     * Gets all proposed akts for current user.
     * @param user Current session user.
     * @return List of proposed files.
     */
    public ArrayList<Akt> getMyFilesProposed(User user){
        StringBuilder query = new StringBuilder();
        query.append("declare namespace a=\"http://www.gradskaskupstina.gov/\";");
        query.append("for $x in fn:collection(\"/predlozeniAktovi\")");
        query.append("where $x//a:UserName/text() = \"" + user.getUsername() + "\"");
        query.append(("return $x"));
        return queryManager.executeQuery(query.toString());
    }

    /**
     * Proposes an document.
     * @param akt Bean to be proposed.
     * @param user User that proposes Akt, needs to sign it first.
     * @return Generated URI. <code>NULL</code> if not successful.
     */
    public String proposeAkt(Akt akt, User user) {

        // Check if users' certificate is revoked
        Certificate cert = keyStoreManager.readCertificate(user.getUsername(),user.getPassword().toCharArray());
        if (crlVerifier.isRevoked(cert)){
            logger.info("Certificate is revoked for user " + user.getUsername()+", can't propose.");
            //TODO: Da li treba da se izadje iz metode ako je revoked?
        }
        if (!validateBeanBySchema(akt)) {
            logger.info("ERROR: Akt is not valid!");
            return null;
        }
        String ret = null;
        DocumentUriTemplate template = xmlManager.newDocumentUriTemplate("xml"); //TODO: DocumentUriTemplate is never used ?
        try {
            ret = this.write(akt, MarkLogicStrings.AKTOVI_PREDLOZEN_COL_ID).getUri();
            akt.setDocumentId(ret);
            // XML DOCUMENT IS READY TO BE SIGNED!
            boolean shouldSign = true;
            if (!this.write(akt, ret, MarkLogicStrings.AKTOVI_PREDLOZEN_COL_ID, shouldSign, user)) {
                ret = null;
                throw new Exception();
            }
        } catch (Exception e) {
            logger.info("[ERROR] Could not propose AKT!");
        }
        return ret;
    }

    /**
     * Approves an document. //TODO: IN PRINCIPLE?
     * @param akt Bean to be approved.
     * @param user User to verify ceritificate.
     * @return Generated URI. <code>NULL</code> if not successful.
     */
    public String approveAkt(Akt akt,User user){

        // Check if users' certificate is revoked
        Certificate cert = keyStoreManager.readCertificate(user.getUsername(),user.getPassword().toCharArray());
        if (crlVerifier.isRevoked(cert)){
            logger.info("Certificate is revoked for user " + user.getUsername()+", can't propose.");
            //TODO: Da li treba da se izadje iz metode ako je revoked?
        }

        if (!validateBeanBySchema(akt)){
            logger.info("[ERROR] Document["+akt.getDocumentId()+"] is not valid!");
            return null;
        }
        String ret = null;
        String docId = akt.getDocumentId();
        try {
            if (docId == null || docId.isEmpty()){
                throw new Exception();
            }
            if (!this.deleteDocument(docId)) {
                throw  new Exception();
            }
        } catch (Exception e) {
            logger.info("[ERROR] Could not delete document[" + docId + "] from proposed!");
        }
        try {
            // XML DOCUMENT SHOULD NOT BE SIGNED!
            boolean shouldSign = false;
            if (!write(akt,akt.getDocumentId(),MarkLogicStrings.AKTOVI_USVOJENI_COL_ID,shouldSign,null)){
                ret = null;
                throw  new Exception();
            } else {
                ret = akt.getDocumentId();
            }

        } catch (Exception e) {
            logger.info("[ERROR] Could not approve document[" + docId + "]!");
        }
        return ret;
    }

    /**
     * Deletes unapproved files.
     * @param docId URI of act to be deleted.
     * @return Indicator of success.
     */
    public boolean deleteAkt(String docId){
        boolean ret = false;

        try {
            deleteDocument(docId);
            ret = true;
        } catch (Exception e){
            logger.info("[ERROR] Could not delete document[" + docId + "]!");
        }
        return ret;
    }

    /**
     * Validates Akt by schema.
     * @param akt Akt to be validated.
     * @return Indicator of success.
     */
    public boolean validateAkt(Akt akt){
        return validateBeanBySchema(akt);
    }

    /**
     * Return document with search results
     * @param parameterofSearch search parametar
     * @param uriOfCollection id of collection
     * @return Document with search results
     */
    public HashMap<String,ArrayList<String>> returnListOfDocumentsMatchedWithOneFieldSearch(String parameterofSearch, String uriOfCollection) {
        MatchDocumentSummary matches[] = customManager.searchByField(parameterofSearch, uriOfCollection);

        MatchDocumentSummary result;
        MatchLocation locations[];
        String text;

        HashMap<String,ArrayList<String>> returnMap = new HashMap<>();

        for (int i = 0; i < matches.length; i++) {
            result = matches[i];
            ArrayList<String> listOfMatched = new ArrayList<>();
            locations = result.getMatchLocations();

            for (MatchLocation location : locations) {
                String item = "";
                for (MatchSnippet snippet : location.getSnippets()) {
                    text = snippet.getText().trim();
                    if (!text.equals("")) {
                        item +=snippet.isHighlighted() ? text.toUpperCase() : text;
                        item +=" ";
                    }
                }
                listOfMatched.add(item);
            }
            //  id, string u kom je mecovano
            returnMap.put(result.getUri(),listOfMatched);
        }
//            System.out.println("PRINT SNIPETA SA DOKUMENTIMA");
//            System.out.println(returnMap);
            return returnMap;
    }

    public HashMap<String,ArrayList<String>> returnListOfDocumentsMatchedWithOneFieldSearchAndTag(String tag,String parameterofSearch, String uriOfCollection){
        MatchDocumentSummary matches[] = customManager.searchByField(parameterofSearch, uriOfCollection);

        MatchDocumentSummary result;
        MatchLocation locations[];
        String text;

        HashMap<String,ArrayList<String>> returnMap = new HashMap<>();

        for (int i = 0; i < matches.length; i++) {
            result = matches[i];
            ArrayList<String> listOfMatched = new ArrayList<>();
            locations = result.getMatchLocations();
            for (MatchLocation location : locations) {
                String putanja = location.getPath();
                String retSplit [] = putanja.split(":");
                if(retSplit[retSplit.length-1].split("\\[")[0].equals(tag)) {
                    String item = "";

                    for (MatchSnippet snippet : location.getSnippets()) {
                        text = snippet.getText().trim();
                        if (!text.equals("")) {
                            item += snippet.isHighlighted() ? text.toUpperCase() : text;
                            item += " ";
                        }
                    }
                    listOfMatched.add(item);
                }
            }
            //  id, string u kom je mecovano
            returnMap.put(result.getUri(),listOfMatched);
        }

        return returnMap;
    }

    /**
     * Applies amendments on document.
     * @param listaAmandmana List of amendments to be applied.
     * @param akt Akt on whom the amendments need to be applied.
     * @return Indicator of success.
     */
    public boolean applyAmendments(ArrayList<Amandmani> listaAmandmana, Akt akt, User user){
        //TODO: Da li treba proveravati sertifikat (CRL) i ovde?
        boolean ret = false;
        try{
            akt.setSignature(null);
            this.write(akt,"usv" +akt.getDocumentId(),MarkLogicStrings.AKTOVI_PRIMENJENI_COL_ID,false,null);

            for (Amandmani amandmani: listaAmandmana){
                for (TAmandman amandman : amandmani.getAmandman()){
                    String tmp = amandman.getSadrzaj();
                    String query = generateXquery(amandman.getPredmetIzmene(), amandman.getTipIzmene().value(), amandman.getSadrzaj(), "usv"+akt.getDocumentId());
                    this.executeQuery(query);

                }
            }
            Akt tmpAkt = read("usv" +akt.getDocumentId(),false);
            tmpAkt.setDocumentId("usv" +akt.getDocumentId());

            write(tmpAkt,"usv" +akt.getDocumentId(),MarkLogicStrings.AKTOVI_USVOJENI_COL_ID,true,user);
            ret = true;
        } catch (Exception e){
            logger.info("[ERROR] Could not apply amandemnts on document " + akt.getDocumentId());
        }
        return ret;
    }

    public String generateXquery(TReferenca referenca, String type, String tekst, String docId){
        String ret = null;
        StringBuilder builder = new StringBuilder();

        String redniBrojClana = referenca.getRefClanovi();
        String redniBrojStava = referenca.getRefStavovi();
        String redniBrojTacke = referenca.getRefTacke();
        String redniBrojGlave = referenca.getRefGlava();
        String redniBrojOdeljka = referenca.getRefOdeljak();
        String rednoSlovoPododeljka = referenca.getRefPododeljak();
        String redniBrojPodtacke = referenca.getRefPodtacka();

        builder.append("declare namespace a=\"http://www.gradskaskupstina.gov/\";\n");
        builder.append("let $x := doc(\"" + docId + "\")\n");
        if (type.toUpperCase().equals(TipIzmene.BRISANJE)){
            builder.append("return xdmp:node-delete($x/a:Akt//a:Clan[@RedniBroj=\"" + redniBrojClana +"\"]");
            if(redniBrojStava != null)
                builder.append("/a:Stav[@RedniBroj = \"" + redniBrojStava + "\"]");
            if(redniBrojTacke != null)
                builder.append("/a:Tacka[@RedniBroj = \"" + redniBrojTacke + "\"]");
            if(redniBrojPodtacke != null)
                builder.append("/a:Podtacka[@RedniBroj = \"" + redniBrojPodtacke + "\"]");
            builder.append(")");
        } else if (type.toUpperCase().equals(TipIzmene.IZMENA)) {
            builder.append("return xdmp:node-replace($x/a:Akt//a:Clan[@RedniBroj=\"" + redniBrojClana +"\"]");
            builder.append("/a:Stav[@RedniBroj = \"" + redniBrojStava + "\"]");
            if(redniBrojTacke != null){
                builder.append("/a:Tacka[@RedniBroj = \"" + redniBrojTacke + "\"]");
                if(redniBrojPodtacke != null){
                    builder.append("/a:Podtacka[@RedniBroj = \"" + redniBrojPodtacke + "\"]/text()");
                }
            }
            builder.append(",");
            builder.append(tekst);
            builder.append(")");
        } else if (type.toUpperCase().equals(TipIzmene.DOPUNA)){

            // Clan treba da se doda u glavu.
            if (redniBrojGlave!= null){
                builder.append("return  xdmp:node-insert-child($x/a:Akt//a:Glava[@RedniBroj = \"" + redniBrojGlave +"\"]");
            }
            // Clan treba da se doda u odeljak.
            else if (redniBrojOdeljka != null){
                builder.append("return  xdmp:node-insert-child($x/a:Akt//a:Odeljak[@RedniBroj = \"" + redniBrojOdeljka +"\"]");
            }
            // Clan treba da se doda u pododeljak.
            else if (rednoSlovoPododeljka != null){
                builder.append("return  xdmp:node-insert-child($x/a:Akt//a:Odeljak[@RednoSlovo = \"" + rednoSlovoPododeljka +"\"]");
            }
            // Treba da se provjeri da li se stav dodaje ili neka manja cjelina.
            else if (redniBrojClana != null){
                // Provjeriti da li se nesto dodaje u stav.
                if (redniBrojStava != null) {
                    // Dodaje.
                    // Proveriti da li se dodaje podtacka?
                    if (redniBrojTacke != null) {
                        // Dodaje se podtacka u tacku.
                        builder.append("return  xdmp:node-insert-child($x/a:Akt//a:Clan[@RedniBroj= \"" + redniBrojClana +"\"]");
                        builder.append("/a:Stav[@RedniBroj = \"" + redniBrojStava +"\"]");
                        builder.append("/a:Tacka[@RedniBroj = \"" + redniBrojTacke+"\"]");
                    } else {
                        // Dodaje se tacka u stav.
                        builder.append("return  xdmp:node-insert-child($x/a:Akt//a:Clan[@RedniBroj= \"" + redniBrojClana +"\"]");
                        builder.append("/a:Stav[@RedniBroj = \"" + redniBrojStava +"\"]");
                    }
                } else {
                    // Dodaje se se stav u clan.
                    builder.append("return  xdmp:node-insert-child($x/a:Akt//a:Clan[@RedniBroj= \"" + redniBrojClana +"\"]");
                }
            }
            // Nista nije poslato - treba da se doda direktno u akt.
            else {
                builder.append("return  xdmp:node-insert-before($x/a:Akt/a:ZavrsniDeo");
            }
            builder.append(",");
            builder.append(tekst);
            builder.append(")");
        }
        logger.info(builder.toString());
        return builder.toString();
    }
    // NE ZNAM DA LI SE KORISTI KOD MOG KOMITA JE BILA TU
    // U MOM LOKALU NA NA REMOTE JE NIJE BILO
    /**
     * Update clan from akt.
     * @param akt Document containing clan.
     * @param data Data containing id of clan, stav and tacka and type of operation.
     * @return Indicator of success.
     */
    public boolean updateClan(Akt akt,ArrayList<Object> data){
        boolean ret = false;

        TReferenca referenca = (TReferenca)data.get(0);
        String redniBrojClana = referenca.getRefClanovi();
        String redniBrojStava = referenca.getRefStavovi();
        String redniBrojTacke = referenca.getRefTacke();
        String typeOfOperation = (String)data.get(1);
        TSemiStruktuiraniTekst tekst = (TSemiStruktuiraniTekst)data.get(2);

        // DODAVANJE CLANA ZA SADA SAMO DIREKTNO U CLAN!




        int clanIndex = -1;

        for (int i = 0; i< akt.getClan().size();i++){
            if (akt.getClan().get(i).getRedniBroj().toString().equals(redniBrojClana)){
                clanIndex = i;
                break;
            }
        }
        if (clanIndex != -1){
            // Tacka treba da se mijenja.
            if (redniBrojTacke != null && redniBrojStava != null){
                int stavIndex = -1;
                // Prodji kroz sve stavove za taj clan.
                for (int i = 0; i < akt.getClan().get(clanIndex).getStav().size(); i++){
                    TStav stav = akt.getClan().get(clanIndex).getStav().get(i);
                    // Prodji kroz sve tacke i uradi izmjenu
                    for(int j = 0; j < stav.getTacka().size();j++){
                        if (stav.getTacka().get(j).equals(redniBrojTacke)){

                        }
                    }
                }
                for(TStav stav :akt.getClan().get(clanIndex).getStav()){
                    for (TTacka tacka : stav.getTacka()){
                        if (typeOfOperation.toUpperCase().equals("BRISANJE")){

                        }
                    }
                }
            }
            akt.getClan().remove(clanIndex);
            return true;
        }

        int deoIndex = -1;
        int glavaIndex = -1;
        for (int i = 0; i < akt.getDeo().size();i++){
            TDeo deo = akt.getDeo().get(i);
            for(int j = 0; j < deo.getGlava().size();j++){
                TGlava glava = deo.getGlava().get(j);
                for(int k =0 ; k < glava.getClan().size();k++){
                    if (glava.getClan().get(k).getRedniBroj().equals(redniBrojClana)){
                        deoIndex = i;
                        glavaIndex = j;
                        clanIndex = k;
                        break;
                    }
                }
            }
        }

        if (clanIndex != -1){
            akt.getDeo().get(deoIndex).getGlava().get(glavaIndex).getClan().remove(clanIndex);
            return true;
        }

        for(int j = 0; j < akt.getGlava().size();j++){
            TGlava glava = akt.getGlava().get(j);
            for(int k =0 ; k < glava.getClan().size();k++){
                if (glava.getClan().get(k).getRedniBroj().equals(redniBrojClana)){
                    glavaIndex = j;
                    clanIndex = k;
                    break;
                }
            }
        }
        if (clanIndex != -1){
            akt.getGlava().get(glavaIndex).getClan().remove(clanIndex);
            return true;
        }

        return ret;
    }



    public Document encryptDoc(Akt akt1){
        /*init*/
        EncryptKEK encKEK=new EncryptKEK();
        /*make xml (save to filesystem)*/
        boolean status = convertToXml(akt1);
        System.out.println("Converting to xml status: "+status);
        /*load it from file system*/
        Document doc = encKEK.loadDocument("./data/tmp.xml");
        doc=encKEK.encrypt(doc);
        return doc;
    }


    /**
    * archiving act
     * DO NOT CALL THIS IF AKT IS 'U NACELU'
    *
    * */
    public void archiveIt(Akt a1){

        Document doc = encryptDoc(a1);
        EncryptKEK e=new EncryptKEK();
        e.saveDocument(doc, "ENKRIPTOVAN_TEMP.xml");


       /*take 01
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("bytes", doc);
        map.add("docID", a1.getDocumentId());
        RestTemplate rt2=new RestTemplate();
        String s2 = rt2.postForObject(archive_app_api, map, String.class);
        */

        //take 02
        RestTemplate rt2=new RestTemplate();
        String s2 = rt2.postForObject(archive_app_api, doc, String.class);
        System.out.println(s2);

    }




}