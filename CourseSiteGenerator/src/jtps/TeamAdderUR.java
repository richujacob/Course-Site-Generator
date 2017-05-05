
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
public class TeamAdderUR implements jTPS_Transaction {
    private String name;
    private String color;
    private String textColor;
    private String link;
    private csg_App app;
    private csg_Workspace workspace;
    
    public TeamAdderUR(csg_App app){
        this.app = app;
        workspace = (csg_Workspace)app.getWorkspaceComponent();
        name = workspace.getNameTeams().getText();
        color = Integer.toHexString(workspace.getColor1().getValue().hashCode()).substring(0, 6);
        textColor = Integer.toHexString(workspace.getColor2().getValue().hashCode()).substring(0, 6);
        link = workspace.getLinkTeams().getText();
        
    }
    
    public void doTransaction(){
        ((csg_TAData)app.getDataComponent()).addTeam(name, color, textColor, link);
    }

    @Override
    public void undoTransaction() {
        ((csg_TAData)app.getDataComponent()).removeTeam(name);
    }
    
}
