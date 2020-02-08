import action.*;
import event.*;

import org.apache.log4j.Logger;
import state.*;
import utils.*;

public class DeterministicProtocolSimulation {

    private final static Logger LOGGER = Logger.getLogger(DeterministicProtocolSimulation.class.getName());

    public static void main(String[] args) {

        int databaseSize = SimulationConfiguration.getInstance().getDatabaseSize();

        // init. database
        for (int i = 0; i < databaseSize; i++ ) {
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

        LOGGER.info("TPS: " + SystemMetrics.getInstance().getTps());
        LOGGER.info("Arrivals: " + SystemMetrics.getInstance().getArrivals());
        LOGGER.info("Completed: " + SystemMetrics.getInstance().getCompleted());
        LOGGER.info("Collisions/Aborts: " + SystemMetrics.getInstance().getCollisions());
        LOGGER.info("Commits: " + SystemMetrics.getInstance().getCommits());
        LOGGER.info("Av Resp. Time: " +  SystemMetrics.getInstance().getAverageResponseTime());

        if (SimulationConfiguration.getInstance().saveResults()) {
            WriteOutResults.writeOutResults(); // write out results
        }

    }

    private static void runSimulation() {

        int txnLimit = SimulationConfiguration.getInstance().getTxnLimit();

        while (SystemMetrics.getInstance().getCompleted() < txnLimit) {


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
                case ABORT_CLEAN_UP:
                    AbortCleanUpEvent abortCleanUpEvent = (AbortCleanUpEvent) nextEvent;
                    new AbortCleanUpAction().action(abortCleanUpEvent);
                    break;
            }

            LOGGER.debug("Event List: " + GlobalEventListSingleton.getInstance().getEventList());
            LOGGER.debug("Edge List: " + GlobalEdgeListSingleton.getInstance().getEdgeList());
            LOGGER.debug("**********************************************************************");
            LOGGER.debug(" ");

        }
    }
}