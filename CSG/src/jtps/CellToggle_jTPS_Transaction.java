/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;
import csg.CSGApp;
import csg.data.TAData;
/**
 *
 * @author Navin
 */
public class CellToggle_jTPS_Transaction implements jTPS_Transaction{
    CSGApp app;
    TAData data;
    String cellKey;
    String taName;
    
    
    
    public CellToggle_jTPS_Transaction(CSGApp app, String cellKey, String name){
        this.app = app;
        data = (TAData) app.getTADataComponent();
        this.cellKey = cellKey;
        taName = name;
    }

    @Override
    public void doTransaction() {
       data.toggleTAOfficeHours(cellKey, taName);
    }

    @Override
    public void undoTransaction() {
       data.toggleTAOfficeHours(cellKey, taName);
    }
    
}
