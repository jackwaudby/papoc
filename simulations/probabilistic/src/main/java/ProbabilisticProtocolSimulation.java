import action.*;
import event.*;

import org.apache.log4j.Logger;
import state.*;
import utils.*;

// TODO: strip out arbiter
// TODO: strip out unused config
// TODO: add mechanism

// txn arrives, updates 4 edges
// for each edge
// get source or destination
// if d < write time then do write and set timer
// else d aborts and returns

// do not unset timer when aborted


public class ProbabilisticProtocolSimulation {

    private final static Logger LOGGER = Logger.getLogger(ProbabilisticProtocolSimulation.class.getName());

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();


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
        LOGGER.info("Collisions: " + SystemMetrics.getInstance().getCollisions());
        LOGGER.info("Total Commits: " + SystemMetrics.getInstance().getCommits());
        LOGGER.info("Av Resp. Time: " +  SystemMetrics.getInstance().getAverageResponseTime());

        long endTime = System.currentTimeMillis();

        long duration = (endTime - startTime)/1000/60;

        SystemMetrics.getInstance().setDuration(duration);

        if (SimulationConfiguration.getInstance().saveResults()) {
            WriteOutResults.writeOutResults(); // write out results
        }


    }

    private static void runSimulation() {

        int txnLimit = SimulationConfiguration.getInstance().getTxnLimit();


        while (SystemMetrics.getInstance().getCompleted() < txnLimit) {

            if (SystemMetrics.getInstance().getArrivals() % 10 == 0){
                System.out.print("Completed: " + SystemMetrics.getInstance().getCompleted() +
                        " Aborts: " + SystemMetrics.getInstance().getCollisions() + "\r");
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
