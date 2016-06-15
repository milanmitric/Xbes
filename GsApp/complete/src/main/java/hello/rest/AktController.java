package hello.rest;

import hello.StringResources.MarkLogicStrings;
import hello.businessLogic.document.AktManager;
import hello.businessLogic.document.AmandmanManager;
import hello.businessLogic.document.UsersManager;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.entity.gov.gradskaskupstina.Amandmani;
import hello.entity.gov.gradskaskupstina.User;
import hello.security.EncryptKEK;
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
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private AmandmanManager amandmanManager = new AmandmanManager();
    private UsersManager usersManager = new UsersManager();
    private EncryptKEK enkryption = new EncryptKEK();

    @RequestMapping(value = "/getallacts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllAct() {
        logger.info("Called REST for getting all acts");

        ArrayList<Akt> aktovi = aktManager.getAllFilesProposed();
        ArrayList<Akt> aktovi2 = aktManager.getAllFilesApproved();
        HashMap<String,ArrayList<Akt>> returnTwoLists = new HashMap<String,ArrayList<Akt>>();
        returnTwoLists.put("proposed",aktovi);
        returnTwoLists.put("approved",aktovi2);
        return new ResponseEntity(returnTwoLists, HttpStatus.OK);
    }

    @RequestMapping(value = "/generatepdf",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity generatePdf(){

        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "/getactbyid",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getActById(@RequestBody String data) {
        logger.info("Called REST for getting act by id "+ "data");

        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("Akt.xsl"));
        if(xslt==null){
            logger.info("NULL JE XSLT");
        }
        try {
            Transformer transformer = factory.newTransformer(xslt);
            Akt aktHtml = aktManager.read(data,false);
            aktManager.convertToXml(aktHtml);
            Source text = new StreamSource(new File("tmp.xml"));
            transformer.transform(text, new StreamResult(new File("tmp.html")));
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        File htmlFile = new File("tmp.html");
        try {
            FileInputStream fis = new FileInputStream(htmlFile);
            String akt = "";
            for (String line : Files.readAllLines(Paths.get("tmp.html"))) {
                akt+=line;
            }
            logger.info("Content of act: ");
            JSONObject returnValue = new JSONObject();
            returnValue.put("akt",akt);
            logger.info(akt);

            return new ResponseEntity(akt,HttpStatus.OK);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('ROLE_ODBORNIK')")
    @RequestMapping(value = "/getmyallacts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMyAllAct() {
        logger.info("Called REST for getting act of a person (getmyallacts)");

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getproposed",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProposed() {
        logger.info("Called REST for getting all proposed acts");

        ArrayList<Akt> aktovi = aktManager.getAllFilesProposed();
        return new ResponseEntity(aktovi, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ODBORNIK')")
    @RequestMapping(value = "/getmyproposedakts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getmyproposed() {
        logger.info("Called REST for getting proposed acts of a user: (getmyproposed)");

        //TODO - rijesen?
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)auth.getPrincipal();
        ArrayList<Akt> aktovi = aktManager.getMyFilesProposed(user);
        for(int i=aktovi.size()-1; i>=0; i--){

            //if(aktovi.get(i).get)
            
        }
        return new ResponseEntity(aktovi, HttpStatus.OK);
    }

    @RequestMapping(value = "/tagsearch",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity searchActs(@RequestParam("parametar") String parametar,@RequestParam("tag") String tag){
        logger.info("Called REST for searching acts");

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
        logger.info("Called REST for searching acts");

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
        logger.info("Called REST for archiving act with an id: " + docID);

        //DOCID EXAMPLE: 16250548801149691694.xml
        //System.out.println("ID JE: "+docID);
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

        logger.info("Act wih an id: " + docID + " successfully archived");

        return new ResponseEntity(docID, HttpStatus.OK);
    }

    /*prihvati akt nekako ili nemoj*/
    @PreAuthorize("hasRole('ROLE_PREDSEDNIK')")
    @RequestMapping(value = "/prihvatiovono",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity aktmadafaka(@RequestBody ArrayList<String> params){
        logger.info("Called REST for PRIHVATIOVOONO");

        //System.out.println("AMANDMANI: "+amandmants);
        //uvek stigne lista, 0. u nizu je UVEK ID od AKTA, a nakon toga idu ID od amandmana
        //String aktID=amandmants.get(0);

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(params.get(0).equals("ODBIJAJUSE")){
            logger.info("All amandments are being rejected");
            //TODO - SVI AMANDMAN ZA AKT SE ODBIJAJU
            //Akt akt = aktManager.read(true,params.get(1));
            //aktManager.
            for (int i = 2; i < params.size();i++){
                amandmanManager.deleteAmandman(params.get(i));
            }
            //AKT ID: amandmants.get(1) //AKT JE PRIHVACEN U NACELU VEC
            //AMANDMANI ID get(2+n) n=0,1,2...Size
        } else
        if(params.get(0).equals("AKTSEODBIJA")){
            logger.info("Act is being rejected");
            //TODO - AKT SE ODBIJA
            ArrayList<Amandmani> amandmani = amandmanManager.getAllAmandmansForAkt(aktManager.read(params.get(0),false));
            aktManager.deleteAkt(params.get(0));
            for (Amandmani amandman : amandmani){
                amandmanManager.deleteAmandman(amandman.getDocumentId());
            }
            //AKT ID: amandmants.get(1)
            //AMANDMANI ID get(2+n) n=0,1,2...Size
        }else {
            if (params.size() == 1) {
                logger.info("Act is accepted in principle");
                Akt akt = aktManager.read(params.get(0), false);
                aktManager.proposeAkt(akt,user);
                //TODO - AKT SE PRIHVATA SE U NACELU - KOJI AKT?
                //AKT ID : amandmants.get(0)
            } else {
                logger.info("Amandments are being accepted");
                //AKT ID : amandmants.get(0) //AKT JE PRIHVACEN U NACELU VEC
                //1. , 2. , 3. ... ID od AMANDMANA
                ArrayList<Amandmani> amandmani = new ArrayList<>();
                for(int i = 1 ; i<params.size() ; i++) {
                    Amandmani amandman = amandmanManager.read(params.get(i), false);
                    amandmanManager.proposeAmandman(amandman,user);
                    amandmani.add(amandman);
                }
                Akt akt = aktManager.read(params.get(0), false);
                aktManager.applyAmendments(amandmani, akt, user);
            }
        }

        return new ResponseEntity("",HttpStatus.OK);
    }

    /*serving static html*/
    @RequestMapping(value = "/givemeakt",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String serveakt(){
        logger.info("Called REST for SERVEAKT (givemeakt)");
        return "forward:/myWebpage.html";
    }

    //WORKING
    @RequestMapping(value="download", method=RequestMethod.GET)
    public void getDownload(HttpServletResponse response) {
        logger.info("Called REST for downloading");

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