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

    public static void testAktManager(){
        AktManager aktManager = new AktManager();
        Akt akt = aktManager.convertFromXml(new File("res/validationTest/AktZOIIDZOJPPIK.xml"));


        String docId = aktManager.proposeAkt(akt,null);
        if (docId != null){
            logger.info("Successfully proposed document [" + docId + "].");
        } else {
            logger.info("[ERROR] Could not propose document [" + docId + "].");
        }

        docId  = aktManager.approveAkt(akt);
        if (docId != null){
            logger.info("Successfully approved document [" + docId + "].");
        } else {
            logger.info("[ERROR] Could not approve document [" + docId + "].");
        }


    }

    public static void deleteAkts(){
        AmandmanManager amandmanManager = new AmandmanManager();
        amandmanManager.deleteAmandman("11012995581814757290.xml");
        amandmanManager.deleteAmandman("11969076111915410290.xml");
        amandmanManager.deleteAmandman("12461612367349772193.xml");
        amandmanManager.deleteAmandman("1436059187549165823.xml");
        amandmanManager.deleteAmandman("17430024136002101644.xml");
        amandmanManager.deleteAmandman("1753570683026796015.xml");
        amandmanManager.deleteAmandman("17927789539760040072.xml");
        amandmanManager.deleteAmandman("5641641447770094113.xml");
        amandmanManager.deleteAmandman("6568285907668089985.xml");
        amandmanManager.deleteAmandman("6990168228709459453.xml");
        amandmanManager.deleteAmandman("8412181606588559053.xml");
        amandmanManager.deleteAmandman("8680279984619103899.xml");



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
        Source xslt = new StreamSource(new File("transform/Amandman.xsl"));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File("transform/Amandman.xml"));
        transformer.transform(text, new StreamResult(new File("transform/Amandman.html")));
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
}

