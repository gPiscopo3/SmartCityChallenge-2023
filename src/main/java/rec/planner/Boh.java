package rec.planner;

import rec.planner.data.mongo.MongoDataManager;
import rec.planner.data.scheduling.SchedulingDataManager;
import rec.planner.scheduling.SchedulerDemon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Boh {


    public static void main(String... args) throws IOException {
        new SchedulerDemon().start();
    }
}
