package rec.planner.data.kafka;

import rec.planner.exception.AlreadyPresentElementException;
import rec.planner.exception.NotFoundElementException;
import rec.planner.model.instantanee.Consumatore;
import rec.planner.model.instantanee.ProduttoreConsumatoreMTU;
import rec.planner.model.instantanee.TariffaOraria;

public interface KafkaDataManager {

    void addProduttore(ProduttoreConsumatoreMTU produttore);
    void updateConsumatore(String smartMeter, double consumo, double factorEWMA) throws NotFoundElementException;
    void addTariffa(TariffaOraria tariffaOraria);
    void registerConsumatore(Consumatore consumatore) throws AlreadyPresentElementException;

}
