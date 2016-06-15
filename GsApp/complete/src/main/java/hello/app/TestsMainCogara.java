package hello.app;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import hello.Application;
import hello.StringResources.MarkLogicStrings;
import hello.StringResources.Role;
import hello.businessLogic.document.AktManager;
import hello.businessLogic.document.AmandmanManager;
import hello.businessLogic.document.UsersManager;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.entity.gov.gradskaskupstina.Amandmani;
import hello.entity.gov.gradskaskupstina.User;
import hello.entity.gov.gradskaskupstina.Users;
import hello.security.PasswordStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by milan on 4.6.2016..
 */
public class TestsMainCogara {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static User user = null;

    static {
        user = new User();
        user.setIme("TestIme");
        user.setPrezime("TestPrezime");
        user.setEmail("test@gradskaskupstina.gov");
        user.setUsername("test");
        user.setPassword("test");
        byte[] salt = new byte[0];
        try {
            salt = PasswordStorage.generateSalt();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        user.setSalt(PasswordStorage.base64Encode(salt));
        /*hash pass*/
        byte[] hashedPassword = new byte[0];
        try {
            hashedPassword = PasswordStorage.hashPassword(user.getPassword(), salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        user.setPassword(PasswordStorage.base64Encode(hashedPassword));
        user.setRole(Role.ROLE_ODBORNIK);
    }

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
        return;
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
        User user2 = new User();
        user2.setIme("Zoran");
        user2.setPrezime("Precednik");
        user2.setEmail("shone@gradskaskupstina.gov");
        user2.setUsername("zoki");
        user2.setPassword("zoki");
        byte[] salt = new byte[0];
        try {
            salt = PasswordStorage.generateSalt();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        user2.setSalt(PasswordStorage.base64Encode(salt));
        /*hash pass*/
        byte[] hashedPassword = new byte[0];
        try {
            hashedPassword = PasswordStorage.hashPassword(user2.getPassword(), salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        user2.setPassword(PasswordStorage.base64Encode(hashedPassword));
        user2.setRole(Role.ROLE_PREDSEDNIK);
        users.getUser().add(user2);

        usersManager.write(users,MarkLogicStrings.USERS_DOC_ID,MarkLogicStrings.USERS_COL_ID,user);
    }

    public static void reverseUsersToInitial(){
        UsersManager usersManager = new UsersManager();
        Users users = new Users();
        users.getUser().add(user);

        {
            User user2 = new User();
            user2.setIme("Zoran");
            user2.setPrezime("Precednik");
            user2.setEmail("shone@gradskaskupstina.gov");
            user2.setUsername("zoki");
            user2.setPassword("zoki");
            byte[] salt2 = new byte[0];
            try {
                salt2 = PasswordStorage.generateSalt();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            user2.setSalt(PasswordStorage.base64Encode(salt2));
        /*hash pass*/
            byte[] hashedPassword2 = new byte[0];
            try {
                hashedPassword2 = PasswordStorage.hashPassword(user2.getPassword(), salt2);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                System.out.println(e.toString());
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
                System.out.println(e.toString());
            }
            user2.setPassword(PasswordStorage.base64Encode(hashedPassword2));
            user2.setRole(Role.ROLE_PREDSEDNIK);
            users.getUser().add(user2);
        }
        {
            User user2 = new User();
            user2.setIme("Бранислав");
            user2.setPrezime("Чогић");
            user2.setEmail("chogara@gradskaskupstina.gov");
            user2.setUsername("chogara");
            user2.setPassword("chogara");
            byte[] salt2 = new byte[0];
            try {
                salt2 = PasswordStorage.generateSalt();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            user2.setSalt(PasswordStorage.base64Encode(salt2));
        /*hash pass*/
            byte[] hashedPassword2 = new byte[0];
            try {
                hashedPassword2 = PasswordStorage.hashPassword(user2.getPassword(), salt2);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                System.out.println(e.toString());
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
                System.out.println(e.toString());
            }
            user2.setPassword(PasswordStorage.base64Encode(hashedPassword2));
            user2.setRole(Role.ROLE_ADMINISTRATOR);
            users.getUser().add(user2);
        }
        {
            User user2 = new User();
            user2.setIme("Radomir");
            user2.setPrezime("Marinkovic");
            user2.setEmail("rasha@gradskaskupstina.gov");
            user2.setUsername("rasha");
            user2.setPassword("rasha");
            byte[] salt2 = new byte[0];
            try {
                salt2 = PasswordStorage.generateSalt();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            user2.setSalt(PasswordStorage.base64Encode(salt2));
        /*hash pass*/
            byte[] hashedPassword2 = new byte[0];
            try {
                hashedPassword2 = PasswordStorage.hashPassword(user2.getPassword(), salt2);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                System.out.println(e.toString());
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
                System.out.println(e.toString());
            }
            user2.setPassword(PasswordStorage.base64Encode(hashedPassword2));
            user2.setRole(Role.ROLE_ADMINISTRATOR);
            users.getUser().add(user2);
        }
        {
            User user2 = new User();
            user2.setIme("Nebojsa");
            user2.setPrezime("Popovic");
            user2.setEmail("shone@gradskaskupstina.gov");
            user2.setUsername("shone");
            user2.setPassword("shone");
            byte[] salt2 = new byte[0];
            try {
                salt2 = PasswordStorage.generateSalt();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            user2.setSalt(PasswordStorage.base64Encode(salt2));
        /*hash pass*/
            byte[] hashedPassword2 = new byte[0];
            try {
                hashedPassword2 = PasswordStorage.hashPassword(user2.getPassword(), salt2);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                System.out.println(e.toString());
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
                System.out.println(e.toString());
            }
            user2.setPassword(PasswordStorage.base64Encode(hashedPassword2));
            user2.setRole(Role.ROLE_ADMINISTRATOR);
            users.getUser().add(user2);
        }
        {
            User user2 = new User();
            user2.setIme("Milan");
            user2.setPrezime("Mitric");
            user2.setEmail("mitra@gradskaskupstina.gov");
            user2.setUsername("mitra");
            user2.setPassword("mitra");
            byte[] salt2 = new byte[0];
            try {
                salt2 = PasswordStorage.generateSalt();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            user2.setSalt(PasswordStorage.base64Encode(salt2));
        /*hash pass*/
            byte[] hashedPassword2 = new byte[0];
            try {
                hashedPassword2 = PasswordStorage.hashPassword(user2.getPassword(), salt2);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                System.out.println(e.toString());
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
                System.out.println(e.toString());
            }
            user2.setPassword(PasswordStorage.base64Encode(hashedPassword2));
            user2.setRole(Role.ROLE_ADMINISTRATOR);
            users.getUser().add(user2);
        }

        usersManager.write(users,MarkLogicStrings.USERS_DOC_ID,MarkLogicStrings.USERS_COL_ID,user);
    }

    public static void deleteAkts(){
        AktManager aktManager = new AktManager();
        aktManager.deleteAkt("");
    }

    public static void deleteAmandmans(){
        AmandmanManager amandmanManager = new AmandmanManager();
        amandmanManager.deleteAmandman("1136342900533315589.xml");
        amandmanManager.deleteAmandman("5637100032808720681.xml");
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

    public static void proposeAmandman(){
        // User test dodaje probni akt koji se nalazi u fajlu res/validationTest/akt1.xml
        AmandmanManager amandmanManager = new AmandmanManager();
        File xmlFile = new File("res/validationTest/amandmanProba1.xml");
        Amandmani amandman = amandmanManager.convertFromXml(xmlFile);
        amandmanManager.proposeAmandman(amandman, user);
    }

    public static void applyAmandmans() {
        AktManager aktManager = new AktManager();
        AmandmanManager amandmanManager = new AmandmanManager();
        ArrayList<Amandmani> amandmani = amandmanManager.getAllAmendmentProposed();
        Akt akt = aktManager.read("8348725606370910161.xml", false);
        aktManager.applyAmendments(amandmani, akt, user);
    }
}