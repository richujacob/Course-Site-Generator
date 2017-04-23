package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Richu
 */
public class csg_CourseDetails {
    private final StringProperty navbarTitle;
    private final StringProperty fileName;
    private final StringProperty script;
    private boolean checkBox;
    
    public csg_CourseDetails(boolean boxCheck, String navBar, String initFileName, String initScript){
        navbarTitle = new SimpleStringProperty(navBar);
        fileName = new SimpleStringProperty(initFileName);
        script = new SimpleStringProperty(initScript);
        checkBox = boxCheck;
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

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
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
