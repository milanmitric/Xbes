package security;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

/**
 * 
 * Cita is keystore fajla
 */
public class KeyStoreReader {

	private static final String KEY_STORE_FILE = "./data/marija.jks";
	
	private char[] password = "test10".toCharArray();
	private char[] keyPass  = "marija1".toCharArray();
	
	
	public void testIt() {
		readKeyStore();
	}
	
	private void readKeyStore(){
		try {
			//kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			//ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(KEY_STORE_FILE));
			ks.load(in, password);
			//citamo par sertifikat privatni kljuc
			System.out.println("Cita se Sertifikat i privatni kljuc Marije...");
			
			if(ks.isKeyEntry("marija")) {
				System.out.println("Sertifikat:");
				Certificate cert = ks.getCertificate("marija");
				System.out.println(cert);
				PrivateKey privKey = (PrivateKey)ks.getKey("marija", keyPass);
				System.out.println("Privatni kljuc:");
				System.out.println(privKey);
			}
			else
				System.out.println("Nema para kljuceva za Mariju");
			
			System.out.println("Cita sertifikat Jovana");
			if(ks.isCertificateEntry("jovan")) {
				Certificate cert = ks.getCertificate("jovan");
				System.out.println(cert);
			}
			else
				System.out.println("Nema sertifikata za Jovana");
		
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		KeyStoreReader test = new KeyStoreReader();
		test.testIt();
	}
}
