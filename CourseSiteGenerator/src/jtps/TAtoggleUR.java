/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jtps;

import jtps.jTPS_Transaction;
import csg.data.csg_TAData;


public class TAtoggleUR implements jTPS_Transaction{
    
    private String TAname;
    private String cellKey;
    private csg_TAData data;
    
    public TAtoggleUR(String TAname, String cellKey, csg_TAData data){
        this.TAname = TAname;
        this.cellKey = cellKey;
        this.data = data;
    }
    

    @Override
    public void doTransaction() {
        data.toggleTAOfficeHours(cellKey, TAname);
    }

    @Override
    public void undoTransaction() {
        data.toggleTAOfficeHours(cellKey, TAname);
    }
    
}
