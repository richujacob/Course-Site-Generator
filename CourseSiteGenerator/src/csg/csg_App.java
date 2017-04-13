package csg;

import java.util.Locale;
import csg.data.csg_TAData;
import csg.file.csg_TAFiles;
import csg.workspace.csg_Workspace;
import djf.AppTemplate;
import csg.style.csg_Style;
import static javafx.application.Application.launch;

/**
 *
 * @author Richu
 */
public class csg_App extends AppTemplate {
    
    
    @Override
    public void buildAppComponentsHook() {
        dataComponent = new csg_TAData(this);
        workspaceComponent = new csg_Workspace(this);
        fileComponent = new csg_TAFiles(this);
        styleComponent = new csg_Style(this);
    }
    
    public static void main(String[] args) {
	try{
        Locale.setDefault(Locale.US);
	launch(args);
        }catch(Exception e){
            e.printStackTrace();
        }
       
    }
}