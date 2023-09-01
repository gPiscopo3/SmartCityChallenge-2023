package rec.planner.data.scheduling;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import rec.planner.data.MongoInstance;
import rec.planner.exception.NotFoundElementException;
import rec.planner.model.MTUArray;
import rec.planner.model.giornalieri.ForecastingGiornaliero;
import rec.planner.model.giornalieri.SchedulingGiornaliero;
import rec.planner.model.giornalieri.TariffeCorrenti;
import rec.planner.model.instantanee.Consumatore;
import rec.planner.model.instantanee.TariffaOraria;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static rec.planner.data.MongoDocumentConverter.fromDocument;
import static rec.planner.data.MongoDocumentConverter.toDocument;

public class SchedulingMongoDataManager implements SchedulingDataManager{

    private static final SchedulingDataManager instance = new SchedulingMongoDataManager();
    private final MongoDatabase database;
    private final CodecRegistry codecRegistry;

    private SchedulingMongoDataManager() {
        database = MongoInstance.getDatabase();
        codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(
                PojoCodecProvider.builder().register(Consumatore.class).build()));
    }

    public static SchedulingDataManager getInstance() {
        return instance;
    }

    @Override
    public List<Consumatore> getConsumatori() {
        MongoCollection<Consumatore> collection = database.getCollection("consumatori", Consumatore.class).withCodecRegistry(codecRegistry);
        List<Consumatore> consumatori = new ArrayList<>();
        for(Consumatore consumatore: collection.find())
            consumatori.add(consumatore);
        return consumatori;
    }

    @Override
    public TariffeCorrenti getTariffeCorrrenti() {
        MongoCollection<Document> collection = database.getCollection("tariffeOrarie", Document.class);
        FindIterable<Document> documents = collection.find();
        TariffaOraria tariffaOraria = null;
        List<Double> costi = new ArrayList<>();
        List<Double> ricavi = new ArrayList<>();
        List<Double> incentivi = new ArrayList<>();

        for(Document document : documents) {
            tariffaOraria = fromDocument(document, TariffaOraria.class);
            costi.add(tariffaOraria.getCostoAcquisto());
            ricavi.add(tariffaOraria.getRicavoVendita());
            incentivi.add(tariffaOraria.getIncentivo());
        }

        return new TariffeCorrenti(MTUArray.ofValues(costi), MTUArray.ofValues(ricavi), MTUArray.ofValues(incentivi));
    }

    @Override
    public ForecastingGiornaliero getForecasting(LocalDate date) throws NotFoundElementException {
        MongoCollection<Document> collection =
                database.getCollection("forecasting", Document.class).withCodecRegistry(codecRegistry);
        try {
            return fromDocument(Objects.requireNonNull(collection.find(eq("giorno", date.format(DateTimeFormatter.ISO_LOCAL_DATE))).first()),
                    ForecastingGiornaliero.class);
        }catch (NullPointerException e){
            throw new NotFoundElementException();
        }
    }

    @Override
    public SchedulingGiornaliero getScheduling(LocalDate date) throws NotFoundElementException{
        MongoCollection<Document> collection =
                database.getCollection("scheduling", Document.class).withCodecRegistry(codecRegistry);
        try {
            return fromDocument(Objects.requireNonNull(collection.find(eq("giorno", date.format(DateTimeFormatter.ISO_LOCAL_DATE))).first()),
                    SchedulingGiornaliero.class);
        }catch (NullPointerException e){
            throw new NotFoundElementException();
        }
    }

    @Override
    public boolean isForecastingPresent(LocalDate date) {
        try {
            getForecasting(date);
        } catch (NotFoundElementException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isSchedulingPresent(LocalDate date) {
        try {
            getScheduling(date);
        } catch (NotFoundElementException e) {
            return false;
        }
        return true;
    }

    @Override
    public void setScheduling(SchedulingGiornaliero scheduling) {
        MongoCollection<Document> mongoCollection = database.getCollection("scheduling", Document.class).withCodecRegistry(codecRegistry);
        try {
            fromDocument(Objects.requireNonNull(mongoCollection.find(eq("giorno", scheduling.getGiorno().
                            format(DateTimeFormatter.ISO_LOCAL_DATE))).first()), SchedulingGiornaliero.class);
            mongoCollection.replaceOne(eq("giorno", scheduling.getGiorno()), toDocument(scheduling));
        }catch (NullPointerException e){
            mongoCollection.insertOne(toDocument(scheduling));
        }
    }
}
