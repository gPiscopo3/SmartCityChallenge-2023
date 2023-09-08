package rec.planner.apiEsterne;


import jakarta.ws.rs.core.Response;
import rec.planner.data.mongo.MongoDataManager;
import rec.planner.data.restApi.RestApiDataManager;
import rec.planner.exception.NotFoundElementException;
import rec.planner.model.Day;
import rec.planner.model.giornalieri.AllocazioneConsumatore;
import rec.planner.model.giornalieri.ForecastingGiornaliero;
import rec.planner.model.giornalieri.Preferenza;
import rec.planner.model.giornalieri.SchedulingGiornaliero;
import rec.planner.model.instantanee.Consumatore;
import rec.planner.model.instantanee.ProduttoreConsumatoreMTU;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static rec.planner.model.Day.MTU_NUMBER;


public class RestApiImpl implements RestApi {


    private final RestApiDataManager dataManager = MongoDataManager.getInstance();


    public RestApiImpl() {

    }

    private LocalDate getLocalDate(String data) throws DateTimeParseException {
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(data, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            localDate = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }
        return localDate;
    }


    @Override
    public Response getSchedulingByDate(String data, String smartMeter) {

        SchedulingGiornaliero schedulingGiornaliero;
        LocalDate localDate;

        try {

            if (data == null || data.equals("") || data.equalsIgnoreCase("last")) {
                localDate = Day.getNextDay();
                while (!dataManager.isSchedulingPresent(localDate) && localDate.isAfter(LocalDate.EPOCH))
                    localDate = localDate.minusDays(1);
                if (localDate.equals(LocalDate.EPOCH))
                    return Response.status(Response.Status.NOT_FOUND).build();
            } else if (data.equalsIgnoreCase("today"))
                localDate = Day.getDay();
            else if (data.equalsIgnoreCase("tomorrow") || data.equalsIgnoreCase("next"))
                localDate = Day.getNextDay();
            else
                localDate = getLocalDate(data);
            schedulingGiornaliero = dataManager.getScheduling(localDate);
        } catch (NotFoundElementException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return getSchedulingResponse(schedulingGiornaliero, smartMeter);
    }


    private Response getSchedulingResponse(SchedulingGiornaliero schedulingGiornaliero, String smartMeter) {
        if (smartMeter != null && !smartMeter.equals("")) {
            for (AllocazioneConsumatore consumatore : schedulingGiornaliero.getConsumatori())
                if (consumatore.getSmartMeter().equalsIgnoreCase(smartMeter))
                    return Response.ok(consumatore).build();
        } else
            return Response.ok(schedulingGiornaliero).build();

        return Response.status(Response.Status.NOT_FOUND).build();

    }


    @Override
    public Response getForecastingByDate(String data) {
        ForecastingGiornaliero forecastingGiornaliero;
        LocalDate localDate;

        try {

            if (data == null || data.equals("") || data.equalsIgnoreCase("last")) {
                localDate = Day.getNextDay();
                while (!dataManager.isForecastingPresent(localDate) && localDate.isAfter(LocalDate.EPOCH))
                    localDate = localDate.minusDays(1);
                if (localDate.equals(LocalDate.EPOCH))
                    return Response.status(Response.Status.NOT_FOUND).build();
            } else if (data.equalsIgnoreCase("today"))
                localDate = Day.getDay();
            else if (data.equalsIgnoreCase("tomorrow") || data.equalsIgnoreCase("next"))
                localDate = Day.getNextDay();
            else
                localDate = getLocalDate(data);
            return Response.ok(dataManager.getForecasting(localDate)).build();
        } catch (NotFoundElementException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @Override
    public Response getBalanceForecastingByDate(String data) {
        LocalDate localDate;

        try {

            if (data == null || data.equals("") || data.equalsIgnoreCase("last")) {
                localDate = Day.getNextDay();
                while (!dataManager.isSchedulingPresent(localDate) && localDate.isAfter(LocalDate.EPOCH))
                    localDate = localDate.minusDays(1);
                if (localDate.equals(LocalDate.EPOCH))
                    return Response.status(Response.Status.NOT_FOUND).build();
            } else if (data.equalsIgnoreCase("today"))
                localDate = Day.getDay();
            else if (data.equalsIgnoreCase("tomorrow") || data.equalsIgnoreCase("next"))
                localDate = Day.getNextDay();
            else
                localDate = getLocalDate(data);
            SchedulingGiornaliero schedulingGiornaliero = dataManager.getScheduling(localDate);
            ForecastingGiornaliero forecastingGiornaliero = dataManager.getForecasting(localDate);

            List<Double> balance = new ArrayList<>();

            for(int i = 0; i < MTU_NUMBER; i++){
                balance.add(forecastingGiornaliero.getProduzione().getValue(i) - schedulingGiornaliero.getTotaleConsumato().getValue(i));
            }

            return Response.ok(balance).build();

        } catch (NotFoundElementException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @Override
    public Response getPreferenzeByDate(String data, String smartMeter) {

        LocalDate localDate;

        try {

            if (data == null || data.equals("") || data.equalsIgnoreCase("last")) {
                localDate = Day.getNextDay();
                while (!dataManager.isForecastingPresent(localDate) && localDate.isAfter(LocalDate.EPOCH))
                    localDate = localDate.minusDays(1);
                if (localDate.equals(LocalDate.EPOCH))
                    return Response.status(Response.Status.NOT_FOUND).build();
            } else if (data.equalsIgnoreCase("today"))
                localDate = Day.getDay();
            else if (data.equalsIgnoreCase("tomorrow") || data.equalsIgnoreCase("next"))
                localDate = Day.getNextDay();
            else
                localDate = getLocalDate(data);

            if(smartMeter!=null && !smartMeter.equals(""))
                return Response.ok(dataManager.getPreferenze(localDate, smartMeter)).build();
            else
                return Response.ok(dataManager.getPreferenze(localDate)).build();

        } catch (NotFoundElementException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @Override
    public Response setPreferenze(Preferenza preferenze) {


        try {
            dataManager.savePreferenza(preferenze);
            return Response.created(URI.create("preferenze/?data=" + preferenze.getGiorno().format(DateTimeFormatter.ISO_LOCAL_DATE)
                    + "&smartMeter=" + preferenze.getSmartMeter())).build();
        }catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }


    @Override
    public Response registerConsumatore(Consumatore consumatore) {

        dataManager.registerConsumatore(consumatore);
        return Response.created(URI.create("/consumatori/" + consumatore.getSmartMeter())).build();
    }

    @Override
    public Response getConsumatoriByHomeController(String homeController) {

        return Response.ok(dataManager.getConsumatoriByHomeController(homeController)).build();

    }

    @Override
    public Response updateConsumoInstantaneo(String smartMeter, double consumo) {

        try {
            dataManager.updateConsumatore(smartMeter, consumo, 0.5);
        } catch (NotFoundElementException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().build();

    }


    @Override
    public Response registerProduzione(ProduttoreConsumatoreMTU produttoreConsumatoreMTU) {

        dataManager.addProduttore(produttoreConsumatoreMTU);

        return Response.ok().build();
    }
}

