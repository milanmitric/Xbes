package hello.util;

import javax.xml.bind.*;
import javax.xml.validation.Schema;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by milan on 31.5.2016..
 * Support class used for converting object model to xml and contrary.
 */
public class Converter<T> {

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
}
