package rec.planner.model.instantanee;

import java.io.Serializable;

public class Consumatore implements Serializable {

    private String smartMeter;
    private double consumoMedio;
    private double consumoNominale;

    public Consumatore(String smartMeter, double consumoMedio) {
        this.smartMeter = smartMeter;
        this.consumoMedio = consumoMedio;
    }

    public Consumatore(String smartMeter, double consumoMedio, double consumoNominale) {
        this.smartMeter = smartMeter;
        this.consumoMedio = consumoMedio;
        this.consumoNominale = consumoNominale;
    }

    public Consumatore() {
    }

    public String getSmartMeter() {
        return smartMeter;
    }

    public void setSmartMeter(String smartMeter) {
        this.smartMeter = smartMeter;
    }

    public double getConsumoMedio() {
        return consumoMedio;
    }

    public void setConsumoMedio(double consumoMedio) {
        this.consumoMedio = consumoMedio;
    }

    public double getConsumoNominale() {
        return consumoNominale;
    }

    public void setConsumoNominale(double consumoNominale) {
        this.consumoNominale = consumoNominale;
    }
}
