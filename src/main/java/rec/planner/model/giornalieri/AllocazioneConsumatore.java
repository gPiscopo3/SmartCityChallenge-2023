package rec.planner.model.giornalieri;

import rec.planner.model.MTUArray;

import java.time.LocalDate;

public class AllocazioneConsumatore {

    private LocalDate giorno;
    private String smartMeter;
    private MTUArray<Boolean> allocazione = new MTUArray<>();


    public AllocazioneConsumatore(LocalDate giorno, String smartMeter, MTUArray<Boolean> allocazione) {
        this.giorno = giorno;
        this.smartMeter = smartMeter;
        this.allocazione = allocazione;
    }

    public AllocazioneConsumatore() {

    }

    public String getSmartMeter() {
        return smartMeter;
    }

    public void setSmartMeter(String smartMeter) {
        this.smartMeter = smartMeter;
    }

    public MTUArray<Boolean> getAllocazione() {
        return allocazione;
    }

    public void setAllocazione(MTUArray<Boolean> allocazione) {
        this.allocazione = allocazione;
    }

    public LocalDate getGiorno() {
        return giorno;
    }

    public void setGiorno(LocalDate giorno) {
        this.giorno = giorno;
    }
}
