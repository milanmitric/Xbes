package actions;

import java.awt.event.ActionEvent;
import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;

import app.CertificateDialog;
import security.CertificateGenerator;
import security.IssuerData;
import security.SubjectData;

/**
 * Class implements action that generates a certificate from filled input fields.
 * @author milan
 *
 */
@SuppressWarnings("serial")
public class GenerateCertificateAction extends AbstractAction{

	/**
	 * Dialog to fill data.
	 */
	private CertificateDialog diag;
	
	/**
	 * Initializes a new instance of GenerateCertificateAction action.
	 * @param diag Dialog to fill data.
	 */
	public GenerateCertificateAction(CertificateDialog diag) {
		this.diag = diag;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		CertificateGenerator generator = new CertificateGenerator();
		
		KeyPair keyPair = generator.generateKeyPair(); 
		X500NameBuilder builder = generateBuilder();
		// TODO Hardcoded - needs to be fixed.
		String certificateNumber = "1";
		Date startDate = null, endDate = null;
		SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
		try {
			startDate = iso8601Formater.parse(diag.getDateField().getText());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			endDate = iso8601Formater.parse(diag.getEndDate().getText());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		IssuerData issuerData = new IssuerData(keyPair.getPrivate(),builder.build());
		SubjectData subjectData = new SubjectData(keyPair.getPublic(),builder.build(),certificateNumber,startDate, endDate);
		
		X509Certificate cert = generator.generateCertificate(issuerData, subjectData);
		
		System.out.println("ISSUER: " + cert.getIssuerX500Principal().getName());
		System.out.println("SUBJECT: " + cert.getSubjectX500Principal().getName());
		System.out.println("Sertifikat:");
		System.out.println("-------------------------------------------------------");
		System.out.println(cert);
		System.out.println("-------------------------------------------------------");
	    
	}
	
	
	/**
	 * Generate builder used for issuer and subject data.
	 * @return Generated builder.
	 */
	private X500NameBuilder generateBuilder(){
		

		
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, diag.getCommon_name().getText());
	    builder.addRDN(BCStyle.SURNAME, diag.getSurname().getText());
	    builder.addRDN(BCStyle.GIVENNAME, diag.getGiven_name().getText());
	    builder.addRDN(BCStyle.O, diag.getOrganization_name().getText());
	    builder.addRDN(BCStyle.OU, diag.getOrganization_unit().getText());
	    builder.addRDN(BCStyle.C, diag.getCountry_code().getText());
	    builder.addRDN(BCStyle.E, diag.getEmail_address().getText());
	    // TODO Hardcoded - needs to be fixed.
	    builder.addRDN(BCStyle.UID, "123445");
	    return builder;
	}
	
	

}
