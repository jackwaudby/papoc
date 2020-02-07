package action;

import event.AbortCleanUpEvent;
import org.apache.log4j.Logger;
import state.GlobalActiveTransactionListSingleton;
import state.GlobalEdgeListSingleton;
import state.Transaction;

/**
 * This action is invoked by a clear event.
 */
public class AbortCleanUpAction {

    private final static Logger LOGGER = Logger.getLogger(AbortCleanUpAction.class.getName());

    public void action(AbortCleanUpEvent abortCleanUpEvent) {

        Transaction transaction = abortCleanUpEvent.getTransaction();

        // clear provisional write
        GlobalEdgeListSingleton.getInstance().clearProvisionalWrites(transaction.getId());

        // remove from active list + everyone pred lists
        GlobalActiveTransactionListSingleton.getInstance().removeTransaction(transaction.getId());


    }



}
