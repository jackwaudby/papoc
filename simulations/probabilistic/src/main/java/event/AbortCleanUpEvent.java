package event;

import state.Transaction;

public class AbortCleanUpEvent extends AbstractEvent {

    private Transaction transaction;

    public AbortCleanUpEvent(double eventTime, EventType eventTypeEnum, Transaction transaction) {
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
