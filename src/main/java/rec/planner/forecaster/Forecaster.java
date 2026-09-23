package rec.planner.forecaster;

import rec.planner.model.giornalieri.ForecastingGiornaliero;
import rec.planner.model.giornalieri.IrradianzaGiornaliera;

import java.time.LocalDate;

public interface Forecaster {

    ForecastingGiornaliero forecasta(LocalDate giorno, IrradianzaGiornaliera irradianza);


}
