package hello;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.TextDocumentManager;
import com.marklogic.client.io.StringHandle;
import hello.util.Util;
import hello.util.Util.ConnectionProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
public class Application {

    private static DatabaseClient client;

    public static void start(ConnectionProperties props) throws FileNotFoundException {

        //System.out.println("[INFO] " + HelloTextWriterExample.class.getSimpleName());

        // Initialize the database client
        if (props.database.equals("")) {
            System.out.println("[INFO] Using default database.");
            client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
        } else {
            System.out.println("[INFO] Using \"" + props.database + "\" database.");
            client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
        }

        // Create a document manager to work with text files.
        TextDocumentManager docManager = client.newTextDocumentManager();

        // Define a URI value for a document.
        String docId = "/example/hello.txt";

        // Create a handle to hold string content.
        StringHandle handle = new StringHandle();

        // Assign some content
        handle.set("A simple text document");

        // Write the document (content from handle) to the database
        System.out.println("[INFO] Inserting \"" + docId + "\" to \"" + props.database + "\" database.");
        docManager.write(docId, handle);

        System.out.println("[INFO] Verify the content at: http://" + props.host + ":8000/v1/documents?database=" + props.database + "&uri=" + docId);

        // Release the client
        client.release();
        System.out.println("[INFO] End.");
    }


    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
        /* START HERE */
        start(Util.loadProperties());

    }


}
