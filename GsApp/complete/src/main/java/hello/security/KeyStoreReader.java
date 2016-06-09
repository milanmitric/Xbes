package hello.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;
import java.security.cert.Certificate;
import java.util.Enumeration;

/**
 *
 * Reads certificates and private keys from keystore.
 */
public class KeyStoreReader {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Keystore instance.
     */
	private KeyStore ks;

    /**
     * Initializes keystore from default location with default password.
     */
	public KeyStoreReader(KeyStore keyStore){
        this.ks = keyStore;
    }

    /**
     * Reads certificate from keystore.
     * @param alias Certificate alias.
     * @param password Certificate password.
     * @return Certificate if found. <code>NULL</code> if doesn't exist.
     */
	public Certificate readCertificate(String alias,char[] password){
		Certificate cert=null;
		try {
			if(ks.isKeyEntry(alias)) {
				cert = ks.getCertificate(alias);
			}
		} catch (KeyStoreException e) {
            logger.info("[ERROR] Can't initialize.");
            logger.info(e.getMessage());
		}
		return cert;
	}

    /**
     * Read private key from keystore.
     * @param alias Certificate alias.
     * @param password Certificate password.
     * @return Private key if found. <code>NULL</code> if doesn't exist.
     */
    public PrivateKey readPrivateKey(String alias, char[] password){
        PrivateKey ret = null;
        try {
            if(ks.isKeyEntry(alias)) {
                ret = (PrivateKey)ks.getKey(alias, password);
            }
        } catch (KeyStoreException e) {
            logger.info("[ERROR] Can't initialize.");
            logger.info(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            logger.info("[ERROR] Can't initialize.");
            logger.info(e.getMessage());
        } catch (UnrecoverableKeyException e) {
            logger.info("[ERROR] Can't initialize.");
            logger.info(e.getMessage());
        }
        return ret;
    }

    /**
     * Returns number of aliases + 1.
     * @return Next serial number.
     */
    public String getCertificateSerialNumber(){
        String ret = "-1";

        try {
            int counter = 1;
            Enumeration<String> aliases = ks.aliases();
            while(aliases.hasMoreElements()){
                counter++;
                aliases.nextElement();
            }
            ret = Integer.toString(counter);
        } catch (KeyStoreException e) {
            logger.info("[ERROR] Can't get number of aliases!");
            logger.info(e.getMessage());
        }
        return ret;
    }


}
