package hello.security;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.CRLNumber;
import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509v2CRLBuilder;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by milan on 12.6.2016..
 */
public class CRLVerifier {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static X509CRL crl = null;
    /**
     * Verifies if certificate is in list of revoked certificates.
     * @param certificate Certificate to be validated.
     * @return Indicator of success.
     */
    public boolean verifyCertificate(Certificate certificate){
        boolean ret = true;


        return true;
    }

    public static X509CRL initializeCRL() throws CertificateParsingException, SignatureException, InvalidKeyException, CertIOException {
        X509CRL crl = null;
        KeyStoreManager keyStoreManager = new KeyStoreManager();

        X500Name rootName = keyStoreManager.getRootData();


        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(2016, 12, 30, 1, 1, 0);
        Date update = cal.getTime();
        PrivateKey pk = keyStoreManager.getRootPrivateKey();
        X509Certificate cert = (X509Certificate) keyStoreManager.getRootCertificate();
        X509v2CRLBuilder crlBuilder = new X509v2CRLBuilder(rootName,now);

        // 9 -

        crlBuilder.addCRLEntry(BigInteger.TEN,update, CRLReason.privilegeWithdrawn);
        crlBuilder.addExtension(X509Extensions.AuthorityKeyIdentifier,false,new AuthorityKeyIdentifierStructure(cert));
        crlBuilder.addExtension(X509Extensions.CRLNumber,false,new CRLNumber(BigInteger.ONE));

        JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");

        contentSignerBuilder.setProvider("BC");

        //crl = crlBuilder.build()

        return crl;
    }

    public static X509CRL getCRL(){
        if (crl == null){
            try{
                crl = initializeCRL();
            } catch (Exception e){
                //logger.info("[ERROR] Can't initialize CRL!");
                //logger.info(e.getMessage());
            }
        }
        return crl;
    }

    public boolean verify(Certificate cert){
        boolean ret = false;
        try{
            crl.verify(cert.getPublicKey());
        } catch (Exception e){
            logger.info("[ERROR] Can't verify certificate!");
            logger.info(e.getMessage());
        }
        return ret;
    }

    public boolean store() throws FileNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("list.crl"));
        return false;
    }
}
