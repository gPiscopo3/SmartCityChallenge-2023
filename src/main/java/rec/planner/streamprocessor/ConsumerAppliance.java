package rec.planner.streamprocessor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import rec.planner.model.instantanee.Consumatore;

import java.time.Duration;
import java.util.Arrays;

public class ConsumerAppliance extends Consumer{

    public ConsumerAppliance(String brokers, String groupId, String offset) {
        super(brokers, groupId, offset);
    }

    @Override
    public void consume(String topicName) {
        getConsumer().subscribe(Arrays.asList(topicName));

        int count = 0;
        while(true) {
            // Poll for records
            Duration d = Duration.ofMillis(0);
            ConsumerRecords<String, String> records = getConsumer().poll(d);

            if (records.count() > 0) {
                // The poll has retrieved some data
                for(ConsumerRecord<String, String> record: records) {

                    Consumatore consumatore = gson.fromJson(record.value(), Consumatore.class);

                    dataManager.registerConsumatore(consumatore);
                }
            }

        }
    }
}
