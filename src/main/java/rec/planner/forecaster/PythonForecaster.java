package rec.planner.forecaster;

import com.opencsv.CSVWriter;
import rec.planner.Configuration;
import rec.planner.model.Day;
import rec.planner.model.MTUArray;
import rec.planner.model.giornalieri.ForecastingGiornaliero;
import rec.planner.model.giornalieri.IrradianzaGiornaliera;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class PythonForecaster implements Forecaster, Configuration {

    private static final String forecaster = FORECASTER_FILE;
    private static final String trainer = TRAINER_FILE;

    public PythonForecaster() {

    }

    private void toCSV(String csvPathFile, IrradianzaGiornaliera irradianza) throws IOException {

        CSVWriter writer = new CSVWriter(new FileWriter(csvPathFile), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                                            CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        String[] headers = {"DATE_TIME", "IRRADIATION"};
        writer.writeNext(headers);

        LocalDate ora = irradianza.getGiorno();
        int i = 0;

        for (Double value : irradianza.getIrradiazione()){
            String[] data = {ora.toString() + " " + Day.getStartTime(i) + ":00", Double.toString(value)};
            String[] data2 = {ora.toString() + " " + Day.getStartTime(i) + ":15", Double.toString(value)};
            String[] data3 = {ora.toString() + " " + Day.getStartTime(i) + ":30", Double.toString(value)};
            String[] data4 = {ora.toString() + " " + Day.getStartTime(i) + ":45", Double.toString(value)};
            writer.writeNext(data);
            writer.writeNext(data2);
            writer.writeNext(data3);
            writer.writeNext(data4);
            i++;
        }
        writer.close();
    }

    @Override
    public ForecastingGiornaliero forecasta(LocalDate giorno, IrradianzaGiornaliera irradianza) {

        System.out.println("Forecasting...");
        System.out.println(irradianza.toString());
        try{
            /* to 96 */

            toCSV("irradiance.csv", irradianza);
        }catch(IOException e){
            System.err.println("Dati irradianza non trovati: " + e);
            return null;
        }

        ProcessBuilder processBuilder = new ProcessBuilder("python3", forecaster);

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader reader_error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            List<String> results = new ArrayList<>();
            String line;
            String error;

            while ((line = reader.readLine()) != null) {
                results.add(line);
            }

            while((error = reader_error.readLine()) != null){
                System.out.println(error);
            }

            int exitCode = process.waitFor();
            results.remove(0);

            if (exitCode == 0) {
                // Il processo è terminato correttamente
                System.out.println("Forecasting terminato con Successo");
                List<Double> values = new ArrayList<>();

                for(String value : results){
                    double d = Double.parseDouble(value);
                    if (d < 0.1)
                        d = 0.0;
                    values.add(d);
                }

                return new ForecastingGiornaliero(Day.getNextDay(), MTUArray.ofValues(values));

            } else {
                // Il processo ha restituito un codice di errore
                System.out.println("Il processo Python Forecaster ha restituito un codice di errore: " + exitCode);
            }

        }catch(Exception e){
            System.err.println(e);
        }

        return null;
    }

}
