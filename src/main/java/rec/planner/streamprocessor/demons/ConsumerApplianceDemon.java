package rec.planner.streamprocessor.demons;

import rec.planner.Configuration;
import rec.planner.streamprocessor.Consumer;
import rec.planner.streamprocessor.ConsumerAppliance;
import rec.planner.streamprocessor.ConsumerLoadData;

public class ConsumerApplianceDemon extends Thread implements Configuration {

    Consumer consumer = new ConsumerAppliance(ADDRESS_KAFKA, KAFKA_GROUP, "earliest");

    public void run()
    {
        while (true){
            consumer.consume("appliances_data");
        }
    }
}
