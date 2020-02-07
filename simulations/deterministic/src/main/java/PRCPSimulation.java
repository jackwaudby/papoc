import action.*;
import event.*;

import org.apache.log4j.Logger;
import state.*;
import utils.*;

// TODO: unit tests
// TODO: add functionality to switch between simulating time or completed txns
// TODO: add arbiter mechanism toggle

public class PRCPSimulation {

    private final static Logger LOGGER = Logger.getLogger(PRCPSimulation.class.getName());

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

        // init. first arbiter event
        if (SimulationConfiguration.getInstance().getUseArbiter()) {
            double initArbiter = SimulationRandom.getInstance().generateServiceDelay();
            GlobalEventListSingleton.getInstance().addEvent(new ArbiterEvent(initArbiter, EventType.ARBITER));
        }
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
        LOGGER.info("Total Aborts: " + SystemMetrics.getInstance().getAborts());
        LOGGER.info("Collisions: " + SystemMetrics.getInstance().getCollisions());
        LOGGER.info("Arbitration: " + SystemMetrics.getInstance().getArbitration());
        LOGGER.info("Total Commits: " + SystemMetrics.getInstance().getCommits());
        LOGGER.info("Av Resp. Time: " +  SystemMetrics.getInstance().getAverageResponseTime());
        LOGGER.info("Sent to Arbiter: " + SystemMetrics.getInstance().getSentToArbiter());
        LOGGER.info("Queue Length: " +  ArbiterSingleton.getInstance().getQueue().size());

        WriteOutResults.writeOutResults(); // write out results

    }

    static void runSimulation() {
        // CHECK: manual transaction limit or scale factor
        int txnLimit = SimulationConfiguration.getInstance().getTxnLimit();

        if (txnLimit == 0) {
            txnLimit = SystemMetrics.getInstance().getTps() * SimulationConfiguration.getInstance().getScaleFactor();
        }

        while (SystemMetrics.getInstance().getCompleted() < txnLimit) {

            if (SystemMetrics.getInstance().getArrivals() % 10 == 0){
                System.out.print("Completed: " + SystemMetrics.getInstance().getCompleted() +
                        " Queue: " + ArbiterSingleton.getInstance().getQueue().size() +
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
                case ABORT_CLEAN_UP:
                    AbortCleanUpEvent abortCleanUpEvent = (AbortCleanUpEvent) nextEvent;
                    new AbortCleanUpAction().action(abortCleanUpEvent);
                    break;
            }

            LOGGER.debug("Event List: " + GlobalEventListSingleton.getInstance().getEventList());
            LOGGER.debug("Edge List: " + GlobalEdgeListSingleton.getInstance().getEdgeList());
            LOGGER.debug("Arbiter Queue: " + ArbiterSingleton.getInstance().getQueue());
            LOGGER.debug("Hit List: " +  ArbiterSingleton.getInstance().getHitList());
            LOGGER.debug("**********************************************************************");
            LOGGER.debug(" ");

        }
    }
}
