package state;

import utils.SimulationConfiguration;
import utils.SimulationRandom;

import java.util.ArrayList;

/**
 * Represents a transaction in the database
 */
public class Transaction {

    private int id;
    private int updates;
    private int updatesCompleted;
    private double startTime;
    private double endTime;

    public Transaction(int id) {
        this.id = id;
        if (SimulationConfiguration.getInstance().getUseArbiter()) {
            this.updates = SimulationRandom.getInstance().getTransactionUpdates();
        } else {
            this.updates = 1;
        }
        this.updatesCompleted = 0;
    }


    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public double getLifetime() {
        return endTime - startTime;
    }

    public int getId() {
        return id;
    }

    public int getUpdates() {
        return updates;
    }

    public int getUpdatesCompleted() {
        return updatesCompleted;
    }

    public void incrementUpdateCompleted() {
        updatesCompleted = updatesCompleted + 1;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", updates=" + updates +
                ", completed=" + updatesCompleted +
                '}';
    }
}
