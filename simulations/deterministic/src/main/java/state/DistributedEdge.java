package state;

import java.util.ArrayList;

/**
 * This class represents a distributed edge.
 * A distributed edge has two distinct side, left and right.
 * Each side has it's own provisional write list containing transaction IDs.
 */
public class DistributedEdge {

    private int edgeId;
    private ArrayList<ProvisionalWrite> leftProvisionalWrites;
    private ArrayList<ProvisionalWrite> rightProvisionalWrites;


    public DistributedEdge(int edgeId) {
        this.edgeId = edgeId;
        leftProvisionalWrites = new ArrayList<>();
        rightProvisionalWrites = new ArrayList<>();
    }

    /**
     * Returns the ID of a given distributed edge.
     * @return edge ID
     */
    public int getEdgeId() {
        return edgeId;
    }

    /**
     * Add a provisional write to a distributed edge
     * @param leftSide, true corresponds to left and false corresponds to right
     * @param provisionalWrite, provisional writes by transactions
     */
    public void addProvisionalWrite(Boolean leftSide,ProvisionalWrite provisionalWrite) {

        if (leftSide) {
            leftProvisionalWrites.add(provisionalWrite);
        } else {
            rightProvisionalWrites.add(provisionalWrite);
        }

    }

    public ArrayList<ProvisionalWrite> getLeftProvisionalWrites() {
        return leftProvisionalWrites;
    }

    public ArrayList<ProvisionalWrite> getRightProvisionalWrites() {
        return rightProvisionalWrites;
    }

    public void removeProvisionalWrites (Integer transactionId) {

        leftProvisionalWrites.removeIf(pw -> (pw.getTransactionId() == transactionId));
        rightProvisionalWrites.removeIf(pw -> (pw.getTransactionId() == transactionId));


    }

    @Override
    public String toString() {
        return "{" +
                "id=" + edgeId +
                ", leftPW=" + leftProvisionalWrites +
                ", rightPW=" + rightProvisionalWrites +
                '}';
    }
}
