package hello.security;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.CRLNumber;
import org.bouncycastle.asn1.x509.CRLReason;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509v2CRLBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CRLConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by milan on 12.6.2016..
 */
public class CRLVerifier {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private X509CRL crl = null;


    //registracija providera
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public CRLVerifier(){
        crl = initializeCRL();
    }


    /**
     * Initializes CRL verifier.
     * @return CRL instance. <CODE>NULL</CODE> if not successful.
     */
    public  X509CRL initializeCRL(){
        X509CRL crl = null;
        try{
            X509CRLHolder crlHolder = null;
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


            crlBuilder.addCRLEntry(BigInteger.TEN,update, CRLReason.privilegeWithdrawn);
            crlBuilder.addExtension(X509Extensions.AuthorityKeyIdentifier,false,new AuthorityKeyIdentifierStructure(cert));
            crlBuilder.addExtension(X509Extensions.CRLNumber,false,new CRLNumber(BigInteger.ONE));

            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            //koji provider se koristi
            builder = builder.setProvider("BC");
            crlHolder = crlBuilder.build(builder.build(pk));
            JcaX509CRLConverter converter = new JcaX509CRLConverter();
            converter.setProvider("BC");

            crl = converter.getCRL(crlHolder);

        } catch (Exception e){
            logger.info("[ERROR] Could not initialize!");
            logger.info(e.getMessage());
        }
        return crl;
    }

    /**
     * Verifies if certificate is in list of revoked certificates.
     * @param cert Certificate to be validated.
     * @return Indicator of success.
     */
    public boolean isRevoked(Certificate cert){
        boolean ret = false;
        try{
            ret = crl.isRevoked(cert);
        } catch (Exception e){
            logger.info("[ERROR] Can't verify certificate!");
            logger.info(e.getMessage());
        }
        return ret;
    }

    /**
     * Store CRL to file.
     * @return Indicator of success.
     */
    public boolean store()  {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File("list.crl"));
            fileOutputStream.write(crl.getEncoded());
            fileOutputStream.close();
            return true;
        } catch (FileNotFoundException e){
            logger.info("[ERROR] Could not store crl to file.");
            logger.info(e.getMessage());
            return false;
        } catch (CRLException e) {
            logger.info("[ERROR] Could not store crl to file.");
            logger.info(e.getMessage());
            return false;
        } catch (IOException e) {
            logger.info("[ERROR] Could not store crl to file.");
            logger.info(e.getMessage());
            return false;
        }

    }

    /**
     * Load CRL from file.
     * @return Indicator of success.
     */
    public boolean load(){
        try{
            FileInputStream fileInputStream = new FileInputStream(new File("list.crl"));

            return true;
        } catch (Exception e){
            logger.info("[ERROR] Could not load crl from file.");
            logger.info(e.getMessage());
            return false;
        }
    }

}
