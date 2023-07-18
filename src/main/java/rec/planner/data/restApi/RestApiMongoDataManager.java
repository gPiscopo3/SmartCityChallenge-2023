package rec.planner.data.restApi;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import rec.planner.data.MongoInstance;
import rec.planner.exception.NotFoundElementException;
import rec.planner.model.giornalieri.ForecastingGiornaliero;
import rec.planner.model.giornalieri.SchedulingGiornaliero;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static rec.planner.data.MongoDocumentConverter.fromDocument;

public class RestApiMongoDataManager implements RestApiDataManager{


    private static final RestApiMongoDataManager instance = new RestApiMongoDataManager();
    private final MongoDatabase database;
    private final CodecRegistry codecRegistry;

    public RestApiMongoDataManager() {
        database = MongoInstance.getDatabase();
        codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry());
    }

    public static RestApiMongoDataManager getInstance(){
        return instance;
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
            for(Document document: collection.find()){
                System.out.println(fromDocument(document, SchedulingGiornaliero.class));
            }
            return fromDocument(Objects.requireNonNull(collection.find(eq("giorno", date.format(DateTimeFormatter.ISO_LOCAL_DATE))).first()),
                    SchedulingGiornaliero.class);
        }catch (NullPointerException e){
            throw new NotFoundElementException();
        }
    }


}
