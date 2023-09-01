package rec.planner.application;

import rec.planner.scheduling.SchedulerDemon;

public class SchedulingApplication {

    public static void main(String... args){

        SchedulerDemon demon = new SchedulerDemon();
        demon.start();
    }
}
