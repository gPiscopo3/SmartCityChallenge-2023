package rec.planner.streamprocessor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import rec.planner.model.instantanee.TariffaOraria;

import java.time.Duration;
import java.util.Arrays;

public class ConsumerTariffData extends Consumer{


    public ConsumerTariffData(String brokers, String groupId, String offset) {
        super(brokers, groupId, offset);
    }

    @Override
    void consume(String topicName) {
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
                    TariffaOraria tariffaOraria = gson.fromJson(record.value(), TariffaOraria.class);
                    dataManager.addTariffa(tariffaOraria);
                }
            }
        }

    }
}
