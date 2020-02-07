package utils;

import org.apache.log4j.Logger;
import state.DistributedEdge;
import state.ProvisionalWrite;
import state.Transaction;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Collision detection mechanism performed during update 2.
 * Note, side refers to the side the edge is updating, e.g. 1 implies the edge is updating the right side second.
 */
public class CollisionDetection {

    private final static Logger LOGGER = Logger.getLogger(CollisionDetection.class.getName());


    public static boolean collisionDetection(DistributedEdge distributedEdge, Transaction transaction, int side){

        // split edge information into 1 and 2
        ArrayList<ProvisionalWrite> edgeCheck1;
        ArrayList<ProvisionalWrite> edgeCheck2;

        // if left side, need 1 and 2s from the left to check against
        if (side == 0) {
            edgeCheck1 = (ArrayList<ProvisionalWrite>) distributedEdge.getLeftProvisionalWrites().
                    stream().filter(pw -> pw.getWriteLabel() == 1).collect(Collectors.toList());
            edgeCheck2 = (ArrayList<ProvisionalWrite>) distributedEdge.getLeftProvisionalWrites().
                    stream().filter(pw -> pw.getWriteLabel() == 2).collect(Collectors.toList());
        } else {        // if right side, need 1 and 2s from the right to check against

            edgeCheck1 = (ArrayList<ProvisionalWrite>) distributedEdge.getRightProvisionalWrites().
                    stream().filter(pw -> pw.getWriteLabel() == 1).collect(Collectors.toList());
            edgeCheck2 = (ArrayList<ProvisionalWrite>) distributedEdge.getRightProvisionalWrites().
                    stream().filter(pw -> pw.getWriteLabel() == 2).collect(Collectors.toList());
        }

        // split collision detection information into 1s and 2s
        ArrayList<ProvisionalWrite> transactionCheck1 = (ArrayList<ProvisionalWrite>) transaction.getCollisionDetection().
                stream().filter(pw -> pw.getWriteLabel() == 1).collect(Collectors.toList());
        ArrayList<ProvisionalWrite> transactionCheck2 = (ArrayList<ProvisionalWrite>) transaction.getCollisionDetection().
                stream().filter(pw -> pw.getWriteLabel() == 2).collect(Collectors.toList());


        // Rule 1: For each write 1 that a transaction has, there is a 2 in edge check
        int expectedMatchesRule1 = transactionCheck1.size(); // get the transaction's 1s
        LOGGER.debug("Rule 1 Expected Matches: " + expectedMatchesRule1);

        int actualMatchesRule1 = 0;
        for (ProvisionalWrite element : transactionCheck1
        ) {
            ArrayList<ProvisionalWrite> match = (ArrayList<ProvisionalWrite>) edgeCheck2.
                    stream().filter(e -> e.getTransactionId() == element.getTransactionId()).collect(Collectors.toList());
            if (!match.isEmpty()) {
                actualMatchesRule1 = actualMatchesRule1 + 1;
            }
        }
        LOGGER.debug("Rule 1 Actual Matches: " + actualMatchesRule1);

        boolean outcome1;

        if (actualMatchesRule1 == expectedMatchesRule1) {
            outcome1 = true;
        } else {
            outcome1 = false;
        }

        // Rule 2: For each write with label 1 at edge, the transaction has a write with label 2
        int expectedMatchesRule2 = edgeCheck1.size(); // number of write at edge with label 1
        LOGGER.debug("Rule 2 Expected Matches: " + expectedMatchesRule2);
        int actualMatchesRule2 = 0;
        for (ProvisionalWrite element : edgeCheck1
        ) {
            ArrayList<ProvisionalWrite> match = (ArrayList<ProvisionalWrite>) transactionCheck2.
                    stream().filter(e -> e.getTransactionId() == element.getTransactionId()).collect(Collectors.toList());
            if (!match.isEmpty()) {
                actualMatchesRule2 = actualMatchesRule2 + 1;
            }
        }
        LOGGER.debug("Rule 2 Actual Matches: " + actualMatchesRule2);
        boolean outcome2;

        if (actualMatchesRule2 == expectedMatchesRule2) {
            outcome2 = true;
        } else {
            outcome2 = false;
        }

        boolean outcome = false;
        if(outcome1 && outcome2) {
            outcome = true;
        }

        return outcome;
    }


}
