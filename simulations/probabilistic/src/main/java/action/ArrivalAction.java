package action;

import event.ArrivalEvent;
import event.EventType;
import event.Update1Event;
import org.apache.log4j.Logger;
import state.GlobalActiveTransactionListSingleton;
import state.GlobalClockSingleton;
import state.Transaction;
import utils.SimulationRandom;
import state.GlobalEventListSingleton;


public class ArrivalAction {

    private final static Logger LOGGER = Logger.getLogger(ArrivalAction.class.getName());

    public void action(ArrivalEvent arrivalEvent) {

        // creating next event
        double nextArrivalEventTime = SimulationRandom.getInstance().generateNextArrival(); // next arrival time
        ArrivalEvent nextArrivalEvent =
                new ArrivalEvent(nextArrivalEventTime,EventType.ARRIVAL); // create event
        GlobalEventListSingleton.getInstance().addEvent(nextArrivalEvent); // add to event list

        // create transaction
        Transaction transaction = new Transaction(GlobalEventListSingleton.getInstance().getTransactionsArrived());
        LOGGER.debug("Arrival: " + transaction);
        GlobalEventListSingleton.getInstance().incrementTransactionsArrived();

        // add to list
        GlobalActiveTransactionListSingleton.getInstance().addTransaction(transaction);

//        Update1Event update1Event = new Update1Event(SimulationRandom.getInstance().generateNetworkDelay(), EventType.UPDATE_1, transaction);
        Update1Event update1Event =
                new Update1Event(
                        GlobalClockSingleton.getInstance().getGlobalClock(),
                        EventType.UPDATE_1,
                        transaction
                );
        GlobalEventListSingleton.getInstance().addEvent(update1Event); // add to event list

    }
}
