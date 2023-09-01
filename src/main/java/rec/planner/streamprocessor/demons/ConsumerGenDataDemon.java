package rec.planner.streamprocessor.demons;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import rec.planner.Configuration;
import rec.planner.streamprocessor.Consumer;
import rec.planner.streamprocessor.ConsumerAppliance;
import rec.planner.streamprocessor.ConsumerGenData;

public class ConsumerGenDataDemon extends Thread implements Configuration {

    Consumer consumer = new ConsumerGenData(ADDRESS_KAFKA, KAFKA_GROUP, "earliest");

    public void run()
    {
        while (true){
            consumer.consume("gen_data");
        }
    }
}
