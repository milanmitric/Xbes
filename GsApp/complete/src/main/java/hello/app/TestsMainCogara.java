package hello.app;

import java.io.*;
import hello.Application;
import hello.StringResources.MarkLogicStrings;
import hello.businessLogic.document.AktManager;
import hello.businessLogic.document.UsersManager;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.entity.gov.gradskaskupstina.User;
import hello.entity.gov.gradskaskupstina.Users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;


/**
 * Created by milan on 4.6.2016..
 */
public class TestsMainCogara {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static User user = null;

   /* static {
        user = new User();
        user.setIme("test");
        user.setPrezime("test");
        user.setEmail("test@gradskaskupstina.gov");
        user.setUsername("test");
        user.setPassword("A18MXitD+Dynjr+mbSnU8Zqir5M=");
        user.setSalt("xAxoT8uwGUA=");
        user.setRole("ROLE_PREDSEDNIK");
    }*/


    static {
        //for localhost testing only
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier(){

                    public boolean verify(String hostname,
                                          javax.net.ssl.SSLSession sslSession) {
                        if (hostname.equals("localhost")) {
                            return true;
                        }
                        return false;
                    }
                });
    }



    public static void main(String[] args){
        //proposeAkt();
       /* try {
            callRest();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        RestTemplate restTemplate = new RestTemplate();
        String s=restTemplate.getForObject("http://localhost:8080/api/getsednicastatus/", String.class);
        System.out.println("RESPONSE: "+s);

    }


    public static void deleteAllUsers(){
        UsersManager usersManager = new UsersManager();
        Users users = null;
        users = usersManager.read(MarkLogicStrings.USERS_DOC_ID);
        users.getUser().clear();
        usersManager.write(users,MarkLogicStrings.USERS_DOC_ID,MarkLogicStrings.USERS_COL_ID,false,null);
    }

    public static void addUser(){
        UsersManager usersManager = new UsersManager();
        Users users = null;
        users = usersManager.read(MarkLogicStrings.USERS_DOC_ID);
        users.getUser().add(user);
        usersManager.write(users,MarkLogicStrings.USERS_DOC_ID,MarkLogicStrings.USERS_COL_ID,user);
    }

    public static void deleteAkts(){
        AktManager aktManager = new AktManager();
        aktManager.deleteAkt("2746325830753861621.xml");
    }

    public static void proposeAkt(){
        // User test dodaje probni akt koji se nalazi u fajlu res/validationTest/akt1.xml
        AktManager aktManager = new AktManager();
        File xmlFile = new File("res/validationTest/akt1.xml");
        Akt akt = aktManager.convertFromXml(xmlFile);
        aktManager.proposeAkt(akt, user);
    }

    public static void callRest() throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        String s=restTemplate.getForObject("https://localhost:9090/api/getsednicastatus/", String.class);
        System.out.println("RESPONSE: "+s);

    }









}










