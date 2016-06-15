package hello.rest;

import hello.StringResources.MarkLogicStrings;
import hello.businessLogic.document.AktManager;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.entity.gov.gradskaskupstina.User;
import hello.security.EncryptKEK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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



    @RequestMapping(value = "/getallacts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllAct() {
        ArrayList<Akt> aktovi = aktManager.getAllFilesProposed();
        ArrayList<Akt> aktovi2 = aktManager.getAllFilesApproved();
        HashMap<String,ArrayList<Akt>> returnTwoLists = new HashMap<String,ArrayList<Akt>>();
        returnTwoLists.put("proposed",aktovi);
        returnTwoLists.put("approved",aktovi2);
        return new ResponseEntity(returnTwoLists, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_ODBORNIK')")
    @RequestMapping(value = "/getmyallacts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyAllAct() {

        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "/getproposed",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProposed() {
        ArrayList<Akt> aktovi = aktManager.getAllFilesProposed();
        return new ResponseEntity(aktovi, HttpStatus.OK);
    }



    @PreAuthorize("hasRole('ROLE_ODBORNIK')")
    @RequestMapping(value = "/getmyproposedakts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getmyproposed() {

        //TODO
        ArrayList<Akt> aktovi = aktManager.getAllFilesProposed();
        for(int i=aktovi.size()-1; i>=0; i--){

            //if(aktovi.get(i).get)
            
        }

        return new ResponseEntity(aktovi, HttpStatus.OK);
    }



    @RequestMapping(value = "/tagsearch",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity searchActs(@RequestParam("parametar") String parametar,@RequestParam("tag") String tag){
        logger.info("[AktController] LOG: ENTER TO API FOR SEARCHING  ACTS");
        HashMap<String,ArrayList<String>> predlozeni = aktManager.returnListOfDocumentsMatchedWithOneFieldSearchAndTag(tag,parametar,MarkLogicStrings.AKTOVI_PREDLOZEN_COL_ID);
        HashMap<String,ArrayList<String>> usvojeni = aktManager.returnListOfDocumentsMatchedWithOneFieldSearchAndTag(tag,parametar,MarkLogicStrings.AKTOVI_USVOJENI_COL_ID);
        HashMap<String,HashMap<String,ArrayList<String>>> returnMap = new HashMap<String,HashMap<String,ArrayList<String>>>();
        returnMap.put("predlozeni",predlozeni);
        returnMap.put("usvojeni",usvojeni);
        return new ResponseEntity(returnMap,HttpStatus.OK);
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


    @PreAuthorize("hasRole('ROLE_ODBORNIK')")
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





    /*prihvati akt nekako ili nemoj*/
    @PreAuthorize("hasRole('ROLE_PREDSEDNIK')")
    @RequestMapping(value = "/prihvatiovono",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity aktmadafaka(@RequestBody ArrayList<String> amandmants){

        System.out.println("AMANDMANI: "+amandmants);
        //uvek stigne lista, 0. u nizu je UVEK ID od AKTA, a nakon toga idu ID od amandmana
        //String aktID=amandmants.get(0);

        if(amandmants.get(0).equals("ODBIJAJUSE")){
            System.out.println("SVI AMANDMAN ZA AKT SE ODBIJAJU");
            //TODO - SVI AMANDMAN ZA AKT SE ODBIJAJU
            //AKT ID: amandmants.get(1) //AKT JE PRIHVACEN U NACELU VEC
            //AMANDMANI ID get(2+n) n=0,1,2...Size



        } else
        if(amandmants.get(0).equals("AKTSEODBIJA")){
            System.out.println("AKT SE ODBIJA");
            //TODO - AKT SE ODBIJA
            //AKT ID: amandmants.get(1)
            //AMANDMANI ID get(2+n) n=0,1,2...Size



        }else {

                if (amandmants.size() == 1) {
                    System.out.println("AKT SE PRIHVATA SE U NACELU");
                    //TODO - AKT SE PRIHVATA SE U NACELU
                    //AKT ID : amandmants.get(0)


                } else {
                    System.out.println("USVAJAJU SE AMNDMANI NA AKT");
                    //TODO - USVAJAJU SE AMNDMANI NA AKT
                    //AKT ID : amandmants.get(0) //AKT JE PRIHVACEN U NACELU VEC
                    //1. , 2. , 3. ... ID od AMANDMANA

                }

        }



        return new ResponseEntity("",HttpStatus.OK);
    }


    /*serving static html*/
    @RequestMapping(value = "/givemeakt",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String serveakt(){


        return "forward:/myWebpage.html";
    }



    //WORKING
    @RequestMapping(value="download", method=RequestMethod.GET)
    public void getDownload(HttpServletResponse response) {

        // Get your file stream from wherever.
        //InputStream myStream = new //someClass.returnFile();

        // Set the content type and attachment header.
        response.addHeader("Content-disposition", "attachment;filename=sednicaHome.html");
        response.setContentType("txt/plain");

        // Copy the stream to the response's output stream.
        //IOUtils.copy(myStream, response.getOutputStream());
        try {
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }










}
