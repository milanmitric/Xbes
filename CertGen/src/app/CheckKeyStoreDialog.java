package app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.X509Name;

import actions.LoadKeyStoreAction;
import net.miginfocom.swing.MigLayout;
import security.IssuerData;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class CheckKeyStoreDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField;
	private JDialog parentDialog;
	private String fileName;
	
	/**
	 * 0 - for keyStore
	 * 1 - for root certificate
	 */
	private int indicator;
	/**
	 * Create the dialog.
	 */
	public CheckKeyStoreDialog(String fileName, JDialog parentDialog,String title,int indicator) {
		this.indicator = indicator;
		this.fileName = fileName;
		this.parentDialog = parentDialog;
		setTitle(title);
		setBounds(100, 100, 316, 168);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][grow]", "[][]"));
		{
			JLabel lblPassword = new JLabel("Password");
			contentPanel.add(lblPassword, "cell 0 1,alignx trailing");
		}
		{
			passwordField = new JPasswordField();
			contentPanel.add(passwordField, "cell 1 1,growx");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				
				final CheckKeyStoreDialog that = this;
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				if(indicator == 0){
					okButton.addActionListener(new LoadKeyStoreAction(this, parentDialog, passwordField, fileName));
				} else if (indicator == 1){
					final CertificateDialog parentDialogFinal = (CertificateDialog)parentDialog;
					final String alias = fileName;
					okButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								KeyStore keyStore = KeyStore.getInstance("JKS","SUN");
								FileInputStream file = new FileInputStream("./keyStore/"+parentDialogFinal.getKeyStore().getSelectedItem().toString());
								keyStore.load(file, parentDialogFinal.getKeyStorePassword());
								
								if(keyStore.isKeyEntry(alias)) {
									java.security.cert.Certificate cert =  keyStore.getCertificate(alias);
									
									X509Certificate cert509 = (X509Certificate)cert;
									//System.out.println(cert509.getIssuerX500Principal().getName());
									parentDialogFinal.setRootNameBuilder(parseDataFromCertificate(cert509));
									PrivateKey privKey = (PrivateKey)keyStore.getKey(alias, passwordField.getPassword());
									parentDialogFinal.setRootCertificatePrivateKey(privKey);
									that.dispose();
								} else {
									throw new Exception();
								}
							}
							catch(Exception exp){
								JOptionPane.showMessageDialog(that, "Could not get root certificate!", "Error", JOptionPane.ERROR_MESSAGE);
								parentDialogFinal.getCa().setSelectedIndex(0);
								System.out.println(exp.getMessage());
							}
							
						}
					});
				}
				
				buttonPane.add(okButton);
				
				getRootPane().setDefaultButton(okButton);
			}
			{
				final CheckKeyStoreDialog that = this;
				 
				if(parentDialog instanceof CertificateDialog){
					final CertificateDialog thatParentDialog = (CertificateDialog)parentDialog;
					JButton cancelButton = new JButton("Cancel");
					cancelButton.setActionCommand("Cancel");
					cancelButton.addActionListener(new ActionListener() {
					
					final int indicatorFinal = CheckKeyStoreDialog.this.indicator;
						@Override
						public void actionPerformed(ActionEvent e) {
							if(thatParentDialog instanceof CertificateDialog){
								if (indicatorFinal == 0) {
									((CertificateDialog)thatParentDialog).getKeyStore().setSelectedIndex(0);
								} else 
								{
									((CertificateDialog)thatParentDialog).getKeyStore().setSelectedIndex(0);	
								}
								
							}
							that.dispose();
						}
					});
					buttonPane.add(cancelButton);
				}else{
					final ShowKeyStoresDialog thatParentDialog = (ShowKeyStoresDialog)parentDialog;
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if(thatParentDialog instanceof ShowKeyStoresDialog){
							((ShowKeyStoresDialog)thatParentDialog).getKeyStoresFromFileSystem().setSelectedIndex(0);
						}
						that.dispose();
					}
				});
				buttonPane.add(cancelButton);
				}
			}
		}
	}



	public JPasswordField getPasswordField() {
		return passwordField;
	}



	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}
	/**
	 * Method for parsing Certificate and getting Issuer data.
	 * @return Issuer 
	 * */
	public X500NameBuilder parseDataFromCertificate(X509Certificate certificateForParse){
		
		String stringForParse = certificateForParse.toString();

		String [] arrayForParse = stringForParse.split("Issuer:")[1].split(",");
		String UID = arrayForParse[0].split("=")[1];
		String EMAILADDRESS = arrayForParse[1].split("=")[1];
		String C = arrayForParse[2].split("=")[1];
		String OU = arrayForParse[3].split("=")[1];
		String O = arrayForParse[4].split("=")[1];
		String GIVENNAME = arrayForParse[5].split("=")[1];
		String SURNAME = arrayForParse[6].split("=")[1];
		String CN = arrayForParse[7].split("=")[1].split("\n")[0];
		
//		System.out.println(UID);
//		System.out.println(EMAILADDRESS);
//		System.out.println(C);
//		System.out.println(OU);
//		System.out.println(O);
//		System.out.println(GIVENNAME);
//		System.out.println(SURNAME);
//		System.out.println(CN);
		
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, CN);
	    builder.addRDN(BCStyle.SURNAME, SURNAME);
	    builder.addRDN(BCStyle.GIVENNAME, GIVENNAME);
	    builder.addRDN(BCStyle.O, O);
	    builder.addRDN(BCStyle.OU, OU);
	    builder.addRDN(BCStyle.C, C);
	    builder.addRDN(BCStyle.E, EMAILADDRESS);
	    builder.addRDN(BCStyle.UID, UID);
		
		return builder;
	}
	
}
