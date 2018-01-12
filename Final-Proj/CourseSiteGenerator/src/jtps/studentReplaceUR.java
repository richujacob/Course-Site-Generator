
package jtps;

import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;
import csg.csg_App;
import csg.data.csg_TAData;
import csg.data.csg_Students;
import csg.workspace.csg_Workspace;
/**
 *
 * @author Richu
 */
public class studentReplaceUR implements jTPS_Transaction {
    private String firstname;
    private String lastName;
    private String team;
    private String role;
    private String newFirstName;
    private String newLastName;
    private String newTeam;
    private String newRole;
    private csg_App app;
    private csg_Workspace workspace;
    private csg_TAData data;
    
    public studentReplaceUR(csg_App app){
        this.app = app;
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        data = (csg_TAData)app.getDataComponent();
        newFirstName = workspace.getFirstNameField().getText();
        newLastName = workspace.getLastNameField().getText();
        newTeam = (String)workspace.getTeams().getValue().toString();
        newRole = (String)workspace.getRole().getValue();
        TableView studentTable = workspace.getStudentsTable();
        Object selectedItem  = studentTable.getSelectionModel().getSelectedItem();
        csg_Students student = (csg_Students)selectedItem;
        firstname = student.getFirstName();
        lastName = student.getLastName();
        team = student.getTeam();
        role = student.getRole();
    }
    
    public void doTransaction(){
        data.removeStudent(firstname);
        data.addStudent(newFirstName, newLastName, newTeam, newRole);
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView studentTable = workspace.getStudentsTable();
        studentTable.getSelectionModel().select(data.getFirstName(newFirstName));
    }
    
    public void undoTransaction(){
        data.removeStudent(newFirstName);
        data.addStudent(firstname, lastName, team, role);
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView studentTable = workspace.getStudentsTable();
        studentTable.getSelectionModel().select(data.getFirstName(firstname));
    }
}
