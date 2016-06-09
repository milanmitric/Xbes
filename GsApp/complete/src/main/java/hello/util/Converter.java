package hello.util;

import org.w3c.dom.Node;

import javax.xml.bind.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import java.io.*;

/**
 * Created by milan on 31.5.2016..
 * Support class used for converting object model to xml and contrary.
 */
public class Converter<T> {

    private static TransformerFactory transformerFactory;
    static {
        transformerFactory = TransformerFactory.newInstance();
    }
    /**
     * Converts JAXB bean to XML file on tmp location.
     * @param bean  JAXB bean to be converted.
     * @return Indicator of success.
     */
    public boolean convertToXml(T  bean){
        boolean ret = false;
        File file = new File("tmp.xml");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            JAXBContext jc = JAXBContext.newInstance("hello.entity.gov.gradskaskupstina");
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
     * Converts JAXB bean to XML file on tmp location.
     * @param bean  JAXB bean to be converted.
     * @param filePath  Output file path.
     * @return Indicator of success.
     */
    public boolean convertToXml(T  bean, String filePath){
        boolean ret = false;
        File file = new File(filePath);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            JAXBContext jc = JAXBContext.newInstance("hello.entity.gov.gradskaskupstina");
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
     * Coverts given File to JAXB bean.
     * @param file File to be written.
     * @return Converted bean,<code>null</code> if not not successful.
     */
    public T convertFromXml(File file, Schema schema){
        T ret = null;
        try {
            JAXBContext jc = JAXBContext.newInstance("hello.entity.gov.gradskaskupstina");
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            unmarshaller.setSchema(schema);
            ret = (T) JAXBIntrospector.getValue(unmarshaller.unmarshal(file));
        } catch (Exception e) {
            e.printStackTrace();
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
    public  void transform(Node node, OutputStream out) {
        try {

            // Kreiranje instance objekta zaduzenog za serijalizaciju DOM modela
            Transformer transformer = transformerFactory.newTransformer();

            // Indentacija serijalizovanog izlaza

            // TODO try fix.
            //transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            //transformer.setOutputProperty(OutputKeys.INDENT, "yes");

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

    /**
     * Converts document to input stream.
     * @param node Document to convert.
     * @return Converted input stream. <code>NULL</code> if not successful.
     */
    public InputStream convertDocumentToInputStream(Node node){
        InputStream ret = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Source xmlSource = new DOMSource(node);
            Result outputTarget = new StreamResult(outputStream);
            TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
            ret = new ByteArrayInputStream(outputStream.toByteArray());
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return ret;
    }
    /**
     * Write string content to xml.
     * @param fileContent Content to be written.
     * @return Indicator of succes.
     */
    public boolean writeStringToFile(String fileContent) {
        boolean ret = false;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("tmp.xml");
            fileOutputStream.write(fileContent.getBytes());
            fileOutputStream.close();
            ret = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
}
