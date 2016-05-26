package hello.rest;


import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * Created by aloha on 24-May-16.
 */
@RestController
@RequestMapping("/api")
public class TestRest {


    @RequestMapping("/test")
    public HttpStatus test() {

        return HttpStatus.OK;
    }

    @RequestMapping("/test2")
    public String test2() {

        RestTemplate restTemplate = new RestTemplate();
        String s=restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", String.class);
        return s;
    }

    @RequestMapping("/testiranje")
    public HttpStatus testJSONA(@RequestBody String s) {

        System.out.println("*************************************");
        String sa="sssssss";


        //JSONObject obj=new JSONObject();
        //JSONObject jsonObj = new JSONObject("{\"phonetype\":\"N95\",\"cat\":\"WP\"}");
      /*  JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject) parser.parse(s);
            System.out.println("OVO JE JSON OBJ");
            System.out.println(json);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        System.out.println(s);

        System.out.println("***************test**********************");

        return HttpStatus.OK;
    }


}
