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
        akm.archiveIt(a1);

        //-----------------------------------------------------------------------------------
        //example
        //RestTemplate rt2=new RestTemplate();
        //String s2 = rt2.postForObject("http://localhost:9090/api/test0", doc, String.class);
        //------------------------------------------------------------------------------------

    }







}