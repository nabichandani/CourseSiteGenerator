
package csg;

import java.util.Locale;
import csg.data.CSGData;
import csg.files.CSGFiles;
import csg.ui.CSGWorkspace;
import djf.AppTemplate;
import javafx.application.Application;


/**
 *
 * @author Navin
 */
public class CSGApp extends AppTemplate{

   
    
    public void buildAppComponentsHook() {
        CSGWorkspace workspace = new CSGWorkspace(this);
        CSGData data = new CSGData(this);
        CSGStyle style = new CSGStyle(this);
        CSGFiles files = new CSGFiles(this);
    }


    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }

    

    
}
