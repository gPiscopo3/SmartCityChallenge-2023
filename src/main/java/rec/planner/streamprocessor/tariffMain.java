package rec.planner.streamprocessor;

public class tariffMain {

    public static void main(String args[]){

        Consumer tariffData = new ConsumerTariffData("172.31.3.218:31200", "groupB", "earliest");
        tariffData.consume("tariff_data");
    }
}
