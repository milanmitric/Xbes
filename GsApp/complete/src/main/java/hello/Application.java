package hello;


import com.marklogic.client.DatabaseClient;
import hello.businessLogic.BeanManager;
import hello.util.Database;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.entity.gov.gradskaskupstina.Amandman;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws IOException {

        //SpringApplication.run(Application.class, args);

        testAkt();
        testAmandman();


        DatabaseClient client = Database.getDbClient();
        client.release();
    }

    public static void testAkt(){
        BeanManager<Akt> aktManager = new BeanManager<>();

        String docId = "/test17/proba2.xml";
        File file = new File("res/validationTest/AktZOIIDZOJPPIK.xml");
        if(file != null) {
            Akt inputAkt = aktManager.convertFromXml(file);
            if(aktManager.validateBeanBySchema(inputAkt))
                logger.info("Schema validation: " + inputAkt + " is valid!");
            else
                logger.info("Schema validation: " + inputAkt + "is NOT valid");
            if (!aktManager.write(inputAkt, docId, "Proba")) {
                //System.out.println("Could't write akt!");
                logger.info("Could't write akt!");
            } else {
                //System.out.println("Write successful!");
                logger.info("Write successful!");

                // Citamo upravo upisani akt iz mark logic baze
                Akt akt = aktManager.read(docId);
                if(aktManager.validateBeanBySchema(akt))
                    logger.info("Schema validation: " + akt + " is valid!");
                else
                    logger.info("Schema validation: " + akt + "is NOT valid");
            }
        }
    }

    public static void testAmandman(){
        BeanManager<Amandman> amandmanManager = new BeanManager<>("schema/Amandmani.xsd");

        String docId = "/test17/proba2.xml";
        File file = new File("res/validationTest/AmandmanPZOIIDZOJPPIK.xml");
        if(file != null) {
            Amandman inputAmandman = amandmanManager.convertFromXml(file);
            if(amandmanManager.validateBeanBySchema(inputAmandman))
                logger.info("Schema validation: " + inputAmandman + " is valid!");
            else
                logger.info("Schema validation: " + inputAmandman + "is NOT valid");
            if (!amandmanManager.write(inputAmandman, docId, "Proba")) {
                //System.out.println("Could't write akt!");
                logger.info("Could't write akt!");
            } else {
                //System.out.println("Write successful!");
                logger.info("Write successful!");

                // Citamo upravo upisani akt iz mark logic baze
                Amandman amandman = amandmanManager.read(docId);
                if(amandmanManager.validateBeanBySchema(amandman))
                    logger.info("Schema validation: " + amandman + " is valid!");
                else
                    logger.info("Schema validation: " + amandman + "is NOT valid");
            }
        }
    }
}
