package rec.planner.scheduling;

import rec.planner.model.giornalieri.ForecastingGiornaliero;
import rec.planner.model.giornalieri.Preferenza;
import rec.planner.model.giornalieri.SchedulingGiornaliero;
import rec.planner.model.giornalieri.TariffeCorrenti;
import rec.planner.model.instantanee.Consumatore;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Scheduler {

    SchedulingGiornaliero schedula(LocalDate giorno, Map<String, Preferenza> preferenze, List<Consumatore> consumatori,
                                   ForecastingGiornaliero forecasting, TariffeCorrenti tariffeCorrenti) throws Exception;
}
