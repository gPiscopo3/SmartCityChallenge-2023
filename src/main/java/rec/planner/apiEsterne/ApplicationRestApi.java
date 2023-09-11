package rec.planner.apiEsterne;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@ApplicationPath("api")
@SpringBootApplication
public class ApplicationRestApi extends ResourceConfig {
    public ApplicationRestApi() throws InterruptedException {
        register(new RestApiImpl());


    }

    public static void main(String... args){
        SpringApplication.run(ApplicationRestApi.class, args);
    }

}
