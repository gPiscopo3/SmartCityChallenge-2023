package rec.planner.apiEsterne;


import jakarta.ws.rs.core.Response;
import rec.planner.data.restApi.RestApiDataManager;
import rec.planner.data.restApi.RestApiMongoDataManager;
import rec.planner.exception.NotFoundElementException;
import rec.planner.model.Day;
import rec.planner.model.giornalieri.AllocazioneConsumatore;
import rec.planner.model.giornalieri.ForecastingGiornaliero;
import rec.planner.model.giornalieri.SchedulingGiornaliero;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class RestApiImpl implements RestApi {


    private final RestApiDataManager dataManager = RestApiMongoDataManager.getInstance();


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

}

