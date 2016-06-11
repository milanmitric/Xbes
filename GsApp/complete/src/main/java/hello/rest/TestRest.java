package hello.rest;


import hello.businessLogic.document.AktManager;
import hello.entity.Sednica;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.security.EncryptKEK;
import hello.security.KeyStoreManager;
import hello.util.SednicaManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.util.ArrayList;

/**
 * Created by aloha on 24-May-16.
 */
@RestController
@RequestMapping("/api")
public class TestRest {


    private int count=0;


    @PreAuthorize("hasAuthority('XXX')")
    @RequestMapping("/test0")
    public HttpStatus test0() {
        /*test - to see if rest is working*/
        SednicaManager sm = new SednicaManager();
        sm.updateSednica(false);
        count++;
        System.out.println("COUNT:"+count);
        return HttpStatus.OK;
    }



    @PreAuthorize("hasRole('XXX'")
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
        RestTemplate restTemplate = new RestTemplate();
        String s=restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", String.class);
        return s;
    }


    @RequestMapping("/test3")
    public HttpStatus test3(@RequestBody String s) {

        //http://www.mkyong.com/java/jaxb-hello-world-example/

    /*    JSONObject obj = null;
        try {
             obj=new JSONObject(s);
            System.out.println(obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Akt document=new Akt();
        try {
            document.setNaslov(obj.getString("naslov"));
            //gde validacija ?!
            //moze da se marshal pa da se naprvi XML doc
            //pa to saljemo mark bazi?
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("NASLOV IZ OBJ AKT: "+document.getNaslov());*/
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
        //////////////// prosirio sa USER doc=encKEK.encrypt(doc, secretKey, cert);
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
