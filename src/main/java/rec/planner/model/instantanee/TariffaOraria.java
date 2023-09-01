package rec.planner.model.instantanee;

public class TariffaOraria {


    private double costoAcquisto;
    private double ricavoVendita;
    private double incentivo;
    private int ora;

    public TariffaOraria() {
    }

    public TariffaOraria(double costoAcquisto, double ricavoVendita, double incentivo, int ora) {
        this.costoAcquisto = costoAcquisto;
        this.ricavoVendita = ricavoVendita;
        this.incentivo = incentivo;
        this.ora = ora;
    }

    public double getCostoAcquisto() {
        return costoAcquisto;
    }

    public void setCostoAcquisto(double costoAcquisto) {
        this.costoAcquisto = costoAcquisto;
    }

    public double getRicavoVendita() {
        return ricavoVendita;
    }

    public void setRicavoVendita(double ricavoVendita) {
        this.ricavoVendita = ricavoVendita;
    }

    public double getIncentivo() {
        return incentivo;
    }

    public void setIncentivo(double incentivo) {
        this.incentivo = incentivo;
    }

    public int getOra() {
        return ora;
    }

    public void setOra(int ora) {
        this.ora = ora;
    }
}
