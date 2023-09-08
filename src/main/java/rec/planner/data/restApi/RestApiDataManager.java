package rec.planner.data.restApi;

import rec.planner.exception.NotFoundElementException;
import rec.planner.model.giornalieri.ForecastingGiornaliero;
import rec.planner.model.giornalieri.Preferenza;
import rec.planner.model.giornalieri.SchedulingGiornaliero;
import rec.planner.model.instantanee.Consumatore;
import rec.planner.model.instantanee.ProduttoreConsumatoreMTU;

import java.time.LocalDate;
import java.util.List;

public interface RestApiDataManager {

    SchedulingGiornaliero getScheduling(LocalDate localDate) throws NotFoundElementException;
    ForecastingGiornaliero getForecasting(LocalDate localDate) throws NotFoundElementException;
    Preferenza getPreferenze(LocalDate date, String smartMeter) throws NotFoundElementException;
    List<Preferenza> getPreferenze(LocalDate date);
    boolean isForecastingPresent(LocalDate date);
    boolean isSchedulingPresent(LocalDate date);

    void savePreferenza(Preferenza preferenza);

    void addProduttore(ProduttoreConsumatoreMTU produttore);
    void updateConsumatore(String smartMeter, double consumo, double factorEWMA);
    void registerConsumatore(Consumatore consumatore);
}
