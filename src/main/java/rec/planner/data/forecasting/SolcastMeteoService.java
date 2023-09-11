package rec.planner.data.forecasting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import rec.planner.model.Day;
import rec.planner.model.MTUArray;
import rec.planner.model.giornalieri.IrradianzaGiornaliera;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolcastMeteoService implements MeteoService{


    private static final SolcastMeteoService instance = new SolcastMeteoService();
    private static final Gson gson =  new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new SolcastDataSerializer()).create();

    private SolcastMeteoService() {
    }

    @Override
    public IrradianzaGiornaliera getIrradazioneGiornaliera(LocalDate localDate) {

        //System.out.println(LocalDateTime.parse("2023-07-10T16:44:02.858951", DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        String json;
        String urlString = "https://api.solcast.com.au/world_radiation/forecasts?latitude=41.129761&longitude=14.782621&hours=48&format=json&api_key=Pgwaub3WfILZH3FVywv0sZM5W9zd2wE6";
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response);
                json = response.toString();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //fai la richiesta e ottieni json

        /*Scanner scanner;
        try {
            scanner = new Scanner(new File("provameteo.json"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        json = "";
        while (scanner.hasNext()){
            json = json + scanner.next();
        }*/

        Forecast forecast = gson.fromJson(json, Forecast.class);
        List<Double> values = new ArrayList<>();

        for(Forecasts forecasts: forecast.forecasts){
            if(!forecasts.period_end.isBefore(LocalDateTime.of(localDate , Day.start)) &&
                    forecasts.period_end.isBefore(LocalDateTime.of(localDate.plusDays(1), Day.start)))
                values.add(forecasts.ghi);
        }

        return new IrradianzaGiornaliera(localDate, MTUArray.ofValues(values));

    }

    public static SolcastMeteoService getInstance() {
        return instance;
    }

    /*public static void main(String... args){
        System.out.println(new SolcastMeteoService().getIrradazioneGiornaliera(LocalDate.of(2023,07,11)));
    }*/

    private static class Forecast{

        Forecasts[] forecasts;

        @Override
        public String toString() {
            return "Forecast{" +
                    "forecasts=" + Arrays.toString(forecasts) +
                    '}'+
                    "\n";
        }

    }

    private static class Forecasts{
        double ghi;
        double ghi90;
        double ghi10;
        double ebh;
        double dni;
        double dni10;
        double dni90;
        double dhi;
        double air_temp;
        double zenit;
        double azimuth;
        double cloud_opacity;
        LocalDateTime period_end;
        String period;

        @Override
        public String toString() {
            return "Forecasts{" +
                    "ghi=" + ghi +
                    ", ghi90=" + ghi90 +
                    ", ghi10=" + ghi10 +
                    ", ebh=" + ebh +
                    ", dni=" + dni +
                    ", dni10=" + dni10 +
                    ", dni90=" + dni90 +
                    ", dhi=" + dhi +
                    ", air_temp=" + air_temp +
                    ", zenit=" + zenit +
                    ", azimuth=" + azimuth +
                    ", cloud_opacity=" + cloud_opacity +
                    ", period_end=" + period_end +
                    ", period='" + period + '\'' +
                    '}';
        }
    }

    private static class SolcastDataSerializer extends TypeAdapter<LocalDateTime>{

        @Override
        public void write(JsonWriter jsonWriter, LocalDateTime dateTime) throws IOException {
            jsonWriter.value(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "Z");
        }

        @Override
        public LocalDateTime read(JsonReader jsonReader) throws IOException {
            String value = jsonReader.nextString();
            return LocalDateTime.parse(value.substring(0,value.length() - 1));
        }
    }
}
