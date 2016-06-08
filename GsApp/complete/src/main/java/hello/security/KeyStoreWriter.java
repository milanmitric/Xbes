package hello.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class KeyStoreWriter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private KeyStore keyStore;
	
	public KeyStoreWriter(KeyStore keyStore) {
		this.keyStore = keyStore;
	}
	
	public void loadKeyStore(String fileName, char[] password) {
		try {
			if(fileName != null) 
				keyStore.load(new FileInputStream(fileName), password);
			else
				//ako nema postojeceg KS fajla u koji cemo dodavati, vec se kreira novi,
				//mora da se pozove load, pri cemu je prvi parametar sada null
				keyStore.load(null, password);
		
		} catch (NoSuchAlgorithmException e) {
            logger.info("[ERROR] Can't load key store!");
            logger.info(e.getMessage());
		} catch (CertificateException e) {
            logger.info("[ERROR] Can't load key store!");
            logger.info(e.getMessage());
		} catch (FileNotFoundException e) {
            logger.info("[ERROR] Can't load key store!");
            logger.info(e.getMessage());
		} catch (IOException e) {
            logger.info("[ERROR] Can't load key store!");
            logger.info(e.getMessage());
		}
	}

    /**
     * Saves keyStore to disk.
     * @param fileName Path to be saved.
     * @param password Keystore password.
     */
	public void saveKeyStore(String fileName, char[] password) {
		try {
			keyStore.store(new FileOutputStream(fileName), password);
		} catch (KeyStoreException e) {
			logger.info("[ERROR] Can't save key store.");
            logger.info(e.getMessage());
		} catch (NoSuchAlgorithmException e) {
            logger.info("[ERROR] Can't save key store.");
            logger.info(e.getMessage());
		} catch (CertificateException e) {
            logger.info("[ERROR] Can't save key store.");
            logger.info(e.getMessage());
		} catch (FileNotFoundException e) {
            logger.info("[ERROR] Can't save key store.");
            logger.info(e.getMessage());
		} catch (IOException e) {
            logger.info("[ERROR] Can't save key store.");
            logger.info(e.getMessage());
		}
	}

    /**
     * Writes certificate to keystore.
     * @param alias Certificate alias - name that will be used to load from keyStore (currently user username).
     * @param privateKey Certificate private key.
     * @param password Certificate password - password that will be used to load from keyStore (currently hashed user password).
     * @param certificate Certificate to be written.
     * @return Indicator of success.
     */
	public void write(String alias, PrivateKey privateKey, char[] password, Certificate certificate) {
		try {
			keyStore.setKeyEntry(alias, privateKey, password, new Certificate[] {certificate});
		} catch (KeyStoreException e) {
            logger.info("[ERROR] Can't write certificate to keystore!");
            logger.info(e.getMessage());
		}
	}



}
