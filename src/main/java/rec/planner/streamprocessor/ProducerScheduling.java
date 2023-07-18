package rec.planner.streamprocessor;

import org.apache.kafka.clients.producer.ProducerRecord;
import rec.planner.model.Day;
import rec.planner.model.giornalieri.SchedulingGiornaliero;

import java.io.IOException;

public class ProducerScheduling extends Producer{

    public ProducerScheduling(String brokers) {
        super(brokers);
    }

    @Override
    public void produce(String topicName, Object object) throws IOException {

        SchedulingGiornaliero schedulingGiornaliero = (SchedulingGiornaliero) object;

        String scheduling = gson.toJson(schedulingGiornaliero);

        try {
            // Send the sentence to the topic
            getProducer().send(new ProducerRecord<>(topicName, Day.getNextDay().toString(), scheduling)).get();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            throw new IOException(ex.toString());
        }
    }
}
