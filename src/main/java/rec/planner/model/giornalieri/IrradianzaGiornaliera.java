package rec.planner.model.giornalieri;

import rec.planner.model.MTUArray;

import java.time.LocalDate;

public class IrradianzaGiornaliera {

    private LocalDate giorno;
    private MTUArray<Double> irradiazione;

    public IrradianzaGiornaliera(LocalDate giorno, MTUArray<Double> irradiazione) {
        this.giorno = giorno;
        this.irradiazione = irradiazione;
    }

    public IrradianzaGiornaliera() {
    }

    public LocalDate getGiorno() {
        return giorno;
    }

    public void setGiorno(LocalDate giorno) {
        this.giorno = giorno;
    }

    public MTUArray<Double> getIrradiazione() {
        return irradiazione;
    }

    public void setIrradiazione(MTUArray<Double> irradiazione) {
        this.irradiazione = irradiazione;
    }

    @Override
    public String toString() {
        return "IrradiazioneGiornaliera{" +
                "giorno=" + giorno +
                ", irradiazione=" + irradiazione +
                '}';
    }
}
