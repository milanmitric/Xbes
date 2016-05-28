package hello.businessLogic;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.document.DocumentMetadataPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import com.marklogic.client.io.JAXBHandle;
import hello.util.Database;

import javax.xml.bind.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

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
     * Initializes database client and XML manager.
     */
    public BeanManager() {
        client = Database.getDbClient();
        xmlManager = client.newXMLDocumentManager();
    }

    /**
     * Writes file to database.
     * @param inputStream File to be written.
     * @param docId URI for document to be written.
     * @param colId URI for collection if the docue.
     * @return Indicator of success.
     */
    public boolean write(InputStream inputStream, String docId, String colId) {
        boolean ret = false;
        try{
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
            JAXBElement<T> root= (JAXBElement<T>) u.unmarshal(file);
            ret = root.getValue();
        } catch (JAXBException e) {
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
            Marshaller m = jc.createMarshaller();
            m.marshal(bean,fileOutputStream);
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





}
