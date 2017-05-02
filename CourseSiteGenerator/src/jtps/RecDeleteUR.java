
package jtps;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import jtps.jTPS_Transaction;
import csg.csg_App;
import csg.data.csg_TAData;
import csg.workspace.csg_Workspace;
/**
 *
 * @author Richu
 */
public class RecDeleteUR implements jTPS_Transaction{
    private csg_App app;
    private csg_TAData data;
    //private ArrayList<StringProperty> cellProps = new ArrayList<StringProperty>();
    private String section;
    private String instructor;
    private String dayTime;
    private String location;
    private String ta1;
    private String ta2;
    
    public RecDeleteUR(csg_App app, String section){
        this.app = app;
        data = (csg_TAData)app.getDataComponent();
        this.section=section;
        this.instructor = data.getSection(section).getInstructor();
        this.dayTime = data.getSection(section).getDayTime();
        this.location = data.getSection(section).getLocation();
        this.ta1 = data.getSection(section).getTa();
        this.ta2 = data.getSection(section).getTa2();
    }
    
    public void doTransaction(){
        data.removeRecitation(section);
    }
    
    public void undoTransaction(){
        data.addRec(section, instructor, dayTime, location, ta1, ta2);
    }
}
