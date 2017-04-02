/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSGTransactions;

/**
 *
 * @author Navin
 */
public interface TransactionTemplate {
    public  void undo();
    
    public  void redo();
    
}
