package hello.rest;


import hello.security.PasswordStorage;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by aloha on 24-May-16.
 */
@RestController
@RequestMapping("/api")
public class TestRest {



    @RequestMapping("/test0")
    public HttpStatus test0() {
        /*test - to see if rest is working*/
        return HttpStatus.OK;
    }

    @PreAuthorize("hasAuthority('GOST')")
    @RequestMapping("/test1")
    public HttpStatus test1() {
        //notacije ne rade uopste WTFFFFFFFFFFFFFFF
        return HttpStatus.OK;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/test2")
    public String test2() {
        //that link generates random JSON
        RestTemplate restTemplate = new RestTemplate();
        String s=restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", String.class);
        return s;
    }


    @RequestMapping("/test3")
    public HttpStatus test3(@RequestBody String s) {

        //http://www.mkyong.com/java/jaxb-hello-world-example/

    /*    JSONObject obj = null;
        try {
             obj=new JSONObject(s);
            System.out.println(obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Akt document=new Akt();
        try {
            document.setNaslov(obj.getString("naslov"));
            //gde validacija ?!
            //moze da se marshal pa da se naprvi XML doc
            //pa to saljemo mark bazi?
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("NASLOV IZ OBJ AKT: "+document.getNaslov());*/
        return HttpStatus.OK;
    }


    @RequestMapping(value = "/rezervacijas/{id}")
    public HttpStatus test4(@PathVariable Long id) {

        System.out.println("USAO U METODU");

        //PasswordStorage.
        byte[] salt = new byte[0];
        try {
            salt= PasswordStorage.generateSalt();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }



        String pass="testpass";
        System.out.println("PASS PLAIN:" +pass.toString());
        System.out.println("SALT: " +salt.toString());
        String so=salt.toString();
        try {
            System.out.println("HASNGIRANA" +PasswordStorage.hashPassword(pass, salt));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("HASNGIRANA OPET SA STRING SOLI" +PasswordStorage.hashPassword(pass, so.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }


        return HttpStatus.OK;
    }





}
