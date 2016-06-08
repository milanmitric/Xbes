package hello.businessLogic.document;

import hello.businessLogic.core.BeanManager;
import hello.entity.gov.gradskaskupstina.Users;

/**
 * Created by Nebojsa on 6/5/2016.
 */
public class UsersManager extends BeanManager<Users> {


    public UsersManager(){
        super("schema/Users.xsd");
    }
    /**
     *
     * @param docId Document URI to read from database.
     * @return
     */
    @Override
    public Users read(String docId){
        return super.read(docId);
    }

    /**
     * Writes Users to database.
     * @param users JAXB bean to be written.
     * @param docId URI for document to be written.
     * @param colId URI for collection if the docue.
     * @return Indicator of success.
     */
    @Override
    public  boolean write(Users  users, String docId, String colId) {
        return super.write(users,docId,colId);
    }



}
