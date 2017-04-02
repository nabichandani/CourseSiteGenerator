
package csg;

import java.util.Locale;
import csg.data.CSGData;
import csg.files.CSGFiles;
import csg.ui.CSGWorkspace;
import djf.AppTemplate;

import static javafx.application.Application.launch;

/**
 *
 * @author Navin
 */
public class CSGApp extends AppTemplate{

   
    
    public void buildAppComponentsHook() {
        CSGWorkspace workspace = new CSGWorkspace();
        CSGData data = new CSGData();
        CSGStyle style = new CSGStyle();
        CSGFiles files = new CSGFiles();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }

    

    
}
