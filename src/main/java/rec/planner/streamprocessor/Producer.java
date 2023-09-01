package rec.planner.streamprocessor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import rec.planner.model.serializer.LocalDateSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

public abstract class Producer {

    private KafkaProducer<String, String> producer;
    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateSerializer()).create();

    public Producer(String brokers) {

        // Set properties used to configure the producer
        Properties props = new Properties();
        // Set the brokers (bootstrap servers)
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        // Set how to serialize key/value pairs
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");

        // create the configured producer
        producer = new KafkaProducer<String, String>(props);
    }

    public abstract void produce(String topicName, Object object) throws IOException;

    public KafkaProducer<String, String> getProducer() {
        return producer;
    }
}
