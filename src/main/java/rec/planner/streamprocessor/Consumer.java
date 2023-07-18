package rec.planner.streamprocessor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import rec.planner.data.kafka.KafkaDataManager;
import rec.planner.data.kafka.KafkaMongoDataManager;
import rec.planner.model.serializer.LocalDateSerializer;

import java.time.LocalDate;
import java.util.Properties;

public abstract class Consumer {
    private KafkaConsumer<String, String> consumer;
    protected KafkaDataManager dataManager = KafkaMongoDataManager.getInstance();
    Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateSerializer()).create();

    public Consumer(String brokers, String groupId, String offset) {

        // Set the properties of the consumer
        Properties props = new Properties();
        // Set the bootstrap brokers
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        // Set the consumer group (all consumers must belong to a group).
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        // Set how to serialize key/value pairs
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        // When a group is first created, it has no offset stored to start reading from.
        // This tells it to start with the earliest record in the stream.
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offset);

        // create the configured consumer
        consumer = new KafkaConsumer<>(props);

    }

    public KafkaConsumer<String, String> getConsumer() {
        return consumer;
    }

    abstract void consume(String topicName);

}
