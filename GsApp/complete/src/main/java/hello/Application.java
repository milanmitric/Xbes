package hello;


import com.marklogic.client.DatabaseClient;
import hello.businessLogic.BeanManager;
import hello.entity.TAkt;
import hello.util.Database;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        BeanManager<TAkt> aktManager = new BeanManager<>();

        String docId = "/example/books.xml";
        File file = new File("instance1.xml");
        TAkt inputAkt = aktManager.convertFromXml(file);
        if (!aktManager.write(inputAkt,docId,"dwadwa")){
            System.out.println("Could't write akt!");
        } else{
            System.out.println("Write successful!");
        }
        TAkt akt = aktManager.read(docId);

        System.out.println(akt.getNaslov());

        DatabaseClient client = Database.getDbClient();

        client.release();

    }


}
