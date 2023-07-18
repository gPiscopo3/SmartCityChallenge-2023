package rec.planner.apiEsterne;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Produces("application/json")
@Path("/rec")
public interface RestApi {

    /**
     * Restituisce ultimo scheduling disponibile
     * @param smartMeter queryParam (opzionale)
     */


    @GET
    @Path("/scheduling")
    Response getScheduling(@QueryParam("smartMeter") String smartMeter);

    /**
     * Restituisce lo scheduling della data specificata
     * @param data pathParam
     * @param smartMeter queryParam (opzionale)
     */
    @GET
    @Path("/scheduling/{date}")
    Response getSchedulingByDate(@PathParam("date") String data, @QueryParam("smartMeter") String smartMeter);



    /**
     * Restituisce ultimo forecasting disponibile
     */
    @GET
    @Path("/forecasting")
    Response getForecasting();

    /**
     * Restituisce forecasting della data specificata
     * @param data pathParam
     */
    @GET
    @Path("/forecasting/{date}")
    Response getForecastingByDate(@PathParam("date") String data);


}
