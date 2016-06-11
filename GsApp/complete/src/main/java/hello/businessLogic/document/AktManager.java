package hello.businessLogic.document;

import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.MatchLocation;
import com.marklogic.client.query.MatchSnippet;
import hello.StringResources.MarkLogicStrings;
import hello.businessLogic.core.BeanManager;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.entity.gov.gradskaskupstina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

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
     * @return List of files in database. <code>NULL</code> if not successful.
     */
    public ArrayList<Akt> getAllFilesProposed(){
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
        query.append(MarkLogicStrings.AKTOVI_PREDLOZEN_COL_ID);
        query.append("\")");
        return queryManager.executeQuery(query.toString());
    }

    /**
     * Proposes an document.
     * @param akt Bean to be proposed.
     * @param user User that proposes Akt, needs to sign it first.
     * @return Generated URI. <code>NULL</code> if not successful.
     */
    public String proposeAkt(Akt akt,User user){

        if (!validateBeanBySchema(akt)){
            logger.info("[AktManager] ERROR: Akt is not valid!");
            return null;
        }
        String ret = null;
        DocumentUriTemplate template = xmlManager.newDocumentUriTemplate("xml");
        try {
            ret = this.write(akt,MarkLogicStrings.AKTOVI_PREDLOZEN_COL_ID).getUri();
            akt.setDocumentId(ret);
            // XML DOCUMENT IS READY TO BE SIGNED!
            boolean shouldSign = true;
            if (!this.write(akt,ret,MarkLogicStrings.AKTOVI_PREDLOZEN_COL_ID, shouldSign,user)){
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
            // XML DOCUMENT IS READY TO BE SIGNED!
            boolean shouldSign = false;
            if (!write(akt,akt.getDocumentId(),MarkLogicStrings.AKTOVI_USVOJENI_COL_ID,shouldSign,null)){
                ret = null;
                throw  new Exception();
            } else {
                ret = akt.getDocumentId();
            }

        } catch (Exception e) {
            logger.info("[ERROR] Could not approve document["+docId+"]!");
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

}
