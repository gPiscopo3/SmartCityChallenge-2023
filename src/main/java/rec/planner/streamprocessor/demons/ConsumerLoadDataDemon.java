package rec.planner.streamprocessor.demons;

import rec.planner.Configuration;
import rec.planner.streamprocessor.Consumer;
import rec.planner.streamprocessor.ConsumerLoadData;

public class ConsumerLoadDataDemon extends Thread implements Configuration {

    Consumer consumer = new ConsumerLoadData(ADDRESS_KAFKA, KAFKA_GROUP, "earliest");

    public void run()
    {
        while (true){
            consumer.consume("load_data");
        }
    }

}
