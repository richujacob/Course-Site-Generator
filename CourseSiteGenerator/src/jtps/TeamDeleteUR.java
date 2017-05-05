
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
public class TeamDeleteUR implements jTPS_Transaction {
    private csg_App app;
    private csg_TAData data;
    private String name;
    private String color;
    private String textColor;
    private String link;
    
    public TeamDeleteUR(csg_App app, String name){
        this.app = app;
        data = (csg_TAData)app.getDataComponent();
        this.name = name;
        this.color = data.getTeamName(name).getColor();
        this.textColor = data.getTeamName(name).getTextColor();
        this.link = data.getTeamName(name).getLink();
    }
    
    public void doTransaction(){
        data.removeTeam(name);
    }
    
    public void undoTransaction(){
        data.addTeam(name, color, textColor, link);
    }
}
