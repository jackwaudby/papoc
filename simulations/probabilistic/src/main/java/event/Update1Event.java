package event;

import state.Transaction;

/**
 * An update 1 event contains the transaction performing the update
 */
public class Update1Event extends AbstractEvent {

    private Transaction transaction;

    public Update1Event(double eventTime, EventType eventTypeEnum, Transaction transaction) {
        super(eventTime, eventTypeEnum);
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    @Override
    public String toString() {
        return "{" +
                "transaction=" + transaction.getId() +
                ", " + "time=" + String.format("%.5f",getEventTime()) +
                ", " + "type=" + getEventType() +
                "}";
    }
}
