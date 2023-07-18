package rec.planner.stubPreferences;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import rec.planner.data.MongoInstance;

import static com.mongodb.client.model.Filters.eq;

public class StubDataManager {

    private static final StubDataManager instance = new StubDataManager();
    private final MongoDatabase database;
    private final CodecRegistry codecRegistry;

    private StubDataManager(){
        database = MongoInstance.getDatabase("stub");
        codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(
                PojoCodecProvider.builder().register(PreferenzaStub.class).register(PreferenzaStub.Wrapper.class).build()));
        database.getCollection("preferenze").drop();
    }

    public static StubDataManager getInstance() {
        return instance;
    }

    public PreferenzaStub getPreferences(String smartMeter){
        MongoCollection<PreferenzaStub> collection = database.getCollection("preferenze", PreferenzaStub.class).withCodecRegistry(codecRegistry);
        return collection.find(eq("smartMeter", smartMeter)).first();
    }

    public void addPrefence(PreferenzaStub preferenzaStub){
        MongoCollection<PreferenzaStub> collection = database.getCollection("preferenze", PreferenzaStub.class).withCodecRegistry(codecRegistry);
        collection.insertOne(preferenzaStub);
    }

}
