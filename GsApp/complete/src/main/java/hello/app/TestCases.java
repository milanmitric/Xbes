package hello.app;

import hello.entity.gov.gradskaskupstina.User;
import hello.security.CRLVerifier;
import hello.security.KeyStoreManager;
import hello.security.VerifySignatureEnveloped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import java.io.FileNotFoundException;
import java.security.cert.Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by milan on 17.6.2016..
 */
public class TestCases {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public static void main(String[] args) throws FileNotFoundException {
        try {
            //testRevokedCertificate();
            //testExpiredCertificate();
            //testWrongSignature();
            testUnsigned();
        } catch (Exception e)
        {

        }
    }
    public static  void testRevokedCertificate() throws ParseException {

        CRLVerifier crlVerifier = new CRLVerifier();

        KeyStoreManager manager = new KeyStoreManager();
        manager.reinitializeKeyStore();
        User user = new User();
        user.setUsername("a");
        user.setPassword("a");
        user.setIme("a");
        user.setPrezime("a");
        user.setEmail("a");

        //datumi
        SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = iso8601Formater.parse("2007-12-31");
        Date endDate = iso8601Formater.parse("2010-12-31");

        try {
            if (!manager.generateCertificate(user,"10",startDate,endDate)){
                throw new Exception("Could not generate certificate.");
            }
            Certificate cert = manager.readCertificate("a","a".toCharArray());

            if (!crlVerifier.isRevoked(cert)){
                throw  new Exception("Certificate should be revoked!");
            }
            System.out.println("Successfully done!");
        } catch (Exception e){
            //logger.info(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static void testExpiredCertificate() throws ParseException {

        KeyStoreManager manager = new KeyStoreManager();
        manager.reinitializeKeyStore();
        User user = new User();
        user.setUsername("a");
        user.setPassword("a");
        user.setIme("a");
        user.setPrezime("a");
        user.setEmail("a");

        //datumi
        SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = iso8601Formater.parse("2007-12-31");
        Date endDate = iso8601Formater.parse("2010-12-31");

        try {
            if (!manager.generateCertificate(user,"9",startDate,endDate)){
                throw new Exception("Could not generate certificate.");
            }
            Certificate cert = manager.readCertificate("a","a".toCharArray());

            if (manager.isCertificateExpired(cert)){
                throw  new Exception("Certificate should be expired!");
            }
            System.out.println("Successfully done!");
        } catch (Exception e){
            //logger.info(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static void testWrongSignature() {

        VerifySignatureEnveloped verifySignatureEnveloped = new VerifySignatureEnveloped();
        Document doc = verifySignatureEnveloped.loadDocument("res/testCases/testCaseWrongSignature.xml");
        if (verifySignatureEnveloped.verifySignature(doc)){
            System.out.println("Document should not be valid.!");
        } else {
            System.out.println("Test successfully done!");
        }
    }

    public static void testUnsigned(){
        VerifySignatureEnveloped verifySignatureEnveloped = new VerifySignatureEnveloped();
        Document doc = verifySignatureEnveloped.loadDocument("res/testCases/testCaseUnsigned.xml");
        if (verifySignatureEnveloped.verifySignature(doc)){
            System.out.println("Document should not be valid.!");
        } else {
            System.out.println("Test successfully done!");
        }
    }
}
