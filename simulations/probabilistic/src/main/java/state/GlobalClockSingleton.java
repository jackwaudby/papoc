package state;

public class GlobalClockSingleton {

    private static final GlobalClockSingleton instance = new GlobalClockSingleton();
    private double globalClock;

    private GlobalClockSingleton() {
        globalClock = 0;
    }

    public static GlobalClockSingleton getInstance() {
        return instance;
    }

    public void setGlobalClock(double eventTime) {
        globalClock = eventTime;
    }

    public double getGlobalClock() {
        return globalClock;
    }
}
