package hello.util;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;

/**
 * Created by milan on 27.5.2016..
 * Class implements singleton pattern for database connection.
 */
public class Database {

    /**
     * Singleton instance of client.
     */
    private static DatabaseClient client = null;

    /**
     * Getter for singleton instance.
     * @return Singleton instance.
     */
    public static DatabaseClient getDbClient() {
        if (client == null){
            client = initializeClient();
        }

        return client;
    }

    /**
     * Initializes database client.
     * @return Initialized client.
     */
    private static DatabaseClient initializeClient(){
        Util.ConnectionProperties props = null;
        DatabaseClient client = null;
        try {
            props = Util.loadProperties();
            // Initialize the database client
            System.out.println("[INFO] Using \"" + props.database + "\" database.");

            client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        } finally {
            return client;
        }

    }
}
