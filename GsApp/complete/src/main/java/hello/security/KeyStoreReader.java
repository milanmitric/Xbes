package hello.security;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

/**
 * 
 * Cita is keystore fajla
 */
public class KeyStoreReader {

	private static final String KEY_STORE_FILE = "./keyStore/";

	private char[] keyPass  = "".toCharArray();

	
	public void testIt() {
		readKeyStore("djoka.jks","djoka".toCharArray(),"prvi","prvi".toCharArray());
	}
	
	public Certificate readKeyStore(String nameOfFile,char[] password, String alias,char[] certPassword){
		Certificate cert=null;
		try {
			//kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			//ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(KEY_STORE_FILE+nameOfFile));
			ks.load(in, password);
			//citamo par sertifikat privatni kljuc
			
			if(ks.isKeyEntry(alias)) {
				System.out.println("Sertifikat:");
				cert = ks.getCertificate(alias);
				System.out.println(cert);
				PrivateKey privKey = (PrivateKey)ks.getKey(alias, certPassword);
				System.out.println("Privatni kljuc:");
				System.out.println(privKey);
			}

			
			
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return cert;
	}
	public static void main(String args[]){
		KeyStoreReader str = new KeyStoreReader();
		str.testIt();
		
	}

}
