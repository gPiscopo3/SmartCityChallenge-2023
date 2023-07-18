package rec.planner.stubPreferences;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rec.planner.apiEsterne.RestApiImpl;


@ApplicationPath("stubPreferences")
@SpringBootApplication
public class StubApplication extends ResourceConfig {

    public StubApplication() {
        register(new PreferencesImpl());

    }

    public static void main(String... args){
        SpringApplication.run(StubApplication.class, args);
    }
}
