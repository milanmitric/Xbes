package hello.businessLogic;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.JAXBHandle;
import hello.security.VerifySignatureEnveloped;
import hello.util.Converter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by milan on 31.5.2016..
 * Class that handles read-associated operation with beans.
 */
public class ReadManager<T>{

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

    public ReadManager(DatabaseClient client, XMLDocumentManager xmlManager, SchemaFactory schemaFactory, Schema schema){
        this.client = client;
        this.xmlManager = xmlManager;
        this.schemaFactory = schemaFactory;
        this.schema = schema;
        converter = new Converter<>();
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


            // Input xml validation.
            /*if (!convertInputToTmp(docId)){
                ret = null;
                throw  new Exception("Can't read from database!");
            }
            // For now. Signature check is not working?
            // TODO: Refactor signature check.

            if (!validateXML("tmp.xml")){
                ret = null;
                throw  new Exception("Input bean signature is not well formated!");
            }*/

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
            System.out.println("Unexpected error: " + e.getMessage());
        } finally {
            return ret;
        }
    }

    /**
     * Validates signed xml document from <b>tmp.xml</b>.
     * @param filepath Path of xml file to be validated.s
     * @return Indicator of success.
     */
    public boolean validateXMLBySignature(String filepath){
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

    /**
     * Read from database and stores to tmp.xml file so it can be validated.
     * @param docId URI of document read from database.
     * @return Indicator of success.
     */
    private boolean convertInputToTmp(String docId){
        boolean ret = false;
        try{
            // A metadata handle for metadata retrieval
            DocumentMetadataHandle metadata = new DocumentMetadataHandle();
            // A handle to receive the document's content.
            DOMHandle content = new DOMHandle();
            xmlManager.read(docId, metadata, content);

            Document doc = content.get();
            FileOutputStream fileOutputStream = new FileOutputStream("tmp.xml");
            transform(doc, fileOutputStream);
            ret = true;

        } catch (Exception e){
            System.out.println("Unexpected error: "+e.getMessage());

        } finally {
            return ret;
        }
    }

    /**
     * Serializes DOM tree to an arbitrary OutputStream.
     *
     * @param node a node to be serialized
     * @param out an output stream to write the serialized
     * DOM representation to
     *
     */
    private static void transform(Node node, OutputStream out) {
        try {

            // Kreiranje instance objekta zaduzenog za serijalizaciju DOM modela
            Transformer transformer = transformerFactory.newTransformer();

            // Indentacija serijalizovanog izlaza
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // Nad "source" objektom (DOM stablo) vrši se transformacija
            DOMSource source = new DOMSource(node);

            // Rezultujući stream (argument metode)
            StreamResult result = new StreamResult(out);

            // Poziv metode koja vrši opisanu transformaciju
            transformer.transform(source, result);

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerFactoryConfigurationError e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
