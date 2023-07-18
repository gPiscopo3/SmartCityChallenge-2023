package rec.planner.data;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoInstance {

    private static final String URL = "mongodb://172.31.3.218:31201";
    //private static final String URL = "mongodb://localhost:27017";
    private static final MongoClient client = MongoClients.create(URL);
    public static MongoDatabase getDatabase() {
        return client.getDatabase("REC");
    }
    public static MongoDatabase getDatabase(String databaseName){
        return client.getDatabase(databaseName);
    }
}
