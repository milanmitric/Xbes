package hello.rest;

import hello.StringResources.MarkLogicStrings;
import hello.businessLogic.document.AktManager;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.security.DecryptKEK;
import hello.security.EncryptKEK;
import hello.security.KeyStoreManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.PrivateKey;
import java.util.Date;

/**
 * Created by Nebojsa on 6/4/2016.
 */
@RestController
@RequestMapping("/api")
public class ArchiveController {

    AktManager aktManager = new AktManager();
    EncryptKEK encryptKEK = new EncryptKEK();
    DecryptKEK decryptKEK = new DecryptKEK();
    KeyStoreManager keyStoreManager = new KeyStoreManager();

    private String lastId = null;
    /*THIS IS ONLY CONTROLLER FOR ARCHIVE APP*/
    /*CREATE NEW INSTANCE OF SAME APP BUT ON DIFFERENT PORT AND WITHOUT HTTPS (CAN'T USE SELFSIGNED CRT)*/
    /*AND APP no1 WILL SEND DATA TO APP no2*/
    /*URL&PORT IS SPECIFIED IN AKTMANAGER.class*/



    @RequestMapping(value = "/testx",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity testx(@RequestBody Document encDoc) throws FileNotFoundException {

        System.out.println("SAVING TO ARCHIVE");
        String tmp="archived.xml";

        /*[TEST] SAVE TO FILE TO SEE ENCRYPTED DOC THAT CAME TO THIS REST*/
        //encryptKEK.saveDocument(encDoc, tmp);
        /*[TEST] LOAD THAT ^ XML AND DECTYPT IT*/
        //Document doc = decryptKEK.loadDocument(tmp);

        //ucitava se privatni kljuc
        PrivateKey pk = keyStoreManager.getRootPrivateKey();
        //dekriptuje se dokument
        System.out.println("Decrypting....");
        encDoc = decryptKEK.decrypt(encDoc, pk);

        FileOutputStream fileOutputStream = new FileOutputStream(new File("tmp.xml"));
        aktManager.transform(encDoc,fileOutputStream);

        Akt akt = aktManager.convertFromXml(new File("tmp.xml"));
        if (lastId == null){
            lastId = akt.getDocumentId();
        } else if (lastId.equals(akt.getDocumentId())){
            return new ResponseEntity("test_0:fail", HttpStatus.BAD_REQUEST);
        }

        Date now = new Date();
        Date timeStamp =akt.getTimeStamp().toGregorianCalendar().getTime();

        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

        long curTimeInMs = timeStamp.getTime();
        timeStamp = new Date(curTimeInMs + (1 * ONE_MINUTE_IN_MILLIS));

        if (timeStamp.after(now)){
            return new ResponseEntity("test_0:fail", HttpStatus.BAD_REQUEST);
        }


        //[TEST] SAVE ENCRYPTED DOC
        //decryptKEK.saveDocument(encDoc, "OUTPUT.xml");
        /*SAVE TO DB*/
        InputStream is=aktManager.convertDocumentToInputStream(encDoc);

        aktManager.write(is, MarkLogicStrings.ARCHIVE_PREFIX+System.nanoTime(), MarkLogicStrings.ARCHIVE_COL_ID, false, null);
        System.out.println("WRITING TO DATABASE...");
        System.out.println("DONE.");

        return new ResponseEntity("test_0:success", HttpStatus.OK);
    }





    //SOME TESTING//----------------------------------------------------------------------------
    /* @RequestMapping(value = "/testx",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity testx(@RequestParam MultiValueMap<String, Object> map) {

        String id= (String) map.get("docID").get(0);
        System.out.println("Documet ID to archive: "+id);

        *//*try {
            Document doc = (Document) map.get("encDoc").get(0);
        }catch (Exception e){
            System.out.println("ERROR MRTVI");
        }*//*

        *//*write to file*//*
        try {
            System.out.println("Writing file... ");
            PrintWriter out = new PrintWriter(id);
            out.println( map.get("encDoc").get(0).toString());
            out.close();
            System.out.println("Finished.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        *//*
        try (PrintStream out2 = new PrintStream(new FileOutputStream("filename.txt"))) {
            out2.print(text);
        }*//*


        return new ResponseEntity("[Response from archive app] Archive status: SUCCESS", HttpStatus.OK);
    }*/
    //--------------------------------------------------------------------------------------------------





}