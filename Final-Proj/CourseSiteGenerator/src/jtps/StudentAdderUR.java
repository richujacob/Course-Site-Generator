
package jtps;

import java.util.regex.Pattern;
import jtps.jTPS_Transaction;
import csg.csg_App;
import csg.data.csg_TAData;
import csg.workspace.csg_TAController;
import csg.workspace.csg_Workspace;
/**
 *
 * @author Richu
 */
public class StudentAdderUR implements jTPS_Transaction {
    private String firstName;
    private String lastName;
    private String team;
    private String role;
    private csg_App app;
    private csg_Workspace workspace;
    
    public StudentAdderUR(csg_App app){
        this.app = app;
        workspace = (csg_Workspace)app.getWorkspaceComponent();
        firstName = workspace.getFirstNameField().getText();
        lastName = workspace.getLastNameField().getText();
        team = (String)workspace.getTeams().getValue().toString();
        role = (String)workspace.getRole().getValue();        
    }
    
    public void doTransaction(){
        ((csg_TAData)app.getDataComponent()).addStudent(firstName, lastName, team, role);
    }
    
    public void undoTransaction() {
        ((csg_TAData)app.getDataComponent()).removeStudent(firstName);
    }
}
