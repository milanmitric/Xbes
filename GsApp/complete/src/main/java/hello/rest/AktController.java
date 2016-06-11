package hello.rest;

import hello.StringResources.MarkLogicStrings;
import hello.businessLogic.document.AktManager;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.entity.gov.gradskaskupstina.User;
import hello.security.EncryptKEK;
import jdk.internal.org.xml.sax.InputSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.ws.rs.Path;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nebojsa on 6/4/2016.
 */
@RestController
@RequestMapping("/api")
public class AktController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private AktManager aktManager = new AktManager();
    private EncryptKEK enkryption = new EncryptKEK();


    //TODO - preimenuj ovo i izmeni u servisina i izmeni ovde da vraca SVE a ne samo proposed
    @RequestMapping(value = "/getallacts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllAct() {
        ArrayList<Akt> aktovi = aktManager.getAllFilesProposed();
        return new ResponseEntity(aktovi, HttpStatus.OK);
    }


    //OVO JE GET PROPOSED A NE OVO GORE ^ SONETOVO :D
    @RequestMapping(value = "/getproposed",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProposed() {
        ArrayList<Akt> aktovi = aktManager.getAllFilesProposed();
        return new ResponseEntity(aktovi, HttpStatus.OK);
    }




    @RequestMapping(value = "/searchacts/{value}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity searchActs(@PathVariable String value){
        //ArrayList<Akt> aktovi = aktManager.getAllFilesProposed();
        //return new ResponseEntity(aktovi, HttpStatus.OK);
        logger.info("[AktController] LOG: ENTER TO API FOR SEARCHING  ACTS");
        HashMap<String,ArrayList<String>> predlozeni = aktManager.returnListOfDocumentsMatchedWithOneFieldSearch(value, MarkLogicStrings.AKTOVI_PREDLOZEN_COL_ID);
        HashMap<String,ArrayList<String>> usvojeni = aktManager.returnListOfDocumentsMatchedWithOneFieldSearch(value, MarkLogicStrings.AKTOVI_USVOJENI_COL_ID);
        HashMap<String,HashMap<String,ArrayList<String>>> returnMap = new HashMap<String,HashMap<String,ArrayList<String>>>();
        returnMap.put("predlozeni",predlozeni);
        returnMap.put("usvojeni",usvojeni);
        return new ResponseEntity(returnMap,HttpStatus.OK);
    }


    @RequestMapping(value = "/akt",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity postAct(@RequestBody Akt akt) {

        //TODO - if sednica false
        logger.info("[CONTENT OF ADDED ACT]:"+akt.toString());

        if(!aktManager.validateAkt(akt)){
            logger.info("[ERROR] NOT VALIDATED");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)auth.getPrincipal();
        String docID = aktManager.proposeAkt(akt,user);
        if(docID==null){
            logger.info("[ERROR] NOT PROPOSED");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            logger.info("Successfully proposed akt: " + akt.toString() + " with id " + docID);
        }

        return new ResponseEntity(HttpStatus.OK);
    }



    @RequestMapping(value = "/archiveit",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity test5(@RequestParam("docID") String docID) throws FileNotFoundException {

        //DOCID EXAMPLE: 16250548801149691694.xml
        System.out.println("ID JE: "+docID);
        Akt akt = aktManager.read(docID, false);
        if(akt==null) {
            return new ResponseEntity(docID, HttpStatus.BAD_REQUEST);
        }
        /* convert AKT OBJ to XML */
        aktManager.convertToXml(akt);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document d=null;
        try {
            /* CONVERT XML TO DOCUMENT OBJ */
            d= builder.parse(new FileInputStream(new File("tmp.xml")));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //EncryptKEK enc = new EncryptKEK();
        Document doc=enkryption.encrypt(d);
        /*CONVERT DOCUMET TO INPUTSTREAM*/
        FileInputStream is= (FileInputStream) aktManager.convertDocumentToInputStream(doc);
        aktManager.write(is, MarkLogicStrings.ARCHIVE_PREFIX+docID, MarkLogicStrings.ARCHIVE_COL_ID, false, null);
        System.out.println("UPISANOOOOOOOOOOOOOOOOO");

        return new ResponseEntity(docID, HttpStatus.OK);
    }




}
