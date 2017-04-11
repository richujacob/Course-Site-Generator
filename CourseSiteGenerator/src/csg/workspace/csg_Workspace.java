package csg.workspace;

import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import static djf.settings.AppPropertyType.SAVE_UNSAVED_WORK_MESSAGE;
import static djf.settings.AppPropertyType.SAVE_UNSAVED_WORK_TITLE;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import csg.csg_App;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import csg.csg_Prop;
import static csg.csg_Prop.ADD_BUTTON_TEXT;
import static csg.csg_Prop.MISSING_TA_NAME_MESSAGE;
import static csg.csg_Prop.MISSING_TA_NAME_TITLE;
import static csg.csg_Prop.UPDATE_BUTTON_TEXT;
import csg.style.csg_Style;
import csg.data.csg_TAData;
import csg.data.csg_TeachingAssistant;
import csg.file.csg_TimeSlot;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ColorPicker;
/**
 *
 * @author Richu
 */
public class csg_Workspace extends AppWorkspaceComponent {
    
    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    csg_App app;
    boolean add;

    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    //TAController controller;

    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT
    
    // FOR THE HEADER ON THE LEFT
    HBox tasHeaderBox;
    Label tasHeaderLabel;
    
    //course details info pane
    HBox courseInfoBox;
    Label courseInfoLabel;
    HBox subjectLine;
    Label subjectLabel;
    Label numberLabel;
    HBox semesterLine;
    Label semesterLabel;
    Label yearLabel;
    HBox titleBox;
    Label titleLabel;
    HBox instructorNameBox;
    Label instructorNameLabel;
    HBox instructorHomeBox;
    Label instructorHomeLabel;
    HBox exportDirectoryBox;
    Label exportDirectoryLabel;
    Label exportText;
    
    ObservableList<String> courseOptions;
    ObservableList<String> numberOptions;
    ObservableList<String> semesterOptions;
    ObservableList<String> yearOptions;
    ComboBox subjectCombo;
    ComboBox semesterCombo;
    ComboBox numberCombo;
    ComboBox yearCombo;
    
    TextField titleField;
    TextField instructorNameField;
    TextField instructorHomeField;
    
    Button changeButton;
    
    HBox siteTemplateBox;
    Label siteTemplateLabel;
    HBox directoryInstructionsBox;
    Label directoryInstructionsLabel;
    HBox selectedFileTemplate;
    Label selectedFileLabel;
    HBox sitePagesBox;
    Label sitePagesLabel;
    HBox selectBox;
    
    Button selectTemplate;
    
    TableView<csg_TeachingAssistant> courseDetailsTable;
    TableColumn<csg_TeachingAssistant, String> useColumn;
    TableColumn<csg_TeachingAssistant, String> navBarColumn;
    TableColumn<csg_TeachingAssistant, String> fileColumn;
    TableColumn<csg_TeachingAssistant, String> scriptColumn;
    
    HBox pageStyleBox;
    Label pageStyleLabel;
    HBox bannerSchoolBox;
    Label bannerSchoolLabel;
    HBox leftFootBox;
    Label leftFootLabel;
    HBox rightFootBox;
    Label rightFootLabel;
    HBox styleSheetBox;
    Label styleSheetLabel;
    HBox styleNoteBox;
    Label styleNoteLabel;
    
    Button changeButton1;
    Button changeButton2;
    Button changeButton3;
    ObservableList<String> style_Options;
    ComboBox styleSheet;        
    
    HBox recitationHeader;
    Label recitationLabel;
    
    TableView<csg_TeachingAssistant> recitationTable;
    TableColumn<csg_TeachingAssistant, String> sectionColumn;
    TableColumn<csg_TeachingAssistant, String> instructorColumn;
    TableColumn<csg_TeachingAssistant, String> dayTimeColumn;
    TableColumn<csg_TeachingAssistant, String> locationColumn;
    TableColumn<csg_TeachingAssistant, String> ta1Column;
    TableColumn<csg_TeachingAssistant, String> ta2Column;
    
    Button recitationDeleteButton;
    
    HBox addEditBox;
    Label addEditLabel;
    HBox sectionBox;
    Label sectionLabel;
    HBox instructorBox;
    Label instructorLabel;
    HBox dayTimeBox;
    Label dayTimeLabel;
    HBox locationBox;
    Label locationLabel;
    HBox supervisingTA1Box;
    Label supervisingTA1Label;
    HBox supervisingTA2Box;
    Label supervisingTA2Label;
    
    ObservableList<String> ta_Options;
    ObservableList<String> ta_Options2;
    ComboBox supervisingTA;
    ComboBox supervisingTA2;
    HBox buttonBox;
    Button addUpdate2;
    Button clear2;
    
    TextField sectionField;
    TextField instructorField;
    TextField dayTimeField;
    TextField locationField;
    
    HBox scheduleBox;
    Label scheduleLabel;
    
    HBox calendarBoundsBox;
    Label calendarBoundsLabel;
    
    HBox startingDayBox;
    Label startingMondayLabel;
    Label endingFridayLabel;
    
    HBox scheduleItemsBox;
    Label scheduleItemsLabel;
    Button scheduleDeleteItems;
    
    TableView<csg_TeachingAssistant> scheduleTable;
    TableColumn<csg_TeachingAssistant, String> typeColumn;
    TableColumn<csg_TeachingAssistant, String> dateColumn;
    TableColumn<csg_TeachingAssistant, String> titleColumn;
    TableColumn<csg_TeachingAssistant, String> topicColumn;
    
    HBox addEditScheduleBox;
    Label addEditLabelBox;
    
    HBox typeBox;
    Label typeLabel;
    HBox dateBox;
    Label dateLabel;
    HBox timeBox;
    Label timeLabel;
    HBox titleScheduleBox;
    Label titleScheduleLabel;
    HBox topicBox;
    Label topicLabel;
    HBox linkBox;
    Label linkLabel;
    HBox criteriaBox;
    Label criteriaLabel;
    
    HBox buttonSetting;
    Button scheduleDeleteButton;
    Button addUpdateButton3;
    Button clearButton3;
    
    ObservableList<String> type_Options;
    ComboBox typeEventBox;
    
    TextField timeField;
    TextField titleScheduleField;
    TextField topicField;
    TextField linkField;
    TextField criteriaField;
    
    DatePicker date = new DatePicker();
    DatePicker date2 = new DatePicker();
    DatePicker date3 = new DatePicker();
    
    HBox projectsHeaderBox;
    Label projectsHeaderLabel;
    
    HBox teamsBox;
    Label teamsLabel;
    Button teamsDeleteButton;
    
    HBox addEditTeamsBox;
    Label addEditTeamsLabel;
    HBox nameTeamsBox;
    Label nameTeamsLabel;
    HBox colorBox;
    Label colorLabel;
    Label textColorLabel;
    HBox linkTeamsBox;
    Label linkTeamsLabel;
    
    TextField nameTeams;
    TextField linkTeams;
    HBox teamsButton;
    Button addUpdate4Button;
    Button clearButton4;
    
    ColorPicker color1 = new ColorPicker();
    ColorPicker color2 = new ColorPicker();
    
    TableView<csg_TeachingAssistant> teamsTable;
    TableColumn<csg_TeachingAssistant, String> nameTeamsColumn;
    TableColumn<csg_TeachingAssistant, String> colorTeamsColumn;
    TableColumn<csg_TeachingAssistant, String> textColorTeamsColumn;
    TableColumn<csg_TeachingAssistant, String> linkTeamsColumn;
    // FOR THE TA TABLE
    TableView<csg_TeachingAssistant> taTable;
    TableColumn<csg_TeachingAssistant, String> nameColumn;
    TableColumn<csg_TeachingAssistant, String> emailColumn;
    
    HBox studentsHeaderBox;
    Label studentsHeaderLabel;
    Button studentsDeleteButton;
    
    HBox addEditStudentsBox;
    Label addEditStudentsLabel;
    HBox firstNameBox;
    Label firstNameLabel;
    HBox lastNameBox;
    Label lastNameLabel;
    HBox teamBox;
    Label teamLabel;
    HBox roleBox;
    Label roleLabel;
    
    TextField firstNameField;
    TextField lastNameField;
    TextField roleField;
    
    ObservableList<String> team_Options;
    ComboBox teams;
    
    HBox studentsButton;
    Button addUpdateButton5;
    Button clearButton5;
    
    TableView<csg_TeachingAssistant> studentsTable;
    TableColumn<csg_TeachingAssistant, String> firstNameColumn;
    TableColumn<csg_TeachingAssistant, String> lastNameColumn;
    TableColumn<csg_TeachingAssistant, String> teamColumn;
    TableColumn<csg_TeachingAssistant, String> roleColumn;
    
    // THE TA INPUT
    HBox addBox;
    TextField nameTextField;
    TextField emailTextField;
    Button addButton;
    Button clearButton;

    // THE HEADER ON THE RIGHT
    HBox officeHoursHeaderBox;
    Label officeHoursHeaderLabel;
    
    ObservableList<String> time_options;
    ComboBox comboBox1;
    ComboBox comboBox2;    
    
    Tab courseDetails;
    Tab taData;
    Tab recitation;
    Tab schedule;
    Tab projects;
    
    
    // THE OFFICE HOURS GRID
    GridPane officeHoursGridPane;
    HashMap<String, Pane> officeHoursGridTimeHeaderPanes;
    HashMap<String, Label> officeHoursGridTimeHeaderLabels;
    HashMap<String, Pane> officeHoursGridDayHeaderPanes;
    HashMap<String, Label> officeHoursGridDayHeaderLabels;
    HashMap<String, Pane> officeHoursGridTimeCellPanes;
    HashMap<String, Label> officeHoursGridTimeCellLabels;
    HashMap<String, Pane> officeHoursGridTACellPanes;
    HashMap<String, Label> officeHoursGridTACellLabels;

    /**
     * The constructor initializes the user interface, except for
     * the full office hours grid, since it doesn't yet know what
     * the hours will be until a file is loaded or a new one is created.
     */
    public csg_Workspace(csg_App initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        add = true;
        
        TabPane tabPane = new TabPane();
        //BorderPane mainPane = new BorderPane();

        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        courseOptions = FXCollections.observableArrayList(
                props.getProperty(csg_Prop.COURSE_CSE.toString())
        );
        
        numberOptions = FXCollections.observableArrayList(
                props.getProperty(csg_Prop.NUMBER_219.toString()),
                props.getProperty(csg_Prop.NUMBER_308.toString())
        );
        
        semesterOptions = FXCollections.observableArrayList(
               props.getProperty(csg_Prop.SEMESTER_FALL.toString()),
               props.getProperty(csg_Prop.SEMESTER_SPRING.toString()) 
        );
        
        yearOptions = FXCollections.observableArrayList(
               props.getProperty(csg_Prop.YEAR_2017.toString())
        );
        
        subjectCombo = new ComboBox(courseOptions);
        numberCombo = new ComboBox(numberOptions);
        semesterCombo = new ComboBox(semesterOptions);
        yearCombo = new ComboBox(yearOptions);
        
        VBox courseDetailsMain = new VBox();
        VBox courseDetailsTop = new VBox();
        
        courseInfoBox = new HBox();
        String courseInfoText = props.getProperty(csg_Prop.COURSE_INFO_TEXT.toString());
        courseInfoLabel = new Label(courseInfoText);
        courseInfoBox.getChildren().add(courseInfoLabel);
                
        subjectLine = new HBox();
        String subjectText = props.getProperty(csg_Prop.SUBJECT_TEXT.toString());
        subjectLabel = new Label(subjectText);
        String numberText = props.getProperty(csg_Prop.NUMBER_TEXT.toString());
        numberLabel = new Label(numberText);
        subjectLine.getChildren().add(subjectLabel);
        subjectLine.getChildren().add(subjectCombo);
        subjectLine.getChildren().add(numberLabel);
        subjectLine.getChildren().add(numberCombo);
        
        semesterLine = new HBox();
        String semesterText = props.getProperty(csg_Prop.SEMESTER_TEXT.toString());
        semesterLabel = new Label(semesterText);
        String yearText = props.getProperty(csg_Prop.YEAR_TEXT.toString());
        yearLabel = new Label(yearText);
        semesterLine.getChildren().add(semesterLabel);
        semesterLine.getChildren().add(semesterCombo);
        semesterLine.getChildren().add(yearLabel);
        semesterLine.getChildren().add(yearCombo);
        
        titleBox = new HBox();
        String titleText = props.getProperty(csg_Prop.TITLE_TEXT.toString());
        titleLabel = new Label(titleText);
        titleField = new TextField();
        titleBox.getChildren().add(titleLabel);
        titleBox.getChildren().add(titleField);
        
        instructorNameBox = new HBox();
        String instructorNameText = props.getProperty(csg_Prop.INSTRUCTOR_NAME_TEXT.toString());
        instructorNameLabel = new Label(instructorNameText);
        instructorNameField = new TextField();
        instructorNameBox.getChildren().add(instructorNameLabel);
        instructorNameBox.getChildren().add(instructorNameField);
        
        exportDirectoryBox = new HBox();
        String exportDirText = props.getProperty(csg_Prop.EXPORT_DIR_TEXT.toString());
        exportDirectoryLabel = new Label(exportDirText);
        String exportTextWords = props.getProperty(csg_Prop.EXPORT_TEXT.toString());
        exportText = new Label(exportTextWords);
        String changeButtonText = props.getProperty(csg_Prop.CHANGE_BUTTON_TEXT.toString());
        changeButton = new Button(changeButtonText);
        exportDirectoryBox.getChildren().add(exportDirectoryLabel);
        exportDirectoryBox.getChildren().add(exportText);
        exportDirectoryBox.getChildren().add(changeButton);
        
        courseDetailsTop.getChildren().add(courseInfoBox);
        courseDetailsTop.getChildren().add(subjectLine);
        courseDetailsTop.getChildren().add(semesterLine);
        courseDetailsTop.getChildren().add(titleBox);
        courseDetailsTop.getChildren().add(instructorNameBox);
        courseDetailsTop.getChildren().add(exportDirectoryBox);
        courseDetailsMain.getChildren().add(courseDetailsTop);
        
        VBox courseDetailsCenter = new VBox();
        
        siteTemplateBox = new HBox();
        String siteTemplateText = props.getProperty(csg_Prop.SITE_TEMPLATE_TEXT.toString());
        siteTemplateLabel = new Label(siteTemplateText);
        siteTemplateBox.getChildren().add(siteTemplateLabel);
        courseDetailsCenter.getChildren().add(siteTemplateBox);
        
        
        directoryInstructionsBox = new HBox();
        String instructionsText = props.getProperty(csg_Prop.DIR_INSTRUCTIONS_TEXT.toString());
        directoryInstructionsLabel = new Label(instructionsText);
        directoryInstructionsBox.getChildren().add(directoryInstructionsLabel);
        courseDetailsCenter.getChildren().add(directoryInstructionsBox);
        
        selectedFileTemplate = new HBox();
        String selectedFileText = props.getProperty(csg_Prop.FILE_TEMPLATE_TEXT.toString());
        selectedFileLabel = new Label(selectedFileText);
        selectedFileTemplate.getChildren().add(selectedFileLabel);
        courseDetailsCenter.getChildren().add(selectedFileTemplate);
        
        selectBox = new HBox();
        String selectTemplateButtonText = props.getProperty(csg_Prop.SELECT_TEMPLATE_BUTTON.toString());
        selectTemplate = new Button(selectTemplateButtonText);
        selectBox.getChildren().add(selectTemplate);
        courseDetailsCenter.getChildren().add(selectBox);
        
        courseDetailsTable = new TableView();
        courseDetailsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        String useColumnText = props.getProperty(csg_Prop.USE_COLUMN_TEXT.toString());
        String navBarText = props.getProperty(csg_Prop.NAVBAR_COLUMN_TEXT.toString());
        String fileTitleText = props.getProperty(csg_Prop.FILE_COLUMN_TEXT.toString());
        String scriptText = props.getProperty(csg_Prop.SCRIPT_COLUMN_TEXT.toString());
        useColumn = new TableColumn(useColumnText);
        navBarColumn = new TableColumn(navBarText);
        fileColumn = new TableColumn(fileTitleText);
        scriptColumn = new TableColumn(scriptText);
        courseDetailsTable.getColumns().add(useColumn);
        courseDetailsTable.getColumns().add(navBarColumn);
        courseDetailsTable.getColumns().add(fileColumn);
        courseDetailsTable.getColumns().add(scriptColumn);
        courseDetailsTable.setMaxWidth(335);
        courseDetailsCenter.getChildren().add(courseDetailsTable);
        
         
        courseDetailsMain.getChildren().add(courseDetailsCenter);
        
        VBox courseDetailsBottom = new VBox();
        
        pageStyleBox = new HBox();
        String pageStyleText = props.getProperty(csg_Prop.PAGE_STYLE_TEXT.toString());
        pageStyleLabel = new Label(pageStyleText);        
        pageStyleBox.getChildren().add(pageStyleLabel);
        courseDetailsBottom.getChildren().add(pageStyleBox);
        
        bannerSchoolBox = new HBox();
        String bannerSchoolText = props.getProperty(csg_Prop.BANNER_SCHOOL_TEXT.toString());
        bannerSchoolLabel = new Label(bannerSchoolText);
        String changeText1 = props.getProperty(csg_Prop.CHANGE_BUTTON1_TEXT.toString());
        changeButton1 = new Button(changeText1);
        bannerSchoolBox.getChildren().add(bannerSchoolLabel);
        bannerSchoolBox.getChildren().add(changeButton1);
        courseDetailsBottom.getChildren().add(bannerSchoolBox);
        
        leftFootBox = new HBox();
        String leftFootText = props.getProperty(csg_Prop.LEFT_FOOTER_TEXT.toString());
        leftFootLabel = new Label(leftFootText);
        String changeText2 = props.getProperty(csg_Prop.CHANGE_BUTTON2_TEXT.toString());
        changeButton2 = new Button(changeText2);
        leftFootBox.getChildren().add(leftFootLabel);
        leftFootBox.getChildren().add(changeButton2);
        courseDetailsBottom.getChildren().add(leftFootBox);
        
        rightFootBox = new HBox();
        String rightFootText = props.getProperty(csg_Prop.RIGHT_FOOTER_TEXT.toString());
        rightFootLabel = new Label(rightFootText);
        String changeText3 = props.getProperty(csg_Prop.CHANGE_BUTTON3_TEXT.toString());
        changeButton3 = new Button(changeText3);
        rightFootBox.getChildren().add(rightFootLabel);
        rightFootBox.getChildren().add(changeButton3);
        courseDetailsBottom.getChildren().add(rightFootBox);
        
        style_Options = FXCollections.observableArrayList(
               props.getProperty(csg_Prop.SEA_WOLF.toString())               
        );
        styleSheet = new ComboBox(style_Options);
        styleSheetBox = new HBox();
        String styleSheetText = props.getProperty(csg_Prop.STYLESHEET_TEXT.toString());
        styleSheetLabel = new Label(styleSheetText);
        styleSheetBox.getChildren().add(styleSheetLabel);
        styleSheetBox.getChildren().add(styleSheet);
        courseDetailsBottom.getChildren().add(styleSheetBox);
        
        styleNoteBox = new HBox();
        String styleNoteText = props.getProperty(csg_Prop.STYLE_NOTE_TEXT.toString());
        styleNoteLabel = new Label(styleNoteText);
        styleNoteBox.getChildren().add(styleNoteLabel);
        courseDetailsBottom.getChildren().add(styleNoteBox);
        
        
        courseDetailsMain.getChildren().add(courseDetailsBottom);
        
        VBox recitationMain = new VBox();
        VBox recitationTop = new VBox();
        VBox recitationBottom = new VBox();
        
        recitationHeader = new HBox();
        String recitationHeaderText = props.getProperty(csg_Prop.RECITATION_HEADER_TEXT.toString());
        recitationLabel = new Label(recitationHeaderText);
        String recitationDeleteText = props.getProperty(csg_Prop.RECITATION_DELETE_BUTTON.toString());
        recitationDeleteButton = new Button(recitationDeleteText);
        recitationHeader.getChildren().add(recitationLabel);
        recitationHeader.getChildren().add(recitationDeleteButton);
        recitationTop.getChildren().add(recitationHeader);
        
        recitationTable = new TableView();
        recitationTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        String sectionColumnText = props.getProperty(csg_Prop.SECTION_COLUMN_TEXT.toString());
        String instructorColumnText = props.getProperty(csg_Prop.INSTRUCTOR_COLUMN_TEXT.toString());
        String dayTimeColumnText = props.getProperty(csg_Prop.DAYTIME_COLUMN_TEXT.toString());
        String locationColumnText = props.getProperty(csg_Prop.LOCATION_COLUMN_TEXT.toString());
        String ta1ColumnText = props.getProperty(csg_Prop.TA1_COLUMN_TEXT.toString());
        String ta2ColumnText = props.getProperty(csg_Prop.TA2_COLUMN_TEXT.toString());
        sectionColumn = new TableColumn(sectionColumnText);
        instructorColumn = new TableColumn(instructorColumnText);
        dayTimeColumn = new TableColumn(dayTimeColumnText);
        locationColumn = new TableColumn(locationColumnText);
        ta1Column = new TableColumn(ta1ColumnText);
        ta2Column = new TableColumn(ta2ColumnText);
        recitationTable.getColumns().add(sectionColumn);
        recitationTable.getColumns().add(instructorColumn);
        recitationTable.getColumns().add(dayTimeColumn);
        recitationTable.getColumns().add(locationColumn);
        recitationTable.getColumns().add(ta1Column);
        recitationTable.getColumns().add(ta2Column);
        recitationTable.setMaxWidth(480);
        recitationTop.getChildren().add(recitationTable);
                        
        recitationMain.getChildren().add(recitationTop);
        
        addEditBox = new HBox();
        String addEditText = props.getProperty(csg_Prop.ADDEDIT_HEADER_TEXT.toString());
        addEditLabel = new Label(addEditText);
        addEditBox.getChildren().add(addEditLabel);
        recitationBottom.getChildren().add(addEditBox);
        
        sectionBox = new HBox();
        String sectionText = props.getProperty(csg_Prop.SECTION_TEXT.toString());
        sectionLabel = new Label(sectionText);
        sectionBox.getChildren().add(sectionLabel);
        sectionField = new TextField();
        sectionBox.getChildren().add(sectionField);
        recitationBottom.getChildren().add(sectionBox);
        
        instructorBox = new HBox();
        String instructorText = props.getProperty(csg_Prop.INSTRUCTOR_TEXT.toString());
        instructorLabel = new Label(instructorText);
        instructorBox.getChildren().add(instructorLabel);
        instructorField = new TextField();
        instructorBox.getChildren().add(instructorField);
        recitationBottom.getChildren().add(instructorBox);
        
        dayTimeBox = new HBox();
        String dayTimeText = props.getProperty(csg_Prop.DAYTIME_TEXT.toString());
        dayTimeLabel = new Label(dayTimeText);
        dayTimeField = new TextField();
        dayTimeBox.getChildren().add(dayTimeLabel);
        dayTimeBox.getChildren().add(dayTimeField);
        recitationBottom.getChildren().add(dayTimeBox);

        locationBox = new HBox();
        String locationText = props.getProperty(csg_Prop.LOCATION_TEXT.toString());
        locationLabel = new Label(locationText);
        locationField = new TextField();
        locationBox.getChildren().add(locationLabel);
        locationBox.getChildren().add(locationField);
        recitationBottom.getChildren().add(locationBox);
        
        ta_Options = FXCollections.observableArrayList(
               props.getProperty(csg_Prop.NAME_JANE.toString()),
               props.getProperty(csg_Prop.NAME_JOE.toString())
        );
        supervisingTA = new ComboBox(ta_Options);
        ta_Options2 = FXCollections.observableArrayList(
               props.getProperty(csg_Prop.NAME_JANE.toString()),
               props.getProperty(csg_Prop.NAME_JOE.toString())
        );
        supervisingTA2 = new ComboBox(ta_Options2);
        
        supervisingTA1Box = new HBox();
        String supervisingTA1Text = props.getProperty(csg_Prop.SUPERVISING_TA1_TEXT.toString());
        supervisingTA1Label = new Label(supervisingTA1Text);
        supervisingTA1Box.getChildren().add(supervisingTA1Label);
        supervisingTA1Box.getChildren().add(supervisingTA);
        recitationBottom.getChildren().add(supervisingTA1Box);
        
        supervisingTA2Box = new HBox();
        String supervisingTA2Text = props.getProperty(csg_Prop.SUPERVISING_TA2_TEXT.toString());
        supervisingTA2Label = new Label(supervisingTA2Text);
        supervisingTA2Box.getChildren().add(supervisingTA2Label);
        supervisingTA2Box.getChildren().add(supervisingTA2);
        recitationBottom.getChildren().add(supervisingTA2Box);
        
        buttonBox = new HBox();
        String addButton2Text = props.getProperty(csg_Prop.ADDUPDATE_BUTTON2_TEXT.toString());
        addUpdate2 = new Button(addButton2Text);
        String clearButton2Text = props.getProperty(csg_Prop.CLEAR_BUTTON2_TEXT.toString());
        clear2 = new Button(clearButton2Text);
        buttonBox.getChildren().add(addUpdate2);
        buttonBox.getChildren().add(clear2);
        recitationBottom.getChildren().add(buttonBox);
        
        ScrollPane recitationScroll = new ScrollPane();
        recitationScroll.setContent(recitationMain);
                
        recitationMain.getChildren().add(recitationBottom);
        
        VBox scheduleMain = new VBox();
        VBox scheduleTop = new VBox();
        VBox scheduleBottom = new VBox();
        
        scheduleBox = new HBox();
        String scheduleText = props.getProperty(csg_Prop.SCHEDULE_HEADER_TEXT.toString());
        scheduleLabel = new Label(scheduleText);
        scheduleBox.getChildren().add(scheduleLabel);
        scheduleTop.getChildren().add(scheduleBox);
        
       calendarBoundsBox = new HBox();
       String calendarBoundsText = props.getProperty(csg_Prop.CALENDAR_BOUNDS_TEXT.toString());
       calendarBoundsLabel = new Label(calendarBoundsText);
       calendarBoundsBox.getChildren().add(calendarBoundsLabel);
       scheduleTop.getChildren().add(calendarBoundsBox);
       
       startingDayBox = new HBox();
       String startingMondayText = props.getProperty(csg_Prop.STARTING_MONDAY_TEXT.toString());
       startingMondayLabel = new Label(startingMondayText);
       String endingFridayText = props.getProperty(csg_Prop.ENDING_FRIDAY_TEXT.toString());
       endingFridayLabel = new Label(endingFridayText);
       startingDayBox.getChildren().add(startingMondayLabel);
       startingDayBox.getChildren().add(date);
       startingDayBox.getChildren().add(endingFridayLabel);
       startingDayBox.getChildren().add(date2);
       scheduleTop.getChildren().add(startingDayBox);
        
       scheduleMain.getChildren().add(scheduleTop);
           
       scheduleItemsBox = new HBox();
       String scheduleItemsString = props.getProperty(csg_Prop.SCHEDULE_ITEMS_TEXT.toString());
       scheduleItemsLabel = new Label(scheduleItemsString);
       String scheduleDeleteText = props.getProperty(csg_Prop.SCHEDULE_DELETE_BUTTON.toString());
       scheduleDeleteButton = new Button(scheduleDeleteText);
       scheduleItemsBox.getChildren().add(scheduleItemsLabel);
       scheduleItemsBox.getChildren().add(scheduleDeleteButton);
       scheduleBottom.getChildren().add(scheduleItemsBox);
       
       scheduleTable = new TableView();
       scheduleTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
       String typeText = props.getProperty(csg_Prop.TYPE_COLUMN_TEXT);
       String dateText = props.getProperty(csg_Prop.DATE_COLUMN_TEXT);
       String titleSchText = props.getProperty(csg_Prop.TITLE_COLUMN_TEXT);
       String topicText = props.getProperty(csg_Prop.TOPIC_COLUMN_TEXT);
       typeColumn = new TableColumn(typeText);
       dateColumn = new TableColumn(dateText);
       titleColumn = new TableColumn(titleSchText);
       topicColumn = new TableColumn(topicText);
       scheduleTable.getColumns().add(typeColumn);
       scheduleTable.getColumns().add(dateColumn);
       scheduleTable.getColumns().add(titleColumn);
       scheduleTable.getColumns().add(topicColumn);
       scheduleTable.setMaxWidth(320);
       scheduleBottom.getChildren().add(scheduleTable);
       
      
       addEditScheduleBox = new HBox();
       String addEditSchText = props.getProperty(csg_Prop.ADDEDIT_SCHEDULE_TEXT.toString());
       addEditLabelBox = new Label(addEditSchText);
       addEditScheduleBox.getChildren().add(addEditLabelBox);
       scheduleBottom.getChildren().add(addEditScheduleBox);
              
        type_Options = FXCollections.observableArrayList(
               props.getProperty(csg_Prop.TYPE_HOLIDAY.toString()),
               props.getProperty(csg_Prop.TYPE_BREAK.toString()),
               props.getProperty(csg_Prop.TYPE_HW.toString()),
               props.getProperty(csg_Prop.TYPE_LECTURE.toString())
        );
       typeEventBox = new ComboBox(type_Options);
       
       typeBox = new HBox();
       String typeSchText = props.getProperty(csg_Prop.TYPE_TEXT.toString());
       typeLabel = new Label(typeSchText);
       typeBox.getChildren().add(typeLabel);
       typeBox.getChildren().add(typeEventBox);
       scheduleBottom.getChildren().add(typeBox);
       
       dateBox = new HBox();
       String dateSchText = props.getProperty(csg_Prop.DATE_TEXT.toString());
       dateLabel = new Label(dateSchText);
       dateBox.getChildren().add(dateLabel);
       dateBox.getChildren().add(date3);
       scheduleBottom.getChildren().add(dateBox);
       
       timeBox = new HBox();
       String timeLabelText = props.getProperty(csg_Prop.TIME_TEXT.toString());
       timeLabel = new Label(timeLabelText);
       timeBox.getChildren().add(timeLabel);
       timeField = new TextField();
       timeBox.getChildren().add(timeField);
       scheduleBottom.getChildren().add(timeBox);
       
       titleScheduleBox = new HBox();
       String titleScheduleLabelText = props.getProperty(csg_Prop.TITLE_SCHEDULE_TEXT.toString());
       titleScheduleLabel = new Label(titleScheduleLabelText);
       titleScheduleBox.getChildren().add(titleScheduleLabel);
       titleScheduleField = new TextField();
       titleScheduleBox.getChildren().add(titleScheduleField);
       //scheduleBox.getChildren().add(titleScheduleBox);
       scheduleBottom.getChildren().add(titleScheduleBox);
              
       topicBox = new HBox();
       String titleLabelText = props.getProperty(csg_Prop.TOPIC_TEXT.toString());
       titleLabel = new Label(titleLabelText);
       topicBox.getChildren().add(titleLabel);
       topicField = new TextField();
       topicBox.getChildren().add(topicField);
       //scheduleBox.getChildren().add(topicBox);
       scheduleBottom.getChildren().add(topicBox);
       
       linkBox = new HBox();
       String linkLabelText = props.getProperty(csg_Prop.LINK_TEXT.toString());
       linkLabel = new Label(linkLabelText);
       linkBox.getChildren().add(linkLabel);
       linkField = new TextField();
       linkBox.getChildren().add(linkField);
       scheduleBottom.getChildren().add(linkBox);
       
       criteriaBox = new HBox();
       String criteriaLabelText = props.getProperty(csg_Prop.CRITERIA_TEXT.toString());
       criteriaLabel = new Label(criteriaLabelText);
       criteriaBox.getChildren().add(criteriaLabel);
       criteriaField = new TextField();
       criteriaBox.getChildren().add(criteriaField);
       scheduleBottom.getChildren().add(criteriaBox);
       
       buttonSetting = new HBox();
       String addUpdate3Text = props.getProperty(csg_Prop.ADDUPDATE_BUTTON3_TEXT.toString());
       addUpdateButton3 = new Button(addUpdate3Text);
       String clear3Text = props.getProperty(csg_Prop.CLEAR_BUTTON3_TEXT.toString());
       clearButton3 = new Button(clear3Text);
       buttonSetting.getChildren().add(addUpdateButton3);
       buttonSetting.getChildren().add(clearButton3);
       scheduleBottom.getChildren().add(buttonSetting);
       
       
       ScrollPane scheduleScroll = new ScrollPane();
       scheduleScroll.setContent(scheduleMain);
        
       scheduleMain.getChildren().add(scheduleBottom);
       
       VBox projectsMain = new VBox();
       VBox projectsTop = new VBox();
       VBox projectsBottom = new VBox();
       
       projectsHeaderBox=new HBox();
       String projectsText = props.getProperty(csg_Prop.PROJECTS_HEADER_TEXT.toString());
       projectsHeaderLabel = new Label(projectsText);
       projectsHeaderBox.getChildren().add(projectsHeaderLabel);
       projectsTop.getChildren().add(projectsHeaderBox);
       
       teamsBox = new HBox();
       String teamsText = props.getProperty(csg_Prop.TEAMS_HEADER_TEXT.toString());
       teamsLabel = new Label(teamsText);
       teamsBox.getChildren().add(teamsLabel);
       String teamsDelete = props.getProperty(csg_Prop.TEAMS_DELETE_BUTTON.toString());
       teamsDeleteButton = new Button(teamsDelete);
       teamsBox.getChildren().add(teamsDeleteButton);
       projectsTop.getChildren().add(teamsBox);
       
       teamsTable = new TableView();
       teamsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
       String nameTeamsText = props.getProperty(csg_Prop.NAME_TEAMS_COLUMN.toString());
       String colorTeamsText = props.getProperty(csg_Prop.COLOR_TEAMS_COLUMN.toString());
       String textColorTeamsText = props.getProperty(csg_Prop.TEXTCOLOR_TEAMS_COLUMN.toString());
       String linkTeamsText = props.getProperty(csg_Prop.LINK_TEAMS_COLUMN.toString());
       nameTeamsColumn = new TableColumn(nameTeamsText);
       colorTeamsColumn = new TableColumn(colorTeamsText);
       textColorTeamsColumn = new TableColumn(textColorTeamsText);
       linkTeamsColumn = new TableColumn(linkTeamsText);
       teamsTable.getColumns().add(nameTeamsColumn);
       teamsTable.getColumns().add(colorTeamsColumn);
       teamsTable.getColumns().add(textColorTeamsColumn);
       teamsTable.getColumns().add(linkTeamsColumn);
       teamsTable.setMaxWidth(420);
       projectsTop.getChildren().add(teamsTable);
       
       addEditTeamsBox = new HBox();
       String addEditTeamsText = props.getProperty(csg_Prop.ADDEDIT_TEAMS_TEXT.toString());
       addEditTeamsLabel = new Label(addEditTeamsText);
       addEditTeamsBox.getChildren().add(addEditTeamsLabel);       
       projectsTop.getChildren().add(addEditTeamsBox);
       
       nameTeamsBox = new HBox();
       String nameTeamsLabelText = props.getProperty(csg_Prop.NAME_TEAMS_TEXT.toString());
       nameTeamsLabel = new Label(nameTeamsLabelText);
       nameTeamsBox.getChildren().add(nameTeamsLabel);
       nameTeams = new TextField();
       nameTeamsBox.getChildren().add(nameTeams);
       projectsTop.getChildren().add(nameTeamsBox);
       
       colorBox = new HBox();
       String colorText = props.getProperty(csg_Prop.COLOR_TEAMS_TEXT.toString());
       colorLabel = new Label(colorText);
       colorBox.getChildren().add(colorLabel);
       colorBox.getChildren().add(color1);
       String textColorText = props.getProperty(csg_Prop.TEXTCOLOR_TEAMS_TEXT.toString());
       textColorLabel = new Label(textColorText);
       colorBox.getChildren().add(textColorLabel);
       colorBox.getChildren().add(color2);
       projectsTop.getChildren().add(colorBox);
     
       linkTeamsBox = new HBox();
       String linkTeamsLabelText = props.getProperty(csg_Prop.LINK_TEAMS_TEXT.toString());
       linkTeamsLabel = new Label(linkTeamsLabelText);
       linkTeamsBox.getChildren().add(linkTeamsLabel);
       linkTeams = new TextField();
       linkTeamsBox.getChildren().add(linkTeams);
       projectsTop.getChildren().add(linkTeamsBox);
       
       teamsButton = new HBox();
       String addUpdate4Text = props.getProperty(csg_Prop.ADDUPDATE_BUTTON4_TEXT.toString());
       addUpdate4Button = new Button(addUpdate4Text);
       String clear4Text = props.getProperty(csg_Prop.CLEAR_BUTTON4_TEXT.toString());
       clearButton4 = new Button(clear4Text);
       teamsButton.getChildren().add(addUpdate4Button);
       teamsButton.getChildren().add(clearButton4);
       
       projectsMain.getChildren().add(projectsTop);
       
       studentsHeaderBox = new HBox();
       String studentsHeaderText = props.getProperty(csg_Prop.STUDENTS_HEADER_TEXT.toString());
       studentsHeaderLabel = new Label(studentsHeaderText);
       String studentsDeleteText = props.getProperty(csg_Prop.STUDENTS_DELETE_BUTTON.toString());
       studentsDeleteButton = new Button(studentsDeleteText);
       studentsHeaderBox.getChildren().add(studentsHeaderLabel);
       studentsHeaderBox.getChildren().add(studentsDeleteButton);
       projectsBottom.getChildren().add(studentsHeaderBox);
       
       studentsTable = new TableView();
       studentsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
       String firstNameColText = props.getProperty(csg_Prop.FIRSTNAME_COLUMN.toString());
       String lastNameColText = props.getProperty(csg_Prop.LASTNAME_COLUMN.toString());
       String teamColText = props.getProperty(csg_Prop.TEAM_COLUMN.toString());
       String roleColText = props.getProperty(csg_Prop.ROLE_COLUMN.toString());
       firstNameColumn = new TableColumn(firstNameColText);
       lastNameColumn = new TableColumn(lastNameColText);
       teamColumn = new TableColumn(teamColText);
       roleColumn = new TableColumn(roleColText);
       studentsTable.getColumns().add(firstNameColumn);
       studentsTable.getColumns().add(lastNameColumn);
       studentsTable.getColumns().add(teamColumn);
       studentsTable.getColumns().add(roleColumn);
       studentsTable.setMaxWidth(320);
       projectsBottom.getChildren().add(studentsTable);
       
       addEditStudentsBox = new HBox();
       String addEditStudentsText = props.getProperty(csg_Prop.ADDEDIT_STUDENTS_TEXT.toString());
       addEditStudentsLabel = new Label(addEditStudentsText);
       addEditStudentsBox.getChildren().add(addEditStudentsLabel);
       projectsBottom.getChildren().add(addEditStudentsBox);
       
       firstNameBox = new HBox();
       String firstNameText = props.getProperty(csg_Prop.FIRSTNAME_TEXT.toString());
       firstNameLabel = new Label(firstNameText);
       firstNameBox.getChildren().add(firstNameLabel);
       firstNameField = new TextField();
       firstNameBox.getChildren().add(firstNameField);
       projectsBottom.getChildren().add(firstNameBox);
       
       lastNameBox = new HBox();
       String lastNameText = props.getProperty(csg_Prop.LASTNAME_TEXT.toString());
       lastNameLabel = new Label(lastNameText);
       lastNameBox.getChildren().add(lastNameLabel);
       lastNameField = new TextField();
       lastNameBox.getChildren().add(lastNameField);
       projectsBottom.getChildren().add(lastNameBox);
       
       team_Options = FXCollections.observableArrayList(
               props.getProperty(csg_Prop.TEAM_1.toString()),
               props.getProperty(csg_Prop.TEAM_2.toString()),
               props.getProperty(csg_Prop.TEAM_3.toString())
       );
       teams = new ComboBox(team_Options);
       
       teamsBox = new HBox();
       String teamsStudentsText = props.getProperty(csg_Prop.TEAM_TEXT.toString());
       teamsLabel = new Label(teamsStudentsText);
       teamsBox.getChildren().add(teamsLabel);
       teamsBox.getChildren().add(teams);
       projectsBottom.getChildren().add(teamsBox);
       
       roleBox = new HBox();
       String roleText = props.getProperty(csg_Prop.ROLE_TEXT.toString());
       roleLabel = new Label(roleText);
       roleBox.getChildren().add(roleLabel);
       roleField = new TextField();
       roleBox.getChildren().add(roleField);
       projectsBottom.getChildren().add(roleBox);
       
       studentsButton = new HBox();
       String addUpdate5Text = props.getProperty(csg_Prop.ADDUPDATE_BUTTON5_TEXT.toString());
       addUpdateButton5 = new Button(addUpdate5Text);
       String clear5Text = props.getProperty(csg_Prop.CLEAR_BUTTON5_TEXT.toString());
       clearButton5 = new Button(clear5Text);
       studentsButton.getChildren().add(addUpdateButton5);
       studentsButton.getChildren().add(clearButton5);
       projectsBottom.getChildren().add(studentsButton);
       
       ScrollPane projectsScroll = new ScrollPane();
       projectsScroll.setContent(projectsMain);
       
       projectsMain.getChildren().add(projectsBottom);
       
        // INIT THE HEADER ON THE LEFT
        tasHeaderBox = new HBox();
        String tasHeaderText = props.getProperty(csg_Prop.TAS_HEADER_TEXT.toString());
        tasHeaderLabel = new Label(tasHeaderText);
        tasHeaderBox.getChildren().add(tasHeaderLabel);
        
        // MAKE THE TABLE AND SETUP THE DATA MODEL
        taTable = new TableView();
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        csg_TAData data = (csg_TAData) app.getDataComponent();
        ObservableList<csg_TeachingAssistant> tableData = data.getTeachingAssistants();
        taTable.setItems(tableData);
        String nameColumnText = props.getProperty(csg_Prop.NAME_COLUMN_TEXT.toString());
        String emailColumnText = props.getProperty(csg_Prop.EMAIL_COLUMN_TEXT.toString());
        nameColumn = new TableColumn(nameColumnText);
        emailColumn = new TableColumn(emailColumnText);
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<csg_TeachingAssistant, String>("name")
        );
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<csg_TeachingAssistant, String>("email")
        );
        taTable.getColumns().add(nameColumn);
        taTable.getColumns().add(emailColumn);

        // ADD BOX FOR ADDING A TA
        String namePromptText = props.getProperty(csg_Prop.NAME_PROMPT_TEXT.toString());
        String emailPromptText = props.getProperty(csg_Prop.EMAIL_PROMPT_TEXT.toString());
        String addButtonText = props.getProperty(csg_Prop.ADD_BUTTON_TEXT.toString());
        String clearButtonText = props.getProperty(csg_Prop.CLEAR_BUTTON_TEXT.toString());
        nameTextField = new TextField();
        emailTextField = new TextField();
        nameTextField.setPromptText(namePromptText);
        emailTextField.setPromptText(emailPromptText);
        addButton = new Button(addButtonText);
        clearButton = new Button(clearButtonText);
        addBox = new HBox();
        nameTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        emailTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        addButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.1));
        clearButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.1));
        addBox.getChildren().add(nameTextField);
        addBox.getChildren().add(emailTextField);
        addBox.getChildren().add(addButton);
        addBox.getChildren().add(clearButton);

        // INIT THE HEADER ON THE RIGHT
        officeHoursHeaderBox = new HBox();
        String officeHoursGridText = props.getProperty(csg_Prop.OFFICE_HOURS_SUBHEADER.toString());
        officeHoursHeaderLabel = new Label(officeHoursGridText);
        officeHoursHeaderBox.getChildren().add(officeHoursHeaderLabel);
        
        // THESE WILL STORE PANES AND LABELS FOR OUR OFFICE HOURS GRID
        officeHoursGridPane = new GridPane();
        officeHoursGridTimeHeaderPanes = new HashMap();
        officeHoursGridTimeHeaderLabels = new HashMap();
        officeHoursGridDayHeaderPanes = new HashMap();
        officeHoursGridDayHeaderLabels = new HashMap();
        officeHoursGridTimeCellPanes = new HashMap();
        officeHoursGridTimeCellLabels = new HashMap();
        officeHoursGridTACellPanes = new HashMap();
        officeHoursGridTACellLabels = new HashMap();

        // ORGANIZE THE LEFT AND RIGHT PANES
        VBox leftPane = new VBox();
        
        leftPane.getChildren().add(tasHeaderBox);        
        leftPane.getChildren().add(taTable);        
        leftPane.getChildren().add(addBox);
        VBox rightPane = new VBox();
        rightPane.getChildren().add(officeHoursHeaderBox);
        
        time_options = FXCollections.observableArrayList(
        props.getProperty(csg_Prop.TIME_12AM.toString()),
        props.getProperty(csg_Prop.TIME_1AM.toString()),
        props.getProperty(csg_Prop.TIME_2AM.toString()),
        props.getProperty(csg_Prop.TIME_3AM.toString()),
        props.getProperty(csg_Prop.TIME_4AM.toString()),
        props.getProperty(csg_Prop.TIME_5AM.toString()),
        props.getProperty(csg_Prop.TIME_6AM.toString()),
        props.getProperty(csg_Prop.TIME_7AM.toString()),
        props.getProperty(csg_Prop.TIME_8AM.toString()),
        props.getProperty(csg_Prop.TIME_9AM.toString()),
        props.getProperty(csg_Prop.TIME_10AM.toString()),
        props.getProperty(csg_Prop.TIME_11AM.toString()),
        props.getProperty(csg_Prop.TIME_12PM.toString()),
        props.getProperty(csg_Prop.TIME_1PM.toString()),
        props.getProperty(csg_Prop.TIME_2PM.toString()),
        props.getProperty(csg_Prop.TIME_3PM.toString()),
        props.getProperty(csg_Prop.TIME_4PM.toString()),
        props.getProperty(csg_Prop.TIME_5PM.toString()),
        props.getProperty(csg_Prop.TIME_6PM.toString()),
        props.getProperty(csg_Prop.TIME_7PM.toString()),
        props.getProperty(csg_Prop.TIME_8PM.toString()),
        props.getProperty(csg_Prop.TIME_9PM.toString()),
        props.getProperty(csg_Prop.TIME_10PM.toString()),
        props.getProperty(csg_Prop.TIME_11PM.toString())
        );
        comboBox1 = new ComboBox(time_options);
        comboBox2 = new ComboBox(time_options);
        
        officeHoursHeaderBox.getChildren().add(comboBox1);
        comboBox1.setPrefHeight(42);
        comboBox1.setPrefWidth(150);
        comboBox1.getSelectionModel().select(data.getStartHour());
        /*comboBox1.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(t != null && t1 != null)
                    if(comboBox1.getSelectionModel().getSelectedIndex() != data.getStartHour())
                        controller.changeTime();
            }
        });*/
        officeHoursHeaderBox.getChildren().add(comboBox2);
        comboBox2.setPrefHeight(42);
        comboBox2.setPrefWidth(150);
        comboBox2.getSelectionModel().select(data.getEndHour());
        /*comboBox2.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(t != null && t1 != null)
                    if(comboBox2.getSelectionModel().getSelectedIndex() != data.getEndHour())
                        controller.changeTime();
            }    
        });*/
        rightPane.getChildren().add(officeHoursGridPane);
       
        // BOTH PANES WILL NOW GO IN A SPLIT PANE
        
        SplitPane sPane = new SplitPane(leftPane, new ScrollPane(rightPane));
        workspace = new BorderPane();
        
        
        // AND PUT EVERYTHING IN THE WORKSPACE
        //((BorderPane) workspace).setCenter(sPane);
        courseDetails = new Tab();
        courseDetails.setText(props.getProperty(csg_Prop.COURSE_DETAILSTAB_TEXT.toString()));
        tabPane.getTabs().add(courseDetails);
        courseDetails.setContent(courseDetailsMain);
        
        taData = new Tab();
        taData.setText(props.getProperty(csg_Prop.TA_DATATAB_TEXT.toString()));
        tabPane.getTabs().add(taData);
        taData.setContent(sPane);
        
        recitation = new Tab();
        recitation.setText(props.getProperty(csg_Prop.RECITATION_TAB_TEXT.toString()));
        tabPane.getTabs().add(recitation);
        recitation.setContent(recitationMain);
        recitation.setContent(recitationScroll);
        
        schedule = new Tab();
        schedule.setText(props.getProperty(csg_Prop.SCHEDULE_TAB_TEXT.toString()));
        tabPane.getTabs().add(schedule);
        schedule.setContent(scheduleMain);
        schedule.setContent(scheduleScroll);
        
        projects = new Tab();
        projects.setText(props.getProperty(csg_Prop.PROJECTS_TAB_TEXT.toString()));
        tabPane.getTabs().add(projects);
        projects.setContent(projectsMain);
        projects.setContent(projectsScroll);
         //mainPane.setCenter(tabPane);
         //((BorderPane)workspace).setCenterShape(tabPane);
         ((BorderPane)workspace).setCenter(tabPane);
        // MAKE SURE THE TABLE EXTENDS DOWN FAR ENOUGH
        taTable.prefHeightProperty().bind(workspace.heightProperty().multiply(1.9));

        // NOW LET'S SETUP THE EVENT HANDLING
        /*controller = new TAController(app);

        // CONTROLS FOR ADDING TAs
        nameTextField.setOnAction(e -> {
            if(!add)
                controller.changeExistTA();
            else
                controller.handleAddTA();
        });
        emailTextField.setOnAction(e -> {
            if(!add)
                controller.changeExistTA();
            else
                controller.handleAddTA();
        });
        addButton.setOnAction(e -> {
            if(!add)
                controller.changeExistTA();
            else
                controller.handleAddTA();
        });
        clearButton.setOnAction(e -> {
            addButton.setText(ADD_BUTTON_TEXT.toString());
            add = true;
            nameTextField.clear();
            emailTextField.clear();
            taTable.getSelectionModel().select(null);
        });

        taTable.setFocusTraversable(true);
        taTable.setOnKeyPressed(e -> {
            controller.handleKeyPress(e.getCode());
        });
        taTable.setOnMouseClicked(e -> {
            addButton.setText(UPDATE_BUTTON_TEXT.toString());
            add = false;
            controller.loadTAtotext();
        });
        
        workspace.setOnKeyPressed(e ->{
            if(e.isControlDown())
                if(e.getCode() == KeyCode.Z)
                    controller.Undo();
                else if(e.getCode() == KeyCode.Y)
                    controller.Redo();
        });*/
    }
    
    
    // WE'LL PROVIDE AN ACCESSOR METHOD FOR EACH VISIBLE COMPONENT
    // IN CASE A CONTROLLER OR STYLE CLASS NEEDS TO CHANGE IT
    
    
    public HBox getTAsHeaderBox() {
        return tasHeaderBox;
    }

    public Label getTAsHeaderLabel() {
        return tasHeaderLabel;
    }

    public TableView getTATable() {
        return taTable;
    }

    public HBox getAddBox() {
        return addBox;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public Button getAddButton() {
        return addButton;
    }
    
    public Button getClearButton() {
        return clearButton;
    }

    public HBox getOfficeHoursSubheaderBox() {
        return officeHoursHeaderBox;
    }

    public Label getOfficeHoursSubheaderLabel() {
        return officeHoursHeaderLabel;
    }

    public GridPane getOfficeHoursGridPane() {
        return officeHoursGridPane;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeHeaderPanes() {
        return officeHoursGridTimeHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeHeaderLabels() {
        return officeHoursGridTimeHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridDayHeaderPanes() {
        return officeHoursGridDayHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridDayHeaderLabels() {
        return officeHoursGridDayHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeCellPanes() {
        return officeHoursGridTimeCellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeCellLabels() {
        return officeHoursGridTimeCellLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTACellPanes() {
        return officeHoursGridTACellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTACellLabels() {
        return officeHoursGridTACellLabels;
    }
    
    public String getCellKey(Pane testPane) {
        for (String key : officeHoursGridTACellLabels.keySet()) {
            if (officeHoursGridTACellPanes.get(key) == testPane) {
                return key;
            }
        }
        return null;
    }

    public Label getTACellLabel(String cellKey) {
        return officeHoursGridTACellLabels.get(cellKey);
    }

    public Pane getTACellPane(String cellPane) {
        return officeHoursGridTACellPanes.get(cellPane);
    }
    
    public ComboBox getOfficeHour(boolean start){
        if(start)
            return comboBox1;
        return comboBox2;
    }

    public String buildCellKey(int col, int row) {
        return "" + col + "_" + row;
    }

    public String buildCellText(int militaryHour, String minutes) {
        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutes;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }

    @Override
    public void resetWorkspace() {
        // CLEAR OUT THE GRID PANE
        officeHoursGridPane.getChildren().clear();
        
        // AND THEN ALL THE GRID PANES AND LABELS
        officeHoursGridTimeHeaderPanes.clear();
        officeHoursGridTimeHeaderLabels.clear();
        officeHoursGridDayHeaderPanes.clear();
        officeHoursGridDayHeaderLabels.clear();
        officeHoursGridTimeCellPanes.clear();
        officeHoursGridTimeCellLabels.clear();
        officeHoursGridTACellPanes.clear();
        officeHoursGridTACellLabels.clear();
    }
    
    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        csg_TAData taData = (csg_TAData)dataComponent;
        reloadOfficeHoursGrid(taData);
    }

    public void reloadOfficeHoursGrid(csg_TAData dataComponent) {        
        ArrayList<String> gridHeaders = dataComponent.getGridHeaders();

        // ADD THE TIME HEADERS
        for (int i = 0; i < 2; i++) {
            addCellToGrid(dataComponent, officeHoursGridTimeHeaderPanes, officeHoursGridTimeHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }
        
        // THEN THE DAY OF WEEK HEADERS
        for (int i = 2; i < 7; i++) {
            addCellToGrid(dataComponent, officeHoursGridDayHeaderPanes, officeHoursGridDayHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));            
        }
        
        // THEN THE TIME AND TA CELLS
        int row = 1;
        for (int i = dataComponent.getStartHour(); i < dataComponent.getEndHour(); i++) {
            // START TIME COLUMN
            int col = 0;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(i, "00"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(i, "30"));

            // END TIME COLUMN
            col++;
            int endHour = i;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(endHour+1, "00"));
            col++;

            // AND NOW ALL THE TA TOGGLE CELLS
            while (col < 7) {
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row);
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row+1);
                col++;
            }
            row += 2;
        }

        // CONTROLS FOR TOGGLING TA OFFICE HOURS
        /*for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setFocusTraversable(true);
            p.setOnKeyPressed(e -> {
                controller.handleKeyPress(e.getCode());
            });
            p.setOnMouseClicked(e -> {
                controller.handleCellToggle((Pane) e.getSource());
            });
            p.setOnMouseExited(e -> {
                controller.handleGridCellMouseExited((Pane) e.getSource());
            });
            p.setOnMouseEntered(e -> {
                controller.handleGridCellMouseEntered((Pane) e.getSource());
            });
        }*/
        
        // AND MAKE SURE ALL THE COMPONENTS HAVE THE PROPER STYLE
        csg_Style taStyle = (csg_Style)app.getStyleComponent();
        taStyle.initOfficeHoursGridStyle();
    }
    
    public void addCellToGrid(csg_TAData dataComponent, HashMap<String, Pane> panes, HashMap<String, Label> labels, int col, int row) {       
        // MAKE THE LABEL IN A PANE
        Label cellLabel = new Label("");
        HBox cellPane = new HBox();
        cellPane.setAlignment(Pos.CENTER);
        cellPane.getChildren().add(cellLabel);

        // BUILD A KEY TO EASILY UNIQUELY IDENTIFY THE CELL
        String cellKey = dataComponent.getCellKey(col, row);
        cellPane.setId(cellKey);
        cellLabel.setId(cellKey);
        
        // NOW PUT THE CELL IN THE WORKSPACE GRID
        officeHoursGridPane.add(cellPane, col, row);
        
        // AND ALSO KEEP IN IN CASE WE NEED TO STYLIZE IT
        panes.put(cellKey, cellPane);
        labels.put(cellKey, cellLabel);
        
        // AND FINALLY, GIVE THE TEXT PROPERTY TO THE DATA MANAGER
        // SO IT CAN MANAGE ALL CHANGES
        dataComponent.setCellProperty(col, row, cellLabel.textProperty());        
    }
}

