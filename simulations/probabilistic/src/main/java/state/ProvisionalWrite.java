package state;

/**
 * This class represents a provisional write made by a transaction on a given distributed edge.
 */
public class ProvisionalWrite {

    private int transactionId;
    private int writeLabel;

    public ProvisionalWrite(int transactionId, int writeLabel) {
        this.transactionId = transactionId;
        this.writeLabel = writeLabel;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getWriteLabel() {
        return writeLabel;
    }

    @Override
    public String toString() {
        return "{" +
                "transactionId=" + transactionId +
                ", writeLabel=" + writeLabel +
                '}';
    }
}
