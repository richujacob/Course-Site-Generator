package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

/**
 *
 * @author Richu
 */
public class csg_CourseDetails {
    private final StringProperty navbarTitle;
    private final StringProperty fileName;
    private final StringProperty script;
    private CheckBox checkBox;
    
    public csg_CourseDetails(String navBar, String initFileName, String initScript){
        navbarTitle = new SimpleStringProperty(navBar);
        fileName = new SimpleStringProperty(initFileName);
        script = new SimpleStringProperty(initScript);
        this.checkBox=checkBox;
    }
    
    public String getNavbarTitle(){
        return navbarTitle.get();
    }
    
    public void setNavbarTitle(String navBar){
        navbarTitle.set(navBar);
    }
    
    public String getFileName(){
        return fileName.get();
    }
    
    public void setFileName(String initFileName){
        fileName.set(initFileName);
    }
    
    public String getScript(){
        return script.get();
    }
    
    public void setScript(String initScript){
        script.set(initScript);
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

   
    
//     @Override
//    public int compareTo(E otherTA) {
//        return getName().compareTo(((csg_TeachingAssistant)otherTA).getName());
//    }
    
    @Override
    public String toString() {
        return navbarTitle.getValue();
    }
}
