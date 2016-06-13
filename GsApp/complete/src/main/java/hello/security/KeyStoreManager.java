package hello.security;

import hello.StringResources.MarkLogicStrings;
import hello.entity.gov.gradskaskupstina.User;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by milan on 8.6.2016..
 */
public class KeyStoreManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static KeyStoreReader keyStoreReader;

    private static KeyStoreWriter keyStoreWriter;

    public KeyStoreManager(){
        KeyStore keyStore = initializeKeyStore();
        keyStoreReader = new KeyStoreReader(keyStore);
        keyStoreWriter = new KeyStoreWriter(keyStore);
    }


    /**
     * Initializes keystore.
     * @return Inidialized keyStore. <code>NULL</code> if not successful.
     */
    private   KeyStore initializeKeyStore(){
        KeyStore ks = null;
        try {
            ks = KeyStore.getInstance("JKS", "SUN");
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(MarkLogicStrings.KEYSTORE_FILEPATH));
            ks.load(in, MarkLogicStrings.KEYSTORE_PASSWORD);
        } catch (KeyStoreException e) {
            //logger.info(e.getMessage());
        } catch (NoSuchProviderException e) {
            //logger.info("[ERROR] Can't initialize.");
            //logger.info(e.getMessage());
        } catch (CertificateException e) {
            //logger.info("[ERROR] Can't initialize.");
            //logger.info(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
//            logger.info("[ERROR] Can't initialize.");
//            logger.info(e.getMessage());
        } catch (FileNotFoundException e) {
//            logger.info("[ERROR] Can't initialize.");
//            logger.info(e.getMessage());
        } catch (IOException e) {
//            logger.info("[ERROR] Can't initialize.");
//            logger.info(e.getMessage());
        }
        return ks;

    }


    /**
     * Gets root private key.
     * @return Root private key. <code>NULL</code> if not successful.
     */
    public  PrivateKey getRootPrivateKey(){
        return keyStoreReader.readPrivateKey(MarkLogicStrings.ROOT_CERTIFICATE_ALIAS,MarkLogicStrings.ROOT_CERTIFICATE_PASSWORD);
    }

    public Certificate getRootCertificate(){
        return keyStoreReader.readCertificate(MarkLogicStrings.ROOT_CERTIFICATE_ALIAS,MarkLogicStrings.ROOT_CERTIFICATE_PASSWORD);
    }


    /**
     * Gets root issuer data.
     * @return Root issuer data. <code>NULL</code> if not successful.
     */
    private IssuerData getRootIssuerData(){
        IssuerData ret = null;


        SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
        try {
            X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
            builder.addRDN(BCStyle.CN, "Skupstina grada Novog Sada");
            builder.addRDN(BCStyle.SURNAME, "Gradska");
            builder.addRDN(BCStyle.GIVENNAME, "Skupstina");
            builder.addRDN(BCStyle.O, "Grad Novi Sad");
            builder.addRDN(BCStyle.OU, "Skupstina grada");
            builder.addRDN(BCStyle.C, "RS");
            builder.addRDN(BCStyle.E, "gradskaSkupstina@novisad.rs");
            //UID (USER ID) je ID korisnika
            builder.addRDN(BCStyle.UID, "123445");
            //Serijski broj sertifikata
            String sn="1";
            Date startDate = iso8601Formater.parse("2007-12-31");
            Date endDate = iso8601Formater.parse("2017-12-31");
            ret = new IssuerData(getRootPrivateKey(), builder.build());
        } catch (ParseException e) {
            logger.info("[ERROR] Can't get root issuer data.");
            logger.info(e.getMessage());
        }
        //kreiraju se podaci za issuer-a


        return  ret;
    }

    
    /**
     * Writes certificate to keystore.
     * @param alias Certificate alias - name that will be used to load from keyStore (currently user username).
     * @param privateKey Certificate private key.
     * @param password Certificate password - password that will be used to load from keyStore (currently hashed user password).
     * @param certificate Certificate to be written.
     * @return Indicator of success.
     */
    public boolean writeToKeyStore(String alias, PrivateKey privateKey, char[] password, java.security.cert.Certificate certificate){
        boolean ret = false;
        try{
            keyStoreWriter.write(alias,privateKey,password,certificate);
            ret = true;
        } catch (Exception e){
            logger.info("[ERROR] Can't write certificate to keyStore!");
            logger.info(e.getMessage());
        }
        return ret;
    }

    /**
     * Generates and saves certificate for user.
     * @param user User to who the certificate is created.
     * @return Indicator of success.
     */
    public boolean generateCertificate(User user){
        boolean ret = false;

        try {
            CertificateGenerator certificateGenerator = new CertificateGenerator();
            KeyPair  keyPair = certificateGenerator.generateKeyPair();

            //datumi
            SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = iso8601Formater.parse("2007-12-31");
            Date endDate = iso8601Formater.parse("2017-12-31");

            X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
            builder.addRDN(BCStyle.CN, user.getPrezime() + user.getIme());
            builder.addRDN(BCStyle.SURNAME, user.getPrezime());
            builder.addRDN(BCStyle.GIVENNAME, user.getIme());
            builder.addRDN(BCStyle.O, "Grad Novi Sad");
            builder.addRDN(BCStyle.OU, "Skupstina grada");
            builder.addRDN(BCStyle.C, "RS");
            // TODO: EMAIL IS HARDCODED!
            builder.addRDN(BCStyle.E, "neki@mail.com");
            //UID (USER ID) je ID korisnika
            builder.addRDN(BCStyle.UID, user.getUsername());
            String sn= keyStoreReader.getCertificateSerialNumber();

            SubjectData subjectData = new SubjectData(keyPair.getPublic(),builder.build(),sn,startDate,endDate);
            //generise se sertifikat
            X509Certificate cert = certificateGenerator.generateCertificate(getRootIssuerData(), subjectData);

            keyStoreWriter.write(user.getUsername(),keyPair.getPrivate(),user.getPassword().toCharArray(),cert);
            keyStoreWriter.saveKeyStore(MarkLogicStrings.KEYSTORE_FILEPATH,MarkLogicStrings.KEYSTORE_PASSWORD);
            ret = true;
        } catch (Exception e){
            logger.info("[ERROR] Can't generate certificate for user " + user.getIme());
            logger.info(e.getMessage());
        }
        return ret;
    }

    /**
     * Read private key from keystore.
     * @param alias Certificate alias.
     * @param password Certificate password.
     * @return Private key if found. <code>NULL</code> if doesn't exist.
     */
    public PrivateKey readPrivateKey(String alias, char[] password){
        return keyStoreReader.readPrivateKey(alias,password);
    }

    /**
     * Reads certificate from keystore.
     * @param alias Certificate alias.
     * @param password Certificate password.
     * @return Certificate if found. <code>NULL</code> if doesn't exist.
     */
    public Certificate readCertificate(String alias, char[] password){
        return keyStoreReader.readCertificate(alias,password);
    }

    /**
     * Creates new keystore with root certificate in data/keystore.jks.
     * @return Indicator of success.
     */
    public boolean reinitializeKeyStore(){
        boolean ret = false;
        try{
            keyStoreWriter.loadKeyStore(null,MarkLogicStrings.KEYSTORE_PASSWORD);
            keyStoreWriter.saveKeyStore(MarkLogicStrings.KEYSTORE_FILEPATH,MarkLogicStrings.KEYSTORE_PASSWORD);
            if (!generateRootCaCertificate()){
                throw new Exception("Could not generate root certificate!");
            } else {
                ret = true;
            }

        } catch (Exception e){
            logger.info("[ERROR] Can't initialize keyStore.");
            logger.info(e.getMessage());
        }
        return ret;
    }

    /**
     * Creates root certificate.
     * @return Indicator of success.
     */
    private boolean generateRootCaCertificate(){
        boolean ret = false;

        try{
            //kreiramo generator i generisemo kljuceve i sertifiakt
            CertificateGenerator gen = new CertificateGenerator();
            //par kljuceva
            KeyPair keyPair = gen.generateKeyPair();

            //datumi
            SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = iso8601Formater.parse("2007-12-31");
            Date endDate = iso8601Formater.parse("2017-12-31");

            //podaci o vlasniku i izdavacu posto je self signed
            //klasa X500NameBuilder pravi X500Name objekat koji nam treba
            X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
            builder.addRDN(BCStyle.CN, "Skupstina grada Novog Sada");
            builder.addRDN(BCStyle.SURNAME, "Gradska");
            builder.addRDN(BCStyle.GIVENNAME, "Skupstina");
            builder.addRDN(BCStyle.O, "Grad Novi Sad");
            builder.addRDN(BCStyle.OU, "Skupstina grada");
            builder.addRDN(BCStyle.C, "RS");
            builder.addRDN(BCStyle.E, "gradskaSkupstina@novisad.rs");
            //UID (USER ID) je ID korisnika
            builder.addRDN(BCStyle.UID, "123445");

            //Serijski broj sertifikata
            String sn="1";
            //kreiraju se podaci za issuer-a
            IssuerData issuerData = new IssuerData(keyPair.getPrivate(), builder.build());
            //kreiraju se podaci za vlasnika
            SubjectData subjectData = new SubjectData(keyPair.getPublic(), builder.build(), sn, startDate, endDate);
            //generise se sertifikat
            X509Certificate cert = gen.generateCertificate(issuerData, subjectData);
            keyStoreWriter.write(MarkLogicStrings.ROOT_CERTIFICATE_ALIAS, keyPair.getPrivate(), MarkLogicStrings.ROOT_CERTIFICATE_PASSWORD, cert);
            keyStoreWriter.saveKeyStore(MarkLogicStrings.KEYSTORE_FILEPATH,MarkLogicStrings.KEYSTORE_PASSWORD);
            ret = true;
        } catch (Exception e){
            logger.info("[ERROR] Can't create root CA certificate.");
            logger.info(e.getMessage());
        }
        return ret;
    }

    public X500Name getRootData(){
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "Skupstina grada Novog Sada");
        builder.addRDN(BCStyle.SURNAME, "Gradska");
        builder.addRDN(BCStyle.GIVENNAME, "Skupstina");
        builder.addRDN(BCStyle.O, "Grad Novi Sad");
        builder.addRDN(BCStyle.OU, "Skupstina grada");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "gradskaSkupstina@novisad.rs");
        //UID (USER ID) je ID korisnika
        builder.addRDN(BCStyle.UID, "123445");
        //Serijski broj sertifikata
        String sn="1";
        return builder.build();
    }
}