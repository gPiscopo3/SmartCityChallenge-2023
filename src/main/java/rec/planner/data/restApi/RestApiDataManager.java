package rec.planner.data.restApi;

import rec.planner.exception.AlreadyPresentElementException;
import rec.planner.exception.NotFoundElementException;
import rec.planner.model.giornalieri.ForecastingGiornaliero;
import rec.planner.model.giornalieri.Preferenza;
import rec.planner.model.giornalieri.SchedulingGiornaliero;
import rec.planner.model.instantanee.Consumatore;
import rec.planner.model.instantanee.ProduttoreConsumatoreMTU;

import java.time.LocalDate;
import java.util.List;

public interface RestApiDataManager {


    List<Consumatore> getConsumatoriByHomeController(String homeController);

    SchedulingGiornaliero getScheduling(LocalDate localDate) throws NotFoundElementException;
    ForecastingGiornaliero getForecasting(LocalDate localDate) throws NotFoundElementException;
    Preferenza getPreferenze(String smartMeter) throws NotFoundElementException;
    List<Preferenza> getPreferenze();
    boolean isForecastingPresent(LocalDate date);
    boolean isSchedulingPresent(LocalDate date);

    void savePreferenza(Preferenza preferenza);

    void addProduttore(ProduttoreConsumatoreMTU produttore);
    void updateConsumatore(String smartMeter, double consumo, double factorEWMA) throws NotFoundElementException;
    void registerConsumatore(Consumatore consumatore) throws AlreadyPresentElementException;
    List<String> getHomeController();

}
