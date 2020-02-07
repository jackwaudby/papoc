package utils;

//import static utils.SimulationConfiguration.getTPS;

public class SystemMetrics {

    private static final SystemMetrics instance = new SystemMetrics();

    private int tps;
    private int arrivals;       // number of arrivals
    private int aborts;         // number of total aborts
    private int collisions;     // aborts by collision detection mechanism
    private int arbitration;    // aborts by arbitration mechanism
    private double cumulativeLifetimes;
    private int commits;
    private int sentToArbiter;

    private SystemMetrics() {
        arrivals = 0;
        aborts = 0;
        collisions = 0;
        arbitration = 0;
        cumulativeLifetimes = 0;
        commits = 0;
        sentToArbiter = 0;
        tps = SimulationConfiguration.getInstance().getTPS();
    }

    public void reset() {
        arrivals = 0;
        aborts = 0;
        collisions = 0;
        arbitration = 0;
        cumulativeLifetimes = 0;
        commits = 0;
        sentToArbiter = 0;
    }

    public int getTps() {
        return tps;
    }

    public void setTps(int tps) {
        this.tps = tps;
    }

    public static SystemMetrics getInstance() {return instance; }

    public void incrementArrivals() {
        arrivals = arrivals + 1;
    }

    public void incrementAborts() {
        aborts = aborts + 1;
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

    public void incrementArbitration() {
        arbitration = arbitration + 1;
    }

    public int getArrivals() {
        return arrivals;
    }

    public int getCollisions() {
        return collisions;
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

    public int getSentToArbiter() {
        return sentToArbiter;
    }

    public void incrementSentToArbiter() {
        sentToArbiter = sentToArbiter + 1;
    }

    public int getArbitration() {
        return arbitration;
    }

    public double getCumulativeLifetimes() {
        return cumulativeLifetimes;
    }

    public double getAverageResponseTime() {
        return cumulativeLifetimes / (aborts + commits);
    }

    @Override
    public String toString() {
        return tps +
                "," + arrivals +
                "," + aborts +
                "," + collisions +
                "," + arbitration +
                "," + commits +
                "," + sentToArbiter;
    }
}
