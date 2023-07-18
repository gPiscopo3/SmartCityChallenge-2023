package rec.planner.data.restApi;

import rec.planner.exception.NotFoundElementException;
import rec.planner.model.giornalieri.ForecastingGiornaliero;
import rec.planner.model.giornalieri.SchedulingGiornaliero;

import java.time.LocalDate;

public interface RestApiDataManager {

    SchedulingGiornaliero getScheduling(LocalDate localDate) throws NotFoundElementException;
    ForecastingGiornaliero getForecasting(LocalDate localDate) throws NotFoundElementException;
}
