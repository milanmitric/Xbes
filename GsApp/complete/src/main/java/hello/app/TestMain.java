package hello.app;

import hello.Application;
import hello.businessLogic.core.BeanManager;
import hello.businessLogic.core.ReadManager;
import hello.businessLogic.core.WriteManager;
import hello.businessLogic.document.AktManager;
import hello.businessLogic.document.AmandmanManager;
import hello.entity.gov.gradskaskupstina.*;
import hello.security.KeyStoreManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by milan on 4.6.2016..
 */
public class TestMain {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws FileNotFoundException {
        try{
            deleteAkts();
        } catch (Exception e){

        }
    }

    public static void reinitializeKeyStore(){
        KeyStoreManager keyStoreManager = new KeyStoreManager();
        if (!keyStoreManager.reinitializeKeyStore()){
            logger.info("Error initializing keystore.");
        } else {
            logger.info("Successfully initialized keystore!");
        }
    }
    public static void testXQuery() {
        String query = "fn:collection(\"userscoll\")";
        BeanManager<Users> aktBeanManager = new BeanManager<>("schema/Users.xsd");


        ArrayList<Users> aktovi = aktBeanManager.executeQuery(query);
        for (Users users: aktovi) {
            System.out.println(users);
        }
    }



    public static void deleteAkts(){
        KeyStoreManager keyStoreManager = new KeyStoreManager();
        keyStoreManager.reinitializeKeyStore();

    }

    public static void testSignature(){

        ReadManager<Akt> aktReadManager = new ReadManager<>();
        WriteManager<Akt> aktWriteManager = new WriteManager<>();
        BeanManager<Akt> aktBeanManager = new BeanManager<>();

        try{
            aktBeanManager.read("test",true);
        } catch (Exception e){
            logger.info("ERROR");
        }
        logger.info("SUCCESS");
    }

    public static void transform() throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("Akt.xsl"));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File("tmp.xml"));
        transformer.transform(text, new StreamResult(new File("transform/Akt.html")));
    }

    public static void testApply(){
        AktManager aktManager = new AktManager();
        AmandmanManager amandmanManager = new AmandmanManager();



    }

    public static void test(){
        User user = new User();
        user.setUsername("c");
        user.setPassword("ojnWxlCvy+RHqqugm+yaF+Yhn34=");

        testAkt(user);
        //testAmandman(user);
    }

    public static void testAkt(User user){
        AktManager aktManager = new AktManager();
        Akt akt = aktManager.convertFromXml(new File("tmp.xml"));

        String uri = aktManager.proposeAkt(akt,user);
        akt = aktManager.read(uri,true);
        aktManager.convertToXml(akt);
        akt = aktManager.convertFromXml(new File("tmp.xml"));
    }

    public static void testAmandman(User user) {
        AmandmanManager amandmanManager = new AmandmanManager();
        Amandmani amandmani = amandmanManager.convertFromXml(new File("tmpForValidation.xml"));

        amandmanManager.convertToXml(amandmani);
        amandmani = amandmanManager.convertFromXml(new File("tmpForValidation.xml"));
        String uri = amandmanManager.proposeAmandman(amandmani, user);
        amandmani = amandmanManager.read(uri,true);
        amandmanManager.convertToXml(amandmani);
        amandmani = amandmanManager.convertFromXml(new File("tmp.xml"));
    }

    public static void testDelete(){
        AktManager aktManager = new AktManager();

        TReferenca referenca = new TReferenca();
        referenca.setRefDeo("1");
        referenca.setRefGlava("1");
        referenca.setRefOdeljak("1");
        referenca.setRefClanovi("2321");
        String type = "BRISANJE";
        String tekst = new String();
        String docId = "12022005771098834329.xml";

        String query = aktManager.generateXquery(referenca,type,tekst,docId);
        aktManager.executeQuery(query);
    }

    public static void testUpdate(){
        AktManager aktManager = new AktManager();
        TReferenca referenca = new TReferenca();
        referenca.setRefDeo("2");
        referenca.setRefGlava("3");
        referenca.setRefClanovi("5");
        //referenca.setRefStavovi("666");

        String izmena = "<Stav RedniBroj=\"44\" xmlns=\"http://www.gradskaskupstina.gov/\">" +
                            "<Tekst>NEKI dwadaw TEKST</Tekst>" +
                        "</Stav>";

        String docId = "2746325830753861621.xml";
        String query = aktManager.generateXquery(referenca,"BRISANJE",izmena,docId);
        aktManager.executeQuery(query);
    }
    public static void testAdd(){
        AktManager manager = new AktManager();
        AmandmanManager amandmanManager = new AmandmanManager();

        Amandmani amandmani = amandmanManager.convertFromXml(new File("tmp.xml"));
        ArrayList<Amandmani> lista = new ArrayList<>();
        lista.add(amandmani);
        manager.applyAmendments(lista,null,null);
    }

    public static  void generatingPdf(){
        /*
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
        StreamSource source = new StreamSource(new File("tmp.xml"));
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
        File pdfFile = new File("Akt.pdf");
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
        }*/


    }
}

