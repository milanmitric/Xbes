package hello;



import hello.util.Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.ServletContextApplicationContextInitializer;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        Util.loadProperties();
        SpringApplication.run(Application.class, args);

        /* START HERE */

    }


}
