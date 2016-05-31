package hello.businessLogic;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.document.DocumentMetadataPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import hello.entity.Akt;
import hello.util.Converter;
import hello.util.MyValidationEventHandler;

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

    public CustomManager(DatabaseClient client, XMLDocumentManager xmlManager, SchemaFactory schemaFactory, Schema schema){
        this.client = client;
        this.xmlManager = xmlManager;
        this.schemaFactory = schemaFactory;
        this.schema = schema;
        converter = new Converter<>();
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
     * Validates JAXB bean by <code>schema</code> field of class.
     * @param akt Bean to be validated
     * @return Indicator of success.
     */
    public boolean validateBeanBySchema(T akt){
        boolean ret = false;

        try{
            if (!(akt instanceof Akt)){
                throw  new Exception("Can't validateBeanBySchema element that is not Akt!");
            }
            JAXBContext context = JAXBContext.newInstance("hello.entity");
            Unmarshaller unmarshaller = context.createUnmarshaller();
            converter.convertToXml(akt);
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
     * Validates xml documnt by <code>schema</code> field of class.
     * @param filePath Path of file to be validated.
     * @return Indicator of success.
     */
    public boolean validateXmlBySchema(String filePath){
        boolean ret = false;

        try{
            JAXBContext context = JAXBContext.newInstance("hello.entity");
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // Podešavanje unmarshaller-a za XML schema validaciju
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new MyValidationEventHandler());
            T tmpAkt = (T) JAXBIntrospector.getValue(unmarshaller.unmarshal(new File(filePath)));
            ret = true;
        } catch (Exception e){
            System.out.println("Unexpected error: " +e.getMessage());
        }finally {
            return ret;
        }
    }
}
