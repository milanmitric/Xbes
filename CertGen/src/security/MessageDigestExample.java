package security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import utils.Base64;


public class MessageDigestExample {
	
	private String data = "Ovo su podaci ciji se digest racuna";
	
	
	public void testIt() {
		
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			
			byte[] digest = sha.digest(data.getBytes());
			
			System.out.println("Poruka " + data);
			System.out.println("Digest poruke " + Base64.encodeToString(digest));
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		MessageDigestExample messageDigestExample = new MessageDigestExample();
		messageDigestExample.testIt();
	}

}
