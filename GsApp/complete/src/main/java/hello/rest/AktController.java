package hello.rest;

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import hello.StringResources.MarkLogicStrings;
import hello.businessLogic.document.AktManager;
import hello.businessLogic.document.AmandmanManager;
import hello.businessLogic.document.UsersManager;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.entity.gov.gradskaskupstina.Amandmani;
import hello.entity.gov.gradskaskupstina.User;
import hello.security.Encrypt;
import hello.security.EncryptKEK;
import org.apache.fop.apps.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import static com.sun.jersey.core.util.ReaderWriter.BUFFER_SIZE;

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


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)auth.getPrincipal();


        ArrayList<Akt> aktovi = aktManager.getAllFilesProposed();
        System.out.println("ALL: "+aktovi.size());
        ArrayList<Akt> aktovi2 = aktManager.getAllFilesApproved();
        System.out.println("ALL: "+aktovi2.size());
        for(int i=aktovi.size()-1; i>=0; i--){
            if(!aktovi.get(i).getUserName().equals(user.getUsername())){
                aktovi.remove(i);
            }
        }
        for(int i=aktovi2.size()-1; i>=0; i--){
            if(!aktovi2.get(i).getUserName().equals(user.getUsername())){
                aktovi2.remove(i);
            }
        }
        System.out.println("MY: "+aktovi.size());
        System.out.println("MY: "+aktovi2.size());


        HashMap<String,ArrayList<Akt>> returnTwoLists = new HashMap<String,ArrayList<Akt>>();
        returnTwoLists.put("proposed",aktovi);
        returnTwoLists.put("approved",aktovi2);
        return new ResponseEntity(returnTwoLists, HttpStatus.OK);
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

    /*prihvati akt nekako ili nemoj*/
    @PreAuthorize("hasRole('ROLE_PREDSEDNIK')")
    @RequestMapping(value = "/prihvatiovono",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity aktmadafaka(@RequestBody ArrayList<String> params){
        logger.info("Called REST for accepting or rejecting acts and amandments");

        //*******************
        //TODO - tamo gde se akt prihvata konacno (nakon sto se primene amandmana ILI nakon sto se obiju amandmani
        //       a ne nakon prihvatanja u nacelu! ! !
        //       ozvati metodu iz aktmanagera - archiveIt(Akt a) koja ce taj akt poslati arhivi
        //*******************

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        if (params.get(0).equals("ODBIJASE")){
            aktManager.deleteAkt(params.get(1));
            ArrayList<Amandmani> amandmani = amandmanManager.getAllAmandmansForAkt(params.get(1));
            for (Amandmani amandman : amandmani){
                amandmanManager.deleteAmandman(amandman.getDocumentId());
            }
        } else if (params.get(0).equals("PRIHVATASE")){
            Akt akt = aktManager.read(params.get(1),false);
            aktManager.approveAkt(akt,user);

            ArrayList<Amandmani> amandmanis =  new ArrayList<>();
            for (int i = 2; i < params.size();i++){
                amandmanis.add(amandmanManager.read(params.get(i),false));
            }

            for (Amandmani amandmani: amandmanis){
                amandmanManager.approveAmandman(amandmani,user);
            }

            aktManager.applyAmendments(amandmanis,akt,user);

            amandmanis = amandmanManager.getAllAmandmansForAkt(akt.getDocumentId());
            for (Amandmani amandmani: amandmanis){
                amandmanManager.deleteAmandman(amandmani.getDocumentId());
            }
        }
        return new ResponseEntity("",HttpStatus.OK);
    }


    @RequestMapping(value="/download/{fileName}",
            method=RequestMethod.POST)
    public void doDownload(HttpServletRequest request,
                           HttpServletResponse response,
                           @PathVariable String fileName) throws IOException {
        logger.info("Called REST for downloading PDF file of act "+ fileName);
        fileName += ".xml";
        aktManager.generatePdf(fileName);
        response.setContentType("application/pdf");
        try (InputStream is = new FileInputStream(new File("Akt.pdf"))){
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            logger.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

}