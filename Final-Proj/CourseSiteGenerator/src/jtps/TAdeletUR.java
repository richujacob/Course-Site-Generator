/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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


public class TAdeletUR implements jTPS_Transaction{
    
    private csg_App app;
    private csg_TAData data;
    private ArrayList<StringProperty> cellProps = new ArrayList<StringProperty>();
    private String TAname;
    private String TAemail;
    
    public TAdeletUR(csg_App app, String TAname){
        this.app = app;
        data = (csg_TAData)app.getDataComponent();
        this.TAname = TAname;
        TAemail = data.getTA(TAname).getEmail();
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        HashMap<String, Label> labels = workspace.getOfficeHoursGridTACellLabels();
        for (Label label : labels.values()) {
            if (label.getText().equals(TAname)
            || (label.getText().contains(TAname + "\n"))
            || (label.getText().contains("\n" + TAname))) {
                cellProps.add(label.textProperty());
            }
        }
    }

    @Override
    public void doTransaction() {
        data.removeTA(TAname);
        for(StringProperty cellProp : cellProps){
            data.removeTAFromCell(cellProp, TAname);
        }
    }

    @Override
    public void undoTransaction() {
        data.addTA(false, TAname, TAemail);
        for(StringProperty cellProp : cellProps){
            String cellText = cellProp.getValue();
            if (cellText.length() == 0){
                cellProp.setValue(TAname);
            } else {
                cellProp.setValue(cellText + "\n" + TAname);}
        }
    }
    
}
