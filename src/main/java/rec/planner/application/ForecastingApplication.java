package rec.planner.application;

import rec.planner.forecaster.ForecasterDemon;

public class ForecastingApplication {

    public static void main(String... args){

        ForecasterDemon demon = new ForecasterDemon();
        demon.start();

    }
}
