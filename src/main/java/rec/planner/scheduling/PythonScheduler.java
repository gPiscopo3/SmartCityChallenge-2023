package rec.planner.scheduling;

import com.google.gson.Gson;
import rec.planner.Configuration;
import rec.planner.model.*;
import rec.planner.model.giornalieri.*;
import rec.planner.model.instantanee.Consumatore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static rec.planner.model.Day.MTU_NUMBER;


public class PythonScheduler implements Scheduler, Configuration {


    private static final String costOptimizer = COST_OPTIMIZER_FILE;
    private static final String fittingProduzione = FITTING_FILE;
    private final String filepath;
    private final Gson gson = new Gson();


    private PythonScheduler(String filepath) {
        this.filepath = filepath;
    }

    public static PythonScheduler costOptimizer(){
        return new PythonScheduler(costOptimizer);
    }

    public static PythonScheduler fittingProduzione(){
        return new PythonScheduler(fittingProduzione);
    }

    @Override
    public SchedulingGiornaliero schedula(LocalDate giorno, Map<String, Preferenza> preferenze, List<Consumatore> consumatori,
                                          ForecastingGiornaliero forecasting, TariffeCorrenti tariffeCorrenti) throws Exception {

        int nConsumatori = consumatori.size();
        double[] produzione = new double[MTU_NUMBER];
        double[] consumi = new double[nConsumatori];
        double[] costi = new double[MTU_NUMBER];
        double[] ricavi = new double[MTU_NUMBER];
        double[] incentivi = new double[MTU_NUMBER];
        int[] durata = new int[nConsumatori];
        int[][] disponibilita = new int[MTU_NUMBER][nConsumatori];
        int[] interrompibile = new int[nConsumatori];


        for(int j = 0; j < nConsumatori; j++){
            Consumatore consumatore = consumatori.get(j);
            consumi[j] = consumatore.getConsumoMedio();
            durata[j] = preferenze.get(consumatore.getSmartMeter()).getDurata();
            for(int i = 0; i < MTU_NUMBER; i++) {
                Boolean disp = preferenze.get(consumatore.getSmartMeter()).getDisponibilita().getValue(i);
                if (disp!=null && disp)
                    disponibilita[i][j] = 1;
                else
                    disponibilita[i][j] = 0;
            }
            if(preferenze.get(consumatore.getSmartMeter()).getTipologia().equals(Tipologia.INTERROMPIBILE))
                interrompibile[j] = 1;
            else
                interrompibile[j] = 0;
        }




        for(int i = 0 ; i < MTU_NUMBER; i++){
            produzione[i] = forecasting.getProduzione().getValue(i);
            costi[i] = tariffeCorrenti.getCosti().getValue(i);
            ricavi[i] = tariffeCorrenti.getRicavi().getValue(i);
            incentivi[i] = tariffeCorrenti.getIncentivi().getValue(i);
        }



        //esegui il modello
        ProcessBuilder processBuilder = new ProcessBuilder("python3", filepath, String.valueOf(MTU_NUMBER), String.valueOf(nConsumatori),
                gson.toJson(produzione), gson.toJson(consumi), gson.toJson(costi), gson.toJson(ricavi), gson.toJson(incentivi),
                gson.toJson(disponibilita), gson.toJson(durata), gson.toJson(interrompibile));

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while((line = reader.readLine())!=null && !line.equals("#"))
                System.out.println(line);
            String error;
            while((error = errorReader.readLine()) !=null)
                System.out.println(error);
            List<AllocazioneConsumatore> arrayScheduling = new ArrayList<>();
            reader.readLine();
            for(int j = 0; j < nConsumatori; j++){

                List<Boolean> arrayAllocazione = new LinkedList<>();
                for(int i = 0 ; i < MTU_NUMBER; i++) {
                    if (Double.parseDouble(reader.readLine()) == 1)
                        arrayAllocazione.add(true);
                    else
                        arrayAllocazione.add(false);

                }
                arrayScheduling.add(new AllocazioneConsumatore(forecasting.getGiorno(), consumatori.get(j).getSmartMeter(), MTUArray.ofValues(arrayAllocazione)));

            }

            return new SchedulingGiornaliero(forecasting.getGiorno(), arrayScheduling);


    }

}
