package hello;


import com.marklogic.client.DatabaseClient;
import hello.util.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Application {

    /* HTTPS - HOW TO LINKS */
    /* BASIC INFO: https://www.instantssl.com/ssl-certificate-products/https.html */
    /* HOW TO CREATE KS AND CERT AND HOT TO INSTALL IT: https://www.digicert.com/csr-creation-java.htm */
    /* MAKE YOU CERT LEGAL FOR FREE: https://secure.instantssl.com/products/SSLIdASignup1a */
    /* SPRING CONFIG: http://docs.spring.io/spring-boot/docs/current/reference/html/howto-embedded-servlet-containers.html */
    /* SPRING INFO ABOUT SERVER: https://spring.io/blog/2014/03/07/deploying-spring-boot-applications */
    /* stackoverflow: http://stackoverflow.com/questions/7811128/how-to-develop-https-site-with-spring-3-x */
    /* tomcat http://tomcat.apache.org/tomcat-5.5-doc/ssl-howto.html#Prepare_the_Certificate_Keystore */

    public static void main(String[] args) throws IOException {
        DatabaseClient client = Database.getDbClient();
        SpringApplication.run(Application.class, args);
        System.out.println("[ https://localhost:8080/ ] SERVER IS WORKING...");
        client.release();
    }


}