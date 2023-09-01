package rec.planner.data.forecasting;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import rec.planner.data.MongoInstance;
import rec.planner.exception.NotFoundElementException;
import rec.planner.model.giornalieri.ForecastingGiornaliero;
import rec.planner.model.giornalieri.IrradianzaGiornaliera;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static rec.planner.data.MongoDocumentConverter.fromDocument;
import static rec.planner.data.MongoDocumentConverter.toDocument;

public class ForecastingMongoDataManager implements ForecastingDataManager{

    private static final ForecastingMongoDataManager istance = new ForecastingMongoDataManager();
    private final MongoDatabase database;
    private final CodecRegistry codecRegistry;

    private ForecastingMongoDataManager(){
        database = MongoInstance.getDatabase();
        codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(
                PojoCodecProvider.builder().register(IrradianzaGiornaliera.class).build()));
    }

    public static ForecastingMongoDataManager getInstance(){
        return istance;
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
    public boolean isForecastingPresent(LocalDate date) {

        try {
            getForecasting(date);
        } catch (NotFoundElementException e) {
            return false;
        }

        return true;

    }

    @Override
    public void setForecasting(ForecastingGiornaliero forecastingGiornaliero) {

        MongoCollection<Document> collection = database.getCollection("forecasting", Document.class).withCodecRegistry(codecRegistry);
        collection.insertOne(toDocument(forecastingGiornaliero));

    }

    @Override
    public IrradianzaGiornaliera getIrradiazioneGiornaliera(LocalDate date) throws NotFoundElementException {

        MongoCollection<Document> collection = database.getCollection("irradianza", Document.class).withCodecRegistry(codecRegistry);
        try{
            return fromDocument(Objects.requireNonNull(collection.find(eq("giorno", date.toString())).first()), IrradianzaGiornaliera.class);
        }catch(NullPointerException e){
            throw new NotFoundElementException();
        }


    }

    @Override
    public void setIrradiazioneGiornaliera(IrradianzaGiornaliera irradianzaGiornaliera) {

        MongoCollection<Document> collection = database.getCollection("irradianza", Document.class).withCodecRegistry(codecRegistry);
        collection.insertOne(toDocument(irradianzaGiornaliera));

    }

    @Override
    public boolean isIrradiazioneGiornalieraPresent(LocalDate date) {

        try{
            getIrradiazioneGiornaliera(date);
        } catch (NotFoundElementException e) {
            return false;
        }
        return true;
    }

}
