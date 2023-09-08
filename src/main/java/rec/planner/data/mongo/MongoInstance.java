package rec.planner.data.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import rec.planner.Configuration;

public class MongoInstance implements Configuration {

    private static final MongoClient client = MongoClients.create(MONGO_CLIENT);
    public static MongoDatabase getDatabase() {
        return client.getDatabase("REC");
    }
    public static MongoDatabase getDatabase(String databaseName){
        return client.getDatabase(databaseName);
    }
}
