package rec.planner.model.instantanee;

import java.time.LocalDate;

public class IrradianzaMTU {

    double irradianza;
    int mtu;
    LocalDate giorno;

    public IrradianzaMTU() {
    }

    public IrradianzaMTU(double irradianza, int mtu, LocalDate giorno) {
        this.irradianza = irradianza;
        this.mtu = mtu;
        this.giorno = giorno;
    }

    public double getIrradianza() {
        return irradianza;
    }

    public void setIrradianza(double irradianza) {
        this.irradianza = irradianza;
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
        return "IrradianzaMTU{" +
                "irradianza=" + irradianza +
                ", mtu=" + mtu +
                ", giorno=" + giorno +
                '}';
    }

}
