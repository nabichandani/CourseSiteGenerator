/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSGTransactions;

import java.util.ArrayList;

/**
 *
 * @author Navin
 */
public class TransactionStack {
    private ArrayList<Transaction> transactions = new ArrayList();
    private int mostRecentTransaction = -1;
    
    public TransactionStack() {}
    
    public void addTransaction(Transaction transaction) {
        // IS THIS THE FIRST TRANSACTION?
        if (mostRecentTransaction < 0) {
            // DO WE HAVE TO CHOP THE LIST?
            if (transactions.size() > 0) {
                transactions = new ArrayList();
            }
            transactions.add(transaction);
        }
        // ARE WE ERASING ALL THE REDO TRANSACTIONS?
        else if (mostRecentTransaction < (transactions.size()-1)) {
            transactions.set(mostRecentTransaction+1, transaction);
            transactions = new ArrayList(transactions.subList(0, mostRecentTransaction+2));
        }
        // IS IT JUST A TRANSACTION TO APPEND TO THE END?
        else {
            transactions.add(transaction);
        }
        mostRecentTransaction++;
        //WE DONT WANT THE TRANSACTION TO TAKE PLACE.
        //doTransaction();
    }
    
    public void doTransaction() {
        if (mostRecentTransaction < (transactions.size()-1)) {
            Transaction transaction = transactions.get(mostRecentTransaction+1);
            transaction.doTransaction();
            mostRecentTransaction++;
        }
    }
    
    public void undoTransaction() {
        if (mostRecentTransaction >= 0) {
            Transaction transaction = transactions.get(mostRecentTransaction);
            transaction.undoTransaction();
            mostRecentTransaction--;
        }
    }
    
    public String toString() {
        String text = "--Number of Transactions: " + transactions.size() + "\n";
        text += "--Current Index on Stack: " + mostRecentTransaction + "\n";
        text += "--Current Transaction Stack:\n";
        for (int i = 0; i <= mostRecentTransaction; i++) {
            Transaction t = transactions.get(i);
            text += "----" + t.toString() + "\n";
        }
        return text;
    }
}

