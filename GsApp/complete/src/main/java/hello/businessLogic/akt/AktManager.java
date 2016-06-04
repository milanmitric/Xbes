package hello.businessLogic.akt;

import com.marklogic.client.document.DocumentDescriptor;
import hello.StringResources.MarkLogicStrings;
import hello.businessLogic.core.BeanManager;
import hello.entity.gov.gradskaskupstina.Akt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

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
     * Proposes an akt.
     * @param akt Bean to be proposed.
     * @return Generated URI. <code>NULL</code> if not successful.
     */
    public DocumentDescriptor proposeAkt(Akt akt){
        DocumentDescriptor ret = null;
        try {
            ret = this.write(akt,MarkLogicStrings.AKTOVI_PREDLOZEN_COL_ID);
        } catch (Exception e) {
            logger.error("[AktManager] Could not propose AKT!");
        }
        return ret;
    }


    /**
     * Approves an akt.
     * @param akt Bean to be approved.
     * @param docId  docID of akt to be deleted from proposed.
     * @return Generated URI. <code>NULL</code> if not successful.
     */
    public DocumentDescriptor approveAkt(Akt akt, String docId){
        DocumentDescriptor ret = null;
        try {
            this.deleteDocument(docId);
        } catch (Exception e) {
            logger.error("[AktManager] Could not delete akt with id [" + docId + "] from proposed!");
        }
        try {
            ret = this.write(akt,MarkLogicStrings.AKTOVI_USVOJENI_COL_ID);
        } catch (Exception e) {
            logger.error("[AktManager] Could not approve akt!");
        }
        return ret;
    }


}
