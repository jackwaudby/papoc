package state;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GlobalActiveTransactionListSingleton {

    private static final GlobalActiveTransactionListSingleton instance = new GlobalActiveTransactionListSingleton();
    private ArrayList<Transaction> activeTransactionList;

    private GlobalActiveTransactionListSingleton() {
        activeTransactionList = new ArrayList<>();
    }

    public static GlobalActiveTransactionListSingleton getInstance() {
        return instance;
    }

    public void addTransaction(Transaction transaction) {
        activeTransactionList.add(transaction);
    }

    public void removeTransaction(int transactionId){
        // remove from list
        activeTransactionList = (ArrayList<Transaction>) activeTransactionList.stream().filter(transaction -> transaction.getId() != transactionId).collect(Collectors.toList());
        // remove from all pred lists
        for (Transaction transaction: activeTransactionList
             ) {
            ArrayList<Integer> newPredecessors = (ArrayList<Integer>) transaction.getPredecessors().stream().filter(pred -> pred != transactionId).collect(Collectors.toList());
            transaction.setPredecessors(newPredecessors);
        }
    }

    public ArrayList<Transaction> getActiveTransactionList() {
        return activeTransactionList;
    }
}
