package rec.planner.application;

import rec.planner.streamprocessor.demons.ConsumerApplianceDemon;
import rec.planner.streamprocessor.demons.ConsumerGenDataDemon;
import rec.planner.streamprocessor.demons.ConsumerLoadDataDemon;
import rec.planner.streamprocessor.demons.ConsumerTariffDemon;

public class KafkaApplication {

    public static void main(String... args){

        ConsumerLoadDataDemon loadDataDemon = new ConsumerLoadDataDemon();
        ConsumerGenDataDemon genDataDemon = new ConsumerGenDataDemon();
        ConsumerTariffDemon tariffDemon = new ConsumerTariffDemon();
        ConsumerApplianceDemon applianceDemon = new ConsumerApplianceDemon();

        loadDataDemon.start();
        genDataDemon.start();
        tariffDemon.start();
        applianceDemon.start();
        
    }
}
