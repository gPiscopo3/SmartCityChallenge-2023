package rec.planner.data.forecasting;

import rec.planner.model.giornalieri.IrradianzaGiornaliera;

import java.time.LocalDate;

public interface MeteoService {

    IrradianzaGiornaliera getIrradazioneGiornaliera(LocalDate localDate);

}
