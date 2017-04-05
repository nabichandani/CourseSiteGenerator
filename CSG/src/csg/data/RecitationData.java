/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGApp;
import djf.components.AppDataComponent;

/**
 *
 * @author Navin
 */
public class RecitationData implements AppDataComponent{
    CSGApp app;
    
    public RecitationData(CSGApp initApp){
    app = initApp;    
    }

    @Override
    public void resetData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
