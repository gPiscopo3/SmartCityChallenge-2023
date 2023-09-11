package rec.planner.forecaster;

import rec.planner.Configuration;
import rec.planner.data.forecasting.ForecastingDataManager;
import rec.planner.data.forecasting.SolcastMeteoService;
import rec.planner.data.mongo.MongoDataManager;
import rec.planner.exception.NotFoundElementException;
import rec.planner.model.Day;
import rec.planner.model.giornalieri.ForecastingGiornaliero;
import rec.planner.model.giornalieri.IrradianzaGiornaliera;
import rec.planner.streamprocessor.Producer;
import rec.planner.streamprocessor.ProducerForecasting;

import java.io.IOException;

public class ForecasterDemon extends Thread implements Configuration {

    private final ForecastingDataManager dataManager = MongoDataManager.getInstance();
    private final SolcastMeteoService meteoService = SolcastMeteoService.getInstance();
    private final Forecaster forecaster = new PythonForecaster();
    private static final int delay = 3600*12*1000;

    private final Producer producer = new ProducerForecasting(ADDRESS_KAFKA);

    @Override
    public void run(){

        System.out.println("Starting forecaster demon");
        while(true){

            System.out.println("Checking for the next day energy forecast");
            if(!dataManager.isForecastingPresent(Day.getNextDay())){

                IrradianzaGiornaliera irradianzaGiornaliera;
                System.out.println("Energy forecasting not present, retrieving meteo forecast");

                if(!dataManager.isIrradiazioneGiornalieraPresent(Day.getNextDay()))
                {
                    irradianzaGiornaliera = meteoService.getIrradazioneGiornaliera(Day.getNextDay());
                    dataManager.setIrradiazioneGiornaliera(irradianzaGiornaliera);
                }

                try {
                    irradianzaGiornaliera = dataManager.getIrradiazioneGiornaliera(Day.getNextDay());
                } catch (NotFoundElementException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Forecasting next day's energy production");
                System.out.println(irradianzaGiornaliera.toString());
                ForecastingGiornaliero forecastingGiornaliero = forecaster.forecasta(Day.getNextDay(), irradianzaGiornaliera);
                System.out.println(forecastingGiornaliero.toString());
                dataManager.setForecasting(forecastingGiornaliero);

                System.out.println("Forecasting perfomed");
                try {
                    producer.produce("DA_gen_data", forecastingGiornaliero);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Next day energy forecast is now on database.");

            }

            try{
                Thread.sleep(delay);
            }catch(InterruptedException e){
                throw new RuntimeException(e);
            }

        }

    }


}
