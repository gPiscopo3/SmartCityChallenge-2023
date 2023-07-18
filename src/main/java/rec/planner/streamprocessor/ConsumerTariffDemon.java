package rec.planner.streamprocessor;

import rec.planner.Configuration;

public class ConsumerTariffDemon extends Thread implements Configuration {

    Consumer consumer = new ConsumerTariffData(ADDRESS_KAFKA, KAFKA_GROUP, "earliest");

    @Override
    public void run(){
        while (true){
           consumer.consume("tariff_data");
        }
    }
}
