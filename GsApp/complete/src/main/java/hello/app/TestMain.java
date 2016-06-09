package hello.app;

import hello.Application;
import hello.StringResources.MarkLogicStrings;
import hello.businessLogic.core.BeanManager;
import hello.businessLogic.core.ReadManager;
import hello.businessLogic.core.WriteManager;
import hello.businessLogic.document.AktManager;
import hello.businessLogic.document.UsersManager;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.entity.gov.gradskaskupstina.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by milan on 4.6.2016..
 */
public class TestMain {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws FileNotFoundException {
        UsersManager manager = new UsersManager();
        FileInputStream fileInputStream = new FileInputStream(new File("res/validationTest/users1.xml"));
        manager.write(fileInputStream, MarkLogicStrings.USERS_DOC_ID,MarkLogicStrings.USERS_COL_ID,false,null);

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
        AktManager aktManager = new AktManager();
        aktManager.deleteAkt("10943157544121059935.xml");
        aktManager.deleteAkt("11514770735810005726.xml");
        aktManager.deleteAkt("13398967872520214127.xml");
        aktManager.deleteAkt("14052306458949503553.xml");
        aktManager.deleteAkt("1433963191818237235.xml");
        aktManager.deleteAkt("15953398522250976203.xml");
        aktManager.deleteAkt("16439675836494731279.xml");
        aktManager.deleteAkt("17265559496333134016.xml");
        aktManager.deleteAkt("2506229216889392209.xml");
        aktManager.deleteAkt("3376611596855260398.xml");
        aktManager.deleteAkt("3515949106954256392.xml");
        aktManager.deleteAkt("3995367421470449471.xml");
        aktManager.deleteAkt("4794372873215895682.xml");
        aktManager.deleteAkt("6354302967360018838.xml");


    }

    public static void testSignature(){

        ReadManager<Akt> aktReadManager = new ReadManager<>();
        WriteManager<Akt> aktWriteManager = new WriteManager<>();
        BeanManager<Akt> aktBeanManager = new BeanManager<>();
        //aktReadManager.validateXMLBySignature("tmpForValidation.xml");
        Akt akt =  aktBeanManager.convertFromXml(new File("tmp.xml"));
        aktBeanManager.convertToXml(akt);
        try{
            aktBeanManager.validateXmlBySchema("tmp.xml");
            aktBeanManager.write(akt,"test","test",true,null);
            aktReadManager.validateXMLBySignature("tmp.xml");

            aktBeanManager.read("test",true);
        } catch (Exception e){
            logger.info("ERROR");
        }
        logger.info("SUCCESS");
    }
}

