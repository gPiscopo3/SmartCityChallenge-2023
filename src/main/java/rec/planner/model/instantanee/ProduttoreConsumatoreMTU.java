package rec.planner.model.instantanee;

import rec.planner.model.Day;

import java.io.Serializable;
import java.time.LocalDate;

public class ProduttoreConsumatoreMTU implements Serializable {
    String smartMeter;
    double value;
    int mtu;
    LocalDate giorno;

    public ProduttoreConsumatoreMTU(String smartMeter, double value, int mtu, LocalDate giorno) {
        this.smartMeter = smartMeter;
        this.value = value;
        this.mtu = mtu;
        this.giorno = giorno;
    }

    public static ProduttoreConsumatoreMTU createProduttoreAttuale(String smartMeter, double prod){
        return new ProduttoreConsumatoreMTU(smartMeter, prod, Day.getMTU(), Day.getDay());
    }

    public void setTime(){
        mtu = Day.getMTU();
        giorno = Day.getDay();
    }


    public ProduttoreConsumatoreMTU() {
    }

    public String getSmartMeter() {
        return smartMeter;
    }

    public void setSmartMeter(String smartMeter) {
        this.smartMeter = smartMeter;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public int getMtu() {
        return mtu;
    }

    public void setMtu(int mtu) {
        this.mtu = mtu;
    }

    public LocalDate getGiorno() {
        return giorno;
    }

    public void setGiorno(LocalDate giorno) {
        this.giorno = giorno;
    }

    @Override
    public String toString() {
        return "ProduttoreConsumatoreMTU{" +
                "smartMeter='" + smartMeter + '\'' +
                ", value=" + value +
                ", mtu=" + mtu +
                ", giorno=" + giorno +
                '}';
    }
}
