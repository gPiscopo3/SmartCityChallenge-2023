package rec.planner.data.kafka;

import rec.planner.model.instantanee.Consumatore;
import rec.planner.model.instantanee.ProduttoreConsumatoreMTU;
import rec.planner.model.instantanee.TariffaOraria;

public interface KafkaDataManager {

    void addProduttore(ProduttoreConsumatoreMTU produttore);
    void updateConsumatore(ProduttoreConsumatoreMTU consumatore, double factorEWMA);
    void addTariffa(TariffaOraria tariffaOraria);
    void registerConsumatore(Consumatore consumatore);

}
