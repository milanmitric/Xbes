package hello.app;

import com.marklogic.client.document.DocumentDescriptor;
import hello.Application;
import hello.businessLogic.akt.AktManager;
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


        for(Akt tmpAkt: aktManager.getAllFilesProposed()){
            System.out.println("AKT " + tmpAkt.getNaslov());
        }
        if (aktManager.deleteAkt(desc.getUri())){
            System.out.println("Successfully deleted akt " + desc.getUri());
        } else {
            System.out.println("Could not delete akt!");
        }
    }
}

