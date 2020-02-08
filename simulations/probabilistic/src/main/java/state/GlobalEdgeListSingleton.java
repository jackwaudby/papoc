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



}
