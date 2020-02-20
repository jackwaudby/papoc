package action;
import event.EventType;
import event.Update1Event;
import event.Update2Event;
import org.apache.log4j.Logger;
import state.*;
import utils.SimulationConfiguration;
import utils.SimulationRandom;
import utils.SystemMetrics;

/**
 * The first update of a distributed edge.
 * Choose source or destination

 * Create update 2 event.
 */
public class Update1Action {

    private final static Logger LOGGER = Logger.getLogger(Update1Action.class.getName());

    public void action(Update1Event update1Event) {

        LOGGER.debug("Transaction ID: " + update1Event.getTransaction().getId());

        int edgeId = SimulationRandom.getInstance().getEdgeId(); // select a distributed edge at random
        LOGGER.debug("Edge ID: " + edgeId);

        int side = SimulationRandom.getInstance().getSide(); // choose side of distributed edge to update
        LOGGER.debug("Edge Side: " + side); // sides indicated by 0 (source) or 1 (destination)

        double delta = SimulationConfiguration.getInstance().getDelta();



        if (side == 0) { // source first
            if (update1Event.getEventTime() > GlobalEdgeListSingleton.getInstance().getEdge(edgeId).getSourceLock()){
                // proceed
                GlobalEdgeListSingleton.getInstance().getEdge(edgeId).setSourceLock(update1Event.getEventTime() + delta);
                LOGGER.debug("Add update 2 event");
                Update2Event update2Event =
                        new Update2Event(
                                SimulationRandom.getInstance().generateNetworkDelay(),
                                EventType.UPDATE_2, update1Event.getTransaction(),
                                edgeId,
                                1);
                GlobalEventListSingleton.getInstance().addEvent(update2Event);
            } else {
                LOGGER.debug("Transaction Aborted");
                SystemMetrics.getInstance().incrementAborts();
                // remove from active list
                GlobalActiveTransactionListSingleton.getInstance().removeTransaction(update1Event.getTransaction().getId());
            }
        } else {
            if (update1Event.getEventTime() > GlobalEdgeListSingleton.getInstance().getEdge(edgeId).getDestinationLock()){
                // proceed
                GlobalEdgeListSingleton.getInstance().getEdge(edgeId).setDestinationLock(update1Event.getEventTime() + delta);
                LOGGER.debug("Add update 2 event");
                Update2Event update2Event =
                        new Update2Event(
                                SimulationRandom.getInstance().generateNetworkDelay(),
                                EventType.UPDATE_2, update1Event.getTransaction(),
                                edgeId,
                                0);
                GlobalEventListSingleton.getInstance().addEvent(update2Event);
            } else {
                LOGGER.debug("Transaction Aborted");
                SystemMetrics.getInstance().incrementAborts();
                // remove from active list
                GlobalActiveTransactionListSingleton.getInstance().removeTransaction(update1Event.getTransaction().getId());
            }

        }



    }
}
