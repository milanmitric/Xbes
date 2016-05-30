package hello.businessLogic;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.document.DocumentMetadataPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.JAXBHandle;
import hello.entity.Akt;
import hello.security.SignEnveloped;
import hello.security.VerifySignatureEnveloped;
import hello.util.Database;
import hello.util.MyValidationEventHandler;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.PrivateKey;
import java.security.cert.Certificate;

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

    private SchemaFactory schemaFactory;
    private Schema schema;
    /**
     * Initializes database client and XML manager.
     */
    public BeanManager() {
        try {
            client = Database.getDbClient();
            xmlManager = client.newXMLDocumentManager();
            schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            schema = schemaFactory.newSchema(new File("./schema/Test.xsd"));
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
        boolean ret = false;
        try{
            if (!singXml(null)) {
                throw  new Exception("Could not sign xml, check tmp.xml.");
            }
            InputStreamHandle handle = new InputStreamHandle(inputStream);
            DocumentMetadataHandle metadata = new DocumentMetadataHandle();
            metadata.getCollections().add(colId);
            xmlManager.write(docId,metadata,handle);
            ret = true;
        }
        catch (Exception e){
            System.out.println("Unexpected error: " + e.getMessage());
        } finally{
            return ret;
        }
    }


    /**
     * Writes bean to database.
     * @param bean JAXB bean to be written.
     * @param docId URI for document to be written.
     * @param colId URI for collection if the docue.
     * @return Indicator of success.
     */
    public boolean write(T bean, String docId, String colId) {
        boolean ret = false;
        try {
            // Try to convert to xml on default location.
            if (convertToXml(bean)){
                FileInputStream inputStream = new FileInputStream(new File("tmp.xml"));
                ret = write(inputStream,docId,colId);
            } else {
                throw new Exception("Can't convert JAXB bean " + bean.toString() + " to XML.");
            }
        }
        catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        finally{
            return ret;
        }
    }

    /**
     * Read a xml document from database for given docId. Assignes it to bean field.
     * @param docId Document URI to read from database.
     * @return Read bean, <code>null</code> if not successful.
     */
    public T read(String docId){
        T ret = null;
        try{
            JAXBContext jc = JAXBContext.newInstance("hello.entity");
            JAXBHandle<T> handle = new JAXBHandle<>(jc);
            // A metadata handle for metadata retrieval
            DocumentMetadataHandle metadata = new DocumentMetadataHandle();
            xmlManager.read(docId,metadata,handle);
            ret = handle.get();
            // Convert so that you can validate it.
            convertToXml(ret);
            /*
            if (!validateXML()){
                ret = null;
            }*/
        }
        catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        } finally {
            return ret;
        }
    }

    /**
     * Coverts given File to JAXB bean.
     * @param file File to be written.
     * @return Converted bean,<code>null</code> if not not successful.
     */
    public T convertFromXml(File file){
        T ret = null;
        try {
            JAXBContext jc = JAXBContext.newInstance("hello.entity");
            Unmarshaller u = jc.createUnmarshaller();
            ret = (T) JAXBIntrospector.getValue(u.unmarshal(file));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return ret;
        }
    }

    /**
     * Converts JAXB bean to XML file on tmp location.
     * @return Indicator of success.
     */
    public boolean convertToXml(T  bean){
        boolean ret = false;
        File file = new File("tmp.xml");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            JAXBContext jc = JAXBContext.newInstance("hello.entity");
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.gradskaskupstina.gov/");
            marshaller.marshal(bean,fileOutputStream);
            fileOutputStream.close();
            ret = true;
        } catch (JAXBException e) {
            e.printStackTrace();
        } finally {
            return ret;
        }
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
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("./schema/Test.xsd"));

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
     * Signs xml files currently with example private key and certificate.
     * TODO: Rewrite to accept custom private key, certificate and input file.
     * @param filePath Path to xml to be signed.
     * @return Indicator of success.
     */
    public boolean singXml(String filePath){

        boolean ret = false;

        try{
            SignEnveloped signEnveloped = new SignEnveloped();
            Document document;
            if (filePath == null) {
              document  =signEnveloped.loadDocument("tmp.xml");
            }  else {
                document = signEnveloped.loadDocument(filePath);
            }
            PrivateKey pk = signEnveloped.readPrivateKey();
            Certificate cert = signEnveloped.readCertificate();
            document = signEnveloped.signDocument(document,pk,cert);
            signEnveloped.saveDocument(document,"tmp.xml");
            ret = true;

        } catch (Exception e){
            System.out.println("Unexpected error: " +e.getMessage());
        } finally {
            return  ret;
        }
    }

    public boolean validateXML(){
        boolean ret = false;

        try{
            VerifySignatureEnveloped verifySignatureEnveloped = new VerifySignatureEnveloped();
            Document document = verifySignatureEnveloped.loadDocument("tmp.xml");
            ret =  verifySignatureEnveloped.verifySignature(document);
        } catch(Exception e){
            System.out.println("Unexpected error: " +e.getMessage());
        } finally {
            return  ret;
        }
    }





}
