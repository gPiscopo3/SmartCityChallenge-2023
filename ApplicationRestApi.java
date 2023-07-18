package rec.planner.apiEsterne;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rec.planner.data.scheduling.PreferenzeRestApi;
import rec.planner.forecaster.ForecasterDemon;
import rec.planner.scheduling.SchedulerDemon;
import rec.planner.streamprocessor.ConsumerTariffDemon;
import rec.planner.stubPreferences.PreferencesImpl;

@ApplicationPath("rec")
@SpringBootApplication
public class ApplicationRestApi extends ResourceConfig {
    public ApplicationRestApi() throws InterruptedException {
        register(new PreferencesImpl());
        Thread.sleep(1000);
        register(new RestApiImpl());
        new ForecasterDemon().start();
        new SchedulerDemon().start();
        register(new PreferencesImpl());
        new ConsumerTariffDemon().start();
    }

    public static void main(String... args){
        SpringApplication.run(ApplicationRestApi.class, args);
    }

}
