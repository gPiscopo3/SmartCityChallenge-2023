package rec.planner.data.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import rec.planner.data.forecasting.ForecastingDataManager;
import rec.planner.data.kafka.KafkaDataManager;
import rec.planner.data.restApi.RestApiDataManager;
import rec.planner.data.scheduling.SchedulingDataManager;
import rec.planner.exception.AlreadyPresentElementException;
import rec.planner.exception.NotFoundElementException;
import rec.planner.model.MTUArray;
import rec.planner.model.giornalieri.*;
import rec.planner.model.instantanee.Consumatore;
import rec.planner.model.instantanee.ProduttoreConsumatoreMTU;
import rec.planner.model.instantanee.ProduzioneMTU;
import rec.planner.model.instantanee.TariffaOraria;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static rec.planner.data.mongo.MongoDocumentConverter.fromDocument;
import static rec.planner.data.mongo.MongoDocumentConverter.toDocument;

public class MongoDataManager implements ForecastingDataManager, SchedulingDataManager, KafkaDataManager, RestApiDataManager {


    private static final MongoDataManager instance = new MongoDataManager();
    private final MongoDatabase database;
    private final CodecRegistry codecRegistry;

    private MongoDataManager() {
        database = MongoInstance.getDatabase();
        codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(
                PojoCodecProvider.builder().register(Consumatore.class).build()));
    }


    public static MongoDataManager getInstance(){
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
    public List<Consumatore> getConsumatoriByHomeController(String homeController) {
        MongoCollection<Document> collection =
                database.getCollection("consumatori", Document.class).withCodecRegistry(codecRegistry);

        List<Consumatore> consumatori = new ArrayList<>();

        for(Document document: collection.find(eq("homeController", homeController)))
            consumatori.add(fromDocument(document, Consumatore.class));

        return consumatori;
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
    public Preferenza getPreferenze(String smartMeter) throws NotFoundElementException {
        MongoCollection<Document> collection =
                database.getCollection("preferenze", Document.class).withCodecRegistry(codecRegistry);

        Document document =
                collection.find(eq("smartMeter", smartMeter)).first();

        try{
            return fromDocument(Objects.requireNonNull(document), Preferenza.class);
        }catch (NullPointerException e){
            throw new NotFoundElementException();
        }
    }

    @Override
    public List<Preferenza> getPreferenze() {
        MongoCollection<Document> collection =
                database.getCollection("preferenze", Document.class).withCodecRegistry(codecRegistry);

        List<Preferenza> preferenze = new ArrayList<>();
        for(Document preferenza :collection.find())
            preferenze.add(fromDocument(preferenza, Preferenza.class));
        return preferenze;
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

    @Override
    public void savePreferenza(Preferenza preferenza) {
        MongoCollection<Document> collection =
                database.getCollection("preferenze", Document.class).withCodecRegistry(codecRegistry);

        if(collection.findOneAndReplace((eq("smartMeter", preferenza.getSmartMeter())),toDocument(preferenza))==null)
            collection.insertOne(toDocument(preferenza));
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

    public void addProduttore(ProduttoreConsumatoreMTU produttore)  {
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
    public void updateConsumatore(String smartMeter, double consumo,  double factorEWMA) throws NotFoundElementException{
        MongoCollection<Consumatore> collection = database.getCollection("consumatori", Consumatore.class).withCodecRegistry(codecRegistry);

        Consumatore consumatoreMedio = collection.find(eq("smartMeter", smartMeter)).first();
        if(consumatoreMedio==null)
            throw new NotFoundElementException();
        else
            collection.replaceOne(eq("smartMeter", smartMeter),
                    new Consumatore(smartMeter, consumatoreMedio.getHomeController(), consumatoreMedio.getNome(),
                            consumatoreMedio.getConsumoMedio()* ( 1 - factorEWMA) + consumo * factorEWMA,
                            consumatoreMedio.getConsumoNominale()));
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
    public void registerConsumatore(Consumatore consumatore) throws AlreadyPresentElementException {
        MongoCollection<Consumatore> collection = database.getCollection("consumatori", Consumatore.class).withCodecRegistry(codecRegistry);

        if(collection.find(eq("smartMeter", consumatore.getSmartMeter())).first() == null){
            collection.insertOne(consumatore);
        }
        else
            throw new AlreadyPresentElementException();


    }

}
