package rec.planner.streamprocessor;

import rec.planner.Configuration;

public class ConsumerLoadDataDemon extends Thread implements Configuration {

    Consumer consumer = new ConsumerLoadData(ADDRESS_KAFKA, KAFKA_GROUP, "earliest");

    public void run()
    {
        while (true){
            consumer.consume("load_data");
        }
    }

}
