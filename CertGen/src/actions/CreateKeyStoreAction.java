package actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import app.CreateKeyStoreDialog;
import security.KeyStoreWriter;

@SuppressWarnings("serial")
public class CreateKeyStoreAction extends AbstractAction{

	private CreateKeyStoreDialog diag;
	private KeyStoreWriter keyStoreWritter;
	
	public CreateKeyStoreAction(CreateKeyStoreDialog diag){
		this.diag = diag;
		keyStoreWritter = new KeyStoreWriter();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		initializeKeyStore();
	}
	
	/**
	 * Creates a new keyStore and saves it to keyStore folder.
	 */
	private void initializeKeyStore(){
		String name = diag.getNameOfKeyStore().getText();
		char[] password = diag.getPassword().getText().toCharArray();
		keyStoreWritter.loadKeyStore(null, password);
		keyStoreWritter.saveKeyStore("./keyStore/"+name+".jks", password);
		diag.dispose();
	}

}
