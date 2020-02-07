package utils;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import state.GlobalClockSingleton;

import java.util.Random;

public class SimulationRandom {

    private ExponentialDistribution networkDelayDistribution;
    private ExponentialDistribution serviceTimeDistribution;
    private ExponentialDistribution arrivalDistribution;
    private Random edgeId;
    private Random side;
    private Random txn;

    private int length; // geometric sequence length
    private double startValue; // geometric sequence start value
    private double commonRatio; // geometric sequence common ratio
    private double[] geometricSequence;
    private double[] cumulativeSum;

    private static final SimulationRandom instance = new SimulationRandom();

    public SimulationRandom() {

        double averageNetworkDelay = SimulationConfiguration.getInstance().getAverageNetworkDelay();
        double averageArbiterServiceTime = SimulationConfiguration.getInstance().getAverageArbiterServiceTime();

        // init random number generators
        networkDelayDistribution = new ExponentialDistribution(averageNetworkDelay);
        serviceTimeDistribution = new ExponentialDistribution(averageArbiterServiceTime);
        double arrivalRate = (double) 1/SystemMetrics.getInstance().getTps();
        arrivalDistribution = new ExponentialDistribution(arrivalRate);
        edgeId = new Random();
        side = new Random();
        txn = new Random();

        // set seeds
        if (SimulationConfiguration.getInstance().isSeedSet()) {
            long seedValue = SimulationConfiguration.getInstance().getSeedValue();
            networkDelayDistribution.reseedRandomGenerator(seedValue);
            serviceTimeDistribution.reseedRandomGenerator(seedValue);
            arrivalDistribution.reseedRandomGenerator(seedValue);
            edgeId.setSeed(seedValue);
            side.setSeed(seedValue);
            txn.setSeed(seedValue);
        }

        // init geometric sequence
        length = SimulationConfiguration.getInstance().getLength();
        startValue = SimulationConfiguration.getInstance().getStartValue();
        commonRatio = SimulationConfiguration.getInstance().getRatio();
        geometricSequence = new double[length];
        geometricSequence[0] = startValue;
        for (int i = 1; i<length; i++) {
            geometricSequence[i] = geometricSequence[i-1] * commonRatio;
        }
        cumulativeSum = new double[length];
        cumulativeSum[0] = geometricSequence[0];
        for (int i = 1; i < length;i++) {

            cumulativeSum[i] = cumulativeSum[i-1] + geometricSequence[i];
        }
    }

    public static SimulationRandom getInstance() {
        return instance;
    }

    public double generateNetworkDelay() {
        return GlobalClockSingleton.getInstance().getGlobalClock()
                + networkDelayDistribution.sample();
    }

    public double generateServiceDelay() {
        return GlobalClockSingleton.getInstance().getGlobalClock() +
                serviceTimeDistribution.sample();
    }

    public double generateNextArrival() {
        return GlobalClockSingleton.getInstance().getGlobalClock() +
                arrivalDistribution.sample();
    }

    public int getEdgeId() {
        return edgeId.nextInt(SimulationConfiguration.getInstance().getDatabaseSize());
    }

    public int getSide() {
        return side.nextInt(2);
    }

    public int getTransactionUpdates() {
        double x = txn.nextDouble();
        int updates = 1;

        int counter = 0;
        while (cumulativeSum[counter] < x) {
            updates = updates + 1;
            counter = counter + 1;
            if (updates == 50 ) {
                break;
            }
        }
        return updates;

    }



}
