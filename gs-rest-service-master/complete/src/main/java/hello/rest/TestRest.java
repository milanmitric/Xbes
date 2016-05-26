package hello.rest;

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


}
