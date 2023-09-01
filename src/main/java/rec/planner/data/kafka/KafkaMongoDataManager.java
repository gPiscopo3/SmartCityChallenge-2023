package rec.planner.data.kafka;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import rec.planner.data.MongoInstance;
import rec.planner.model.instantanee.Consumatore;
import rec.planner.model.instantanee.ProduttoreConsumatoreMTU;
import rec.planner.model.instantanee.ProduzioneMTU;
import rec.planner.model.instantanee.TariffaOraria;
import rec.planner.streamprocessor.ConsumerTariffData;
import static rec.planner.data.MongoDocumentConverter.fromDocument;
import static rec.planner.data.MongoDocumentConverter.toDocument;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class KafkaMongoDataManager implements KafkaDataManager{


    private static final KafkaMongoDataManager instance = new KafkaMongoDataManager();
    private final MongoDatabase database;
    private final CodecRegistry codecRegistry;

    private KafkaMongoDataManager(){
        database = MongoInstance.getDatabase();
        codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(
                PojoCodecProvider.builder().register(ProduzioneMTU.class).register(TariffaOraria.class).register(ConsumerTariffData.class).register(Consumatore.class).build()));
    }

    public static KafkaMongoDataManager getInstance() {
        return instance;
    }

    @Override
    public void addProduttore(ProduttoreConsumatoreMTU produttore) {
        MongoCollection<ProduzioneMTU> collection = database.getCollection("produzione", ProduzioneMTU.class).withCodecRegistry(codecRegistry);

        ProduzioneMTU produzioneMTU = collection.find(and(eq("giorno", produttore.getGiorno()), eq("mtu", produttore.getMtu()))).first();
        if(produzioneMTU == null)
            collection.insertOne(new ProduzioneMTU(produttore.getValue(), produttore.getMtu(), produttore.getGiorno()));
        else {
            produzioneMTU.setProduzione(produzioneMTU.getProduzione() + produttore.getValue());
            collection.replaceOne(and(eq("giorno", produttore.getGiorno()), eq("mtu", produttore.getMtu())), produzioneMTU);
        }

    }

    @Override
    public void updateConsumatore(ProduttoreConsumatoreMTU consumatore, double factorEWMA) {
        MongoCollection<Consumatore> collection = database.getCollection("consumatori", Consumatore.class).withCodecRegistry(codecRegistry);

        Consumatore consumatoreMedio = collection.find(eq("smartMeter", consumatore.getSmartMeter())).first();
        if(consumatoreMedio==null)
            collection.insertOne(new Consumatore(consumatore.getSmartMeter(), consumatore.getValue()));
        else
            collection.replaceOne(eq("smartMeter", consumatore.getSmartMeter()),
                    new Consumatore(consumatore.getSmartMeter(), consumatoreMedio.getConsumoMedio() * ( 1 - factorEWMA) + consumatore.getValue() * factorEWMA));
    }

    @Override
    public void addTariffa(TariffaOraria tariffaOraria) {
        //MongoCollection<TariffaOraria> collection = database.getCollection("tarriffeOrarie", TariffaOraria.class);

        MongoCollection<Document> collection = database.getCollection("tariffeOrarie", Document.class).withCodecRegistry(codecRegistry);

        if(collection.find(eq("ora", tariffaOraria)).first() == null)
            collection.insertOne(toDocument(tariffaOraria));
        else
            collection.replaceOne(eq("ora", tariffaOraria.getOra()), toDocument(tariffaOraria));

    }

    @Override
    public void registerConsumatore(Consumatore consumatore) {
        MongoCollection<Consumatore> collection = database.getCollection("consumatori", Consumatore.class).withCodecRegistry(codecRegistry);

        if(collection.find(eq("smartMeter", consumatore.getSmartMeter())).first() == null){
            collection.insertOne(new Consumatore(consumatore.getSmartMeter(), consumatore.getConsumoMedio(), consumatore.getConsumoNominale()));
        }


    }

}
