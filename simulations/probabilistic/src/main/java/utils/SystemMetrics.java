package utils;

public class SystemMetrics {

    private static final SystemMetrics instance = new SystemMetrics();

    private int tps;
    private int arrivals;       // number of arrivals
    private int collisions;     // aborts by delta mechanism
    private double cumulativeLifetimes;
    private int commits;
    private long duration;

    private SystemMetrics() {
        arrivals = 0;
        collisions = 0;
        cumulativeLifetimes = 0;
        commits = 0;
        tps = SimulationConfiguration.getInstance().getTPS();
    }

    public void reset() {
        arrivals = 0;
        collisions = 0;
        cumulativeLifetimes = 0;
        commits = 0;
    }

    public int getTps() {
        return tps;
    }

    public static SystemMetrics getInstance() {return instance; }

    public void incrementArrivals() {
        arrivals = arrivals + 1;
    }

    public void incrementCommitted() {
        commits = commits + 1;
    }

    public void incrementCollisions() {
        collisions = collisions + 1;
    }

    public void addLifetime(double txnLifetime) {
        cumulativeLifetimes = cumulativeLifetimes + txnLifetime;
    }

    public int getArrivals() {
        return arrivals;
    }

    public int getCollisions() {
        return collisions;
    }

    public int getCommits() {
        return commits;
    }

    public int getCompleted() {
        return collisions + commits;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public double getAverageResponseTime() {
        return cumulativeLifetimes / (collisions + commits);
    }

    @Override
    public String toString() {
        return tps +
                "," + arrivals +
                "," + collisions +
                "," + commits +
                "," + duration;
    }
}
