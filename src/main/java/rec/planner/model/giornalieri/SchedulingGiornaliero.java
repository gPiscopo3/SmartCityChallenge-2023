package rec.planner.model.giornalieri;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SchedulingGiornaliero implements Serializable {

    private LocalDate giorno;
    private List<AllocazioneConsumatore> consumatori;

    public SchedulingGiornaliero(LocalDate giorno, List<AllocazioneConsumatore> consumatori) {
        this.giorno = giorno;
        this.consumatori = consumatori;
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


}
