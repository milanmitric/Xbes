package actions;

import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import app.CertificateDialog;

public class LoadKeyStoreAction extends AbstractAction{

	private JPasswordField password;
	private String fileName;
	private CertificateDialog parentDialog;
	private JDialog panel;
	
	public LoadKeyStoreAction(JDialog panel,CertificateDialog parentDialog, JPasswordField password, String fileName) {
		this.password = password;
		this.parentDialog= parentDialog;
		this.fileName = fileName;
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS","SUN");
			FileInputStream file = new FileInputStream("./keyStore/"+fileName);
			keyStore.load(file, password.getPassword());
			parentDialog.setSelectedKeyStore(keyStore);
			parentDialog.setKeyStorePassword(password.getPassword());
			panel.dispose();
		} catch (Exception exp) {
			JOptionPane.showMessageDialog(panel, "Could not open keyStore!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
