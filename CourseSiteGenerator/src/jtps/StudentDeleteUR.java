
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
public class StudentDeleteUR implements jTPS_Transaction {
    private csg_App app;
    private csg_TAData data;
    private String firstName;
    private String lastName;
    private String team;
    private String role;
    
    
    public StudentDeleteUR(csg_App app, String firstName){
        this.app = app;
        data = (csg_TAData)app.getDataComponent();
        this.firstName = firstName;
        this.lastName = data.getFirstName(firstName).getLastName();
        this.team = data.getFirstName(firstName).getTeam();
        this.role = data.getFirstName(firstName).getRole();
    }
    
    public void doTransaction(){
        data.removeStudent(firstName);
    }
    
    public void undoTransaction(){
        data.addStudent(firstName, lastName, team, role);
    }
        
}
