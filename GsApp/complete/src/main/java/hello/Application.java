package hello;


import com.marklogic.client.DatabaseClient;
import hello.businessLogic.BeanManager;
import hello.entity.Akt;
import hello.util.Database;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Application {

            public static void main(String[] args) throws IOException {

        //SpringApplication.run(Application.class, args);

        BeanManager<Akt> aktManager = new BeanManager<>();

                String docId = "/example/books.xml";
                File file = new File("instance1.xml");
                Akt inputAkt = aktManager.convertFromXml(file);
                if (!aktManager.write(inputAkt,docId,"dwadwa")){
                    System.out.println("Could't write akt!");
                } else{
                    Akt akt = aktManager.read(docId);

                    System.out.println(aktManager.validate(akt));

                    System.out.println("Write successful!");
                }


                DatabaseClient client = Database.getDbClient();

        client.release();

    }


}
