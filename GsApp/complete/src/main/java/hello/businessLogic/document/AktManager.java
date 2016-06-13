package hello.businessLogic.document;

import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.MatchLocation;
import com.marklogic.client.query.MatchSnippet;
import hello.StringResources.MarkLogicStrings;
import hello.businessLogic.core.BeanManager;
import hello.entity.gov.gradskaskupstina.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by milan on 4.6.2016..
 * Class that handles all file related operations.
 */
public class AktManager extends BeanManager<Akt> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
     * Proposes an document.
     * @param akt Bean to be proposed.
     * @param user User that proposes Akt, needs to sign it first.
     * @return Generated URI. <code>NULL</code> if not successful.
     */
    public String proposeAkt(Akt akt, User user) {

        if (!validateBeanBySchema(akt)) {
            logger.info("[AktManager] ERROR: Akt is not valid!");
            return null;
        }
        String ret = null;
        DocumentUriTemplate template = xmlManager.newDocumentUriTemplate("xml");
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
     * Approves an document.
     * @param akt Bean to be approved.
     * @return Generated URI. <code>NULL</code> if not successful.
     */
    public String approveAkt(Akt akt){
        if (!validateBeanBySchema(akt)){
            logger.info("[ERROR] Document["+akt.getDocumentId()+"] is not valid!");
            return null;
        }
        String ret = null;
        String docId = akt.getDocumentId();
        try {
            if (docId == null ||docId.isEmpty()){
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
        boolean ret = false;

        //akt.setSignature(null);
        //this.write(akt,akt.getDocumentId(),MarkLogicStrings.AKTOVI_PRIMENJENI_COL_ID,false,null);

        for (Amandmani amandmani: listaAmandmana){
            for (TAmandman amandman : amandmani.getAmandman()){
                String tmp = amandman.getSadrzaj();
                String query = generateXquery(amandman.getPredmetIzmene(),amandman.getTipIzmene().value(),amandman.getSadrzaj(), "2746325830753861621.xml");
                this.executeQuery(query);
            }
        }
        ret = true;
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

        builder.append("declare namespace a=\"http://www.gradskaskupstina.gov/\";");
        builder.append("let $x := doc(\"" + docId + "\")");
        if (type.toUpperCase().equals("BRISANJE")){
            builder.append("return xdmp:node-delete($x/a:Akt//a:Clan[@RedniBroj=\"" + redniBrojClana +"\"]");
            if(redniBrojStava != null)
                builder.append("/a:Stav[@RedniBroj = \"" + redniBrojStava + "\"]");
            if(redniBrojTacke != null)
                builder.append("/a:Tacka[@RedniBroj = \"" + redniBrojTacke + "\"]");
            if(redniBrojPodtacke != null)
                builder.append("/a:Podtacka[@RedniBroj = \"" + redniBrojPodtacke + "\"]");
            builder.append(")");
        } else if (type.toUpperCase().equals("IZMENA")) {
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
        } else if (type.toUpperCase().equals("DOPUNA")){

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
        }

        builder.append(",");
        builder.append(tekst);
        builder.append(")");
        return builder.toString();
    }

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
}
