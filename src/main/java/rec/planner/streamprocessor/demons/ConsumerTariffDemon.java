package rec.planner.streamprocessor.demons;

import rec.planner.Configuration;
import rec.planner.streamprocessor.Consumer;
import rec.planner.streamprocessor.ConsumerTariffData;

public class ConsumerTariffDemon extends Thread implements Configuration {

    Consumer consumer = new ConsumerTariffData(ADDRESS_KAFKA, KAFKA_GROUP, "earliest");

    @Override
    public void run(){
        while (true){
           consumer.consume("tariff_data");
        }
    }
}
