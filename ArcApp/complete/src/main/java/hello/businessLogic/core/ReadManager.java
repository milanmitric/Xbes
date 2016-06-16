package hello.businessLogic.core;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.JAXBHandle;
import hello.security.VerifySignatureEnveloped;
import hello.util.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

/**
 * Created by milan on 31.5.2016..
 * Class that handles read-associated operation with beans.
 */
public class ReadManager<T>{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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



    /**
     * Support class for xml-bean conversion.
     */
    private Converter<T> converter;



    public ReadManager(){

    }
    public ReadManager(DatabaseClient client, XMLDocumentManager xmlManager, SchemaFactory schemaFactory, Schema schema, Converter converter){
        this.client = client;
        this.xmlManager = xmlManager;
        this.schemaFactory = schemaFactory;
        this.schema = schema;
        this.converter = converter;
    }

    /**
     * Reads a xml document from database for given docId. Assignes it to bean field.
     * @param docId Document URI to read from database.
     * @param shouldValidate Indicator whether xml document should be validated by digital signature.
     * @return Read bean, <code>null</code> if not successful.
     */
    public T read(String docId,boolean shouldValidate){
        T ret = null;
        try{
            JAXBContext jc = JAXBContext.newInstance("hello.entity.gov.gradskaskupstina");
            JAXBHandle<T> handle = new JAXBHandle<>(jc);


            if (shouldValidate){
                // Input xml validation.
                if (!validateXMLBySignature(docId)){
                    ret = null;
                    throw  new Exception("Input bean signature is not well formated!");
                }
            }

            // A metadata handle for metadata retrieval
            DocumentMetadataHandle metadata = new DocumentMetadataHandle();
            // Read real data when it is validated.
            xmlManager.read(docId,metadata,handle);

            ret = handle.get();
            // Convert bean to tmp.xml so that you can validateBeanBySchema it.
            if (!converter.convertToXml(ret)){
                ret = null;
                throw  new Exception("Could not convert bean to xml!");
            }
        }
        catch (Exception e) {
            logger.info("Could not read database with URI "+ docId + ".");
            logger.info("[ERROR] " + e.getMessage());
            logger.info("[STACK TRACE] " + e.getStackTrace());
        } finally {
            return ret;
        }
    }

    /**
     * Reads a xml document from database for given docId. Return read document.
     * @param shouldValidate Indicator whether xml document should be validated by digital signature.
     * @param docId ocument URI to read from database.
     * @return Read document. <code>NULL</code> if not successful.
     */
    public Document read(boolean shouldValidate,String docId){
        Document ret = null;
        // A metadata handle for metadata retrieval
        DocumentMetadataHandle metadata = new DocumentMetadataHandle();
        // A handle to receive the document's content.
        DOMHandle content = new DOMHandle();
        xmlManager.read(docId, metadata, content);

        ret = content.get();
        if (shouldValidate){
            VerifySignatureEnveloped verifySignatureEnveloped = new VerifySignatureEnveloped();
            if (!verifySignatureEnveloped.verifySignature(ret)){
                logger.info("[ERROR] Document is not valid by signature!");
                ret = null;
            }
        }
        return ret;
    }
    /**
     * Validates signed xml document from database,
     * @param docId Document id of file to be validated.
     * @return Indicator of success.
     */
    public boolean validateXMLBySignature(String docId){
        boolean ret = false;
        try{
            // A metadata handle for metadata retrieval
            DocumentMetadataHandle metadata = new DocumentMetadataHandle();
            // A handle to receive the document's content.
            DOMHandle content = new DOMHandle();
            xmlManager.read(docId, metadata, content);

            Document doc = content.get();
            VerifySignatureEnveloped verifySignatureEnveloped = new VerifySignatureEnveloped();
            ret =  verifySignatureEnveloped.verifySignature(doc);
        } catch (Exception e){
            logger.info("Could not read xml["+ docId+ "] and validate!");
            logger.info("[ERROR] " + e.getMessage());
            logger.info("[STACK TRACE] " + e.getStackTrace());
        } finally {
            return ret;
        }
    }


}