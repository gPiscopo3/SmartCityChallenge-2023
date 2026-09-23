package rec.planner.stubPreferences;

import jakarta.ws.rs.core.Response;
import rec.planner.model.Tipologia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PreferencesImpl implements Preferences{

    StubDataManager dataManager = StubDataManager.getInstance();

    public PreferencesImpl() {


        dataManager.addPrefence(new PreferenzaStub("s0", generateArray(), new Random().nextInt(4), Tipologia.NON_INTERROMPIBILE));
        dataManager.addPrefence(new PreferenzaStub("s1", generateArray(), new Random().nextInt(4), Tipologia.NON_INTERROMPIBILE));
        dataManager.addPrefence(new PreferenzaStub("s2", generateArray(), new Random().nextInt(4), Tipologia.INTERROMPIBILE));
        dataManager.addPrefence(new PreferenzaStub("s3", generateArray(), new Random().nextInt(4), Tipologia.NON_INTERROMPIBILE));
    }

    private List<Integer> generateArray(){

        List<Integer> array = new ArrayList<>();
        for(int i = 0 ; i <24; i++)
            array.add(new Random().nextInt(2));

        return  array;
    }

    @Override
    public Response getPreference(String id) {

        PreferenzaStub preferenzaStub = dataManager.getPreferences(id);
        if(preferenzaStub == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(preferenzaStub).build();
    }
}
