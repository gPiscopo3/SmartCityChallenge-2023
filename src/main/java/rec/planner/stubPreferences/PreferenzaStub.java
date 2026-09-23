package rec.planner.stubPreferences;

import rec.planner.model.Tipologia;

import java.io.Serializable;
import java.util.List;

public class PreferenzaStub implements Serializable {

    String smartMeter;
    List<Integer> disponibilita;
    int durata;
    Tipologia tipo;

    public PreferenzaStub(String smartMeter, List<Integer> disponibilita, int durata, Tipologia tipo) {
        this.smartMeter = smartMeter;
        this.disponibilita = disponibilita;
        this.durata = durata;
        this.tipo = tipo;
    }

    public PreferenzaStub() {
    }

    public String getSmartMeter() {
        return smartMeter;
    }

    public void setSmartMeter(String smartMeter) {
        this.smartMeter = smartMeter;
    }

    public List<Integer> getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(List<Integer> disponibilita) {
        this.disponibilita = disponibilita;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public Tipologia getTipo() {
        return tipo;
    }

    public void setTipo(Tipologia tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "PreferenzaStub{" +
                "smartMeter='" + smartMeter + '\'' +
                ", disponibilita=" + disponibilita +
                ", durata=" + durata +
                ", tipo=" + tipo +
                '}';
    }


    public static class Wrapper{
        public List<Integer> values;

        public Wrapper(List<Integer> values) {
            this.values = values;
        }
    }
}
