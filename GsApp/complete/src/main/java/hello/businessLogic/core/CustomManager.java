package hello.businessLogic.core;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.document.DocumentMetadataPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.entity.gov.gradskaskupstina.Amandmani;
import hello.entity.gov.gradskaskupstina.Users;
import hello.util.Converter;
import hello.util.MyValidationEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.TransformerFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

/**
 * Created by milan on 31.5.2016..
 * Class that handles all validations and update and delete operations.
 */
public class CustomManager <T>{
    private static final Logger logger = LoggerFactory.getLogger(CustomManager.class);
    /**
     * Manages CRUD operations on XML documents and JAXB beans..
     */
    private XMLDocumentManager xmlManager;

    /**
     * Schema factory for creating validation schemas.
     */
    private SchemaFactory schemaFactory;

    /**
     * Default schema located in <b>"./schema/Test.xsd"</b>
     */
    private Schema schema;

    /**
     * Database client to communicate with MarkLogic database.
     */
    private DatabaseClient client;

    private static TransformerFactory transformerFactory;

    /**
     * Support class for xml-bean conversion.
     */
    private Converter<T> converter;

    static {
        transformerFactory = TransformerFactory.newInstance();
    }

    public CustomManager(DatabaseClient client, XMLDocumentManager xmlManager, SchemaFactory schemaFactory, Schema schema, Converter converter){
        this.client = client;
        this.xmlManager = xmlManager;
        this.schemaFactory = schemaFactory;
        this.schema = schema;
        this.converter = converter;
    }

    /**
     * Deletes a document from given URI.
     * @param docId URI of document to be deleted.
     * @return Indicator of success.
     */
    public boolean deleteDocument(String docId){
        boolean ret = false;
        try {
            xmlManager.delete(docId);
            ret = true;
        }catch (Exception e){
            logger.info("Can't delete file[" + docId + "]");
            logger.info("[ERROR] " + e.getMessage());
            logger.info("[STACK TRACE] " + e.getStackTrace());
        } finally {
            return  ret;
        }
    }

    /**
     * Updates document from given URI and patchHandle
     * @param docId URI of document to be deleted.
     * @param patchHandle Contains updates on document using XPath, positions to be inserted and patches as Strings.
     * @return Indicator of success.
     */
    public boolean updateDocument(String docId, DocumentMetadataPatchBuilder.PatchHandle patchHandle){
        boolean ret = false;
        try{
            xmlManager.patch(docId,patchHandle);
            ret = true;
        } catch (Exception e){
            logger.info("Can't update file[" + docId + "]");
            logger.info("[ERROR] " + e.getMessage());
            logger.info("[STACK TRACE] " + e.getStackTrace());
        } finally {
            return  ret;
        }
    }

    /**
     * Validates JAXB bean by <code>schema</code> field of class.
     * @param bean Bean to be validated
     * @return Indicator of success.
     */
    public boolean validateBeanBySchema(T bean){
        boolean ret = false;

        try{
            if (!(bean instanceof Akt) && !(bean instanceof Amandmani) && !(bean instanceof Users)){
                throw  new Exception("Can't validateBeanBySchema element that is not Akt or Amandman or Users!");
            }
            JAXBContext context = JAXBContext.newInstance("hello.entity.gov.gradskaskupstina");
            Unmarshaller unmarshaller = context.createUnmarshaller();
            converter.convertToXml(bean);
            // Podešavanje unmarshaller-a za XML schema validaciju
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new MyValidationEventHandler());
            T tmpAkt = (T) JAXBIntrospector.getValue(unmarshaller.unmarshal(new File("tmp.xml")));
            ret = true;
        } catch (Exception e){
            logger.info("Can't validate bean!");
            logger.info("[ERROR] " + e.getMessage());
            logger.info("[STACK TRACE] " + e.getStackTrace());
        }finally {
            return ret;
        }
    }

    /**
     * Validates xml documnt by <code>schema</code> field of class.
     * @param filePath Path of file to be validated.
     * @return Indicator of success.
     */
    public boolean validateXmlBySchema(String filePath){
        boolean ret = false;

        try{
            JAXBContext context = JAXBContext.newInstance("hello.entity.gov.gradskaskupstina");
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // Podešavanje unmarshaller-a za XML schema validaciju
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new MyValidationEventHandler());
            T tmpAkt = (T) JAXBIntrospector.getValue(unmarshaller.unmarshal(new File(filePath)));
            ret = true;
        } catch (Exception e){
            logger.info("Can't validate bean on path " + filePath + ".");
            logger.info("[ERROR] " + e.getMessage());
            logger.info("[STACK TRACE] " + e.getStackTrace());
        }finally {
            return ret;
        }
    }
    /**
     * Searches xml documents from collection for given parameters
     * @param parameterOfSearch string with query for searching collection
     * */
    public MatchDocumentSummary[] searchByField(String parameterOfSearch, String uriOfCollection){

        // Initialize query manager
        QueryManager queryManager = client.newQueryManager();

        // Query definition is used to specify Google-style query string
        StringQueryDefinition queryDefinition = queryManager.newStringDefinition();

        // Set the criteria
        queryDefinition.setCriteria(parameterOfSearch);

        // Search within a specific collection
        queryDefinition.setCollections(uriOfCollection);

        // Perform search
        SearchHandle results = queryManager.search(queryDefinition, new SearchHandle());

        // Serialize search results to the standard output
        MatchDocumentSummary matches[] = results.getMatchResults();

        return matches;
    }

}
