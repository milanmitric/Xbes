import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import gov.gradskaskupstina.TAkt;

public class Test {

	public static void main(String[] args) throws JAXBException {
		
		try
		{
			File file = new File("data\\instance1.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(TAkt.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			Source source = new StreamSource(file);
			JAXBElement<TAkt> root = unmarshaller.unmarshal(source, TAkt.class);
			TAkt akt = root.getValue();
			System.out.println(akt.getNaslov());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
