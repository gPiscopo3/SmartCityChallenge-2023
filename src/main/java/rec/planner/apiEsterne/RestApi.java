package rec.planner.apiEsterne;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Produces("application/json")
@Path("/rec")
public interface RestApi {


    /**
     * Restituisce lo scheduling
     * @param data queryParam (opzionale)
     * @param smartMeter queryParam (opzionale)
     */
    @GET
    @Path("/scheduling")
    Response getSchedulingByDate(@QueryParam("data") String data, @QueryParam("smartMeter") String smartMeter);


    /**
     * Restituisce forecasting della data specificata
     * @param data pathParam
     */
    @GET
    @Path("/forecasting/")
    Response getForecastingByDate(@QueryParam("data") String data);


}
