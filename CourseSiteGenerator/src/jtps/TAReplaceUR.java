/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;

import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;
import csg.csg_App;
import csg.data.csg_TAData;
import csg.data.csg_TeachingAssistant;
import csg.workspace.csg_Workspace;

public class TAReplaceUR implements jTPS_Transaction{
    private String TAname;
    private String TAemail;
    private String newName;
    private String newEmail;
    private csg_App app;
    private csg_TAData data;
    
    public TAReplaceUR(csg_App app){
        this.app = app;
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        data = (csg_TAData)app.getDataComponent();
        newName = workspace.getNameTextField().getText();
        newEmail = workspace.getEmailTextField().getText();
        TableView taTable = workspace.getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        csg_TeachingAssistant ta = (csg_TeachingAssistant)selectedItem;
        TAname = ta.getName();
        TAemail = ta.getEmail();
    }

    @Override
    public void doTransaction() {
        data.replaceTAName(TAname, newName);
        data.removeTA(TAname);
        data.addTA(false, newName, newEmail);
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        taTable.getSelectionModel().select(data.getTA(newName));
    }

    @Override
    public void undoTransaction() {
        data.replaceTAName(newName, TAname);
        data.removeTA(newName);
        data.addTA(false, TAname, TAemail);
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        taTable.getSelectionModel().select(data.getTA(TAname));
    }
    
    
    
}
