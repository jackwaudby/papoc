package utils;

public class SystemMetrics {

    private static final SystemMetrics instance = new SystemMetrics();

    private int arrivals;       // number of arrivals
    private int aborts;     // aborts by delta mechanism
    private int commits;
    private long simulationDuration;

    private SystemMetrics() {
        arrivals = 0;
        aborts = 0;
        commits = 0;
        simulationDuration = 0;
    }

    public void reset() {
        arrivals = 0;
        aborts = 0;
        commits = 0;
    }

    public static SystemMetrics getInstance() {return instance; }

    public void incrementArrivals() {
        arrivals = arrivals + 1;
    }

    public void incrementCommitted() {
        commits = commits + 1;
    }

    public void incrementAborts() {
        aborts = aborts + 1;
    }

    public int getArrivals() {
        return arrivals;
    }

    public int getAborts() {
        return aborts;
    }

    public int getCommits() {
        return commits;
    }

    public int getCompleted() {
        return aborts + commits;
    }

    public long getSimulationDuration() {
        return simulationDuration;
    }

    public void setSimulationDuration(long simulationDuration) {
        this.simulationDuration = simulationDuration;
    }

    @Override
    public String toString() {
        return arrivals +
                "," + aborts +
                "," + commits +
                "," + simulationDuration;
    }
}
