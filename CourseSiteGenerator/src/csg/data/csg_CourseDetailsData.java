
package csg.data;

/**
 *
 * @author Richu
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import djf.components.AppDataComponent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;
import csg.csg_App;
import csg.csg_Prop;
import csg.file.csg_TimeSlot;
import csg.workspace.csg_Workspace;

public class csg_CourseDetailsData implements AppDataComponent{
    
    csg_App app;
    
    ObservableList<csg_CourseDetails> courseDetailsInfo;
    ObservableList<csg_Recitation> recTable;
    
    public csg_CourseDetailsData(csg_App initApp){
        app = initApp;
        
        courseDetailsInfo = FXCollections.observableArrayList();
        
    }
    
    public void resetData(){
        courseDetailsInfo.clear();
        
        csg_Workspace workspaceComponent = (csg_Workspace)app.getWorkspaceComponent();
    }
    
    public void addPage(boolean checkBox, String navbar, String fileName, String script) {
        // MAKE THE TA
        csg_CourseDetails cD = new csg_CourseDetails(checkBox, navbar, fileName, script);
        
       courseDetailsInfo.add(cD);
        // ADD THE TA
//        if (!containsTA(initName, initEmail)) {
//            teachingAssistants.add(ta);
//        }

        //Collections.sort(courseDetailsInfo);
    }

    public ObservableList getCourseDetailsInfo() {
        return courseDetailsInfo;
    }
    
    
    
}
