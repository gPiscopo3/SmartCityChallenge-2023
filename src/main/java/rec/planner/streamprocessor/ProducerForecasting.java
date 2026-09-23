package rec.planner.streamprocessor;

import org.apache.kafka.clients.producer.ProducerRecord;
import rec.planner.model.Day;
import rec.planner.model.giornalieri.ForecastingGiornaliero;

import java.io.IOException;

public class ProducerForecasting extends Producer{

    public ProducerForecasting(String brokers) {
        super(brokers);
    }

    @Override
    public void produce(String topicName, Object object) throws IOException {
        ForecastingGiornaliero forecastingGiornaliero = (ForecastingGiornaliero) object;

        String forecasting= gson.toJson(forecastingGiornaliero);

        try {
            // Send the sentence to the topic
            getProducer().send(new ProducerRecord<>(topicName, Day.getNextDay().toString(), forecasting)).get();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            throw new IOException(ex.toString());
        }
    }
/*
    public static void main(String args[]) throws IOException {
        Producer p = new ProducerForecasting("172.31.3.218:31200");
        MTUArray<Double> value = new MTUArray<>();
        value.setValue(2, 3.5);
        ForecastingGiornaliero forecasting = new ForecastingGiornaliero(LocalDate.now(), value );
        p.produce("DA_gen_data", forecasting );
    }*/
}
