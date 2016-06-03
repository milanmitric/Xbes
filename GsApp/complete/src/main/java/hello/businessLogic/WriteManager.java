package hello.businessLogic;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import hello.security.SignEnveloped;
import hello.util.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileInputStream;
import java.security.PrivateKey;
import java.security.cert.Certificate;

/**
 * Created by milan on 31.5.2016..
 * Class that handles write-associated operation with beans.
 */
public class WriteManager <T>{
    private static final Logger logger = LoggerFactory.getLogger(WriteManager.class);
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

    private Converter<T> converter;

    public WriteManager(DatabaseClient client, XMLDocumentManager xmlManager, SchemaFactory schemaFactory, Schema schema){
        this.client = client;
        this.xmlManager = xmlManager;
        this.schemaFactory = schemaFactory;
        this.schema = schema;
        converter = new Converter<>();
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
            //if (!singXml(null)) {
            //    throw  new Exception("Could not sign xml, check tmp.xml.");
            //}
            InputStreamHandle handle = new InputStreamHandle(inputStream);
            DocumentMetadataHandle metadata = new DocumentMetadataHandle();
            metadata.getCollections().add(colId);
            xmlManager.write(docId,metadata,handle);
            ret = true;
        }
        catch (Exception e){
            logger.info("Unexpected error: " + e.getMessage());
            //System.out.println("Unexpected error: " + e.getMessage());
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
            if (converter.convertToXml(bean)){
                FileInputStream inputStream = new FileInputStream(new File("tmp.xml"));
                ret = write(inputStream,docId,colId);
            } else {
                throw new Exception("Can't convert JAXB bean " + bean.toString() + " to XML.");
            }
        }
        catch (Exception e) {
            logger.info("ERROR: Unexpected error: " + e.getMessage());
            //System.out.println("Unexpected error: " + e.getMessage());
        }
        finally{
            return ret;
        }
    }

    /**
     * Signs xml files currently with example private key and certificate.
     * TODO: Rewrite to accept custom private key, certificate and input file.
     * @param filePath Path to xml to be signed.
     * @return Indicator of success.
     */
    private boolean singXml(String filePath){

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
}
