package hello.businessLogic.core;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.document.DocumentDescriptor;
import com.marklogic.client.document.DocumentMetadataPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.query.MatchDocumentSummary;
import hello.entity.gov.gradskaskupstina.User;
import hello.security.KeyStoreManager;
import hello.util.Converter;
import hello.util.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by milan on 27.5.2016..
 * Class used for CRUD operations on bean classes.
 */
public class BeanManager <T>{


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Default docId for CRUD operations.
     */
    private final String DOC_ID_DEFAULT = "default/document";

    /**
     * Default colId for CRUD operations.
     */
    private final String COL_ID_DEFAULT = "default/collection";

    /**
     * Database client to communicate with MarkLogic database.
     */
    private DatabaseClient client;
    /**
     * Manages CRUD operations on XML documents and JAXB beans..
     */
    protected XMLDocumentManager xmlManager;

    /**
     * Schema factory for creating validation schemas.
     */
    private SchemaFactory schemaFactory;

    /**
     * Default schema located in <b>"./schema/Akt.xsd"</b>
     */
    private Schema schema;

    /**
     * Encapsulates all read-related operations.
     */
    private ReadManager<T> readManager;

    /**
     * Encapsulates all write-related operations.
     */
    private WriteManager<T> writeManager;

    /**
     * Support class for xml-bean conversion.
     */
    private Converter<T> converter;

    /**
     * Encapsulates all update, delete operations and all validations.
     */
    protected CustomManager<T> customManager;

    /**
     * Query executing manager.
     */
    protected QueryManager queryManager;

    /**
     * Used for creating certificates.
     */
    protected KeyStoreManager keyStoreManager;
    /**
     * Initializes database client and XML manager.
     */
    public BeanManager() {
        try {
            client = Database.getDbClient();
            xmlManager = client.newXMLDocumentManager();
            schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            schema = schemaFactory.newSchema(new File("schema/Akt.xsd"));
            converter = new Converter<>();
            readManager = new ReadManager<T>(client,xmlManager,schemaFactory,schema,converter);
            writeManager = new WriteManager<>(client,xmlManager,schemaFactory,schema,converter);
            customManager = new CustomManager<>(client,xmlManager,schemaFactory,schema,converter);
            queryManager = new QueryManager(client, schema,converter);
            keyStoreManager = new KeyStoreManager();

        } catch (Exception e){
            logger.info("[ERROR] Can't initialize.");
            e.printStackTrace();
        }
    }

    /**
     * Initializes database client and XML manager.
     * @param schemaFilePath Schema to validateBeanBySchema xmls from.
     */
    public BeanManager(String schemaFilePath) {
        try {
            client = Database.getDbClient();
            xmlManager = client.newXMLDocumentManager();
            schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            schema = schemaFactory.newSchema(new File(schemaFilePath));
            converter = new Converter<>();
            readManager = new ReadManager<T>(client,xmlManager,schemaFactory,schema,converter);
            writeManager = new WriteManager<>(client,xmlManager,schemaFactory,schema,converter);
            customManager = new CustomManager<>(client,xmlManager,schemaFactory,schema,converter);
            queryManager = new QueryManager(client,schema, converter);
            keyStoreManager = new KeyStoreManager();
        } catch (Exception e){
            logger.info("[ERROR] Can't initialize.");
        }
    }

    /**
     * Writes file to database.
     * @param inputStream File to be written.
     * @param docId URI for document to be written.
     * @param colId URI for collection if the docue.
     * @param shouldSign  Indicator whether xml should be signed.
     * @param user User that proposes Akt, needs to sign it first.
     * @return Indicator of success.
     */
    public boolean write(InputStream inputStream, String docId, String colId, boolean shouldSign, User user) {
        return  writeManager.write(inputStream,docId,colId,shouldSign,user);
    }

    /**
     * Writes file to database with template docId.
     * @param inputStream File to be written.
     * @param colId URI for collection if the docue.
     * @return Generated URI. <code>NULL</code> if not successful.
     */
    protected DocumentDescriptor write(InputStream inputStream, String colId) {
        return writeManager.write(inputStream,colId);
    }

    /**
     * Writes bean to database.
     * @param bean JAXB bean to be written.
     * @param docId URI for document to be written.
     * @param colId URI for collection if the docue.
     * @param shouldSign  Indicator whether xml should be signed.
     * @param user User that proposes Akt, needs to sign it first.
     * @return Indicator of success.
     */
    public boolean write(T bean, String docId, String colId,boolean shouldSign,User user) {
        return writeManager.write(bean,docId,colId,shouldSign,user);
    }

    /**
     * Writes bean to database with template docId.
     * @param bean JAXB bean to be written.
     * @param colId URI for collection if the docue.
     * @return Generated URI. <code>NULL</code> if not successful.
     */
    protected DocumentDescriptor write(T bean,String colId) {
        if (customManager.validateBeanBySchema(bean)){
            logger.info("Successfully validated bean!");
            return writeManager.write(bean,colId);
        } else {
            logger.info("[ERROR] Could not validate bean!");
            return null;
        }

    }

    /**
     * Read a xml document from database for given docId. Assignes it to bean field.
     * @param docId Document URI to read from database.
     * @param shouldValidate Indicator whether xml document should be validated by digital signature.
     * @return Read bean, <code>null</code> if not successful.
     */
    public T read(String docId,boolean shouldValidate){
        return readManager.read(docId,shouldValidate);
    }
    /**
     * Reads a xml document from database for given docId. Return read document.
     * @param shouldValidate Indicator whether xml document should be validated by digital signature.
     * @param docId ocument URI to read from database.
     * @return Read document. <code>NULL</code> if not successful.
     */
    public Document read(boolean shouldValidate, String docId){
        return readManager.read(shouldValidate,docId);
    }

    /**
     * Coverts given File to JAXB bean.
     * @param file File to be written.
     * @return Converted bean,<code>null</code> if not not successful.
     */
    public T convertFromXml(File file){
        return converter.convertFromXml(file,schema);
    }

    /**
     * Converts JAXB bean to XML file on tmp location.
     * @return Indicator of success.
     */
    public boolean convertToXml(T  bean){
       return converter.convertToXml(bean);
    }


    /**
     * Converts JAXB bean to XML file on awesome, cool, supercool location.
     * @return Indicator of success.
     */
    public boolean convertToXml_withCustomName(T  bean, String customFileName){
        return converter.convertToXml_withCustomName(bean, customFileName);
    }


    /**
     * Deletes a document from given URI.
     * @param docId URI of document to be deleted.
     * @return Indicator of success.
     */
    protected boolean deleteDocument(String docId){
        return  customManager.deleteDocument(docId);
    }

    /**
     * Updates document from given URI and patchHandle
     * @param docId URI of document to be deleted.
     * @param patchHandle Contains updates on document using XPath, positions to be inserted and patches as Strings.
     * @return Indicator of success.
     */
    protected boolean updateDocument(String docId, DocumentMetadataPatchBuilder.PatchHandle patchHandle){
       return customManager.updateDocument(docId,patchHandle);
    }

    /**
     * Validates JAXB bean by <code>schema</code> field of class.
     * @return Indicator of success.
     */
    public boolean validateBeanBySchema(T bean){
        return customManager.validateBeanBySchema(bean);
    }

    /**
     * Validates xml document by <code>schema</code> field of class.
     * @param filePath Path of file to be validated.
     * @return Indicator of success.
     */
    public boolean validateXmlBySchema(String filePath){
        return customManager.validateXmlBySchema(filePath);
    }

    /**
     * Validates signed xml document database.
     * @param docId Document URI of file to be validated.
     * @return Indicator of success.
     */
    public boolean validateXMLBySignature(String docId){
        return readManager.validateXMLBySignature(docId);
    }

    /**
     * Executes given query.
     * @param query String representation of XQuery.
     * @return Interpreted results. <code>NULL</code> if error occurs.
     */
    public ArrayList<T> executeQuery(String query){
        return queryManager.executeQuery(query);
    }

    /**
     * Generates certificate and saves it to file. Sets alias as users' username and password as users' password.
     * @param user User infos needed for certificate.
     * @return Indicator of success.
     */
    protected  boolean generateCertificate(User user) {
        return keyStoreManager.generateCertificate(user);
    }

    /**
     * Converts document to input stream.
     * @param node Document to convert.
     * @return Converted input stream. <code>NULL</code> if not successful.
     */
    public InputStream convertDocumentToInputStream(Node node){
        return converter.convertDocumentToInputStream(node);
    }
    /**
     * Return document with search results
     * @param parameterofSearch search parametar
     * @param uriOfCollection id of collection
     * @return Document with search results
     */
    protected MatchDocumentSummary[] searchByField(String parameterofSearch, String uriOfCollection){
        return customManager.searchByField(parameterofSearch,uriOfCollection);
    }

    public  void transform(Node node, OutputStream out) {
        converter.transform(node,out);
    }
}
