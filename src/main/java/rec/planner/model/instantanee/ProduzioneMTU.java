package rec.planner.model.instantanee;

import java.time.LocalDate;

public class ProduzioneMTU {

    double produzione;
    int mtu;
    LocalDate giorno;

    public ProduzioneMTU() {
    }

    public ProduzioneMTU(double produzione, int mtu, LocalDate giorno) {
        this.produzione = produzione;
        this.mtu = mtu;
        this.giorno = giorno;
    }

    public double getProduzione() {
        return produzione;
    }

    public void setProduzione(double produzione) {
        this.produzione = produzione;
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
        return "ProduzioneMTU{" +
                "produzione=" + produzione +
                ", mtu=" + mtu +
                ", giorno=" + giorno +
                '}';
    }
}
