package state;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ArbiterSingleton {

    private static final ArbiterSingleton instance = new ArbiterSingleton();

    private Queue<Transaction> queue = new LinkedList<>();
    private ArrayList<Integer> hitList = new ArrayList<>();


    public static ArbiterSingleton getInstance() {
        return instance;
    }

    public Queue<Transaction> getQueue() {
        return queue;
    }

    public ArrayList<Integer> getHitList() {
        return hitList;
    }
}
