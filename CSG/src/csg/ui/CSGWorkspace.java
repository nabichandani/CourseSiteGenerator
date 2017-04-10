
package csg.ui;

import CSGTransactions.TransactionStack;
import csg.CSGApp;
import csg.CSGProp;
import static csg.CSGProp.COURSE_SUBHEADER;
import csg.CSGStyle;
import csg.data.CourseTemplate;
import csg.data.ProjectData;
import csg.data.Recitation;
import csg.data.RecitationData;
import csg.data.ScheduleData;
import csg.data.ScheduleItem;
import csg.data.Student;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.data.Team;
import csg.files.CSGFiles;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import static djf.settings.AppPropertyType.GRID_CONFIRMATION_MESSAGE;
import static djf.settings.AppPropertyType.GRID_CONFIRMATION_TITLE;
import static djf.settings.AppPropertyType.GRID_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.GRID_ERROR_TITLE;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.shape.Rectangle;
import java.io.File;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import sun.applet.Main;

/**
 *
 * @author Navin
 */
public class CSGWorkspace extends AppWorkspaceComponent{
    CSGApp app;
    TAData data;
    CSGControls controller;
    TransactionStack stack = new TransactionStack();
    
    // FOR THE HEADER ON THE LEFT
    HBox tasHeaderBox;
    Label tasHeaderLabel;
    
    // For creating the table of Teaching Assistant Tab.
    TableView <TeachingAssistant> taTable;
    TableColumn<TeachingAssistant, Boolean> underGradColumn;
    TableColumn<TeachingAssistant, String> nameColumn;
    TableColumn<TeachingAssistant, String> emailColumn;
    
    // Items needed to add a TA. 
    HBox addBox;
    TextField taNameTextField;
    TextField taEmailTextField;
    Button taAddButton;
    Button taClearButton;
    
    // THE HEADER ON THE RIGHT
    HBox officeHoursHeaderBox;
    Label officeHoursHeaderLabel;
    VBox comboBoxPane;
    
    //For creating the office hours grid
    GridPane officeHoursGridPane;
    HashMap<String, Pane> officeHoursGridTimeHeaderPanes;
    HashMap<String, Label> officeHoursGridTimeHeaderLabels;
    HashMap<String, Pane> officeHoursGridDayHeaderPanes;
    HashMap<String, Label> officeHoursGridDayHeaderLabels;
    HashMap<String, Pane> officeHoursGridTimeCellPanes;
    HashMap<String, Label> officeHoursGridTimeCellLabels;
    HashMap<String, Pane> officeHoursGridTACellPanes;
    HashMap<String, Label> officeHoursGridTACellLabels;
    
    Label courseInfoLabel;
    HBox courseInfoPane;
    Label courseSubjectLabel;
    Label courseNumberLabel;
    Label courseSemesterLabel;
    Label courseYearLabel;
    Label courseTitleLabel;
    Label courseInsNameLabel;
    Label courseInsHomeLabel;
    Label courseExportDirLabel;
    
    Label exportLabel;
    VBox courseDetails;
    VBox coursePane;
    VBox courseTemplatePane;
    Label courseTemplateHeaderLabel;
    Label courseTemplateLocLabel;
    Label courseSitePageLabel;
    
    
    //CourseTemplate objects that will go into the template tableview
    ObservableList<CourseTemplate> templates;
    CourseTemplate home;
    CourseTemplate syllabus;
    CourseTemplate schedule;
    CourseTemplate hws;
    CourseTemplate projects;
    
    TableView <CourseTemplate> templateTable;
    TableColumn<CourseTemplate, Boolean> useColumn;
    TableColumn<CourseTemplate, String> navColumn;
    TableColumn<CourseTemplate, String> fileNameColumn;
    TableColumn<CourseTemplate, String> scriptColumn;
    
    VBox pageStylePane;
    Label pageStyleHeader; 
    Label bannerLabel;
    Label leftFooterLabel;
    Label rightFooterLabel;
    Label styleSheetLabel;
    
    VBox recitationPane;
    HBox recitationHeaderPane;
    Label recitationHeaderLabel;
    VBox recitationAddPane;
    Label recitationAddLabel;
    
    TableView <Recitation> recitationTable;
    TableColumn<Recitation, String> sectionColumn;
    TableColumn<Recitation, String> instructorColumn;
    TableColumn<Recitation, String> dayTimeColumn;
    TableColumn<Recitation, String> locationColumn;
    TableColumn<Recitation, String> ta1Column;
    TableColumn<Recitation, String> ta2Column;
    
    Label recSection;
    Label recInstructor;
    Label recDayTime;
    Label recLocation;
    Label recTA1;
    Label recTA2;
    TextField recSectionText;
    TextField recInstructorText;
    TextField recDayTimeText;
    TextField recLocationText;
    ComboBox recTA1Combo;
    ComboBox recTA2Combo;
    
    VBox schedulePane;
    Label scheduleHeaderLabel;
    VBox calenderPane;
    Label calenderBoundariesLabel;
    HBox calenderRowPane;
    Label startMondayLabel;
    Label endFridayLabel;
    DatePicker monStartDatePicker;
    DatePicker friEndDatePicker;
    VBox scheduleTablePane;
    Label scheduleItemLabel;
    
    TableView<ScheduleItem> scheduleTable;
    TableColumn<ScheduleItem, String> typeColumn;
    TableColumn<ScheduleItem, String> dateColumn;
    TableColumn<ScheduleItem, String> titleColumn;
    TableColumn<ScheduleItem, String> topicColumn;
    
    Label scheduleAddEdit;
    Label scheduleTypeLabel;
    ComboBox scheduleTypeCombo;
    Label scheduleDateLabel;
    DatePicker scheduleDatePicker;
    Label scheduleTimeLabel;
    TextField scheduleTimeTextField; 
    Label scheduleTitleLabel;
    TextField scheduleTitleTextField; 
    Label scheduleTopicLabel;
    TextField scheduleTopicTextField; 
    Label scheduleLinkLabel;    
    TextField scheduleLinkTextField; 
    Label scheduleCriteriaLabel;
    TextField scheduleCriteriaTextField; 
    
    VBox projectPane;
    Label projectHeaderLabel;
    VBox teamsPane;
    Label teamsHeaderLabel;
    
    TableView<Team> teamsTable;
    TableColumn<Team, String> teamNameColumn;
    TableColumn<Team, String> colorColumn;
    TableColumn<Team, String> textColorColumn;
    TableColumn<Team, String> linkColumn;
    
    Label teamAddEditLabel;
    Label teamNameLabel;
    Label teamColorLabel;
    Label teamTextColorLabel;
    Label teamLinkLabel;
    
    TextField teamNameTextField;
    Circle colorCircle;
    TextField colorCircleText;
    Circle textColorCircle;
    TextField textColorCircleText;
    TextField teamLinkTextField;
    
    VBox studentPane;
    Label studentsLabel;
    Label studentHeaderLabel;
    
    TableView<Student> studentTable;
    TableColumn<Student, String> fNameColumn;
    TableColumn<Student, String> lNameColumn;
    TableColumn<Student, String> studentTeamColumn;
    TableColumn<Student, String> roleColumn;
    
    Label studentAddEditLabel;
    Label studentFNameLabel;
    Label studentLNameLabel;
    Label studentTeamLabel;
    Label studentRoleLabel;
    TextField studentFNameTextField;
    TextField studentLNameTextField;
    TextField studentTeamTextField;
    TextField studentRoleTextField;
    
    VBox projectWholePane;
                 
    public CSGWorkspace(CSGApp initApp){
        app = initApp;
        controller = new CSGControls(app);
        PropertiesManager props = PropertiesManager.getPropertiesManager();     
        TabPane courseTabPane = new TabPane();
        
        Tab courseTab = new Tab();
        courseTab.setText(props.getProperty(CSGProp.COURSE_TAB.toString()));
        courseTab.setClosable(false);
        courseTabPane.getTabs().add(courseTab);
        
        Tab taTab = new Tab();
        taTab.setText(props.getProperty(CSGProp.TA_TAB.toString()));
        taTab.setClosable(false);
        courseTabPane.getTabs().add(taTab);
        
        Tab recitationTab = new Tab();
        recitationTab.setText(props.getProperty(CSGProp.REC_TAB.toString()));
        recitationTab.setClosable(false);
        courseTabPane.getTabs().add(recitationTab);
        
        Tab scheduleTab = new Tab();
        scheduleTab.setText(props.getProperty(CSGProp.SCHEDULE_TAB.toString()));
        scheduleTab.setClosable(false);
        courseTabPane.getTabs().add(scheduleTab);
        
        Tab projectTab = new Tab();
        projectTab.setText(props.getProperty(CSGProp.PROJECT_TAB.toString()));
        projectTab.setClosable(false);
        courseTabPane.getTabs().add(projectTab);
        
        workspace = new BorderPane();
        
        
        
        tasHeaderBox = new HBox();
        String tasHeaderText = props.getProperty(CSGProp.TAS_HEADER_TEXT.toString());
        tasHeaderLabel = new Label(tasHeaderText);
        
        Rectangle taRectangle = new Rectangle(40, 40, Color.WHITE);
        taRectangle.setStroke(Color.BLACK);
        Text taText = new Text("-");
        taText.setBoundsType(TextBoundsType.VISUAL); 
        StackPane taRectPane = new StackPane();
        taRectPane.setPadding(new Insets(0,0,0, 10));
        taRectPane.getChildren().addAll(taRectangle, taText);
        
        tasHeaderBox.getChildren().add(tasHeaderLabel);
        tasHeaderBox.getChildren().add(taRectPane);

        // MAKE THE TABLE AND SETUP THE DATA MODEL
        taTable = new TableView();
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TAData data = (TAData) app.getTADataComponent();
        ObservableList<TeachingAssistant> tableData = data.getTeachingAssistants();
        taTable.setItems(tableData);
        String undergradColumnText = props.getProperty(CSGProp.UNDERGRAD_COLUMN_TEXT.toString());
        String nameColumnText = props.getProperty(CSGProp.NAME_COLUMN_TEXT.toString());
        String emailColumnText = props.getProperty(CSGProp.EMAIL_COLUMN_TEXT.toString());
        
        underGradColumn = new TableColumn(undergradColumnText);
        underGradColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, Boolean>("undergrad")
        );
        taTable.getColumns().add(underGradColumn);
        
        nameColumn = new TableColumn(nameColumnText);
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("name")
        );
        taTable.getColumns().add(nameColumn);
        emailColumn = new TableColumn(emailColumnText);
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("email")
        );
        taTable.getColumns().add(emailColumn);
        
      
        

        // ADD BOX FOR ADDING A TA
        String namePromptText = props.getProperty(CSGProp.NAME_PROMPT_TEXT.toString());
        String addButtonText = props.getProperty(CSGProp.ADD_BUTTON_TEXT.toString());
        String emailPromptText = props.getProperty(CSGProp.EMAIL_PROMPT_TEXT.toString());
        taNameTextField = new TextField();
        taNameTextField.setPromptText(namePromptText);
        taEmailTextField = new TextField();
        taEmailTextField.setPromptText(emailPromptText);
        taAddButton = new Button(addButtonText);
        taClearButton = new Button();
        taClearButton.setText(props.getProperty(CSGProp.CLEAR_TEXT.toString()));
        addBox = new HBox();
        taNameTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        taEmailTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        taAddButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        taClearButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        addBox.getChildren().add(taNameTextField);
        addBox.getChildren().add(taEmailTextField);
        addBox.getChildren().add(taAddButton);
        addBox.getChildren().add(taClearButton);

        // INIT THE HEADER ON THE RIGHT
        officeHoursHeaderBox = new HBox();
        String officeHoursGridText = props.getProperty(CSGProp.OFFICE_HOURS_SUBHEADER.toString());
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
        HBox wholeRightPane = new HBox();
        VBox rightPane = new VBox();
        rightPane.getChildren().add(officeHoursHeaderBox);
        rightPane.getChildren().add(officeHoursGridPane);
        HBox comboPane = new HBox();
        comboBoxPane = new VBox();
        comboPane.getChildren().add(comboBoxPane);
        ArrayList<String> timeStringList = new ArrayList<>();
        
        for(int i = 0; i < 24; i++){
            String time1 = "";
            int hours = i % 12;
            if(i == 0){
               time1 = "12:00 AM";
            }
            else if(i == 12){
               time1 = "12:00 PM";
            
            }
            else{
                time1 = hours +":00";
                if (i/12 == 0){
                    time1 = time1 + " AM";
                }
                else{
                  time1 = time1 + " PM";
                }
            }
             timeStringList.add(time1);
        }
        
        ComboBox startTime = new ComboBox();
        ComboBox endTime = new ComboBox();
        startTime.getItems().addAll(timeStringList);
        endTime.getItems().addAll(timeStringList);
        Button submitButton = new Button(props.getProperty
            (CSGProp.SUBMIT_TEXT.toString()));
        comboBoxPane.getChildren().add(startTime);
        comboBoxPane.getChildren().add(endTime);
        comboBoxPane.getChildren().add(submitButton);
        startTime.setValue(props.getProperty(CSGProp.START_TIME_TEXT.toString()));
        endTime.setValue(props.getProperty(CSGProp.END_TIME_TEXT.toString()));
        startTime.setPrefWidth(200);
        endTime.setPrefWidth(200);
        wholeRightPane.getChildren().add(rightPane);
        wholeRightPane.getChildren().add(comboPane);
        
        // BOTH PANES WILL NOW GO IN A SPLIT PANE
        SplitPane sPane = new SplitPane(leftPane, new ScrollPane(wholeRightPane));        
        // AND PUT EVERYTHING IN THE WORKSPACE
        // MAKE SURE THE TABLE EXTENDS DOWN FAR ENOUGH
        taTable.prefHeightProperty().bind(workspace.heightProperty().multiply(1.9));
        
        taTab.setContent(sPane);
        
        
        //CoursePane will hold all the panes in CourseTab and CourseDetails
        //holds the first VBox.
        coursePane = new VBox();
        coursePane.setSpacing(10);
        courseDetails = new VBox();
        courseDetails.setPadding(new Insets(0,0,10,0)); 
        courseDetails.setSpacing(15);
        coursePane.setAlignment(Pos.TOP_CENTER);
        courseDetails.setMaxWidth(900);
       

        //FirstBox Header
        courseTab.setContent(coursePane);
        coursePane.getChildren().add(courseDetails);
        courseInfoPane = new HBox();
        String courseSubheaderText = props.getProperty(CSGProp.COURSE_SUBHEADER.toString());
        courseInfoLabel = new Label(courseSubheaderText);
        courseInfoLabel.setPadding(new Insets(0,0,0,10));
        courseInfoPane.getChildren().add(courseInfoLabel);
        courseDetails.getChildren().add(courseInfoPane);
        
        //FirstBox First Row
        HBox courseSubNum = new HBox();
        String courseSubjectText = props.getProperty(CSGProp.SUBJECT_TEXT.toString());
        courseSubjectLabel = new Label(courseSubjectText);
        courseSubjectLabel.setPadding(new Insets(0,0,0,10));
        courseSubNum.getChildren().add(courseSubjectLabel);
        ComboBox subjectCombo = new ComboBox();
        subjectCombo.setPrefWidth(100);
        courseSubNum.getChildren().add(subjectCombo);
        
        courseSubNum.setSpacing(200);
        String courseNumberText = props.getProperty(CSGProp.NUMBER_TEXT.toString());
        courseNumberLabel = new Label(courseNumberText);
        courseSubNum.getChildren().add(courseNumberLabel);
        courseSubNum.setSpacing(89);
        ComboBox numCombo = new ComboBox();
        numCombo.setPrefWidth(100);
        courseSubNum.getChildren().add(numCombo);
        courseDetails.getChildren().add(courseSubNum);
        
        //FirstBox Second Row
        HBox courseSemYear = new HBox();
        String courseSemesterText = props.getProperty(CSGProp.SEMESTER_TEXT.toString());
        courseSemesterLabel = new Label(courseSemesterText);
        courseSemesterLabel.setPadding(new Insets(0,0,0,10));
        courseSemYear.getChildren().add(courseSemesterLabel);
        ComboBox semCombo = new ComboBox();
        semCombo.setPrefWidth(100);
        courseSemYear.getChildren().add(semCombo);
        
        String courseYearText = props.getProperty(CSGProp.YEAR_TEXT.toString());
        courseYearLabel = new Label(courseYearText);
        courseYearLabel.setPadding(new Insets(0,26,0,9)); 
        courseSemYear.getChildren().add(courseYearLabel);
        courseSemYear.setSpacing(80);
        
        ComboBox yearCombo = new ComboBox();
        yearCombo.setPrefWidth(100);
        courseSemYear.getChildren().add(yearCombo);
        courseDetails.getChildren().add(courseSemYear);
        
        //FirstBox Third Row
        HBox courseTitle = new HBox();
        courseTitle.setSpacing(106);
        String courseTitleText = props.getProperty(CSGProp.TITLE_TEXT.toString());
        courseTitleLabel = new Label(courseTitleText);
        courseTitleLabel.setPadding(new Insets(0,0,0,10));
        courseTitle.getChildren().add(courseTitleLabel);
        TextField titleTextField = new TextField();
        titleTextField.setMinWidth(443);
        courseTitle.getChildren().add(titleTextField);
        courseDetails.getChildren().add(courseTitle);
        
        //FirstBox Fourth Row
        HBox courseInsName = new HBox();
        courseInsName.setSpacing(15);
        String courseInsNameText = props.getProperty(CSGProp.INSTRUCTOR_NAME_TEXT.toString());
        courseInsNameLabel = new Label(courseInsNameText);
        courseInsNameLabel.setPadding(new Insets(0,0,0,10));
        courseInsName.getChildren().add(courseInsNameLabel);
        TextField insNameTextField = new TextField();
        insNameTextField.setMinWidth(443);
        courseInsName.getChildren().add(insNameTextField);
        courseDetails.getChildren().add(courseInsName);
        
        //FirstBox Fifth Row
        HBox courseInsHome = new HBox();
        courseInsHome.setSpacing(15);
        String courseInsHomeText = props.getProperty(CSGProp.INSTRUCTOR_HOME_TEXT.toString());
        courseInsHomeLabel = new Label(courseInsHomeText);
        courseInsHome.getChildren().add(courseInsHomeLabel);
        TextField insHomeTextField = new TextField();
        insHomeTextField.setMinWidth(443);
        courseInsHomeLabel.setPadding(new Insets(0,0,0,10));
        courseInsHome.getChildren().add(insHomeTextField);
        courseDetails.getChildren().add(courseInsHome);
        
        //FirstBox Sixth Row
        HBox courseExportDir = new HBox();
        courseExportDir.setSpacing(15);
        courseExportDir.setPadding(new Insets(5,0,0,0));
        String courseExportDirText = props.getProperty(CSGProp.EXPORT_DIR_TEXT.toString());
        courseExportDirLabel = new Label(courseExportDirText);
        courseExportDirLabel.setPadding(new Insets(0,37,0,12));
        courseExportDir.getChildren().add(courseExportDirLabel);
        courseDetails.getChildren().add(courseExportDir);
        String changeButtonText = props.getProperty(CSGProp.CHANGE_TEXT.toString());
        Button exportChangeButton = new Button();
        exportChangeButton.setText(changeButtonText);
        exportLabel = new Label();             
        String exportDirLoc= props.getProperty(CSGProp
            .EXPORT_LOCATION_TEXT.toString());
        exportLabel.setText(exportDirLoc);
        
        exportChangeButton.setOnAction(e ->{
             String exportDirLocation = pickDirectory();
             exportLabel.setText(exportDirLocation);
            
        });
        courseExportDir.getChildren().add(exportLabel);
        courseExportDir.getChildren().add(exportChangeButton);
        
        //Course Template is second box in Course Tab
        courseTemplatePane = new VBox();
        courseTemplatePane.setMaxWidth(900);
        courseTemplatePane.setSpacing(15);
        coursePane.getChildren().add(courseTemplatePane);
        
        //Header for Course Template
        String courseTemplateText = props.getProperty(CSGProp.TEMPLATE_TEXT.toString());
        courseTemplateHeaderLabel = new Label();
        courseTemplateHeaderLabel.setPadding(new Insets(0,0,0,12));
        courseTemplateHeaderLabel.setText(courseTemplateText);
        courseTemplatePane.getChildren().add(courseTemplateHeaderLabel);
        
        //Directory Info label
        String courseDirInfoText = props.getProperty(CSGProp.DIR_INFO_TEXT.toString());
        Label courseDirInfoLabel = new Label();
        courseDirInfoLabel.setPadding(new Insets(0,0,0,12));
        courseDirInfoLabel.setText(courseDirInfoText);
        courseTemplatePane.getChildren().add(courseDirInfoLabel);
        
        //Template Location Label
        String courseTemplateLocText = props.getProperty(CSGProp.TEMPLATE_LOCATION_TEXT.toString());;
        courseTemplateLocLabel = new Label();
        courseTemplateLocLabel.setPadding(new Insets(0,0,0,12));
        courseTemplateLocLabel.setText(courseTemplateLocText);
        courseTemplatePane.getChildren().add(courseTemplateLocLabel);
        
        //SelectTemplateButton
        HBox courseTemplateButtonPane = new HBox();
        courseTemplateButtonPane.setPadding(new Insets(0,0,0,12));
        String courseTemplateButtonString = props.getProperty(CSGProp.SELECT_TEMPLATE_DIRECTORY_TEXT.toString());
        Button courseTemplateButton = new Button(courseTemplateButtonString);
        courseTemplateButtonPane.getChildren().add(courseTemplateButton);
        courseTemplatePane.getChildren().add(courseTemplateButtonPane);
        courseTemplateButton.setOnAction(e ->{
             String exportDirLocation = pickDirectory();
             courseTemplateLocLabel.setText(exportDirLocation); 
        });        
        

        //Site Pages Label
        String courseSitePageText = props.getProperty(CSGProp.SITE_PAGE_TEXT.toString());
        courseSitePageLabel = new Label();
        courseSitePageLabel.setPadding(new Insets(0,0,0,12));
        courseSitePageLabel.setText(courseSitePageText);
        courseTemplatePane.getChildren().add(courseSitePageLabel);
        
        //Create and initialize the template variables        
        templates = FXCollections.observableArrayList();
        home = new CourseTemplate(true, "Home", "index.html", "Homebuilder.js");
        syllabus = new CourseTemplate(true, "Syllabus", 
            "syllabus.html", "SyllabusBuilder.js");
        schedule = new CourseTemplate(true, "Schedule",
            "schedule.html", "ScheduleBuilder.js");
        hws = new CourseTemplate(true, "HWs", "hws.html", "HWsBuilder.js");
        projects = new CourseTemplate(true, "Projects", 
            "projects.html", "ProjectsBuilder.html");
        
        templates.add(home);
        templates.add(syllabus);
        templates.add(schedule);
        templates.add(hws);
        templates.add(projects);
        
        //Creates the tableview for template items
        templateTable = new TableView();
        HBox templateTablePane = new HBox();
        templateTablePane.setPadding(new Insets(0,0,0,12));
        templateTablePane.getChildren().add(templateTable);
        courseTemplatePane.getChildren().add(templateTablePane);
        templateTable.getSelectionModel().
            setSelectionMode(SelectionMode.SINGLE);
        templateTable.setItems(templates);
        String useColumnText = props.getProperty(CSGProp.USE_TEXT.toString());
        String navbarColumnText = props.getProperty(CSGProp.NAVBAR_TEXT
            .toString());
        String fileColumnText = props.getProperty(CSGProp.FILE_TEXT.toString());
        String scriptColumnText = props.getProperty(CSGProp.SCRIPT_TEXT
            .toString());
        useColumn = new TableColumn(useColumnText);
        
        useColumn.setCellValueFactory(new PropertyValueFactory<CourseTemplate,
            Boolean>("use"));
        useColumn.setCellFactory(CheckBoxTableCell.forTableColumn(useColumn));
        
        navColumn = new TableColumn(navbarColumnText);
        navColumn.setMinWidth(150);
        navColumn.setCellValueFactory(new PropertyValueFactory<CourseTemplate, 
            String>("navbarTitle"));
        fileNameColumn = new TableColumn(fileColumnText);
        fileNameColumn.setCellValueFactory(new PropertyValueFactory<CourseTemplate, 
            String>("fileName"));
        scriptColumn = new TableColumn(scriptColumnText);
        scriptColumn.setCellValueFactory(new PropertyValueFactory<CourseTemplate, 
            String>("script"));
        templateTable.getColumns().add(useColumn);
        
        templateTable.getColumns().add(navColumn);
        templateTable.getColumns().add(fileNameColumn);
        templateTable.getColumns().add(scriptColumn);
        templateTable.setMinWidth(530);
        templateTable.setMaxHeight(170);
        
        
        //Third and last pane in the Course Tab
        pageStylePane = new VBox();
        pageStylePane.setSpacing(15);
        pageStylePane.setMaxWidth(900);
        coursePane.getChildren().add(pageStylePane);
        String pageStyleText = props.getProperty(CSGProp.PAGE_STYLE_TEXT.toString());
        pageStyleHeader = new Label(pageStyleText);
        pageStyleHeader.setPadding(new Insets(0,0,0,12));
        pageStylePane.getChildren().add(pageStyleHeader);
        
        //first row in Page Style Pane
        HBox bannerStylePane = new HBox();
        bannerStylePane.setPadding(new Insets(0,0,0,12));
        pageStylePane.getChildren().add(bannerStylePane);
        String bannerText = props.getProperty(CSGProp.BANNER_IMAGE_TEXT.toString());
        bannerLabel = new Label(bannerText);
        Button bannerChangeButton = new Button();
        ImageView bannerImage = new ImageView();
        bannerChangeButton.setText(props.getProperty(CSGProp.CHANGE_TEXT.toString()));
        bannerChangeButton.setOnAction(e ->{
             String bannerLocation = pickFile();
             //edit this
             Image newImg = new Image(Main.class.getResourceAsStream("About.png"));
             bannerImage.setImage(newImg);        
        }); 
        bannerStylePane.getChildren().add(bannerLabel);
        bannerStylePane.getChildren().add(bannerImage);
        bannerStylePane.getChildren().add(bannerChangeButton);
        
        //second row in Page Style Pane
        HBox leftFooterPane = new HBox();
        leftFooterPane.setPadding(new Insets(0,0,0,12));
        pageStylePane.getChildren().add(leftFooterPane);
        String leftFooterText = props.getProperty(CSGProp.LEFT_FOOTER_TEXT.toString());
        leftFooterLabel = new Label(leftFooterText);
        Button leftFooterChangeButton = new Button();
        ImageView leftFooterImage = new ImageView();
        leftFooterChangeButton.setText(props.getProperty(CSGProp.CHANGE_TEXT.toString()));
        leftFooterChangeButton.setOnAction(e ->{
             String bannerLocation = pickFile();
             //edit this
             Image newImg = new Image(Main.class.getResourceAsStream("About.png"));
             bannerImage.setImage(newImg);        
        }); 
        leftFooterPane.getChildren().add(leftFooterLabel);
        leftFooterPane.getChildren().add(leftFooterImage);
        leftFooterPane.getChildren().add(leftFooterChangeButton);
        
        
        //third row in Page Style Pane
        HBox rightFooterPane = new HBox();
        rightFooterPane.setPadding(new Insets(0,0,0,12));
        pageStylePane.getChildren().add(rightFooterPane);
        String rightFooterText = props.getProperty(CSGProp.RIGHT_FOOTER_TEXT.toString());
        rightFooterLabel = new Label(rightFooterText);
        Button rightFooterChangeButton = new Button();
        ImageView rightFooterImage = new ImageView();
        rightFooterChangeButton.setText(props.getProperty(CSGProp.CHANGE_TEXT.toString()));
        rightFooterChangeButton.setOnAction(e ->{
             String bannerLocation = pickFile();
             //edit this
             Image newImg = new Image(Main.class.getResourceAsStream("About.png"));
             bannerImage.setImage(newImg);        
        }); 
        rightFooterPane.getChildren().add(rightFooterLabel);
        rightFooterPane.getChildren().add(rightFooterImage);
        rightFooterPane.getChildren().add(rightFooterChangeButton);
        
        //fourth row in Page Style Pane
        HBox styleSheetPane = new HBox();
        styleSheetPane.setPadding(new Insets(0,0,0,12));
        styleSheetPane.setSpacing(15);
        pageStylePane.getChildren().add(styleSheetPane);
        String styleSheetText = props.getProperty(CSGProp.STYLESHEET_TEXT.toString());
        styleSheetLabel = new Label(styleSheetText);
        ComboBox styleSheetCombo = new ComboBox();
        styleSheetCombo.setMinWidth(200);
        styleSheetPane.getChildren().add(styleSheetLabel);
        styleSheetPane.getChildren().add(styleSheetCombo);
        
        // Note in Page Style Pane
        Label styleNote = new Label();
        styleNote.setPadding(new Insets(0,0,0,12));
        String styleSheetNoteText = props.getProperty(CSGProp.STYLESHEET_NOTE.toString());
        styleNote.setText(styleSheetNoteText);
        pageStylePane.getChildren().add(styleNote);
        
        
        //Recitation tab with recitationPane covering the whole tab.
        recitationPane = new VBox();
        recitationPane.setSpacing(10);
        recitationPane.setAlignment(Pos.TOP_CENTER);
        recitationTab.setContent(recitationPane);
        
        //Recitation Header and delete button
        recitationHeaderPane = new HBox();
        recitationHeaderPane.setPadding(new Insets(0,0,0, 554));
        recitationHeaderPane.setSpacing(15);
        recitationHeaderLabel = new Label();
        String recitationHeaderText = props.getProperty
             (CSGProp.RECITATION_TEXT.toString());
        recitationHeaderLabel.setText(recitationHeaderText);
        recitationHeaderPane.getChildren().add(recitationHeaderLabel);
        recitationPane.getChildren().add(recitationHeaderPane);
        
        //Creating rectangle in Recitation Header
        Rectangle recitationRectangle = new Rectangle(40, 40, Color.WHITE);
        recitationRectangle.setStroke(Color.BLACK);
        Text rectText = new Text("-");
        rectText.setBoundsType(TextBoundsType.VISUAL); 
        StackPane recRectPane = new StackPane();
        recRectPane.getChildren().addAll(recitationRectangle, rectText);
        recitationHeaderPane.getChildren().add(recRectPane);
        
        //eventHandler for clicking rectangle
        recitationRectangle.setOnMouseClicked(e ->{

        });
        
        
        RecitationData recData = (RecitationData) app.
            getRecitationDataComponent();
        
        //Creates the recitation TableView
        recitationTable = new TableView(); 
        recitationTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Recitation> recitationList = recData.getRecitations();
        recitationTable.setItems(recitationList);
        String sectionColumnText = props.getProperty(CSGProp.SECTION_TEXT.toString());
        String instructorColumnText = props.getProperty(CSGProp.INSTRUCTOR_TEXT.toString());
        String dayTimeColumnText = props.getProperty(CSGProp.DAYTIME_TEXT.toString());
        String locationColumnText = props.getProperty(CSGProp.LOCATION_TEXT.toString());
        String taColumnText = props.getProperty(CSGProp.TA_TEXT.toString());
        sectionColumn = new TableColumn(sectionColumnText);
        sectionColumn.setMinWidth(80);
        instructorColumn = new TableColumn(instructorColumnText);
        instructorColumn.setMinWidth(120);
        dayTimeColumn = new TableColumn(dayTimeColumnText);
        locationColumn = new TableColumn(locationColumnText);
        ta1Column = new TableColumn(taColumnText);
        ta2Column = new TableColumn(taColumnText);
        
        sectionColumn.setCellValueFactory(
             new PropertyValueFactory<Recitation, String>("section"));
        instructorColumn.setCellValueFactory(
             new PropertyValueFactory<Recitation, String>("instructor"));        
        dayTimeColumn.setCellValueFactory(
             new PropertyValueFactory<Recitation, String>("dayTime"));
        locationColumn.setCellValueFactory(
             new PropertyValueFactory<Recitation, String>("location"));
        ta1Column.setCellValueFactory(
             new PropertyValueFactory<Recitation, String>("firstTA"));
        ta2Column.setCellValueFactory(
             new PropertyValueFactory<Recitation, String>("secondTA"));
       
        recitationTable.getColumns().add(sectionColumn);
        recitationTable.getColumns().add(instructorColumn);
        recitationTable.getColumns().add(dayTimeColumn);
        recitationTable.getColumns().add(locationColumn);
        recitationTable.getColumns().add(ta1Column);
        recitationTable.getColumns().add(ta2Column);
        recitationTable.setMaxWidth(800);    
        recitationPane.getChildren().add(recitationTable);
        
        // add/edit recitation box under table
        // Recitation Add Header
        recitationAddPane = new VBox();
        recitationAddPane.setSpacing(15);
        recitationAddPane.setMaxWidth(800);
        recitationPane.getChildren().add(recitationAddPane);
        recitationAddLabel = new Label(props.getProperty
        (CSGProp.ADDEDIT_TEXT.toString()));
        recitationAddPane.getChildren().add(recitationAddLabel);
        
        //Recitation Section HBox
        HBox recSecBox = new HBox();
        recSection = new Label(props.getProperty(CSGProp.SECTION_COLON_TEXT.toString()));
        recSection.setPadding(new Insets(0,103,0,20));
        recSectionText = new TextField();
        recSectionText.setMinWidth(200);
        recSecBox.getChildren().add(recSection);
        recSecBox.getChildren().add(recSectionText);
        recitationAddPane.getChildren().add(recSecBox);
        
        //Recitation Instructor HBox
        HBox recInsBox = new HBox();
        recInstructor = new Label(props.getProperty(CSGProp.INSTRUCTOR_COLON_TEXT.toString()));
        recInstructor.setPadding(new Insets(0,77,0,19));
        recInstructorText = new TextField();
        recInstructorText.setMinWidth(200);
        recInsBox.getChildren().add(recInstructor);
        recInsBox.getChildren().add(recInstructorText);
        recitationAddPane.getChildren().add(recInsBox);        
        
        //Recitation DayTime HBox
        HBox recDayTimeBox = new HBox();
        recDayTime = new Label(props.getProperty(CSGProp.DAYTIME_COLON_TEXT.toString()));
        recDayTime.setPadding(new Insets(0,94,0,20));
        recDayTimeText = new TextField();
        recDayTimeText.setMinWidth(200);
        recDayTimeBox.getChildren().add(recDayTime);
        recDayTimeBox.getChildren().add(recDayTimeText);
        recitationAddPane.getChildren().add(recDayTimeBox);   
        
        //Recitation Location HBox
        HBox recLocationBox = new HBox();
        recLocation = new Label(props.getProperty(CSGProp.LOCATION_COLON_TEXT.toString()));
        recLocation.setPadding(new Insets(0,94,0,20));
        recLocationText = new TextField();
        recLocationText.setMinWidth(200);
        recLocationBox.getChildren().add(recLocation);
        recLocationBox.getChildren().add(recLocationText);
        recitationAddPane.getChildren().add(recLocationBox); 
        
        //Recitation TA1 HBox
        HBox recTA1Box = new HBox();
        recTA1 = new Label(props.getProperty(CSGProp.TA_COLON_TEXT.toString()));
        recTA1.setPadding(new Insets(0,40,0,20));
        recTA1Combo = new ComboBox();
        recTA1Combo.setMinWidth(200);
        recTA1Box.getChildren().add(recTA1);
        recTA1Box.getChildren().add(recTA1Combo);
        recitationAddPane.getChildren().add(recTA1Box); 

        //Recitation TA2 HBox
        HBox recTA2Box = new HBox();
        recTA2 = new Label(props.getProperty(CSGProp.TA_COLON_TEXT.toString()));
        recTA2.setPadding(new Insets(0,40,0,20));
        recTA2Combo = new ComboBox();
        recTA2Combo.setMinWidth(200);
        recTA2Box.getChildren().add(recTA2);
        recTA2Box.getChildren().add(recTA2Combo);
        recitationAddPane.getChildren().add(recTA2Box); 
        
        // Recitation Buttons 
        HBox recButtonsBox = new HBox();
        recButtonsBox.setPadding(new Insets(0,0,15,19));
        recButtonsBox.setSpacing(65);
        Button recAddButton = new Button();

        recAddButton.setText(props.getProperty(CSGProp.ADDUPDATE_TEXT
            .toString()));
        Button recClearButton = new Button();
        recClearButton.setText(props.getProperty(CSGProp.CLEAR_TEXT.toString()));
        recButtonsBox.getChildren().add(recAddButton);
        recButtonsBox.getChildren().add(recClearButton);
        recitationAddPane.getChildren().add(recButtonsBox); 
        
        //Schedule Tab Start
        schedulePane = new VBox();
        schedulePane.setSpacing(10);
        schedulePane.setAlignment(Pos.TOP_CENTER);
        scheduleTab.setContent(schedulePane);
        
        //Schedule Header Label
        HBox scheduleHeaderPane = new HBox();
        scheduleHeaderPane.setPadding(new Insets(0,0,0, 552));
        scheduleHeaderLabel = new Label();
        scheduleHeaderLabel.setText(props.getProperty
            (CSGProp.SCHEDULE_TEXT.toString()));
        scheduleHeaderPane.getChildren().add(scheduleHeaderLabel);
        schedulePane.getChildren().add(scheduleHeaderPane);
        
        //Calender pane in recitation - header
        calenderPane = new VBox();
        calenderPane.setSpacing(15);
        calenderBoundariesLabel = new Label(props.getProperty
            (CSGProp.CALENDER_BOUNDARIES_TEXT.toString()));
        calenderBoundariesLabel.setPadding(new Insets(0,0,0,30));
        calenderPane.setMaxWidth(800);
        calenderPane.getChildren().add(calenderBoundariesLabel);
        schedulePane.getChildren().add(calenderPane);
        
        //Calender pane in recitation - calender row
        calenderRowPane = new HBox();
        calenderRowPane.setPadding(new Insets(0, 0, 20, 0));
        calenderPane.getChildren().add(calenderRowPane);
        startMondayLabel = new Label();
        startMondayLabel.setPadding(new Insets(0,10,0,30));
        startMondayLabel.setText(props.getProperty
            (CSGProp.START_MONDAY_TEXT.toString()));
        calenderRowPane.getChildren().add(startMondayLabel);
        monStartDatePicker = new DatePicker();
        monStartDatePicker.setMinWidth(100);
        calenderRowPane.getChildren().add(monStartDatePicker);
        endFridayLabel = new Label();
        endFridayLabel.setPadding(new Insets(0,10,0,70));
        endFridayLabel.setText(props.getProperty
            (CSGProp.END_FRIDAY_TEXT.toString()));
        calenderRowPane.getChildren().add(endFridayLabel);
        friEndDatePicker = new DatePicker();
        friEndDatePicker.setMinWidth(100);
        calenderRowPane.getChildren().add(friEndDatePicker);
        
        //SchedulePane with table- header
        scheduleTablePane = new VBox();
        scheduleTablePane.setMaxWidth(800);
        schedulePane.getChildren().add(scheduleTablePane);
        scheduleTablePane.setSpacing(15);
        HBox scheduleTableHeaderPane = new HBox();
        scheduleItemLabel = new Label(props.getProperty
            (CSGProp.SCHEDULE_ITEM_TEXT.toString()));
        scheduleItemLabel.setPadding(new Insets(15,0,0,5));
        scheduleTablePane.getChildren().add(scheduleTableHeaderPane);
        scheduleTableHeaderPane.getChildren().add(scheduleItemLabel);
        
        Rectangle scheduleRectangle = new Rectangle(40, 30, Color.WHITE);
        scheduleRectangle.setStroke(Color.BLACK);
        Text scheduleText = new Text("-");
        scheduleText.setBoundsType(TextBoundsType.VISUAL); 
        StackPane schRectPane = new StackPane();
        schRectPane.getChildren().addAll(scheduleRectangle, scheduleText);
        schRectPane.setPadding(new Insets(0,0,0,15));
        scheduleTableHeaderPane.getChildren().add(schRectPane);
        
        scheduleRectangle.setOnMouseClicked(e ->{
            //add text here
        });
        
        //SchedulePane with table- table
        HBox scheduleTableHolderPane = new HBox();
        scheduleTable = new TableView();
        scheduleTableHolderPane.getChildren().add(scheduleTable);
        scheduleTableHolderPane.setPadding(new Insets(0,0,0,10));
        scheduleTablePane.getChildren().add(scheduleTableHolderPane);  
        scheduleTable.getSelectionModel().setSelectionMode
            (SelectionMode.SINGLE);
        ScheduleData scheduleData= (ScheduleData)app.getScheduleDataComponent();
        ObservableList<ScheduleItem> schedule = scheduleData.getSchedule();
        scheduleTable.setItems(schedule);
        String typeColumnText = props.getProperty(CSGProp.TYPE_COL_TEXT.toString());
        String dateColumnText = props.getProperty(CSGProp.DATE_COL_TEXT.toString());
        String titleColumnText = props.getProperty(CSGProp.TITLE_COL_TEXT.toString());
        String topicColumnText = props.getProperty(CSGProp.TOPIC_COL_TEXT.toString());
        typeColumn = new TableColumn(typeColumnText);
        dateColumn = new TableColumn(dateColumnText);
        titleColumn = new TableColumn(titleColumnText);
        topicColumn = new TableColumn(topicColumnText);
        scheduleTable.getColumns().add(typeColumn);
        scheduleTable.getColumns().add(dateColumn);
        scheduleTable.getColumns().add(titleColumn);
        scheduleTable.getColumns().add(topicColumn);
        typeColumn.setCellValueFactory
            (new PropertyValueFactory<ScheduleItem, String>("type"));
        dateColumn.setCellValueFactory
            (new PropertyValueFactory<ScheduleItem, String>("date"));
        titleColumn.setCellValueFactory
            (new PropertyValueFactory<ScheduleItem, String>("title"));
        topicColumn.setCellValueFactory
            (new PropertyValueFactory<ScheduleItem, String>("topic"));
        typeColumn.setMinWidth(150);
        dateColumn.setMinWidth(150);
        titleColumn.setMinWidth(200);
        topicColumn.setMinWidth(280);
        scheduleTable.setMaxWidth(780);
        scheduleTable.setMaxHeight(300);
        scheduleTable.setPadding(new Insets(0,0,40,0));
        
        //Add Edit Label in Schedule
        scheduleAddEdit = new Label(props.getProperty(CSGProp.ADDEDIT_TEXT
            .toString()));
        scheduleTablePane.getChildren().add(scheduleAddEdit);
        scheduleAddEdit.setPadding(new Insets(0,0,0,30));
        
        //Type Row in Schedule
        HBox scheduleTypePane = new HBox();
        scheduleTablePane.getChildren().add(scheduleTypePane);
        scheduleTypeLabel = new Label();
        String scheduleTypeText = props.getProperty(CSGProp.TYPE_COLON_TEXT
            .toString());
        scheduleTypeLabel.setText(scheduleTypeText);
        scheduleTypeLabel.setPadding(new Insets(0,80,0,30));
        scheduleTypeCombo = new ComboBox();
        scheduleTypeCombo.setMinWidth(250);
        scheduleTypePane.getChildren().add(scheduleTypeLabel);
        scheduleTypePane.getChildren().add(scheduleTypeCombo);
                

        //Date Row in Schedule
        HBox scheduleDatePane = new HBox();
        scheduleTablePane.getChildren().add(scheduleDatePane);
        scheduleDateLabel = new Label();
        String scheduleDateText = props.getProperty(CSGProp.DATE_COLON_TEXT
            .toString());
        scheduleDateLabel.setText(scheduleDateText);
        scheduleDateLabel.setPadding(new Insets(0,80,0,30));
        scheduleDatePicker = new DatePicker();
        scheduleDatePicker.setMinWidth(100);
        scheduleDatePane.getChildren().add(scheduleDateLabel);
        scheduleDatePane.getChildren().add(scheduleDatePicker);
        
        
        //Time Row in Schedule
        HBox scheduleTimePane = new HBox();
        scheduleTablePane.getChildren().add(scheduleTimePane);
        scheduleTimeLabel = new Label();
        String scheduleTimeText = props.getProperty(CSGProp.TIME_COLON_TEXT
            .toString());
        scheduleTimeLabel.setText(scheduleTimeText);
        scheduleTimeLabel.setPadding(new Insets(0,80,0,30));
        scheduleTimeTextField = new TextField();
        scheduleTimeTextField.setMinWidth(250);
        scheduleTimePane.getChildren().add(scheduleTimeLabel);
        scheduleTimePane.getChildren().add(scheduleTimeTextField);
        
         //Title Row in Schedule
        HBox scheduleTitlePane = new HBox();
        scheduleTablePane.getChildren().add(scheduleTitlePane);
        scheduleTitleLabel = new Label();
        String scheduleTitleText = props.getProperty(CSGProp.TITLE_TEXT
            .toString());
        scheduleTitleLabel.setText(scheduleTitleText);
        scheduleTitleLabel.setPadding(new Insets(0,71,0,30));
        scheduleTitleTextField = new TextField();
        scheduleTitleTextField.setMinWidth(500);
        scheduleTitlePane.getChildren().add(scheduleTitleLabel);
        scheduleTitlePane.getChildren().add(scheduleTitleTextField);
        
        //Topic Row in Schedule
        HBox scheduleTopicPane = new HBox();
        scheduleTablePane.getChildren().add(scheduleTopicPane);
        scheduleTopicLabel = new Label();
        String scheduleTopicText = props.getProperty(CSGProp.TOPIC_COLON_TEXT
            .toString());
        scheduleTopicLabel.setText(scheduleTopicText);
        scheduleTopicLabel.setPadding(new Insets(0,71,0,30));
        scheduleTopicTextField = new TextField();
        scheduleTopicTextField.setMinWidth(500);
        scheduleTopicPane.getChildren().add(scheduleTopicLabel);
        scheduleTopicPane.getChildren().add(scheduleTopicTextField);
        
        //Link Row in Schedule
        HBox scheduleLinkPane = new HBox();
        scheduleTablePane.getChildren().add(scheduleLinkPane);
        scheduleLinkLabel = new Label();
        String schedulelLinkText = props.getProperty(CSGProp
            .LINK_COLON_TEXT.toString());
        scheduleLinkLabel.setText(schedulelLinkText);
        scheduleLinkLabel.setPadding(new Insets(0,80,0,30));
        scheduleLinkTextField = new TextField();
        scheduleLinkTextField.setMinWidth(500);
        scheduleLinkPane.getChildren().add(scheduleLinkLabel);
        scheduleLinkPane.getChildren().add(scheduleLinkTextField);
        
        //Criteria Row in Schedule
        HBox scheduleCriteriaPane = new HBox();
        scheduleTablePane.getChildren().add(scheduleCriteriaPane);
        scheduleCriteriaLabel = new Label();
        String schedulelCriteriaText = props.getProperty(CSGProp.
            CRITERIA_COLON_TEXT.toString());
        scheduleCriteriaLabel.setText(schedulelCriteriaText);
        scheduleCriteriaLabel.setPadding(new Insets(0,44,0,30));
        scheduleCriteriaTextField = new TextField();
        scheduleCriteriaTextField.setMinWidth(500);
        scheduleCriteriaPane.getChildren().add(scheduleCriteriaLabel);
        scheduleCriteriaPane.getChildren().add(scheduleCriteriaTextField);
        
        
        //Buttons row ins Schedule
        HBox scheduleButtonPane = new HBox();
        scheduleTablePane.getChildren().add(scheduleButtonPane);
        scheduleButtonPane.setPadding(new Insets(5, 0, 10, 30));
        scheduleButtonPane.setSpacing(14);
        Button scheduleAddUpdateButton = new Button();
        scheduleAddUpdateButton.setText(props.getProperty(CSGProp.ADDUPDATE_TEXT
            .toString()));
        Button scheduleClearButton = new Button();
        scheduleClearButton.setText(props.getProperty(CSGProp.CLEAR_TEXT
            .toString()));
        scheduleButtonPane.getChildren().add(scheduleAddUpdateButton);
        scheduleButtonPane.getChildren().add(scheduleClearButton);
        
        //Start of project tab
        projectPane = new VBox();
        projectPane.setSpacing(15);
        projectPane.setAlignment(Pos.TOP_CENTER);
        projectTab.setContent(projectPane);
        HBox projectHeaderPane = new HBox();
        projectHeaderLabel = new Label(props.getProperty(CSGProp.PROJECT_TEXT
            .toString()));
        projectHeaderPane.setMaxWidth(800);
        projectHeaderPane.getChildren().add(projectHeaderLabel);
        projectPane.getChildren().add(projectHeaderPane);
        
        teamsPane = new VBox();
        teamsPane.setSpacing(15);
        
        
        //Teams Pane - header
        HBox teamsHeaderPane = new HBox();
        teamsHeaderLabel = new Label();
        teamsHeaderPane.getChildren().add(teamsHeaderLabel);
        String teamsHeaderString = props.getProperty(CSGProp.TEAMS_TEXT
            .toString());
        teamsHeaderLabel.setText(teamsHeaderString);
        teamsHeaderLabel.setPadding(new Insets(0, 10, 0, 0));
        
         //Teams Rectangle in Header
        Rectangle teamsRectangle = new Rectangle(30, 20, Color.WHITE);
        teamsRectangle.setStroke(Color.BLACK);
        Text teamsText = new Text("-");
        teamsText.setBoundsType(TextBoundsType.VISUAL); 
        StackPane teamsRectPane = new StackPane();
        teamsRectPane.getChildren().addAll(teamsRectangle, teamsText);
        teamsHeaderPane.getChildren().add(teamsRectPane);
        teamsHeaderPane.setPadding(new Insets(15, 0, 0, 5));
        
        teamsRectangle.setOnMouseClicked(e ->{
            //add text here
        });
        
        teamsPane.getChildren().add(teamsHeaderPane);
        //projectPane.getChildren().add(teamsPane);
        teamsPane.setMaxWidth(800);
        ProjectData projectData = (ProjectData) app.getProjectDataComponent();
        
        //teams tableview
        teamsTable = new TableView();
        teamsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Team> teamsData = projectData.getTeams();
        teamsTable.setItems(teamsData);
        teamsTable.setMinWidth(780);
        teamsTable.setMaxHeight(300);
        
        String teamNameColumnText = props.getProperty(CSGProp.
           NAME_COLUMN_TEXT.toString());
        String colorColumnText = props.getProperty(CSGProp
            .COLOR_TEXT.toString());
        String textColorColumnText = props.getProperty(CSGProp
            .TEXT_COLOR_TEXT.toString());
        String linkColumnText = props.getProperty(CSGProp
            .LINK_TEXT.toString());
        
        teamNameColumn = new TableColumn(teamNameColumnText);
        teamNameColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("name")
        );
        teamsTable.getColumns().add(teamNameColumn);
        colorColumn = new TableColumn(colorColumnText);
        colorColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("color")
        );
        colorColumn.setMinWidth(120);
        teamsTable.getColumns().add(colorColumn);
        textColorColumn = new TableColumn(textColorColumnText);
        textColorColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("textColor")
        );
        textColorColumn.setMinWidth(170);
        teamsTable.getColumns().add(textColorColumn);
        linkColumn = new TableColumn(linkColumnText);
        linkColumn.setCellValueFactory(
                new PropertyValueFactory<Team, String>("link")
        );
        linkColumn.setMinWidth(410);
        teamsTable.getColumns().add(linkColumn);
        HBox teamsTablePane = new HBox();
        teamsTablePane.setPadding(new Insets(0,0,0,10));
        teamsTablePane.getChildren().add(teamsTable);
        teamsPane.getChildren().add(teamsTablePane);
        
        // add/edit label in projects
        teamAddEditLabel = new Label(props.getProperty(CSGProp.
           ADDEDIT_TEXT.toString()));
        teamAddEditLabel.setPadding(new Insets(0,0,0,15));
        teamsPane.getChildren().add(teamAddEditLabel);
        
        //Team name row
        HBox teamNamePane = new HBox();
        teamsPane.getChildren().add(teamNamePane);
        teamNameLabel = new Label(props.getProperty(CSGProp.
           NAME_COLON_TEXT.toString()));
        teamNameLabel.setPadding(new Insets(0,80,0,15));
        teamNamePane.getChildren().add(teamNameLabel);
        teamNameTextField = new TextField();
        teamNameTextField.setMinWidth(200);
        teamNamePane.getChildren().add(teamNameTextField);
        
        //Team Colors row
        HBox teamColorPane = new HBox();
        teamColorLabel = new Label(props.getProperty(CSGProp.
           COLOR_COLON_TEXT.toString()));
        teamColorLabel.setPadding(new Insets(0,68,0,15));
        teamColorPane.getChildren().add(teamColorLabel);
        teamsPane.getChildren().add(teamColorPane);
        Circle colorCircle = new Circle(80, Color.WHITE);
        colorCircleText = new TextField();
        colorCircleText.setMinWidth(50);
        StackPane colorStackPane = new StackPane();
        colorStackPane.getChildren().addAll(colorCircle, colorCircleText);
        teamColorPane.getChildren().add(colorStackPane);
        colorCircleText.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER){
                String textFieldText = colorCircleText.getText();
                colorCircle.setStyle("-fx-fill:" + textFieldText);
            }
        });
       
        teamTextColorLabel = new Label(props.getProperty(CSGProp.
           TEXT_COLOR_COLON_TEXT.toString()));
        teamColorPane.getChildren().add(teamTextColorLabel);
        teamTextColorLabel.setPadding(new Insets(0,40,0,50));
        Circle colorTextCircle = new Circle(80, Color.WHITE);
        textColorCircleText = new TextField();
        textColorCircleText.setMinWidth(50);
        StackPane textColorStackPane = new StackPane();
        textColorStackPane.getChildren().addAll(colorTextCircle, textColorCircleText);
        teamColorPane.getChildren().add(textColorStackPane);
        textColorCircleText.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER){
                String textFieldText = textColorCircleText.getText();
                colorTextCircle.setStyle("-fx-fill:" + textFieldText);
            }
        });
        
        //Team link row
        HBox teamLinkPane = new HBox();
        teamsPane.getChildren().add(teamLinkPane);
        teamLinkLabel = new Label(props.getProperty(CSGProp.
           LINK_COLON_TEXT.toString()));
        teamLinkPane.getChildren().add(teamLinkLabel);
        teamLinkTextField = new TextField();
        teamLinkLabel.setPadding(new Insets(0,80,0,15));
        teamLinkPane.getChildren().add(teamLinkTextField);
        teamLinkTextField.setMinWidth(550);
        
        //Buttons row in Team Pane
        HBox teamButtonPane = new HBox();
        teamsPane.getChildren().add(teamButtonPane);
        teamButtonPane.setPadding(new Insets(0, 0, 15, 15));
        teamButtonPane.setSpacing(15);
        Button teamAddUpdateButton = new Button();
        teamAddUpdateButton.setText(props.getProperty(CSGProp.ADDUPDATE_TEXT
            .toString()));
        Button teamClearButton = new Button();
        teamClearButton.setText(props.getProperty(CSGProp.CLEAR_TEXT
            .toString()));
        teamButtonPane.getChildren().add(teamAddUpdateButton);
        teamButtonPane.getChildren().add(teamClearButton);
        
        //Students Pane start
        studentPane = new VBox();
        studentPane.setMaxWidth(800);
        studentPane.setSpacing(15);
        HBox studentHeaderPane = new HBox();
        //projectPane.getChildren().add(studentPane);
        studentHeaderLabel = new Label(props.getProperty(CSGProp.STUDENT_TEXT
            .toString()));
        studentHeaderLabel.setPadding(new Insets(0, 10, 0, 0));
        
        Rectangle studentRectangle = new Rectangle(30, 20, Color.WHITE);
        studentRectangle.setStroke(Color.BLACK);
        Text studentText = new Text("-");
        studentText.setBoundsType(TextBoundsType.VISUAL); 
        StackPane studentRectPane = new StackPane();
        studentRectPane.getChildren().addAll(studentRectangle, studentText);
        
        //eventHandler for clicking rectangle
        studentRectangle.setOnMouseClicked(e ->{

        });
        
        studentHeaderPane.getChildren().add(studentHeaderLabel);
        studentHeaderPane.getChildren().add(studentRectPane);
        studentHeaderPane.setPadding(new Insets(15, 0, 0, 5));
        studentPane.getChildren().add(studentHeaderPane);
        
        //Student Table
        studentTable = new TableView();
        studentTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<Student> students = projectData.getStudents();
        studentTable.setItems(students);
        
        String fNameColumnText = (props.getProperty(CSGProp.FIRSTNAME_TEXT
            .toString()));
        String lNameColumnText = (props.getProperty(CSGProp.LASTNAME_TEXT
            .toString()));
        String studentTeamColumnText = (props.getProperty(CSGProp.STUDENT_TEAM_TEXT
            .toString()));
        String roleColumnText = (props.getProperty(CSGProp.ROLE_TEXT
            .toString()));
        
        fNameColumn = new TableColumn(fNameColumnText);
        fNameColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String> ("firstName")
        );
        studentTable.getColumns().add(fNameColumn);
        fNameColumn.setMinWidth(170);
        
        lNameColumn = new TableColumn(lNameColumnText);
        lNameColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String> ("lastName")
        );
        studentTable.getColumns().add(lNameColumn);
        lNameColumn.setMinWidth(170);
        
        studentTeamColumn = new TableColumn(studentTeamColumnText);
        studentTeamColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String> ("team")
        );
        studentTable.getColumns().add(studentTeamColumn);
        studentTeamColumn.setMinWidth(230);
        
        roleColumn = new TableColumn(roleColumnText);
        roleColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String> ("role")
        );
        studentTable.getColumns().add(roleColumn);
        roleColumn.setMinWidth(230);
        
        HBox studentTablePane = new HBox();
        studentTablePane.setPadding(new Insets(0,0,0,10));
        studentTable.setMinWidth(780);
        studentTable.setMaxHeight(300);
        studentTablePane.getChildren().add(studentTable);
        studentPane.getChildren().add(studentTablePane);
        
        //Add edit label in students
        studentAddEditLabel = new Label(props.getProperty(CSGProp.
           ADDEDIT_TEXT.toString()));
        studentAddEditLabel.setPadding(new Insets(0,0,0,15));
        studentPane.getChildren().add(studentAddEditLabel);
        
        //first name row
        HBox studentFNamePane = new HBox();
        studentFNameLabel = new Label(props.getProperty(CSGProp
                .FIRSTNAME_COLON_TEXT.toString()));
        studentFNameTextField = new TextField();
        studentFNameTextField.setMinWidth(250);
        studentFNameLabel.setPadding(new Insets(0,27,0,15));
        studentFNamePane.getChildren().add(studentFNameLabel);
        studentFNamePane.getChildren().add(studentFNameTextField);
        studentPane.getChildren().add(studentFNamePane);
        
        //last name row
        HBox studentLNamePane = new HBox();
        studentLNameLabel = new Label(props.getProperty(CSGProp
                .LASTNAME_COLON_TEXT.toString()));
        studentLNameTextField = new TextField();
        studentLNameTextField.setMinWidth(250);
        studentLNameLabel.setPadding(new Insets(0,36,0,15));
        studentLNamePane.getChildren().add(studentLNameLabel);
        studentLNamePane.getChildren().add(studentLNameTextField);
        studentPane.getChildren().add(studentLNamePane);
        
        //student team row
        HBox studentTeamPane = new HBox();
        studentTeamLabel = new Label(props.getProperty(CSGProp
                .STUDENT_TEAM_COLON_TEXT.toString()));
        studentTeamTextField = new TextField();
        studentTeamTextField.setMinWidth(250);
        studentTeamLabel.setPadding(new Insets(0,81,0,15));
        studentTeamPane.getChildren().add(studentTeamLabel);
        studentTeamPane.getChildren().add(studentTeamTextField);
        studentPane.getChildren().add(studentTeamPane);
        
        //student team row
        HBox studentRolePane = new HBox();
        studentRoleLabel = new Label(props.getProperty(CSGProp
                .ROLE_COLON_TEXT.toString()));
        studentRoleTextField = new TextField();
        studentRoleTextField.setMinWidth(250);
        studentRoleLabel.setPadding(new Insets(0,81,0,15));
        studentRolePane.getChildren().add(studentRoleLabel);
        studentRolePane.getChildren().add(studentRoleTextField);
        studentPane.getChildren().add(studentRolePane);
        
        // Student Buttons Row
        HBox studentButtonPane = new HBox();
        studentPane.getChildren().add(studentButtonPane);
        studentButtonPane.setPadding(new Insets(0, 0, 15, 15));
        studentButtonPane.setSpacing(15);
        Button studentAddUpdateButton = new Button();
        studentAddUpdateButton.setText(props.getProperty(CSGProp.ADDUPDATE_TEXT
            .toString()));
        Button studentClearButton = new Button();
        studentClearButton.setText(props.getProperty(CSGProp.CLEAR_TEXT
            .toString()));
        studentButtonPane.getChildren().add(studentAddUpdateButton);
        studentButtonPane.getChildren().add(studentClearButton);

        
        
        projectWholePane = new VBox();
        projectWholePane.setSpacing(15);
        projectWholePane.getChildren().add(teamsPane);
        projectWholePane.getChildren().add(studentPane);
        ScrollPane projectScroll = new ScrollPane(projectWholePane);
        projectScroll.setMaxWidth(810);
        projectPane.getChildren().add(projectScroll);
        
        
        
        
        ((BorderPane) workspace).setCenter(courseTabPane);
        
        //This is now the end of the creation of GUIS and now includes
        // event handlers.
            
        startTime.setOnMouseClicked(e ->{
            int indexOfSplit = 0;
            String time = (String) endTime.getValue();
            if(endTime.getValue() == null || endTime.getValue().equals("End Time")){
                
            }
            else{
            for(int i = 0; i < timeStringList.size(); i++){
                if(timeStringList.get(i).equals(time)){
                    indexOfSplit = i;
                    break;
                }
            }
            List<String> tempList = timeStringList.subList(0, indexOfSplit);
            startTime.getItems().clear();    
            startTime.getItems().addAll(tempList);
            }
        });
        
        endTime.setOnMouseClicked(e ->{
            int indexOfSplit = 0;
            String time = (String) startTime.getValue();
            if(startTime.getValue() == null || startTime.getValue().equals("Start Time")){
                
            }
            else{
            for(int i = 0; i < timeStringList.size(); i++){
                if(timeStringList.get(i).equals(time)){
                    indexOfSplit = i;
                    break;
                }
            }
            List<String> tempList = timeStringList.subList(indexOfSplit + 1, timeStringList.size());
            endTime.getItems().clear();    
            endTime.getItems().addAll(tempList);
            }
        });
        
       submitButton.setOnAction(e ->{
           if(startTime.getValue() == null ||startTime.getValue().equals("Start Time") ||
             endTime.getValue() == null || endTime.getValue().equals("End Time")){
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	        dialog.show(props.getProperty(GRID_ERROR_TITLE), props.getProperty(GRID_ERROR_MESSAGE));
           }
           else{
               String start = (String)startTime.getValue();
               String end = (String)endTime.getValue();
               int startInt;
               int endInt;
            if(start.contains("AM")){
            if(start.equals("12:00 AM")){
                startInt = 0;
            }
            else{
                start = start.substring(0, 2);
                start = start.replace(":", "");
                startInt = Integer.parseInt(start);
            }
        }
        else{
            if(start.equals("12:00 PM")){
                startInt = 12;
            }
            else{
             start = start.substring(0, 2);
             start = start.replace(":", "");
             startInt = Integer.parseInt(start);
             startInt += 12;
            }
        }
        if(end.contains("AM")){
            if(end.equals("12:00 AM")){
                endInt = 0;
            }
            else{
                end = end.substring(0,2);
                end = end.replace(":", "");
                endInt = Integer.parseInt(end);
            }
        }
        else{
            if(end.equals("12:00 PM")){
                endInt = 12;
            }
            else{
                end = end.substring(0,2);
                end = end.replace(":", "");
                endInt = Integer.parseInt(end);
                endInt += 12;
            }
            }   
        try{
            if(startInt > data.getStartHour() || endInt < data.getEndHour() || 
            (startInt < data.getStartHour() && endInt < data.getStartHour()) || 
            (startInt > data.getEndHour() && endInt > data.getEndHour())){
               AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
               yesNoDialog.show(props.getProperty(GRID_CONFIRMATION_TITLE), props.getProperty(GRID_CONFIRMATION_MESSAGE));
               String selection = yesNoDialog.getSelection();
               if(selection.equals(AppYesNoCancelDialogSingleton.NO) ||
                       selection.equals(AppYesNoCancelDialogSingleton.CANCEL)){
               }
               else if (selection.equals(AppYesNoCancelDialogSingleton.YES)) {
                    editedGrid((String)startTime.getValue(),(String) endTime.getValue());
               }
            }
            else{
                editedGrid((String)startTime.getValue(),(String) endTime.getValue());
            }
        }catch(NullPointerException s){
        }
           } 
       });
        
        taTable.setOnMouseClicked(e -> {
            try{
                if(taTable.getSelectionModel().getSelectedItem() == null){
                    
                }
                else{
                    taAddButton.setText(props.getProperty(CSGProp.UPDATE_TA
                    .toString()));
                    TeachingAssistant clickedName = (TeachingAssistant) taTable.getFocusModel().getFocusedItem();
                    taNameTextField.setText(clickedName.getName());
                    taEmailTextField.setText(clickedName.getEmail());
                }
            }catch(NullPointerException ex){    
           }
        });
        
        taTable.setOnKeyPressed(e -> {
           if(e.getCode() == KeyCode.DELETE)
            try{
             
            String name = taTable.getFocusModel().getFocusedItem().getName();
            String email = taTable.getFocusModel().getFocusedItem().getEmail();
            HashMap<String, String> taOfficeHours = new HashMap<String,String>();
            for(String key: data.getOfficeHours().keySet()){
                String val = data.getOfficeHours().get(key).getValue();
                taOfficeHours.put(key, val);   
            }
            controller.handleDeleteTA();
            if(taAddButton.getText().equals("Update TA")){
                TeachingAssistant clickedName = (TeachingAssistant) taTable.getFocusModel().getFocusedItem();
                taNameTextField.setText(clickedName.getName());
                taEmailTextField.setText(clickedName.getEmail());
            }
            if(taTable.getItems().size() == 0){
                taAddButton.setText(props.getProperty(CSGProp.ADD_BUTTON_TEXT
                    .toString()));
                taNameTextField.clear();
                taEmailTextField.clear(); 
            }
            }
           catch(NullPointerException t){
                taAddButton.setText(props.getProperty(CSGProp.ADD_BUTTON_TEXT
                    .toString()));
                taNameTextField.clear();
                taEmailTextField.clear();
                taNameTextField.requestFocus();  
           }
        });
        
                //eventHandler for clicking rectangle
        taRectangle.setOnMouseClicked(e ->{
            try{
             
            String name = taTable.getFocusModel().getFocusedItem().getName();
            String email = taTable.getFocusModel().getFocusedItem().getEmail();
            HashMap<String, String> taOfficeHours = new HashMap<String,String>();
            for(String key: data.getOfficeHours().keySet()){
                String val = data.getOfficeHours().get(key).getValue();
                taOfficeHours.put(key, val);   
            }
            controller.handleDeleteTA();
            if(taAddButton.getText().equals("Update TA")){
                TeachingAssistant clickedName = (TeachingAssistant) taTable.getFocusModel().getFocusedItem();
                taNameTextField.setText(clickedName.getName());
                taEmailTextField.setText(clickedName.getEmail());
            }  
            }
           catch(NullPointerException t){
                taAddButton.setText(props.getProperty(CSGProp.ADD_BUTTON_TEXT
                    .toString()));
                taNameTextField.clear();
                taEmailTextField.clear();
                taNameTextField.requestFocus();  
           }
        });
        // CONTROLS FOR ADDING TAs
        
        taNameTextField.setOnAction(e -> {
            if(taAddButton.getText().equals("Update TA")){
                TeachingAssistant clickedName = (TeachingAssistant) taTable.getFocusModel().getFocusedItem();
                controller.editTA(clickedName);
                TeachingAssistant clickedName2 = (TeachingAssistant) taTable.getFocusModel().getFocusedItem();
                taNameTextField.setText(clickedName2.getName());
                taEmailTextField.setText(clickedName2.getEmail());
            }
            else{
                String name = taNameTextField.getText();
                String email = taEmailTextField.getText();
                controller.handleAddTA();
            }
        });
        
        
        taEmailTextField.setOnAction(e -> {
            if(taAddButton.getText().equals("Update TA")){
                TeachingAssistant clickedName = (TeachingAssistant) taTable.getFocusModel().getFocusedItem();
                controller.editTA(clickedName);
                TeachingAssistant clickedName2 = (TeachingAssistant) taTable.getFocusModel().getFocusedItem();
                taNameTextField.setText(clickedName2.getName());
                taEmailTextField.setText(clickedName2.getEmail());
            }
            else{
                String name = taNameTextField.getText();
                String email = taEmailTextField.getText();
                controller.handleAddTA();
            }
        });
        
        taAddButton.setOnAction(e -> {
            if(taAddButton.getText().equals("Update TA")){
                TeachingAssistant clickedName = (TeachingAssistant) taTable.getFocusModel().getFocusedItem();
                controller.editTA(clickedName);
                TeachingAssistant clickedName2 = (TeachingAssistant) taTable.getFocusModel().getFocusedItem();
                taNameTextField.setText(clickedName2.getName());
                taEmailTextField.setText(clickedName2.getEmail());
            }
            else{
                String name = taNameTextField.getText();
                String email = taEmailTextField.getText();
                controller.handleAddTA();
            }
        });
        
        taClearButton.setOnAction(e -> {
            taAddButton.setText(props.getProperty(CSGProp.ADD_BUTTON_TEXT
                    .toString()));
            taNameTextField.clear();
            taEmailTextField.clear();
            taNameTextField.requestFocus(); 

    });
    }
    
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

    public VBox getCourseDetails() {
        return courseDetails;
    }

    public VBox getCourseTemplatePane() {
        return courseTemplatePane;
    }

    public Label getCourseTemplateLocLabel() {
        return courseTemplateLocLabel;
    }

    public Label getCourseSubjectLabel() {
        return courseSubjectLabel;
    }

    public Label getCourseNumberLabel() {
        return courseNumberLabel;
    }

    public Label getCourseSemesterLabel() {
        return courseSemesterLabel;
    }

    public Label getCourseYearLabel() {
        return courseYearLabel;
    }

    public Label getCourseTitleLabel() {
        return courseTitleLabel;
    }

    public Label getCourseInsNameLabel() {
        return courseInsNameLabel;
    }

    public Label getCourseInsHomeLabel() {
        return courseInsHomeLabel;
    }

    public Label getCourseExportDirLabel() {
        return courseExportDirLabel;
    }
    
    
    
    public TextField getNameTextField() {
        return taNameTextField;
    }
    
    public TextField getEmailTextField() {
        return taEmailTextField;
    }

    public Button getAddButton() {
        return taAddButton;
    }
    public Button getClearButton(){
        return taClearButton;
    }

    public VBox getCoursePane() {
        return coursePane;
    }

    public Label getCourseTemplateHeaderLabel() {
        return courseTemplateHeaderLabel;
    }

    public Label getCourseSitePageLabel() {
        return courseSitePageLabel;
    }

    public Label getBannerLabel() {
        return bannerLabel;
    }

    public Label getLeftFooterLabel() {
        return leftFooterLabel;
    }

    public Label getRightFooterLabel() {
        return rightFooterLabel;
    }

    public VBox getSchedulePane() {
        return schedulePane;
    }

    public Label getScheduleHeaderLabel() {
        return scheduleHeaderLabel;
    }

    public VBox getCalenderPane() {
        return calenderPane;
    }

    public Label getCalenderBoundariesLabel() {
        return calenderBoundariesLabel;
    }
    
    public Label getExportLabel() {
        return exportLabel;
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

    public String buildCellKey(int col, int row) {
        return "" + col + "_" + row;
    }
    
    public TransactionStack getTransactionStack(){
        return stack;
    }
    
    public Label getCourseInfoLabel(){
        return courseInfoLabel;
    }
    public HBox getCourseInfoPane(){
        return courseInfoPane;
    }

    public TableColumn<TeachingAssistant, String> getNameColumn() {
        return nameColumn;
    }

    public TableColumn<TeachingAssistant, String> getEmailColumn() {
        return emailColumn;
    }

    public TableColumn<CourseTemplate, Boolean> getUseColumn() {
        return useColumn;
    }

    public TableColumn<CourseTemplate, String> getNavColumn() {
        return navColumn;
    }

    public TableColumn<CourseTemplate, String> getFileNameColumn() {
        return fileNameColumn;
    }

    public TableColumn<CourseTemplate, String> getScriptColumn() {
        return scriptColumn;
    }

    public TableColumn<Recitation, String> getInstructorColumn() {
        return instructorColumn;
    }

    public TableColumn<Recitation, String> getDayTimeColumn() {
        return dayTimeColumn;
    }

    public TableColumn<Recitation, String> getLocationColumn() {
        return locationColumn;
    }

    public TableColumn<Recitation, String> getTa1Column() {
        return ta1Column;
    }

    public TableColumn<Recitation, String> getTa2Column() {
        return ta2Column;
    }

    public TableColumn<ScheduleItem, String> getTypeColumn() {
        return typeColumn;
    }

    public TableColumn<ScheduleItem, String> getDateColumn() {
        return dateColumn;
    }

    public TableColumn<ScheduleItem, String> getTitleColumn() {
        return titleColumn;
    }

    public TableColumn<ScheduleItem, String> getTopicColumn() {
        return topicColumn;
    }

    public TableColumn<Team, String> getTeamNameColumn() {
        return teamNameColumn;
    }

    public TableColumn<Team, String> getColorColumn() {
        return colorColumn;
    }

    public TableColumn<Team, String> getTextColorColumn() {
        return textColorColumn;
    }

    public TableColumn<Team, String> getLinkColumn() {
        return linkColumn;
    }

    public TableColumn<Recitation, String> getSectionColumn() {
        return sectionColumn;
    }

    public VBox getProjectWholePane() {
        return projectWholePane;
    }
    
    
    
    

    public String pickDirectory(){
        DirectoryChooser dc = new DirectoryChooser();
        File file = dc.showDialog(app.getGUI().getWindow());
        String location = file.getPath();
        return location;
    }
    public String pickFile(){
        FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(app.getGUI().getWindow());
        String location = file.getPath();
        return location;
    }

    public VBox getPageStylePane() {
        return pageStylePane;
    }

    public Label getPageStyleHeader() {
        return pageStyleHeader;
    }

    public Label getStyleSheetLabel() {
        return styleSheetLabel;
    }

    public VBox getRecitationPane() {
        return recitationPane;
    }

    public Label getRecitationHeaderLabel() {
        return recitationHeaderLabel;
    }

    public VBox getRecitationAddPane() {
        return recitationAddPane;
    }

    public Label getRecitationAddLabel() {
        return recitationAddLabel;
    }

    public Label getRecSection() {
        return recSection;
    }

    public Label getRecInstructor() {
        return recInstructor;
    }

    public Label getRecDayTime() {
        return recDayTime;
    }

    public Label getRecLocation() {
        return recLocation;
    }

    public Label getRecTA1() {
        return recTA1;
    }

    public Label getRecTA2() {
        return recTA2;
    }

    public Label getStartMondayLabel() {
        return startMondayLabel;
    }

    public Label getEndFridayLabel() {
        return endFridayLabel;
    }

    public VBox getScheduleTablePane() {
        return scheduleTablePane;
    }

    public Label getScheduleItemLabel() {
        return scheduleItemLabel;
    }

    public Label getScheduleAddEdit() {
        return scheduleAddEdit;
    }

    public Label getScheduleTypeLabel() {
        return scheduleTypeLabel;
    }

    public Label getScheduleDateLabel() {
        return scheduleDateLabel;
    }

    public Label getScheduleTimeLabel() {
        return scheduleTimeLabel;
    }

    public Label getScheduleTitleLabel() {
        return scheduleTitleLabel;
    }

    public Label getScheduleTopicLabel() {
        return scheduleTopicLabel;
    }

    public Label getScheduleLinkLabel() {
        return scheduleLinkLabel;
    }

    public Label getScheduleCriteriaLabel() {
        return scheduleCriteriaLabel;
    }

    public VBox getProjectPane() {
        return projectPane;
    }

    public Label getProjectHeaderLabel() {
        return projectHeaderLabel;
    }

    public VBox getTeamsPane() {
        return teamsPane;
    }

    public Label getTeamsHeaderLabel() {
        return teamsHeaderLabel;
    }

    public Label getTeamAddEditLabel() {
        return teamAddEditLabel;
    }

    public Label getTeamNameLabel() {
        return teamNameLabel;
    }

    public Label getTeamColorLabel() {
        return teamColorLabel;
    }

    public Label getTeamTextColorLabel() {
        return teamTextColorLabel;
    }

    public Label getTeamLinkLabel() {
        return teamLinkLabel;
    }

    public VBox getStudentPane() {
        return studentPane;
    }

    public Label getStudentHeaderLabel() {
        return studentHeaderLabel;
    }

    public Label getStudentAddEditLabel() {
        return studentAddEditLabel;
    }

    public Label getStudentFNameLabel() {
        return studentFNameLabel;
    }

    public Label getStudentLNameLabel() {
        return studentLNameLabel;
    }

    public Label getStudentTeamLabel() {
        return studentTeamLabel;
    }

    public Label getStudentRoleLabel() {
        return studentRoleLabel;
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
        TAData taData = (TAData)dataComponent;
        reloadOfficeHoursGrid(taData);
    }
    
    public void editedGrid(String start, String end){
        int startInt;
        int endInt;
        TAData data = (TAData) app.getTADataComponent();
        CSGFiles file = (CSGFiles) app.getFileComponent();
        if(start.contains("AM")){
            if(start.equals("12:00 AM")){
                startInt = 0;
            }
            else{
                start = start.substring(0, 2);
                start = start.replace(":", "");
                startInt = Integer.parseInt(start);
            }
        }
        else{
            if(start.equals("12:00 PM")){
                startInt = 12;
            }
            else{
             start = start.substring(0, 2);
             start = start.replace(":", "");
             startInt = Integer.parseInt(start);
             startInt += 12;
            }
        }
        if(end.contains("AM")){
            if(end.equals("12:00 AM")){
                endInt = 0;
            }
            else{
                end = end.substring(0,2);
                end = end.replace(":", "");
                endInt = Integer.parseInt(end);
            }
        }
        else{
            if(end.equals("12:00 PM")){
                endInt = 12;
            }
            else{
                end = end.substring(0,2);
                end = end.replace(":", "");
                endInt = Integer.parseInt(end);
                endInt += 12;
            }
            }       
        HashMap<String, StringProperty> taOfficeHours = new HashMap<String,StringProperty>(data.getOfficeHours());
        for(String key: data.getOfficeHours().keySet()){
            taOfficeHours.put(key, data.getOfficeHours().get(key));   
        }
        int prevStartIndex = data.getStartHour() * 2;
        int prevStart = data.getStartHour();
        int prevEnd = data.getEndHour();
        resetWorkspace(); 
        data.initOfficeHours(startInt, endInt);
        for(String key: taOfficeHours.keySet()){
            String[] keyArr = key.split("_");
            int hour = Integer.parseInt(keyArr[1]);
            int startIndex = 2 * startInt;
            int endIndex = 2 * endInt;
            hour = (hour - 1) + prevStartIndex;
            if(hour == prevStartIndex - 1){   
            }
            else if(hour >= startIndex && hour < endIndex){
                hour = hour - startIndex + 1;
                String newKey = keyArr[0] + "_" + hour;
                data.addToGrid(newKey, taOfficeHours.get(key).getValue());
            }
        }
    }

        public void reloadOfficeHoursGrid(TAData dataComponent) {        
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
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setOnMouseClicked(e -> {
                controller.handleCellToggle((Pane) e.getSource());
            });
            p.setOnMouseEntered((MouseEvent e) -> {
                p.setStyle("-fx-border-color: yellow;");
                String hashkey = getCellKey(p);
                String[] rowCol = hashkey.split("_");
                int rowOfP = Integer.parseInt(rowCol[1]);
                int colOfP = Integer.parseInt(rowCol[0]);
                for(Pane rowPaneCheck : officeHoursGridTACellPanes.values()){
                    String hashkey2 = getCellKey(rowPaneCheck);
                    String[] rowColNew = hashkey2.split("_");
                    int rowOfNewPane = Integer.parseInt(rowColNew[1]);
                    int colOfNewPane = Integer.parseInt(rowColNew[0]);
                    if(rowOfNewPane < rowOfP && colOfNewPane == colOfP){
                        rowPaneCheck.setStyle("-fx-border-color: rgb(255, 255, 179);");
                    }
                }
                for(Pane colPaneCheck : officeHoursGridTACellPanes.values()){
                    String hashkey2 = getCellKey(colPaneCheck);
                    String[] rowColNew = hashkey2.split("_");
                    int rowOfNewPane = Integer.parseInt(rowColNew[1]);
                    int colOfNewPane = Integer.parseInt(rowColNew[0]);
                    if(rowOfNewPane == rowOfP && colOfNewPane < colOfP){
                        colPaneCheck.setStyle("-fx-border-color: rgb(255, 255, 179);");
                    }
                }
            });
            p.setOnMouseExited((MouseEvent e) -> { 
                for(Pane clearPane : officeHoursGridTACellPanes.values()){
                    clearPane.setStyle("-fx-border-color: black;");
                }
            });
            }
        
        // AND MAKE SURE ALL THE COMPONENTS HAVE THE PROPER STYLE
        CSGStyle taStyle = (CSGStyle)app.getStyleComponent();
        taStyle.initOfficeHoursGridStyle();
    }
    
    public void addCellToGrid(TAData dataComponent, HashMap<String, Pane> panes, HashMap<String, Label> labels, int col, int row) {       
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
    
        public String buildCellText(int militaryHour, String minutes) {
        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        else if(hour == 0){
            hour = 12;
        }
        String cellText = "" + hour + ":" + minutes;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }
}
    

   

    

