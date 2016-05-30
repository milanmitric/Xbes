package hello.rest;

import hello.businessLogic.BeanManager;
import hello.entity.Akt;
import hello.entity.TGlava;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

        JSONObject obj = null;
        try {
             obj=new JSONObject(s);
            System.out.println(obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Akt akt=new Akt();
        try {
            akt.setNaslov(obj.getString("naslov"));
            //gde validacija ?!
            //moze da se marshal pa da se naprvi XML doc
            //pa to saljemo mark bazi?
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("NASLOV IZ OBJ AKT: "+akt.getNaslov());
        return HttpStatus.OK;
    }


    @RequestMapping("/test4")
    public HttpStatus test4(@RequestBody TGlava t) {
        //nece - 405
        System.out.println(t.getNaziv());

        BeanManager<TGlava> bm=new BeanManager<>();
        bm.write(t, "/lalalala", "testovoono");

        return HttpStatus.OK;
    }





}
