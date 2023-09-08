package rec.planner.data.mongo;


import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import rec.planner.model.*;
import rec.planner.model.giornalieri.ForecastingGiornaliero;
import rec.planner.model.instantanee.Consumatore;

import java.util.UUID;

public class Mongo {

    public static void main(String... args){

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        MongoDatabase database = mongoClient.getDatabase("boh");

        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().register(Consumatore.class).register(ForecastingGiornaliero.class)
                        .register(MTUArray.class).build()));

        MongoCollection<Consumatore> consumatori = database.getCollection("consumatori", Consumatore.class).withCodecRegistry(codecRegistry);

        for(Consumatore consumatore: consumatori.find()){
            System.out.println(consumatore.getSmartMeter() + " " + consumatore.getConsumoMedio());
        }

        consumatori.insertOne(new Consumatore(UUID.randomUUID().toString(), Math.random()));

        MongoCollection<ForecastingGiornaliero> forecastingGiornalieri =
                database.getCollection("forecasting", ForecastingGiornaliero.class).withCodecRegistry(codecRegistry);

        for(ForecastingGiornaliero forecastingGiornaliero: forecastingGiornalieri.find()){
            System.out.println(forecastingGiornaliero.getGiorno() + " " + forecastingGiornaliero.getGiorno());
        }

        forecastingGiornalieri.insertOne(new ForecastingGiornaliero(Day.getNextDay(), MTUArray.inMTU(100.00, 0,1,2,3,4,5)));




    }
}
