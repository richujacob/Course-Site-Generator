/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;

import java.util.regex.Pattern;
import jtps.jTPS_Transaction;
import csg.csg_App;
import csg.data.csg_TAData;
import csg.workspace.csg_TAController;
import csg.workspace.csg_Workspace;


public class TAAdderUR implements jTPS_Transaction{
    
    private String TAName;
    private String TAEmail;
    private csg_App app;
    private csg_Workspace workspace;
    
    public TAAdderUR(csg_App app){
        this.app = app;
        workspace = (csg_Workspace)app.getWorkspaceComponent();
        TAName = workspace.getNameTextField().getText();
        TAEmail = workspace.getEmailTextField().getText();
    }

    @Override
    public void doTransaction() {
        ((csg_TAData)app.getDataComponent()).addTA(false, TAName, TAEmail);
    }

    @Override
    public void undoTransaction() {
        ((csg_TAData)app.getDataComponent()).removeTA(TAName);
    }
    
}
