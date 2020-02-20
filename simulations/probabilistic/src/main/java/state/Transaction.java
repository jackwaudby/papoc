package state;

import utils.SimulationRandom;

/**
 * Represents a transaction in the database
 */
public class Transaction {

    private int id;
    private int updates;
    private int updatesCompleted;

    public Transaction(int id) {
        this.id = id;
//        this.updates = SimulationRandom.getInstance().getTransactionUpdates();
        this.updates = 1;
        this.updatesCompleted = 0;
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
