package hello.rest;


import hello.businessLogic.document.AktManager;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.security.EncryptKEK;
import hello.security.KeyStoreManager;
import hello.util.SednicaManager;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import javax.crypto.SecretKey;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.security.cert.Certificate;
import java.util.ArrayList;

import static com.sun.jersey.core.util.ReaderWriter.BUFFER_SIZE;

/**
 * Created by aloha on 24-May-16.
 */
@RestController
@RequestMapping("/api")
public class TestRest {


    private int count=0;



    //do not touch!
    @RequestMapping(value="/file0",
            method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadStuff()
            throws IOException {
        //String fullPath = stuffService.figureOutFileNameFor(stuffId);
        File file = new File("opa.pdf");

        HttpHeaders respHeaders = new HttpHeaders();
       respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        respHeaders.setContentLength(12345678);
        respHeaders.setContentDispositionFormData("attachment", "opa.pdf");

        InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
        return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
    }




    @RequestMapping(value="/downloadLogFile/{id}")
    public void getLogFile(HttpSession session, HttpServletResponse response, @PathVariable String id) throws Exception {
        try {
            String filePathToBeServed = "opa.pdf";//complete file name with path;
                    File fileToDownload = new File(filePathToBeServed);
            InputStream inputStream = new FileInputStream(fileToDownload);
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment; filename="+"opa"+".pdf");
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (Exception e){
            //LOGGER.debug("Request could not be completed at this moment. Please try again.");
            e.printStackTrace();
        }

    }

    //do not touch
        @RequestMapping(value="/file/{id}",
            method=RequestMethod.GET,
        produces = "application/pdf")
        public void doDownload(HttpServletRequest request,
                               HttpServletResponse response
                                ,@PathVariable String id
                                    ) throws IOException {

            // get absolute path of the application
            ServletContext context = request.getServletContext();
            //String appPath = context.getRealPath("");
            //System.out.println("appPath = " + appPath);

            // construct the complete absolute path of the file
            String fullPath = "opa.pdf";
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
            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;

            // write bytes read from the input stream into the output stream
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outStream.close();

        }



    @PreAuthorize("hasRole('ROLE_ODBORNIK')")
    @RequestMapping("/test1")
    public HttpStatus test1() {
        //notacije ne rade uopste WTFFFFFFFFFFFFFFF
        System.out.println(new SednicaManager().getSednicaStatus());
        return HttpStatus.OK;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/test2")
    public String test2() {
        //that link generates random JSON

       // http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html#postForObject-java.lang.String-java.lang.Object-java.lang.Class-java.lang.Object...-
        //RestTemplate restTemplate = new RestTemplate();
        //String s=restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", String.class);

        RestTemplate rt=new RestTemplate();
        rt.put("https://localhost:8080/api/getsednicastatus/", "", "");


        return "";
    }


    @RequestMapping("/test3")
    public HttpStatus test3(@RequestBody String s) {



       // System.out.println("NASLOV IZ OBJ AKT: "+document.getNaslov());*/
        return HttpStatus.OK;
    }



    @RequestMapping(value = "/enc/")
    public ResponseEntity test4() {

        //USER: a with PASS: a
        String usr="b";
        String pass = "cwlSgmlVvpTFea3TACUBKoME8pI=";

        AktManager akm = new AktManager();
        ArrayList<Akt> akts= akm.getAllFilesProposed();
        Akt akt1=akts.get(0);
        /*make key*/
        EncryptKEK encKEK=new EncryptKEK();
        SecretKey secretKey = encKEK.generateDataEncryptionKey();
        /*get user*/
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //User user = (User) auth.getPrincipal();
        /*get cet fot that user*/
        KeyStoreManager ksm=new KeyStoreManager();
        Certificate cert = ksm.readCertificate(usr, pass.toCharArray());
        /*make xml file*/
        boolean status = akm.convertToXml(akt1);
        System.out.println("CONVERT STATUS:"+status);
        /*load that xml file as Documet*/
        //SignEnveloped signEnveloped = new SignEnveloped();
        //Document doc=signEnveloped.loadDocument("tmp.xml");
        Document doc = encKEK.loadDocument("./data/tmp.xml");
        /*encrypt that doc*/
        //////////////// prosirio sa USER
        //doc=encKEK.encrypt(doc, secretKey, cert);
        encKEK.saveDocument(doc, "./data/tmpENCR.xml");

        System.out.println("ENCRYPTED DOC");

        return new ResponseEntity("springara", HttpStatus.ACCEPTED);
    }



    @RequestMapping(value = "/enc2/")
    public ResponseEntity test5() {

        //load doc from data/tmp.xlm
        //enctypt it and save it to database

        EncryptKEK e=new EncryptKEK();
        Document doc = e.encryptTEST();

        AktManager am=new AktManager();
        am.convertFromXml(new File("./data/IDEMOOOO.xml"));
        InputStream is=am.convertDocumentToInputStream(doc);
       // am.write(is, "arg", "arh", false, null);

        return new ResponseEntity("springara2", HttpStatus.ACCEPTED);
    }


    @RequestMapping(value = "/pdf", method = RequestMethod.GET, produces = "application/octet-stream")
    public ResponseEntity<InputStreamResource> downloadPDFFile()
            throws IOException {

        ClassPathResource pdfFile = new ClassPathResource("akt1.xml");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(pdfFile.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(pdfFile.getInputStream()));
    }








}
