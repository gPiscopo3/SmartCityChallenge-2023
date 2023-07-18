package rec.planner.data.forecasting;

import rec.planner.exception.NotFoundElementException;
import rec.planner.model.giornalieri.ForecastingGiornaliero;
import rec.planner.model.giornalieri.IrradianzaGiornaliera;

import java.time.LocalDate;

public interface ForecastingDataManager{

    ForecastingGiornaliero getForecasting(LocalDate date) throws NotFoundElementException;
    boolean isForecastingPresent(LocalDate date);
    void setForecasting(ForecastingGiornaliero forecasting);
    IrradianzaGiornaliera getIrradiazioneGiornaliera(LocalDate date) throws NotFoundElementException;
    void setIrradiazioneGiornaliera(IrradianzaGiornaliera irradianzaGiornaliera);
    boolean isIrradiazioneGiornalieraPresent(LocalDate date);

}
