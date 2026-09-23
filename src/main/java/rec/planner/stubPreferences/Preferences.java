package rec.planner.stubPreferences;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Consumes("application/json")
@Produces("application/json")
@Path("/preferences")
public interface Preferences {

    @GET
    @Path("{id}")
    Response getPreference(@PathParam("id") String id);

}
