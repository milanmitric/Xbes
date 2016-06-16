package hello.rest;

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import hello.StringResources.MarkLogicStrings;
import hello.businessLogic.document.AktManager;
import hello.businessLogic.document.AmandmanManager;
import hello.businessLogic.document.UsersManager;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.entity.gov.gradskaskupstina.Amandmani;
import hello.entity.gov.gradskaskupstina.User;
import hello.security.DecryptKEK;
import hello.security.EncryptKEK;
import hello.security.KeyStoreManager;
import org.apache.fop.apps.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;

import static com.sun.jersey.core.util.ReaderWriter.BUFFER_SIZE;

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

    /*THIS IS ONLY CONTROLLER FOR ARCHIVE APP*/
    /*CREATE NEW INSTANCE OF SAME APP BUT ON DIFFERENT PORT AND WITHOUT HTTPS (CAN'T USE SELFSIGNED CRT)*/
    /*AND APP no1 WILL SEND DATA TO APP no2*/
    /*URL&PORT IS SPECIFIED IN AKTMANAGER.class*/


    @RequestMapping(value = "/testx",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity testx(@RequestBody Document encDoc) {


        /*SAVE AS XML*/
        String tmp="archived.xml";
        encryptKEK.saveDocument(encDoc, tmp);

        /*LOAD THAT ^ XML AND DECTYPT IT*/
        Document doc = decryptKEK.loadDocument(tmp);
        //ucitava se privatni kljuc
        PrivateKey pk = keyStoreManager.getRootPrivateKey();
        //dekriptuje se dokument
        System.out.println("Decrypting....");
        doc = decryptKEK.decrypt(doc, pk);
        //snima se dokument
        decryptKEK.saveDocument(doc, "OUTPUT.xml");
        /*SAVE TO DB*/
        InputStream is=aktManager.convertDocumentToInputStream(doc);
        aktManager.write(is, "argTEST"+System.nanoTime(), "arhTEST", false, null);


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