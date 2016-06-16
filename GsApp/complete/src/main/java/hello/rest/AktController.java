package hello.rest;

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import hello.StringResources.MarkLogicStrings;
import hello.businessLogic.document.AktManager;
import hello.businessLogic.document.AmandmanManager;
import hello.businessLogic.document.UsersManager;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.entity.gov.gradskaskupstina.Amandmani;
import hello.entity.gov.gradskaskupstina.User;
import hello.security.EncryptKEK;
import org.apache.fop.apps.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.print.attribute.standard.Media;
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
        logger.info("Called REST for PRIHVATIOVOONO");

        //*******************
        //TODO - tamo gde se akt prihvata konacno (nakon sto se primene amandmana ILI nakon sto se obiju amandmani
        //       a ne nakon prihvatanja u nacelu! ! !
        //       ozvati metodu iz aktmanagera - archiveIt(Akt a) koja ce taj akt poslati arhivi
        //*******************

        //System.out.println("AMANDMANI: "+amandmants);
        //uvek stigne lista, 0. u nizu je UVEK ID od AKTA, a nakon toga idu ID od amandmana
        //String aktID=amandmants.get(0);

        if(params.get(0).equals("ODBIJAJUSE")){
            logger.info("All amandments are being rejected");
            //TODO - SVI AMANDMAN ZA AKT SE ODBIJAJU
            //AKT ID: amandmants.get(1) //AKT JE PRIHVACEN U NACELU VEC
            //AMANDMANI ID get(2+n) n=0,1,2...Size
        } else
        if(params.get(0).equals("AKTSEODBIJA")){
            logger.info("Act is being rejected");
            //TODO - AKT SE ODBIJA
            //AKT ID: amandmants.get(1)
            //AMANDMANI ID get(2+n) n=0,1,2...Size
        }else {
            if (params.size() == 1) {
                logger.info("Act is accepted in principle");
                //TODO - AKT SE PRIHVATA SE U NACELU
                //AKT ID : amandmants.get(0)
            } else {
                logger.info("Amandments are being accepted");
                //TODO - USVAJAJU SE AMNDMANI NA AKT
                //AKT ID : amandmants.get(0) //AKT JE PRIHVACEN U NACELU VEC
                //1. , 2. , 3. ... ID od AMANDMANA
                ArrayList<Amandmani> amandmani = new ArrayList<>();
                for(int i = 1 ; i<params.size() ; i++) {
                    Amandmani amandman = amandmanManager.read(params.get(i), false);
                    amandmani.add(amandman);
                }
                Akt akt = aktManager.read(params.get(0), false);
                User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                aktManager.applyAmendments(amandmani, akt, user);
            }
        }

        return new ResponseEntity("",HttpStatus.OK);
    }




    //TODO - crap - not working
    @RequestMapping(value="/download",
            method=RequestMethod.POST,
            produces = "application/pdf")
    public void doDownload(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam String docID) throws IOException {
        logger.info("Called REST for downloading");

        System.out.println("PATH VAR: "+docID);
        //String[] parts=docID.split('.');
        //docID=parts[0];
        System.out.println("PATH VAR: "+docID);
        // Get your file stream from wherever.
        //InputStream myStream = new //someClass.returnFile();

        Akt a=aktManager.read(docID, false);
        aktManager.convertToXml_withCustomName(a, docID);

        //------------------------------//
        //SONARA CODE FOR xml_TO_PDF

        FopFactory fopFactory=null;
        // Initialize FOP factory object
        try {
            fopFactory = FopFactory.newInstance(new File("fop.xconf"));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TransformerFactory transformerFactory = new TransformerFactoryImpl();
        // Point to the XSL-FO file
        File xsltFile = new File("Akt_fo.xsl");
        // Create transformation source
        StreamSource transformSource = new StreamSource(xsltFile);
        // Initialize the transformation subject
        StreamSource source = new StreamSource(new File(docID+".xml"));
        // Initialize user agent needed for the transformation
        FOUserAgent userAgent = fopFactory.newFOUserAgent();
        // Create the output stream to store the results
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        // Initialize the XSL-FO transformer object
        Transformer xslFoTransformer = null;
        try {
            xslFoTransformer = transformerFactory.newTransformer(transformSource);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

        // Construct FOP instance with desired output format
        Fop fop = null;
        try {
            fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);
        } catch (FOPException e) {
            e.printStackTrace();
        }

        // Resulting SAX events
        Result res = null;
        try {
            res = new SAXResult(fop.getDefaultHandler());
        } catch (FOPException e) {
            e.printStackTrace();
        }

        // Start XSLT transformation and FOP processing
        try {
            xslFoTransformer.transform(source, res);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        // Generate PDF file
        File pdfFile = new File(docID+".pdf");
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(pdfFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            out.write(outStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("[INFO] File \"" + pdfFile.getCanonicalPath() + "\" generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


       //------my my my my my --------///

        // Set the content type and attachment header.
      /*   response.addHeader("Content-disposition", "attachment;filename="+docID+".pdf");
        response.setContentType("application/pdf");

        // Copy the stream to the response's output stream.
        //IOUtils.copy(myStream, response.getOutputStream());
        try {
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        ServletContext context = request.getServletContext();
        //String appPath = context.getRealPath("");
        //System.out.println("appPath = " + appPath);

        // construct the complete absolute path of the file
        String fullPath = docID+".pdf";
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream2 = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream2.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream2.close();

    }





}