package rec.planner.forecaster;

import rec.planner.Configuration;
import rec.planner.data.forecasting.ForecastingDataManager;
import rec.planner.data.forecasting.ForecastingMongoDataManager;
import rec.planner.data.forecasting.SolcastMeteoService;
import rec.planner.exception.NotFoundElementException;
import rec.planner.model.Day;
import rec.planner.model.giornalieri.ForecastingGiornaliero;
import rec.planner.model.giornalieri.IrradianzaGiornaliera;
import rec.planner.streamprocessor.Producer;
import rec.planner.streamprocessor.ProducerForecasting;

import java.io.IOException;

public class ForecasterDemon extends Thread implements Configuration {

    private final ForecastingDataManager dataManager = ForecastingMongoDataManager.getInstance();
    private final SolcastMeteoService meteoService = SolcastMeteoService.getInstance();
    private final Forecaster forecaster = new PythonForecaster();
    private static final int delay = 3600*12*1000;

    private final Producer producer = new ProducerForecasting(ADDRESS_KAFKA);

    @Override
    public void run(){

        System.out.println("start demon forecaster");
        while(true){


            if(!dataManager.isForecastingPresent(Day.getNextDay())){

                IrradianzaGiornaliera irradianzaGiornaliera;

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


                ForecastingGiornaliero forecastingGiornaliero = forecaster.forecasta(Day.getNextDay(), irradianzaGiornaliera);
                dataManager.setForecasting(forecastingGiornaliero);
                System.out.println("forecasting effettuato");
                try {
                    producer.produce("DA_gen_data", forecastingGiornaliero);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            try{
                Thread.sleep(delay);
            }catch(InterruptedException e){
                throw new RuntimeException(e);
            }

        }

    }


}
