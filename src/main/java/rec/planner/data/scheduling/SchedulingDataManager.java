package rec.planner.data.scheduling;

import rec.planner.exception.NotFoundElementException;
import rec.planner.model.giornalieri.ForecastingGiornaliero;
import rec.planner.model.giornalieri.SchedulingGiornaliero;
import rec.planner.model.giornalieri.TariffeCorrenti;
import rec.planner.model.instantanee.Consumatore;

import java.time.LocalDate;
import java.util.List;

public interface SchedulingDataManager {

    List<Consumatore> getConsumatori();
    TariffeCorrenti getTariffeCorrrenti();
    ForecastingGiornaliero getForecasting(LocalDate date) throws NotFoundElementException;
    SchedulingGiornaliero getScheduling(LocalDate date) throws NotFoundElementException;
    boolean isForecastingPresent(LocalDate date);
    boolean isSchedulingPresent(LocalDate date);
    void setScheduling(SchedulingGiornaliero scheduling);
}
