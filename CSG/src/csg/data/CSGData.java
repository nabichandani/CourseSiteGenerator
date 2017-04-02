/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGApp;
import java.util.ArrayList;

/**
 *
 * @author Navin
 */
public class CSGData {
    CSGApp app;
    ArrayList<TeachingAssistant> TeachingAssistants;
    
    public CSGData(CSGApp initApp){
        app = initApp;
    }

    public ArrayList<TeachingAssistant> getTeachingAssistants() {
        return TeachingAssistants;
    }
    
    
    
}
