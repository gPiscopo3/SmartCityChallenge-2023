package rec.planner.streamprocessor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import rec.planner.data.kafka.KafkaDataManager;
import rec.planner.data.kafka.KafkaMongoDataManager;
import rec.planner.model.instantanee.ProduttoreConsumatoreMTU;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

public class ConsumerGenData extends Consumer{

    public ConsumerGenData(String brokers, String groupId, String offset) {
        super(brokers, groupId, offset);
    }

    public void consume(String topicName) {

        // Subscribe to the topic
        getConsumer().subscribe(Arrays.asList(topicName));

        //TopicPartition tp = new TopicPartition(topicName, p);
        //consumer.assign(Arrays.asList(tp));

        int count = 0;
        while(true) {
            // Poll for records
            Duration d = Duration.ofMillis(0);
            ConsumerRecords<String, String> records = getConsumer().poll(d);

            if (records.count() > 0) {
                // The poll has retrieved some data
                for(ConsumerRecord<String, String> record: records) {
                    System.out.println( ++count + ": " + record.value() );
                    ProduttoreConsumatoreMTU prodcons = gson.fromJson(record.value(), ProduttoreConsumatoreMTU.class);
                    prodcons.setTime();
                    dataManager.addProduttore(prodcons);
                }
            }

        }
    }

}
