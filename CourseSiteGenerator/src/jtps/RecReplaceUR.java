
package jtps;

import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;
import csg.csg_App;
import csg.data.csg_TAData;
import csg.data.csg_Recitation;
import csg.workspace.csg_Workspace;
/**
 *
 * @author Richu
 */
public class RecReplaceUR implements jTPS_Transaction{
    private String section;
    private String instructor;
    private String dayTime;
    private String location;
    private String ta1;
    private String ta2;
    private String sectionNew;
    private String instructorNew;
    private String dayTimeNew;
    private String locationNew;
    private String ta1New;
    private String ta2New;
    private csg_App app;
    private csg_Workspace workspace;
    private csg_TAData data;
    
    public RecReplaceUR(csg_App app){
        this.app = app;
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        data = (csg_TAData)app.getDataComponent();
        sectionNew = workspace.getSectionField().getText();
        instructorNew = workspace.getInstructorField().getText();
        dayTimeNew = workspace.getDayTimeField().getText();
        locationNew = workspace.getLocationField().getText();
        ta1New = (String)workspace.getSupervisingTA().getValue();
        ta2New = (String)workspace.getSupervisingTA2().getValue();
        TableView recTable = workspace.getRecitationTable();
        Object selectedItem  = recTable.getSelectionModel().getSelectedItem();
        csg_Recitation rec = (csg_Recitation)selectedItem;
        section = rec.getSection();
        instructor = rec.getInstructor();
        dayTime = rec.getDayTime();
        location = rec.getLocation();
        ta1 = rec.getTa();
        ta2 = rec.getTa2();        
    }

    
    @Override
    public void doTransaction() {
        //data.replaceRecitationName(section, sectionNew);
        data.removeRecitation(section);
        data.addRec(sectionNew, instructorNew, dayTimeNew, locationNew, ta1New, ta2New);
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView recTable = workspace.getRecitationTable();
        recTable.getSelectionModel().select(data.getTA(sectionNew));
    }
    
    @Override
    public void undoTransaction(){
        //data.replaceTAName(newName, TAname);
        data.removeRecitation(sectionNew);
        data.addRec(section, instructor, dayTime, location, ta1, ta2);
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView recTable = workspace.getRecitationTable();
        recTable.getSelectionModel().select(data.getTA(section));
    }

   
}
