/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.files;

import csg.CSGApp;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.IOException;

/**
 *
 * @author Navin
 */
public class CSGFiles implements AppFileComponent{
    CSGApp app;
    
    public CSGFiles(CSGApp initApp){
    app = initApp;
}

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
       
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        
    }

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
       
    }
}
