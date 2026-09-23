package rec.planner.data.scheduling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rec.planner.exception.NotFoundElementException;
import rec.planner.model.*;
import rec.planner.model.giornalieri.Preferenza;
import rec.planner.model.serializer.BooleanToIntSerializer;
import rec.planner.model.serializer.LocalDateSerializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import static rec.planner.model.Day.MTU_NUMBER;

public class PreferenzeRestApi implements PreferenzeApi{


    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
            .registerTypeAdapter(Boolean.class, new BooleanToIntSerializer()).create();
    private static final String url = "http://localhost:8080/rec/preferences";
    //private static final String url = "http://localhost:8080/userPreferences/";

    private PreferenzeRestApi() {
    }

    public static PreferenzeRestApi getRestApi(){
        return new PreferenzeRestApi();
    }

    @Override
    public Preferenza getPreferenze(String smartMeter) throws NotFoundElementException {


        try {
            URL url = new URL(PreferenzeRestApi.url + "/" + smartMeter);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            System.out.println(url);
            connection.connect();
            if (connection.getResponseCode() != 200)
                throw new NotFoundElementException();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
                responseBuilder.append(line);

            PreferenzaRest preferenzaRest = gson.fromJson(responseBuilder.toString(), PreferenzaRest.class);

            return new Preferenza(Day.getNextDay(), preferenzaRest.smartMeter, MTUArray.ofValues(preferenzaRest.disponibilita),
                    (int) (((double) preferenzaRest.durata) / preferenzaRest.disponibilita.size() * MTU_NUMBER), preferenzaRest.tipo);
        }catch (Exception e){
            throw new NotFoundElementException();
        }


    }

    private static class PreferenzaRest{


        String smartMeter;
        List<Boolean> disponibilita;
        int durata;
        Tipologia tipo;

        public PreferenzaRest() {
        }
    }
}
