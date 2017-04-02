
package CSGTransactions;

/**
 *
 * @author Navin
 */
public interface Transaction {
    public  void undoTransaction();
    
    public  void doTransaction();
}
