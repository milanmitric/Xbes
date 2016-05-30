package app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import security.KeyStoreReader;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class EnterCertificateInfoDialog extends JDialog {
	
	private static final String KEY_STORE_FILE = "./keyStore/";
	private final JPanel contentPanel = new JPanel();
	private JTextField alias;
	private JPasswordField passwordField;
	
	private KeyStore selectedKeyStore;
	private char[] keyStorePassword;
	private String nameOfFile;
	/**
	 * Create the dialog.
	 */
	public EnterCertificateInfoDialog(KeyStore selectedKeyStore,char[] keyStorePassword,String nameOfFile) {
		this.nameOfFile = nameOfFile;
		this.selectedKeyStore= selectedKeyStore;
		this.keyStorePassword= keyStorePassword;
		setTitle("Enter Certificate Alias and Password");
		setBounds(100, 100, 360, 164);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][][grow]", "[][]"));
		{
			JLabel lblAlias = new JLabel("Alias");
			contentPanel.add(lblAlias, "cell 0 0");
		}
		{
			alias = new JTextField();
			contentPanel.add(alias, "cell 2 0,growx");
			alias.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password");
			contentPanel.add(lblPassword, "cell 0 1");
		}
		{
			passwordField = new JPasswordField();
			contentPanel.add(passwordField, "cell 2 1,growx");
		}
		{
			final EnterCertificateInfoDialog cid = this;
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				final KeyStore selKeyStore = selectedKeyStore;
				final char[]keyStorePass = keyStorePassword;
				final String staticNameOfFile = nameOfFile;
				final char[]certPassword = passwordField.getPassword();
				final EnterCertificateInfoDialog ecid = this;
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						//ucitavamo podatke
						BufferedInputStream in;
						
						try {
							in = new BufferedInputStream(new FileInputStream(KEY_STORE_FILE+staticNameOfFile));
							selKeyStore.load(in, keyStorePass);
							if(selKeyStore.isKeyEntry(alias.getText())) {
								//System.out.println("Sertifikat:");
								Certificate cert = selKeyStore.getCertificate(alias.getText());
								
								ecid.dispose();
								ShowCertificateInformationDialog scid = new ShowCertificateInformationDialog(cert,alias.getText());
								scid.setModal(true);
								scid.setLocationRelativeTo(BaseWindow.getInstance());
								scid.setVisible(true);
									
							}
						} catch (NoSuchAlgorithmException | CertificateException | IOException |  KeyStoreException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						cid.dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						cid.dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

	public JTextField getAlias() {
		return alias;
	}

	public void setAlias(JTextField alias) {
		this.alias = alias;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}

	public KeyStore getSelectedKeyStore() {
		return selectedKeyStore;
	}

	public void setSelectedKeyStore(KeyStore selectedKeyStore) {
		this.selectedKeyStore = selectedKeyStore;
	}

	public char[] getKeyStorePassword() {
		return keyStorePassword;
	}

	public void setKeyStorePassword(char[] keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

}
