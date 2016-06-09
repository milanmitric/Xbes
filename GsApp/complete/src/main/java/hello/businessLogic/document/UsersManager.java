package hello.businessLogic.document;

import hello.businessLogic.core.BeanManager;
import hello.entity.gov.gradskaskupstina.User;
import hello.entity.gov.gradskaskupstina.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Nebojsa on 6/5/2016.
 */
public class UsersManager extends BeanManager<Users> {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UsersManager(){
        super("schema/Users.xsd");
    }
    /**
     *
     * @param docId Document URI to read from database.
     * @return
     */
    public Users read(String docId){
        // Users xml document is not signed so it won't be validated by digital signature.
        boolean shouldValidate = false;
        return super.read(docId, shouldValidate);
    }

    /**
     * Writes Users to database.
     * @param users JAXB bean to be written.
     * @param docId URI for document to be written.
     * @param colId URI for collection if the docue.
     * @return Indicator of success.
     */
    public  boolean write(Users  users, String docId, String colId, User user) {
        // TODO: Generate certificate for user.
        if (!generateCertificate(user)){
            logger.info("[ERROR] can't generate certificate for user.");
            return false;
        }
        // Users xml should never be signed!
        boolean shouldSign = false;
        return super.write(users,docId,colId,shouldSign,null);
    }

    /**
     * Generates certificate and saves it to file. Sets alias as users' username and password as users' password.
     * @param user User infos needed for certificate.
     * @return Indicator of success.
     */
    protected boolean generateCertificate(User user){
        return super.generateCertificate(user);
    }
}
