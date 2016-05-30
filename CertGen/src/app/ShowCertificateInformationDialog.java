package app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;

public class ShowCertificateInformationDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Certificate cert;
	private JTextField surname;
	private JTextField commonName;
	private JTextField organizationUnit;
	private JTextField organizationName;
	private JTextField givenName;
	private JTextField countryCode;
	private JTextField emailAdress;
	private JTextField uid;
	private String alias;
	/**
	 * Create the dialog.
	 */
	public ShowCertificateInformationDialog(Certificate cert,String alias) {
		setTitle("Certificate information");
		this.cert = cert;
		this.alias = alias;
		setBounds(100, 100, 290, 290);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[][][grow]", "[][][][][][][][]"));
		{
			JLabel lblName = new JLabel("Surname");
			contentPanel.add(lblName, "cell 0 0");
		}
		HashMap<String, String> ret = parseDataFromCertificate((X509Certificate)cert);
		{
			surname = new JTextField();
			surname.setEditable(false);
			surname.setText(ret.get("SURNAME"));
			contentPanel.add(surname, "cell 2 0,growx");
			surname.setColumns(10);
		}
		{
			JLabel lblCommonName = new JLabel("Common name");
			contentPanel.add(lblCommonName, "cell 0 1");
		}
		{
			commonName = new JTextField();
			commonName.setEditable(false);
			commonName.setText(ret.get("CN"));
			contentPanel.add(commonName, "cell 2 1,growx");
			commonName.setColumns(10);
		}
		{
			JLabel lblOrganizationUnit = new JLabel("Organization unit");
			contentPanel.add(lblOrganizationUnit, "cell 0 2");
		}
		{
			organizationUnit = new JTextField();
			organizationUnit.setEditable(false);
			organizationUnit.setText(ret.get("OU"));
			contentPanel.add(organizationUnit, "cell 2 2,growx");
			organizationUnit.setColumns(10);
		}
		{
			JLabel lblOrganizationName = new JLabel("Organization name");
			contentPanel.add(lblOrganizationName, "cell 0 3");
		}
		{
			organizationName = new JTextField();
			organizationName.setEditable(false);
			organizationName.setText(ret.get("O"));
			contentPanel.add(organizationName, "cell 2 3,growx");
			organizationName.setColumns(10);
		}
		{
			JLabel lblGivenName = new JLabel("Given name");
			contentPanel.add(lblGivenName, "cell 0 4");
		}
		{
			givenName = new JTextField();
			givenName.setEditable(false);
			givenName.setText(ret.get("GIVENNAME"));
			contentPanel.add(givenName, "cell 2 4,growx");
			givenName.setColumns(10);
		}
		{
			JLabel lblCountryCode = new JLabel("Country code");
			contentPanel.add(lblCountryCode, "cell 0 5");
		}
		{
			countryCode = new JTextField();
			countryCode.setEditable(false);
			countryCode.setText(ret.get("C"));
			contentPanel.add(countryCode, "cell 2 5,growx");
			countryCode.setColumns(10);
		}
		{
			JLabel lblEmailAdress = new JLabel("Email adress");
			contentPanel.add(lblEmailAdress, "cell 0 6");
		}
		{
			emailAdress = new JTextField();
			emailAdress.setEditable(false);
			emailAdress.setText(ret.get("EMAILADDRESS"));
			contentPanel.add(emailAdress, "cell 2 6,growx");
			emailAdress.setColumns(10);
		}
		{
			JLabel lblUid = new JLabel("UID");
			contentPanel.add(lblUid, "cell 0 7");
		}
		{
			uid = new JTextField();
			uid.setEditable(false);
			uid.setText(ret.get("UID"));
			contentPanel.add(uid, "cell 2 7,growx");
			uid.setColumns(10);
		}
		
//		JTextPane textPane = new JTextPane();
//		textPane.setEditable(false);
//		textPane.setText(cert.toString());
//		textPane.setBounds(10, 11, 819, 495);
		
//		contentPanel.add(textPane);
		{	final ShowCertificateInformationDialog that = this;
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			final X509Certificate x509Certificat = (X509Certificate)this.cert;
			
			final String name  = alias;
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						saveCertificate(x509Certificat, name);
						//saveCert(x509Certificat, name);
						that.dispose();
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
						that.dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
	
public HashMap<String, String> parseDataFromCertificate(X509Certificate certificateForParse){
		
		String stringForParse = certificateForParse.toString();
		
		HashMap<String , String> mapOfInformation = new HashMap<>();
		
		String [] arrayForParse = stringForParse.split("Issuer:")[1].split(",");
		String UID = arrayForParse[0].split("=")[1];
		String EMAILADDRESS = arrayForParse[1].split("=")[1];
		String C = arrayForParse[2].split("=")[1];
		String OU = arrayForParse[3].split("=")[1];
		String O = arrayForParse[4].split("=")[1];
		String GIVENNAME = arrayForParse[5].split("=")[1];
		String SURNAME = arrayForParse[6].split("=")[1];
		String CN = arrayForParse[7].split("=")[1].split("\n")[0];
		
		mapOfInformation.put("UID", UID);
		mapOfInformation.put("EMAILADDRESS", EMAILADDRESS);//--
		mapOfInformation.put("C", C);//--
		mapOfInformation.put("OU", OU);//--
		mapOfInformation.put("O", O);//--
		mapOfInformation.put("GIVENNAME", GIVENNAME);//--
		mapOfInformation.put("SURNAME", SURNAME);//--
		mapOfInformation.put("CN", CN);//--

		
		return mapOfInformation;
	}

private void saveCertificate(X509Certificate cert, String alias){
	   
	   byte[] buf;
	  try {
	   buf = cert.getEncoded();
	   FileOutputStream os; 
	   os = new FileOutputStream("./cert/"+alias+".cer"); 
	   os.write(buf);
	   os.close(); 
	  } catch (CertificateEncodingException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  } catch (FileNotFoundException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  } catch (IOException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	  }

public void saveCert(X509Certificate x509Certificat, String ime) {

	try {
		final FileOutputStream os = new FileOutputStream("./gen/" + ime
				+ ".cer");
		os.write("-----BEGIN CERTIFICATE-----\n".getBytes("US-ASCII"));
		os.write(Base64.encodeBase64(x509Certificat.getEncoded(), true));
		os.write("-----END CERTIFICATE-----\n".getBytes("US-ASCII"));
		os.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (CertificateEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}



}
