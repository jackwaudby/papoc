package action;

import event.AbortCleanUpEvent;
import event.EventType;
import event.Update1Event;
import event.Update2Event;
import org.apache.log4j.Logger;
import state.*;
import utils.*;

public class Update2Action  {

    private final static Logger LOGGER = Logger.getLogger(Update2Action.class.getName());

    public void action(Update2Event update2Event) {

        Transaction transaction = update2Event.getTransaction(); // get transaction
        LOGGER.debug("Transaction: "  + transaction.getId());
        LOGGER.debug("Collision Detection: "  + transaction.getCollisionDetection());

        DistributedEdge distributedEdge = // get edge to be updated
                GlobalEdgeListSingleton.getInstance().getEdge(update2Event.getEdgeId());
        LOGGER.debug("Distributed Edge: "  + distributedEdge);

        int side = update2Event.getSide(); // get side to be updated
        LOGGER.debug("Side: "  + side);


        boolean outcome =  // perform collision detection test
                CollisionDetection.collisionDetection(distributedEdge,transaction,side);

        if (outcome) { // passes test
            LOGGER.debug("Collision Detection: PASS" );

            transaction.removeCollisionDetectionInformation(); // clear collision meta data
            LOGGER.debug("Clear Collection Detection: "  + transaction.getCollisionDetection());

            transaction.addPredecessors(GlobalEdgeListSingleton.getInstance().collectPredecessors(distributedEdge.getEdgeId(),
                    side)); // collect predecessor
            LOGGER.debug("Collect Predecessors: "  + transaction.getPredecessors());

            transaction.incrementUpdateCompleted(); // increment number complete
            LOGGER.debug("Updates Completed: " + transaction.getUpdatesCompleted());

            // put tentative write in the other side
            ProvisionalWrite provisionalWrite = new ProvisionalWrite(update2Event.getTransaction().getId(),2);
            if (side == 0) {
                GlobalEdgeListSingleton.getInstance().getEdge(update2Event.getEdgeId()).
                        addProvisionalWrite(true,provisionalWrite);
            } else {
                GlobalEdgeListSingleton.getInstance().getEdge(update2Event.getEdgeId()).
                        addProvisionalWrite(false,provisionalWrite);
            }

            if (transaction.getUpdatesCompleted() == transaction.getUpdates()) { // finished updating

                LOGGER.debug("Transaction Successful");
                transaction.setEndTime(update2Event.getEventTime()); // set end time
                LOGGER.debug("All Provisional Writes Removed");
                GlobalEdgeListSingleton.getInstance().
                        clearProvisionalWrites(transaction.getId()); // clear provisional writes
                SystemMetrics.getInstance().incrementCommitted();
                SystemMetrics.getInstance().addLifetime(transaction.getLifetime());

                // remove from active list + everyone pred lists
                GlobalActiveTransactionListSingleton.getInstance().removeTransaction(transaction.getId());

            } else {
                // create another update 1
                // TODO: Code below simulates delays between updates
//              Update1Event nextUpdateEvent = new Update1Event(SimulationRandom.getInstance().generateNetworkDelay(), EventType.UPDATE_1,transaction);
                Update1Event nextUpdateEvent =
                        new Update1Event(
                                GlobalClockSingleton.getInstance().getGlobalClock(),
                                EventType.UPDATE_1,
                                transaction
                        );
                GlobalEventListSingleton.getInstance().addEvent(nextUpdateEvent);
            }
        } else { // fails collision detection test

            LOGGER.debug("Collision Detection: FAIL" );
            LOGGER.debug("Transaction Aborted");
            LOGGER.debug("All Provisional Writes Removed");

            transaction.setEndTime(GlobalClockSingleton.getInstance().getGlobalClock());
            SystemMetrics.getInstance().incrementCollisions();
            SystemMetrics.getInstance().addLifetime(transaction.getLifetime());

            // schedule event at other side for removal
            AbortCleanUpEvent abortCleanUpEvent = new AbortCleanUpEvent(SimulationRandom.getInstance().generateNetworkDelay(),EventType.ABORT_CLEAN_UP,transaction);
            GlobalEventListSingleton.getInstance().addEvent(abortCleanUpEvent);

        }
    }
}
