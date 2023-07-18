package rec.planner.model.giornalieri;

import rec.planner.model.MTUArray;

public class TariffeCorrenti {

    private MTUArray<Double> costi = new MTUArray<>();
    private MTUArray<Double> ricavi = new MTUArray<>();
    private MTUArray<Double> incentivi = new MTUArray<>();

    public TariffeCorrenti(MTUArray<Double> costi, MTUArray<Double> ricavi, MTUArray<Double> incentivi) {
        this.costi = costi;
        this.ricavi = ricavi;
        this.incentivi = incentivi;
    }

    public TariffeCorrenti() {
    }

    public MTUArray<Double> getCosti() {
        return costi;
    }

    public void setCosti(MTUArray<Double> costi) {
        this.costi = costi;
    }

    public MTUArray<Double> getRicavi() {
        return ricavi;
    }

    public void setRicavi(MTUArray<Double> ricavi) {
        this.ricavi = ricavi;
    }

    public MTUArray<Double> getIncentivi() {
        return incentivi;
    }

    public void setIncentivi(MTUArray<Double> incentivi) {
        this.incentivi = incentivi;
    }
}
