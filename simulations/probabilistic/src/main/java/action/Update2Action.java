package action;

import event.AbortCleanUpEvent;
import event.EventType;
import event.Update1Event;
import event.Update2Event;
import org.apache.log4j.Logger;
import state.*;
import utils.*;

public class Update2Action {

    private final static Logger LOGGER = Logger.getLogger(Update2Action.class.getName());

    public void action(Update2Event update2Event) {

        Transaction transaction = update2Event.getTransaction(); // get transaction
        LOGGER.debug("Transaction: " + transaction.getId());

        DistributedEdge distributedEdge = // get edge to be updated
                GlobalEdgeListSingleton.getInstance().getEdge(update2Event.getEdgeId());
        LOGGER.debug("Distributed Edge: " + distributedEdge);

        int side = update2Event.getSide(); // get side to be updated
        LOGGER.debug("Side: " + side);


        double delta = SimulationConfiguration.getInstance().getDelta();
        if (side == 0) {
            LOGGER.debug("Check source timer");

            if (update2Event.getEventTime() > distributedEdge.getSourceLock()) {

                transaction.incrementUpdateCompleted(); // increment number complete
                LOGGER.debug("Updates Completed: " + transaction.getUpdatesCompleted());
                distributedEdge.setSourceLock(update2Event.getEventTime() + delta);
                if (transaction.getUpdatesCompleted() == transaction.getUpdates()) { // finished updating
                    LOGGER.debug("Transaction Successful");
                    transaction.setEndTime(update2Event.getEventTime()); // set end time
                    SystemMetrics.getInstance().incrementCommitted();
                    SystemMetrics.getInstance().addLifetime(transaction.getLifetime());
                    GlobalActiveTransactionListSingleton.getInstance().removeTransaction(transaction.getId());
                }
            } else {

                LOGGER.debug("Transaction Aborted");
                update2Event.getTransaction().setEndTime(GlobalClockSingleton.getInstance().getGlobalClock());
                SystemMetrics.getInstance().incrementCollisions();
                SystemMetrics.getInstance().addLifetime(update2Event.getTransaction().getLifetime());
                // remove from active list
                GlobalActiveTransactionListSingleton.getInstance().removeTransaction(update2Event.getTransaction().getId());

            }
        } else {
            LOGGER.debug("Check destination timer");
            if (update2Event.getEventTime() > distributedEdge.getDestinationLock()) {

                transaction.incrementUpdateCompleted(); // increment number complete
                LOGGER.debug("Updates Completed: " + transaction.getUpdatesCompleted());
                distributedEdge.setDestinationLock(update2Event.getEventTime() + delta);
                if (transaction.getUpdatesCompleted() == transaction.getUpdates()) { // finished updating
                    LOGGER.debug("Transaction Successful");
                    transaction.setEndTime(update2Event.getEventTime()); // set end time
                    SystemMetrics.getInstance().incrementCommitted();
                    SystemMetrics.getInstance().addLifetime(transaction.getLifetime());
                    GlobalActiveTransactionListSingleton.getInstance().removeTransaction(transaction.getId());

                }
            } else {

                LOGGER.debug("Transaction Aborted");
                update2Event.getTransaction().setEndTime(GlobalClockSingleton.getInstance().getGlobalClock());
                SystemMetrics.getInstance().incrementCollisions();
                SystemMetrics.getInstance().addLifetime(update2Event.getTransaction().getLifetime());
                // remove from active list
                GlobalActiveTransactionListSingleton.getInstance().removeTransaction(update2Event.getTransaction().getId());

            }
        }

    }
}
