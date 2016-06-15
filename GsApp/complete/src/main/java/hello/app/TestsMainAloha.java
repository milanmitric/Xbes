package hello.app;

import hello.Application;
import hello.StringResources.MarkLogicStrings;
import hello.businessLogic.document.AktManager;
import hello.businessLogic.document.AmandmanManager;
import hello.businessLogic.document.UsersManager;
import hello.entity.gov.gradskaskupstina.Akt;
import hello.entity.gov.gradskaskupstina.Amandmani;
import hello.entity.gov.gradskaskupstina.User;
import hello.entity.gov.gradskaskupstina.Users;
import hello.security.EncryptKEK;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by milan on 4.6.2016..
 */
public class TestsMainAloha {

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

        try {
            callRest();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void callRest()throws IOException {


        AktManager akm=new AktManager();
        ArrayList<Akt> akts = new ArrayList<>();
        akts=akm.getAllFilesProposed();
        /*akt to encrypt*/
        Akt a1=akts.get(0);
        /*encrypt it and save it do filesystem and load it and return it and yea!*/
        Document doc =akm.encryptDoc(a1);
        /*EncryptKEK eee=new EncryptKEK();

        eee.saveDocument(doc,  "./data/IDEMOOOO.xml");
        Document do2c = eee.loadDocument("./data/IDEMOOOO.xml");
        System.out.println("DOCT TO STR:"+do2c.toString());
        *//*send that doc*//*
        *//*testing*//*
        RestTemplate restTemplate = new RestTemplate();
        String s=restTemplate.getForObject("http://localhost:9090/api/test1", String.class);
        System.out.println("RESPONSE: "+s);

        //https://dzone.com/articles/how-use-spring-resttemplate-0
       // Map<String, String> vars = new HashMap<String, String>();
        //vars.put("encDoc", );

       // RestTemplate rt2=new RestTemplate();
      //  String s2 = rt2.postForObject("http://localhost:9090/api/test0", "THIS_IS_SENDED!", String.class);
*/


        //RADI OVO
        //RestTemplate rt2=new RestTemplate();
        //String s2 = rt2.postForObject("http://localhost:9090/api/test0", doc, String.class);
        //------------------------------------------------------------------------------------

        HashMap<String, Object> map = new HashMap<>();
        map.put("encDoc", doc);
        map.put("docID", a1.getDocumentId());
        RestTemplate rt2=new RestTemplate();
        String s2 = rt2.postForObject("http://localhost:9090/api/testx", doc, String.class);
        System.out.println(s2);

       /* ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        String s2 = restTemplate.postForObject("http://localhost:9090/api/testx", map, String.class);
*/



        /*RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        rt.getMessageConverters().add(new StringHttpMessageConverter());
        String uri = new String("http://" + mRESTServer.getHost() + ":8080/springmvc-resttemplate-test/api/{id}");
        User u = new User();
        u.setName("Johnathan M Smith");
        u.setUser("JS01");
        User returns = rt.postForObject(uri, u, User.class, vars);*/


       /* RestTemplate rest = new RestTemplate();


        HttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        HttpMessageConverter stringHttpMessageConverternew = new StringHttpMessageConverter();
        rest.setMessageConverters(new HttpMessageConverter[]{formHttpMessageConverter, stringHttpMessageConverternew});


        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("name", "xx");
        map.add("password", "xx");
        String result = rest.postForObject("http://localhost:8080/soa-server/user/", map, String.class);
        System.out.println(result);

*/




    }







}