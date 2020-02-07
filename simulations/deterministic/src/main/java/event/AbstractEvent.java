package event;

public abstract class AbstractEvent implements Event, Comparable<AbstractEvent> {

    private double eventTime;           // event time
    private EventType eventTypeEnum;        // event type

    AbstractEvent(double eventTime, EventType eventTypeEnum) {
        this.eventTime = eventTime;
        this.eventTypeEnum = eventTypeEnum;
    }

    @Override
    public double getEventTime() {
        return eventTime;
    }

    @Override
    public EventType getEventType() {
        return eventTypeEnum;
    }

    @Override
    public int compareTo(AbstractEvent event) {
        int result;
        if((this.eventTime - event.getEventTime()) < 0) {
            result = -1;
        } else if ((this.eventTime - event.getEventTime()) > 0) {
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }
}
