/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;

import java.util.ArrayList;
import javafx.scene.control.ComboBox;
import jtps.jTPS_Transaction;
import properties_manager.PropertiesManager;
import csg.csg_App;
import csg.data.csg_TAData;
import csg.file.csg_TimeSlot;
import csg.workspace.csg_Workspace;


public class TAhourschangeUR implements jTPS_Transaction{
    
    private csg_App app;
    private int startTime;
    private int endTime;
    private int newStartTime;
    private int newEndTime;
    private ArrayList<csg_TimeSlot> officeHours;
    
    public TAhourschangeUR(csg_App app){
        this.app = app;
        csg_TAData data = (csg_TAData)app.getDataComponent();
        csg_Workspace workspace = (csg_Workspace)app.getWorkspaceComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ComboBox comboBox1 = workspace.getOfficeHour(true);
        ComboBox comboBox2 = workspace.getOfficeHour(false);
        startTime = data.getStartHour();
        endTime = data.getEndHour();
        newStartTime = comboBox1.getSelectionModel().getSelectedIndex();
        newEndTime = comboBox2.getSelectionModel().getSelectedIndex();
        officeHours = csg_TimeSlot.buildOfficeHoursList(data);
    }

    @Override
    public void doTransaction() {
        ((csg_Workspace)app.getWorkspaceComponent()).getOfficeHoursGridPane().getChildren().clear();
        ((csg_TAData)app.getDataComponent()).changeTime(newStartTime, newEndTime, officeHours);
    }

    @Override
    public void undoTransaction() {
        ((csg_Workspace)app.getWorkspaceComponent()).getOfficeHoursGridPane().getChildren().clear();
        ((csg_TAData)app.getDataComponent()).changeTime(startTime, endTime, officeHours);
    }
    
}
