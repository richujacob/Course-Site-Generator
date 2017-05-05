
package jtps;

import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;
import csg.csg_App;
import csg.data.csg_TAData;
import csg.data.csg_Teams;
import csg.workspace.csg_Workspace;
/**
 *
 * @author Richu
 */
public class TeamReplaceUR implements jTPS_Transaction {
    private String name;
    private String color;
    private String textColor;
    private String link;
    private String newName;
    private String newColor;
    private String newTextColor;
    private String newLink;
    private csg_App app;
    private csg_Workspace workspace;
    private csg_TAData data;
    
    public TeamReplaceUR(csg_App app){
        this.app = app;
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        data = (csg_TAData)app.getDataComponent();
        newName = workspace.getNameTeams().getText();
        newColor = Integer.toHexString(workspace.getColor1().getValue().hashCode()).substring(0, 6);
        newTextColor = Integer.toHexString(workspace.getColor2().getValue().hashCode()).substring(0, 6);
        newLink = workspace.getLinkTeams().getText();
        TableView teamTable = workspace.getTeamsTable();
        Object selectedItem  = teamTable.getSelectionModel().getSelectedItem();
        csg_Teams team = (csg_Teams)selectedItem;
        name = team.getName();
        color = team.getColor();
        textColor = team.getTextColor();
        link = team.getLink();
    }
    
    public void doTransaction(){
        data.removeTeam(name);
        data.addTeam(newName, newColor, newTextColor, newLink);
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView teamTable = workspace.getTeamsTable();
        teamTable.getSelectionModel().select(data.getTeamName(newName));
    }
    
    public void undoTransaction(){
        data.removeTeam(newName);
        data.addTeam(name, color, textColor, link);
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView teamTable = workspace.getTeamsTable();
        teamTable.getSelectionModel().select(data.getTeamName(name));
    }
}
