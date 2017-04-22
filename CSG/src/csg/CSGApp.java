
package csg;

import csg.data.CourseData;
import csg.data.ProjectData;
import csg.data.Recitation;
import csg.data.RecitationData;
import csg.data.ScheduleData;
import java.util.Locale;
import csg.data.TAData;
import csg.files.CSGFiles;
import csg.ui.CSGWorkspace;
import djf.AppTemplate;
import javafx.application.Application;
import test_bed.TestSave;


/**
 *
 * @author Navin
 */
public class CSGApp extends AppTemplate{

   
    
    public void buildAppComponentsHook() {
        courseDataComponent = new CourseData(this);
        taDataComponent = new TAData(this);
        recitationDataComponent= new RecitationData(this);
        projectDataComponent= new ProjectData(this);
        scheduleDataComponent= new ScheduleData(this);
        workspaceComponent = new CSGWorkspace(this);
        //fileComponent = new TestSave(this);
        fileComponent = new CSGFiles(this);
        styleComponent = new CSGStyle(this);
    }


    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }

    

    
}
