package event;

public class ArrivalEvent extends AbstractEvent {

    public ArrivalEvent(double eventTime, EventType eventTypeEnum) {
        super(eventTime, eventTypeEnum);
    }

    @Override
    public String toString() {
        return "{" +
                "time=" + String.format("%.5f",getEventTime()) +
                ", " + "type=" + getEventType() +
                "}";
    }

}
