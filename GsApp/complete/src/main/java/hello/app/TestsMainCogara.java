package hello.app;

import hello.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by milan on 4.6.2016..
 */
public class TestsMainCogara {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    /*
    public static void main(String[] args){

        testAkt();
        //testAmandman();
        //testUsers();

        DatabaseClient client = Database.getDbClient();
        client.release();
    }



    public static void testAkt(){
        BeanManager<Akt> aktManager = new BeanManager<>();

        String docId = "/test17/jasamZakon.xml";
        File file = new File("res/validationTest/AktZOIIDZOJPPIK.xml");
        if(file != null) {
            Akt inputAkt = aktManager.convertFromXml(file);
            if(aktManager.validateBeanBySchema(inputAkt))
                logger.info("Schema validation: " + inputAkt + " is valid!");
            else
                logger.info("Schema validation: " + inputAkt + "is NOT valid");
            if (!aktManager.write(inputAkt, docId, "Proba")) {
                //System.out.println("Could't write document!");
                logger.info("Could't write document!");
            } else {
                //System.out.println("Write successful!");
                logger.info("Write successful!");

                // Citamo upravo upisani document iz mark logic baze
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

        String docId = "/test17/zalimoSe.xml";
        File file = new File("res/validationTest/AmandmanPZOIIDZOJPPIK.xml");
        if(file != null) {
            Amandman inputAmandman = amandmanManager.convertFromXml(file);
            if(amandmanManager.validateBeanBySchema(inputAmandman))
                logger.info("Schema validation: " + inputAmandman + " is valid!");
            else
                logger.info("Schema validation: " + inputAmandman + "is NOT valid");
            if (!amandmanManager.write(inputAmandman, docId, "Proba")) {
                //System.out.println("Could't write document!");
                logger.info("Could't write document!");
            } else {
                //System.out.println("Write successful!");
                logger.info("Write successful!");

                // Citamo upravo upisani document iz mark logic baze
                Amandman amandman = amandmanManager.read(docId);
                if(amandmanManager.validateBeanBySchema(amandman))
                    logger.info("Schema validation: " + amandman + " is valid!");
                else
                    logger.info("Schema validation: " + amandman + "is NOT valid");
            }
        }
    }

    public static void testUsers(){
        BeanManager<Users> userManager = new BeanManager<>("schema/Users.xsd");

        //String docId = "/test17/coveci.xml";
        File file = new File("res/validationTest/users1.xml");
        if(file != null) {
            Users inputUsers = userManager.convertFromXml(file);
            if(userManager.validateBeanBySchema(inputUsers))
                logger.info("Schema validation: " + inputUsers + " is valid!");
            else
                logger.info("Schema validation: " + inputUsers + "is NOT valid");
            if (!userManager.write(inputUsers, MarkLogicStrings.USERS_DOC_ID, MarkLogicStrings.USERS_DOC_ID)) {


                //System.out.println("Could't write document!");
                logger.info("Could't write document!");
            } else {
                //System.out.println("Write successful!");
                logger.info("Write successful!");

                // Citamo upravo upisani document iz mark logic baze
                Users users = userManager.read(MarkLogicStrings.USERS_DOC_ID);
                if(userManager.validateBeanBySchema(users))
                    logger.info("Schema validation: " + users + " is valid!");
                else
                    logger.info("Schema validation: " + users + "is NOT valid");
            }
        }
    }*/
}
