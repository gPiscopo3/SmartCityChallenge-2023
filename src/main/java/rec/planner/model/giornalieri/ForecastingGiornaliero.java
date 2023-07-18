package rec.planner.model.giornalieri;

import rec.planner.model.MTUArray;

import java.time.LocalDate;

public class ForecastingGiornaliero {
    private LocalDate giorno;
    private MTUArray<Double> produzione;

    public ForecastingGiornaliero(LocalDate giorno, MTUArray<Double> produzione) {
        this.giorno = giorno;
        this.produzione = produzione;
    }

    public ForecastingGiornaliero() {
        produzione = new MTUArray<>();
    }

    public LocalDate getGiorno() {
        return giorno;
    }

    public void setGiorno(LocalDate giorno) {
        this.giorno = giorno;
    }

    public MTUArray<Double> getProduzione() {
        return produzione;
    }

    public void setProduzione(MTUArray<Double> produzione) {
        this.produzione = produzione;
    }

    @Override
    public String toString() {
        return "ForecastingGiornaliero{" +
                "giorno=" + giorno +
                ", produzione=" + produzione +
                '}';
    }
}
