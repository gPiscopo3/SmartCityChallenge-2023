package rec.planner.scheduling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.ws.rs.core.Response;
import rec.planner.data.scheduling.PreferenzeApi;
import rec.planner.data.scheduling.PreferenzeRestApi;
import rec.planner.data.scheduling.SchedulingDataManager;
import rec.planner.data.scheduling.SchedulingMongoDataManager;
import rec.planner.exception.NotFoundElementException;
import rec.planner.model.Day;
import rec.planner.model.MTUArray;
import rec.planner.model.Tipologia;
import rec.planner.model.giornalieri.ForecastingGiornaliero;
import rec.planner.model.giornalieri.Preferenza;
import rec.planner.model.giornalieri.SchedulingGiornaliero;
import rec.planner.model.giornalieri.TariffeCorrenti;
import rec.planner.model.instantanee.Consumatore;
import rec.planner.model.serializer.BooleanToIntSerializer;
import rec.planner.model.serializer.LocalDateSerializer;
import rec.planner.stubPreferences.PreferencesImpl;
import rec.planner.stubPreferences.PreferenzaStub;
import rec.planner.stubPreferences.StubApplication;
import rec.planner.stubPreferences.StubDataManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class SchedulerDemon extends Thread{

    private SchedulingDataManager dataManager = SchedulingMongoDataManager.getInstance();
    private PreferenzeApi preferenzeApi= PreferenzeRestApi.getRestApi();

    private Scheduler scheduler = PythonScheduler.fittingProduzione();
    private static final int hour_min = 24;
    private static final int hour_max = 6;
    private static final int delay = 1800 * 1000;
    @Override
    public void run(){

        System.out.println("start demon scheduler");
        while (true) {

            if (true || !dataManager.isSchedulingPresent(domani())) {


                System.out.println("ok");
                while (LocalDateTime.now().isBefore(Day.getNextDayStart().minusHours(hour_min)))
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }


                while (!dataManager.isForecastingPresent(domani()) && !Day.getNextDayStart().minusHours(hour_max).isAfter(LocalDateTime.now())) {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }


                ForecastingGiornaliero forecasting = null;

                LocalDate forecastingDate = domani();

                while (forecasting==null && forecastingDate.isAfter(LocalDate.EPOCH)) {
                    System.out.println("sono qua");
                    try {
                        forecasting = dataManager.getForecasting(forecastingDate);
                        System.out.println(forecasting);
                    } catch (NotFoundElementException ignored) {
                    }
                    forecastingDate = forecastingDate.minusDays(1);
                }

                if(forecasting == null){
                    System.out.println("Impossibile effettuare lo scheduling a causa della mancanza di un forecasting");
                    try {
                        Thread.sleep(1000*3600*hour_min);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }

                List<Consumatore> consumatori = dataManager.getConsumatori();

                Map<String, Preferenza> preferenze = new HashMap<>();
                List<Consumatore> consumatoriScheduling = new ArrayList<>();

                for(Consumatore consumatore: consumatori){
                    try {
                        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                                .registerTypeAdapter(Boolean.class, new BooleanToIntSerializer()).create();
                        //Preferenza preferenza = preferenzeApi.getPreferenze(consumatore.getSmartMeter());
                        Preferenza preferenza =
                                new Preferenza(Day.getNextDay(), consumatore.getSmartMeter(), MTUArray.ofValues(generateArray()), new Random().nextInt(4), Tipologia.NON_INTERROMPIBILE);
                        consumatoriScheduling.add(consumatore);
                        preferenze.put(consumatore.getSmartMeter(),preferenza);


                    } catch(RuntimeException ignored) {}

                }

                TariffeCorrenti tariffeCorrenti = dataManager.getTariffeCorrrenti();



                try {
                    SchedulingGiornaliero scheduling = scheduler.schedula(domani(),preferenze, consumatoriScheduling, forecasting, tariffeCorrenti);
                    dataManager.setScheduling(scheduling);
                    System.out.println("scheduling effettuato");
                }catch (Exception e){
                    System.out.println("impossibile effettuare lo scheduling");
                    try {
                        Thread.sleep(1000*3600*hour_min);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
    }

    private LocalDate domani(){
        return Day.getNextDay();
    }

    private List<Boolean> generateArray(){

        List<Boolean> array = new ArrayList<>();
        for(int i = 0 ; i <24; i++)
            array.add(new Random().nextBoolean());

        return  array;
    }

}
