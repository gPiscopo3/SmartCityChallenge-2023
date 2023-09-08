package rec.planner.apiEsterne;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import rec.planner.model.giornalieri.Preferenza;
import rec.planner.model.instantanee.Consumatore;

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
    @Path("/forecasting")
    Response getForecastingByDate(@QueryParam("data") String data);


    /**
     * Restituisci il forecasting del bilancio energetico
     * @param data (opzionale)
     */

    @GET
    @Path("/balance")
    Response getBalanceForecastingByDate(@QueryParam("data") String data);


    /**
     * restituisce le preferenze della data specificato
     * @param data queryParam (opzionale)
     * @param smartMeter queryParam (opzionale)
     * @return
     */

    @GET
    @Path("/preferenze")
    Response getPreferenzeByDate(@QueryParam("data") String data, @QueryParam("smartMeter") String smartMeter);


    /**
     * Inserisce le preferenze
     * @param preferenze
     * @return
     */
    @POST
    @Path("/preferenze")
    Response setPreferenze(Preferenza preferenze);


    /**
     * Registra un consumatore
     * @param consumatore
     * @return
     */
    @POST
    @Path("/consumatori")
    Response registerConsumatore(Consumatore consumatore);

    /**
     * Aggiorna il consumo medio di un consumatore dato lo smart meter e il consumo instanteneo
     * @param smartMeter
     * @param consumo
     */
    @Path("/consumatori/{smartMeter}")
    Response updateConsumoInstantaneo(@PathParam("smartMeter") String smartMeter, double consumo);


}
