package rec.planner.data.scheduling;

import rec.planner.exception.NotFoundElementException;
import rec.planner.model.giornalieri.Preferenza;

public interface PreferenzeApi {

    Preferenza getPreferenze(String smartMeter) throws NotFoundElementException;

}
