package rec.planner.apiEsterne;


import jakarta.ws.rs.core.Response;
import rec.planner.data.restApi.RestApiDataManager;
import rec.planner.data.restApi.RestApiMongoDataManager;
import rec.planner.exception.NotFoundElementException;
import rec.planner.model.Day;
import rec.planner.model.giornalieri.AllocazioneConsumatore;
import rec.planner.model.giornalieri.SchedulingGiornaliero;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class RestApiImpl implements RestApi{


    private final RestApiDataManager dataManager = RestApiMongoDataManager.getInstance();


    public RestApiImpl() {

    }

    private LocalDate getLocalDate(String data) throws DateTimeParseException{
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(data, DateTimeFormatter.ISO_DATE);
        }catch (DateTimeParseException e) {
            try {
                localDate = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e1) {
                localDate = LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            }
        }
        return localDate;
    }


    @Override
    public Response getScheduling(String smartMeter) {

        SchedulingGiornaliero schedulingGiornaliero;

        try {

            schedulingGiornaliero = dataManager.getScheduling(Day.getNextDay());
        } catch (NotFoundElementException e) {
            try {
                schedulingGiornaliero = dataManager.getScheduling(Day.getDay());
            } catch (NotFoundElementException ex) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        return getResponse(schedulingGiornaliero, smartMeter);

    }

    @Override
    public Response getSchedulingByDate(String data, String smartMeter) {
        SchedulingGiornaliero schedulingGiornaliero;
        try{
            schedulingGiornaliero = dataManager.getScheduling(getLocalDate(data));
        } catch (NotFoundElementException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DateTimeParseException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return getResponse(schedulingGiornaliero, smartMeter);
    }


    private Response getResponse(SchedulingGiornaliero schedulingGiornaliero, String smartMeter){
        if(smartMeter!=null && !smartMeter.equals("")){
            for(AllocazioneConsumatore consumatore: schedulingGiornaliero.getConsumatori())
                if(consumatore.getSmartMeter().equalsIgnoreCase(smartMeter))
                    return Response.ok(consumatore).build();
        }
        else
            return Response.ok(schedulingGiornaliero).build();

        return Response.status(Response.Status.NOT_FOUND).build();

    }


    @Override
    public Response getForecasting() {
        try{
            return Response.ok(dataManager.getForecasting(Day.getNextDay())).build();
        } catch (NotFoundElementException e) {
            try {
                return Response.ok(dataManager.getForecasting(Day.getDay())).build();
            } catch (NotFoundElementException ex) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
    }

    @Override
    public Response getForecastingByDate(String data) {
        try{
            return Response.ok(dataManager.getForecasting(getLocalDate(data))).build();
        } catch (NotFoundElementException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DateTimeParseException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
