package rec.planner.model.giornalieri;

import rec.planner.model.Day;
import rec.planner.model.MTUArray;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static rec.planner.model.Day.MTU_NUMBER;

public class SchedulingGiornaliero implements Serializable {

    private LocalDate giorno;
    private List<AllocazioneConsumatore> consumatori;

    private MTUArray<Double> totaleConsumato;

    public SchedulingGiornaliero(LocalDate giorno, List<AllocazioneConsumatore> consumatori) {
        this.giorno = giorno;
        this.consumatori = consumatori;

        for(int i = 0; i <MTU_NUMBER; i++){
            double totale = 0;
            for(AllocazioneConsumatore consumatore: consumatori){
                if(consumatore.getAllocazione().getValue(i).equals(true))
                    totale = totale + consumatore.getConsumo();
            }
        }

    }

    public SchedulingGiornaliero() {
        consumatori = new ArrayList<>();
    }

    public LocalDate getGiorno() {
        return giorno;
    }

    public void setGiorno(LocalDate giorno) {
        this.giorno = giorno;
    }

    public List<AllocazioneConsumatore> getConsumatori() {
        return consumatori;
    }

    public void setConsumatori(List<AllocazioneConsumatore> consumatori) {
        this.consumatori = consumatori;
    }


    public MTUArray<Double> getTotaleConsumato() {
        return totaleConsumato;
    }

    public void setTotaleConsumato(MTUArray<Double> totaleConsumato) {
        this.totaleConsumato = totaleConsumato;
    }
}
