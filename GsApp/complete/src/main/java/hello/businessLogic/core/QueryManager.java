package hello.businessLogic.core;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import hello.util.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.validation.Schema;
import java.io.File;
import java.util.ArrayList;
/**
 * Created by milan on 4.6.2016..
 * Class that invokes xquery on mark logic server.
 */
public class QueryManager <T> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Database client.
     */
    private DatabaseClient client;

    /**
     * XQuery invoker.
     */
    private ServerEvaluationCall invoker;

    /**
     * Support class for xml-bean conversion.
     */
    private Converter<T> converter;

    /**
     * Default schema located in <b>"./schema/Akt.xsd"</b>
     */
    private Schema schema;


    /**
     * Initializes a new QueryManager.
     * @param client Database client.
     */
    public QueryManager(DatabaseClient client, Schema schema ,Converter converter){
        try {
            this.client = client;
            this.converter = converter;
            this.schema = schema;
            invoker = client.newServerEval();
        } catch (Exception e){
            logger.error("[QueryManager] Can't initialize QueryManager!");
            e.printStackTrace();
        }
    }

    /**
     * Executes given query.
     * @param query String representation of XQuery.
     * @return Interpreted results. <code>NULL</code> if error occurs.
     */
    public ArrayList<T> executeQuery(String query){
        ArrayList<T> ret = null;
        try {
            EvalResultIterator response = null;
            // Invoke the query
            invoker.xquery(query);
            response = invoker.eval();
            ret = new ArrayList<>();
            for (EvalResult result : response) {
                if(converter.writeStringToFile(result.getString())){
                    T bean = converter.convertFromXml(new File("tmp.xml"),schema);
                    if (bean != null) {
                        ret.add(bean);
                    } else {
                         logger.error("[QueryManager] ERROR: Could not convert xml to JAXB bean!");
                    }
                } else {
                    logger.error("[QueryManager] ERROR: Could not write query result to file!");
                }
            }
        } catch (Exception e){
            System.out.println("Unexpected error: " + e.getMessage());
        }
        return ret;
    }

}
