package state;

import java.util.ArrayList;
import java.util.stream.Collectors;


/**
 * This class represents all of the distributed edges in the database.
 */
public class GlobalEdgeListSingleton {

    private static final GlobalEdgeListSingleton instance = new GlobalEdgeListSingleton();

    private ArrayList<DistributedEdge> edgeList;

    private GlobalEdgeListSingleton() {

        this.edgeList = new ArrayList<>();

    }

    /**
     * Global point of access to edge list
     * @return GlobalEdgeListSingleton, edge list
     */
    public static GlobalEdgeListSingleton getInstance() {return instance; }

    /**
     * Add edge to edge list
     * @param edge distributed edge
     */
    public void addEdge(DistributedEdge edge) {
        edgeList.add(edge);
    }

    /**
     * Get distributed edge by id
     * @param edgeId distributed edge id
     * @return distributed edge
     */
    public DistributedEdge getEdge(int edgeId) {
        ArrayList<DistributedEdge> result =
                (ArrayList<DistributedEdge>) edgeList.
                        stream().
                        filter(e -> e.getEdgeId() == edgeId).
                        collect(Collectors.toList());
        return result.get(0);
    }

    /**
     * Get all distributed edges in the database
     * @return array list of distributed edges
     */
    public ArrayList<DistributedEdge> getEdgeList() {
        return edgeList;
    }

    /**
     * Clears the provisional writes at all edges for a given transaction id
     * @param transactionId transaction whose provisional writes are cleared
     */
    public void clearProvisionalWrites(int transactionId) {

        for (DistributedEdge distributedEdge: edgeList
             ) {
            distributedEdge.removeProvisionalWrites(transactionId);
        }

    }

    /**
     * Collects predecessors (transaction ids) for a given side of a distributed edge
     * @param edgeId distributed edge id
     * @param side side of the distributed edge
     * @return
     */
    public ArrayList<Integer> collectPredecessors(int edgeId, int side) {
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<ProvisionalWrite> provisionalWrites;
        if (side == 0) {
            provisionalWrites =
                    getEdge(edgeId).getLeftProvisionalWrites();
        } else {
            provisionalWrites =
                    getEdge(edgeId).getRightProvisionalWrites();
        }
        if (!provisionalWrites.isEmpty()) {
            for (ProvisionalWrite pw : provisionalWrites
            ) {
                result.add(pw.getTransactionId());
            }
        }
        return result;
        }

}
