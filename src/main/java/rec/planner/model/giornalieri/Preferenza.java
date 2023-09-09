package rec.planner.model.giornalieri;

import rec.planner.model.MTUArray;
import rec.planner.model.Tipologia;

import java.time.LocalDate;

import static rec.planner.model.Day.MTU_NUMBER;

public class Preferenza {

    private LocalDate giorno;
    private String smartMeter;
    private MTUArray<Boolean> disponibilita;
    private int durata;
    private Tipologia tipologia;

    public Preferenza(LocalDate giorno, String smartMeter, MTUArray<Boolean> disponibilita, int durata, Tipologia tipologia) {
        this.giorno = giorno;
        this.smartMeter = smartMeter;
        this.disponibilita = disponibilita;
        this.durata = durata;
        this.tipologia = tipologia;
    }

    public Preferenza() {
        disponibilita = new MTUArray<>();
    }

    public String getSmartMeter() {
        return smartMeter;
    }

    public void setSmartMeter(String smartMeter) {
        this.smartMeter = smartMeter;
    }

    public MTUArray<Boolean> getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(MTUArray<Boolean> disponibilita) {
        this.disponibilita = disponibilita;
    }

    public int getDurata() {
        return (int) (((double)durata*MTU_NUMBER)/disponibilita.getMtuNumber());
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public Tipologia getTipologia() {
        return tipologia;
    }

    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    public LocalDate getGiorno() {
        return giorno;
    }

    public void setGiorno(LocalDate giorno) {
        this.giorno = giorno;
    }

    @Override
    public String toString() {
        return "Preferenza{" +
                "giorno=" + giorno +
                ", smartMeter='" + smartMeter + '\'' +
                ", disponibilita=" + getDisponibilita() +
                ", durata=" + durata +
                ", tipologia=" + tipologia +
                '}';
    }
}
