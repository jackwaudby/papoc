package event;

import state.Transaction;


public class Update2Event extends AbstractEvent {

    private Transaction transaction;
    private int edgeId;
    private int side;

    public Update2Event(double eventTime, EventType eventTypeEnum, Transaction transaction, int edgeId, int side) {
        super(eventTime, eventTypeEnum);
        this.transaction = transaction;
        this.edgeId = edgeId;
        this.side = side;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public int getEdgeId() {
        return edgeId;
    }

    public int getSide() {
        return side;
    }

    @Override
    public String toString() {
        return "{" +
                "transaction=" + transaction.getId() +
                ", edgeId=" + edgeId +
                ", " + "time=" + String.format("%.5f",getEventTime()) +
                ", " + "type=" + getEventType() +
                "}";
    }
}
