package state;

import java.util.ArrayList;

/**
 * This class represents a distributed edge.
 * A distributed edge has a source and destination.
 */
public class DistributedEdge {

    private int edgeId;
    private double sourceLock = 0;
    private double destinationLock = 0;

    public DistributedEdge(int edgeId) {
        this.edgeId = edgeId;
    }

    /**
     * Returns the ID of a given distributed edge.
     * @return edge ID
     */
    public int getEdgeId() {
        return edgeId;
    }

    public double getSourceLock() {
        return sourceLock;
    }

    public void setSourceLock(double sourceLock) {
        this.sourceLock = sourceLock;
    }

    public double getDestinationLock() {
        return destinationLock;
    }

    public void setDestinationLock(double destinationLock) {
        this.destinationLock = destinationLock;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + edgeId +
                ", sourceLock= " + sourceLock +
                ", destinationLock=" + destinationLock +
                '}';
    }
}
