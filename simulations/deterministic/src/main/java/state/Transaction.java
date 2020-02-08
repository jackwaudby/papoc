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
    private ArrayList<Integer> predecessors;
    private ArrayList<ProvisionalWrite> collisionDetection;
    private double startTime;
    private double endTime;

    public Transaction(int id) {
        this.id = id;
        this.updates = SimulationRandom.getInstance().getTransactionUpdates();
        this.updatesCompleted = 0;
        predecessors = new ArrayList<>();
        collisionDetection = new ArrayList<>();
    }

    void setPredecessors(ArrayList<Integer> predecessors) {
        this.predecessors = predecessors;
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

    public ArrayList<Integer> getPredecessors() {
        return predecessors;
    }

    public void addPredecessor(int id) {
        predecessors.add(id);
    }

    public void addPredecessors(ArrayList<Integer> predecessorList) {
        predecessors.addAll(predecessorList);
    }

    public void incrementUpdateCompleted() {
        updatesCompleted = updatesCompleted + 1;
    }

    public void addCollisionDetectionInformation(ProvisionalWrite pw) {
        collisionDetection.add(pw);
    }

    public void removeCollisionDetectionInformation() {
        collisionDetection.clear();
    }

    public ArrayList<ProvisionalWrite> getCollisionDetection() {
        return collisionDetection;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", updates=" + updates +
                ", completed=" + updatesCompleted +
                ", predecessors=" + predecessors +
                ", collisionDetection=" + collisionDetection +
                '}';
    }
}
