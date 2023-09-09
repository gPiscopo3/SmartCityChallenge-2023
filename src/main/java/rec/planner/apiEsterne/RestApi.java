package rec.planner.apiEsterne;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import rec.planner.model.giornalieri.Preferenza;
import rec.planner.model.instantanee.Consumatore;
import rec.planner.model.instantanee.ProduttoreConsumatoreMTU;


@Produces("application/json")
@Path("/rec")
public interface RestApi {


    /**
     * Restituisce lo scheduling
     * @param data queryParam (opzionale)
     * @param smartMeter queryParam (opzionale)
     * @return oggeto scheduling
     */
    @GET
    @Path("/scheduling")
    Response getSchedulingByDate(@QueryParam("date") String data, @QueryParam("smartMeter") String smartMeter);


    /**
     * Restituisce forecasting della data specificata
     * @param data pathParam
     * @return oggetto forecasting
     */
    @GET
    @Path("/forecasting")
    Response getForecastingByDate(@QueryParam("date") String data);


    /**
     * Restituisci il forecasting del bilancio energetico
     * @param data (opzionale)
     * @return array di double per ogni unità di tempo
     */

    @GET
    @Path("/balance")
    Response getBalanceForecastingByDate(@QueryParam("date") String data);


    /**
     * restituisce le preferenze della data specificato
     * @param smartMeter queryParam (opzionale)
     * @return preferenze relative allo smart meter oppure tutte le preferenze di una giornata
     */

    @GET
    @Path("/preferences")
    Response getPreferenze(@QueryParam("smartMeter") String smartMeter);


    /**
     * Inserisce le preferenze
     * @param preferenze
     * @return URI
     */
    @POST
    @Path("/preferences")
    Response setPreferenze(Preferenza preferenze);


    /**
     * Registra un consumatore
     * @param consumatore
     * @return URI
     */
    @POST
    @Path("/consumers")
    Response registerConsumatore(Consumatore consumatore);


    /**
     * Restituisci i consumatori relativi a un home controller
     * @param homeController
     * @return lista dei consumatori
     */
    @GET
    @Path("/consumers")
    Response getConsumatoriByHomeController(@QueryParam("homeController") String homeController);
    /**
     * Aggiorna il consumo medio di un consumatore dato lo smart meter e il consumo instanteneo
     * @param smartMeter
     * @param consumo
     */
    @PUT
    @Path("/consumers/{smartMeter}")
    Response updateConsumoInstantaneo(@PathParam("smartMeter") String smartMeter, double consumo);


    /**
     * Permette di registrare la produzione instantea in un momento preciso associato a uno smart meter.
     * @param produttoreConsumatoreMTU
     */
    @Path("/producers")
    @POST
    Response registerProduzione(ProduttoreConsumatoreMTU produttoreConsumatoreMTU);


}
