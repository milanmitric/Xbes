package hello.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

/**
 * Implementira metodu koja handluje greške nastale prilikom validacije u odnosu
 * na XML schemu tokom unmarshallovanja.
 * 
 */
public class MyValidationEventHandler implements ValidationEventHandler {
	private static final Logger logger = LoggerFactory.getLogger(MyValidationEventHandler.class);

	public boolean handleEvent(ValidationEvent event) {

		// Ako nije u pitanju WARNING metoda vraća false
		if (event.getSeverity() != ValidationEvent.WARNING) {
			ValidationEventLocator validationEventLocator = event.getLocator();
			/*System.out.println("ERROR: Line "
					+ validationEventLocator.getLineNumber() + ": Col"
					+ validationEventLocator.getColumnNumber() + ": "
					+ event.getMessage());*/
			logger.info("ERROR: Line "
					+ validationEventLocator.getLineNumber() + ": Col"
					+ validationEventLocator.getColumnNumber() + ": "
					+ event.getMessage());

			// Dalje izvršavanje se prekida
			return false;
		} else {
			ValidationEventLocator validationEventLocator = event.getLocator();
			/*System.out.println("WARNING: Line "
					+ validationEventLocator.getLineNumber() + ": Col"
					+ validationEventLocator.getColumnNumber() + ": "
					+ event.getMessage());*/
			logger.info("WARNING: Line "
					+ validationEventLocator.getLineNumber() + ": Col"
					+ validationEventLocator.getColumnNumber() + ": "
					+ event.getMessage());

			// Nastavlja se dalje izvršavanje
			return true;
		}
	}

}
