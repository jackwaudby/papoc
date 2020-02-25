import action.*;
import event.*;

import org.apache.log4j.Logger;
import state.*;
import utils.*;


public class ProbabilisticProtocolSimulation {

    private final static Logger LOGGER = Logger.getLogger(ProbabilisticProtocolSimulation.class.getName());

    public static void main(String[] args) {

        int delta = Integer.parseInt(args[0]);
        SimulationConfiguration.getInstance().setDelta(delta);

        int tps = Integer.parseInt(args[1]);
        SimulationConfiguration.getInstance().setTPS(tps);

        String[] headers = {"tps", "arrivals", "collisions", "commits", "duration"};

        WriteOutResults.writeOut(headers);

        long startTime = System.currentTimeMillis();
        int databaseSize = SimulationConfiguration.getInstance().getDatabaseSize();

        // initialise database
        for (int i = 0; i < databaseSize; i++) {
            DistributedEdge e = new DistributedEdge(i);
            GlobalEdgeListSingleton.getInstance().addEdge(e);
        }

        // init. first arrival event
        double initEventTime = SimulationRandom.getInstance().generateNextArrival();
        GlobalEventListSingleton.getInstance().addEvent(new ArrivalEvent(initEventTime, EventType.ARRIVAL));

        LOGGER.debug("**********************************************************************");
        LOGGER.debug("**********************************************************************");
        LOGGER.debug("Initial State");
        LOGGER.debug("**********************************************************************");
        LOGGER.debug("Event List: " + GlobalEventListSingleton.getInstance().getEventList());
        LOGGER.debug("**********************************************************************");
        LOGGER.debug("**********************************************************************");
        LOGGER.debug(" ");

        runSimulation();

        System.out.println(" ");
        LOGGER.info("TPS: " + SimulationConfiguration.getInstance().getTPS());
        LOGGER.info("Arrivals: " + SystemMetrics.getInstance().getArrivals());
        LOGGER.info("Completed: " + SystemMetrics.getInstance().getCompleted());
        LOGGER.info("Collisions: " + SystemMetrics.getInstance().getAborts());
        LOGGER.info("Total Commits: " + SystemMetrics.getInstance().getCommits());
        System.out.println(" ");

        long endTime = System.currentTimeMillis();

        long duration = (endTime - startTime) / 1000 / 60;

        SystemMetrics.getInstance().setSimulationDuration(duration);

        String[] result = {
                String.valueOf(SimulationConfiguration.getInstance().getTPS()),
                String.valueOf(SystemMetrics.getInstance().getArrivals()),
                String.valueOf(SystemMetrics.getInstance().getAborts()),
                String.valueOf(SystemMetrics.getInstance().getCommits()),
                String.valueOf(SystemMetrics.getInstance().getSimulationDuration())};


        if (SimulationConfiguration.getInstance().saveResults()) {
            WriteOutResults.writeOut(result); // write out results
        }

        // clear simulation
//        GlobalEdgeListSingleton.getInstance().clearDatabase();
//        GlobalClockSingleton.getInstance().resetClock();
//        GlobalEventListSingleton.getInstance().clearEvents();
//        SystemMetrics.getInstance().reset();

    }

    private static void runSimulation() {

        while (SystemMetrics.getInstance().getCompleted() < SimulationConfiguration.getInstance().getTPS() * 10) {
//        while (SystemMetrics.getInstance().getCompleted() < 10) {

            if (SystemMetrics.getInstance().getArrivals() % 10 == 0){
                System.out.print("Completed: " + SystemMetrics.getInstance().getCompleted() +
                        " Aborts: " + SystemMetrics.getInstance().getAborts() + "\r");
            }

            AbstractEvent nextEvent = GlobalEventListSingleton.getInstance().getNextEvent();   // get next event

            LOGGER.debug("**********************************************************************");
            LOGGER.debug("**********************************************************************");
            LOGGER.debug("Event: " + nextEvent.getEventType() + " at " + String.format("%.5f",nextEvent.getEventTime()));
            GlobalClockSingleton.getInstance().setGlobalClock(nextEvent.getEventTime()); // set global clock to event time
            LOGGER.debug("**********************************************************************");
            LOGGER.debug("Event List: " + GlobalEventListSingleton.getInstance().getEventList());
            LOGGER.debug("Edge List: " + GlobalEdgeListSingleton.getInstance().getEdgeList());

            switch (nextEvent.getEventType()) {
                case ARRIVAL:
                    ArrivalEvent arrivalEvent = (ArrivalEvent) nextEvent;
                    new ArrivalAction().action(arrivalEvent);
                    SystemMetrics.getInstance().incrementArrivals();
                    break;
                case UPDATE_1:
                    Update1Event update1Event = (Update1Event) nextEvent;
                    new Update1Action().action(update1Event);
                    break;
                case UPDATE_2:
                    Update2Event update2Event = (Update2Event) nextEvent;
                    new Update2Action().action(update2Event);
                    break;
            }

            LOGGER.debug("Event List: " + GlobalEventListSingleton.getInstance().getEventList());
            LOGGER.debug("Edge List: " + GlobalEdgeListSingleton.getInstance().getEdgeList());
            LOGGER.debug("**********************************************************************");
            LOGGER.debug(" ");

        }
    }
}
