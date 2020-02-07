package action;
import event.EventType;
import event.Update1Event;
import event.Update2Event;
import org.apache.log4j.Logger;
import state.ProvisionalWrite;
import utils.SimulationRandom;
import state.GlobalEventListSingleton;
import state.GlobalEdgeListSingleton;

import java.util.ArrayList;

/**
 * The first update of a distributed edge.
 * Choose an edge and side to update.
 * Collect collision meta data.
 * Add provisional write.
 * Create update 2 event.
 */
public class Update1Action {

    private final static Logger LOGGER = Logger.getLogger(Update1Action.class.getName());

    public void action(Update1Event update1Event) {

        LOGGER.debug("Transaction ID: " + update1Event.getTransaction().getId());

        int edgeId = SimulationRandom.getInstance().getEdgeId(); // select a distributed edge at random
        LOGGER.debug("Edge ID: " + edgeId);

        int side = SimulationRandom.getInstance().getSide(); // choose side of distributed edge to update
        LOGGER.debug("Edge Side: " + side); // sides indicated by 0 (left) or 1 (right)

        // collect provisional write info from edge for collision detection at second update
        ArrayList<ProvisionalWrite> pws;
        if (side == 0) { // get information from left side
            pws = GlobalEdgeListSingleton.getInstance().getEdge(edgeId).getLeftProvisionalWrites();
        } else { // get information from right  side
            pws = GlobalEdgeListSingleton.getInstance().getEdge(edgeId).getRightProvisionalWrites();
        }
        if (!pws.isEmpty()) { // if there is some information
            for (ProvisionalWrite pw : pws // add to transaction's collision information
            ) {
                update1Event.getTransaction().addCollisionDetectionInformation(pw);
            }
        }
        LOGGER.debug("Collision Detection: " + update1Event.getTransaction().getCollisionDetection());


        LOGGER.debug("Add provisional write to edge " + edgeId);
        ProvisionalWrite provisionalWrite =
                new ProvisionalWrite(update1Event.getTransaction().getId(),1); // provisional write with label 1

        if (side == 0) { // add provisional to the side transaction is updating
            GlobalEdgeListSingleton.getInstance().getEdge(edgeId).addProvisionalWrite(true,provisionalWrite);
        } else {
            GlobalEdgeListSingleton.getInstance().getEdge(edgeId).addProvisionalWrite(false,provisionalWrite);
        }

        LOGGER.debug("Add update 2 event");
        Update2Event update2Event;
                if(side == 0){
                     update2Event = new Update2Event(SimulationRandom.getInstance().generateNetworkDelay(), EventType.UPDATE_2, update1Event.getTransaction(),edgeId,1);

                } else
                {
                    update2Event = new Update2Event(SimulationRandom.getInstance().generateNetworkDelay(), EventType.UPDATE_2, update1Event.getTransaction(),edgeId,0);

                }
        GlobalEventListSingleton.getInstance().addEvent(update2Event);

    }
}
