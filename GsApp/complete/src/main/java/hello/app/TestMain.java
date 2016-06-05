package hello.app;

import com.marklogic.client.document.DocumentDescriptor;
import hello.Application;
import hello.businessLogic.document.AktManager;
import hello.businessLogic.core.BeanManager;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.entity.gov.gradskaskupstina.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by milan on 4.6.2016..
 */
public class TestMain {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args){
        testAktManager();
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

        DocumentDescriptor desc = aktManager.proposeAkt(akt);
        logger.info("Successfully proposed document [" + desc.getUri() + "].");
        desc  = aktManager.approveAkt(akt,desc.getUri());
        logger.info("Successfully approved document [" + desc.getUri() + "].");

    }
}

