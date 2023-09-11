package rec.planner;
import static rec.planner.XMLReader.read;
public interface Configuration {

    String ADDRESS_KAFKA = read("kafka");
    String KAFKA_GROUP ="group";
    String MONGO_CLIENT = read("mongo");
    String FITTING_FILE = read("fitting");
    String COST_OPTIMIZER_FILE = read("costOptimizer");
    String FORECASTER_FILE = read("forecaster");
    String TRAINER_FILE = read("trainer");

    enum FontePreferenze{
        INTERNE, ESTERNE;
    }

    FontePreferenze FONTE_PREFERENZE = FontePreferenze.valueOf(read("preferenze").toUpperCase());

}
