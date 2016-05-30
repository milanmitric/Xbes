package hello.businessLogic;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.document.DocumentMetadataPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import hello.entity.Akt;
import hello.security.VerifySignatureEnveloped;
import hello.util.Converter;
import hello.util.Database;
import hello.util.MyValidationEventHandler;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by milan on 27.5.2016..
 * Class used for CRUD operations on bean classes.
 */
public class BeanManager <T>{


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
     * Initializes database client and XML manager.
     */
    public BeanManager() {
        try {
            client = Database.getDbClient();
            xmlManager = client.newXMLDocumentManager();
            schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            schema = schemaFactory.newSchema(new File("./schema/Test.xsd"));
            readManager = new ReadManager<T>(client,xmlManager,schemaFactory,schema);
            writeManager = new WriteManager<>(client,xmlManager,schemaFactory,schema);
            converter = new Converter<>();
        } catch (Exception e){
            System.out.println("Can't initialize Bean manager.");
        }
    }

    /**
     * Initializes database client and XML manager.
     * @param schemaFilePath Schema to validate xmls from.
     */
    public BeanManager(String schemaFilePath) {
        try {
            client = Database.getDbClient();
            xmlManager = client.newXMLDocumentManager();
            schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            schema = schemaFactory.newSchema(new File(schemaFilePath));
            readManager = new ReadManager<T>(client,xmlManager,schemaFactory,schema);
            writeManager = new WriteManager<>(client,xmlManager,schemaFactory,schema);
            converter = new Converter<>();
        } catch (Exception e){
            System.out.println("Can't initialize Bean manager.");
        }
    }

    /**
     * Writes file to database.
     * @param inputStream File to be written.
     * @param docId URI for document to be written.
     * @param colId URI for collection if the docue.
     * @return Indicator of success.
     */
    public boolean write(FileInputStream inputStream, String docId, String colId) {
        return  writeManager.write(inputStream,docId,colId);
    }


    /**
     * Writes bean to database.
     * @param bean JAXB bean to be written.
     * @param docId URI for document to be written.
     * @param colId URI for collection if the docue.
     * @return Indicator of success.
     */
    public boolean write(T bean, String docId, String colId) {
        return writeManager.write(bean,docId,colId);
    }

    /**
     * Read a xml document from database for given docId. Assignes it to bean field.
     * @param docId Document URI to read from database.
     * @return Read bean, <code>null</code> if not successful.
     */
    public T read(String docId){
        return readManager.read(docId);
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
            e.printStackTrace();
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
            e.printStackTrace();
        } finally {
            return  ret;
        }
    }

    /**
     * Validates JAXB bean.
     * @param akt Bean to be validated
     * @return Indicator of success.
     */
    public boolean validate(T akt){
        boolean ret = false;

        try{
            if (!(akt instanceof Akt)){
                throw  new Exception("Can't validate element that is not Akt!");
            }
            JAXBContext context = JAXBContext.newInstance("hello.entity");
            Unmarshaller unmarshaller = context.createUnmarshaller();
            convertToXml(akt);
            // Podešavanje unmarshaller-a za XML schema validaciju
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new MyValidationEventHandler());
            T tmpAkt = (T) JAXBIntrospector.getValue(unmarshaller.unmarshal(new File("tmp.xml")));
            ret = true;
        } catch (Exception e){
            System.out.println("Unexpected error: " +e.getMessage());
        }finally {
            return ret;
        }
    }

    /**
     * Validates signed xml document from <b>tmp.xml</b>.
     * @param filepath Path of xml file to be validated.s
     * @return Indicator of success.
     */
    private boolean validateXML(String filepath){
        boolean ret = false;

        try{
            VerifySignatureEnveloped verifySignatureEnveloped = new VerifySignatureEnveloped();
            Document document = verifySignatureEnveloped.loadDocument(filepath);
            ret =  verifySignatureEnveloped.verifySignature(document);
        } catch(Exception e){
            System.out.println("Unexpected error: " +e.getMessage());
        } finally {
            return  ret;
        }
    }








}
