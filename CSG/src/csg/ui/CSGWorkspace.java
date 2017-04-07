
package csg.ui;

import CSGTransactions.TransactionStack;
import csg.CSGApp;
import csg.CSGProp;
import static csg.CSGProp.COURSE_SUBHEADER;
import csg.CSGStyle;
import csg.data.CourseTemplate;
import csg.data.Recitation;
import csg.data.RecitationData;
import csg.data.ScheduleData;
import csg.data.ScheduleItem;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
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
                 
    public CSGWorkspace(CSGApp initApp){
        app = initApp;
        PropertiesManager props = PropertiesManager.getPropertiesManager();     
        TabPane courseTabPane = new TabPane();
        
        Tab courseTab = new Tab();
        courseTab.setText("Course Data");
        courseTab.setClosable(false);
        courseTabPane.getTabs().add(courseTab);
        
        Tab taTab = new Tab();
        taTab.setText("TA Data");
        taTab.setClosable(false);
        courseTabPane.getTabs().add(taTab);
        
        Tab recitationTab = new Tab();
        recitationTab.setText("Recitation Data");
        recitationTab.setClosable(false);
        courseTabPane.getTabs().add(recitationTab);
        
        Tab scheduleTab = new Tab();
        scheduleTab.setText("Schedule Data");
        scheduleTab.setClosable(false);
        courseTabPane.getTabs().add(scheduleTab);
        
        Tab projectTab = new Tab();
        projectTab.setText("Project Data");
        projectTab.setClosable(false);
        courseTabPane.getTabs().add(projectTab);
        
        workspace = new BorderPane();
        
        
        
        tasHeaderBox = new HBox();
        String tasHeaderText = props.getProperty(CSGProp.TAS_HEADER_TEXT.toString());
        tasHeaderLabel = new Label(tasHeaderText);
        tasHeaderBox.getChildren().add(tasHeaderLabel);

        // MAKE THE TABLE AND SETUP THE DATA MODEL
        taTable = new TableView();
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        TAData data = (TAData) app.getTADataComponent();
        ObservableList<TeachingAssistant> tableData = data.getTeachingAssistants();
        taTable.setItems(tableData);
        String nameColumnText = props.getProperty(CSGProp.NAME_COLUMN_TEXT.toString());
        String emailColumnText = props.getProperty(CSGProp.EMAIL_COLUMN_TEXT.toString());
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
        taClearButton = new Button("Clear");
        addBox = new HBox();
        taNameTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        taEmailTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        taAddButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        taClearButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        addBox.getChildren().add(taNameTextField);
        addBox.getChildren().add(taEmailTextField);
        addBox.getChildren().add(taAddButton);
        addBox.getChildren().add(taClearButton);
        taClearButton.setDisable(true);

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
        Button submitButton = new Button("Submit");
        comboBoxPane.getChildren().add(startTime);
        comboBoxPane.getChildren().add(endTime);
        comboBoxPane.getChildren().add(submitButton);
        startTime.setValue("Start Time");
        endTime.setValue("End Time");
        startTime.setPrefWidth(150);
        endTime.setPrefWidth(150);
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
        courseInfoPane.getChildren().add(courseInfoLabel);
        courseDetails.getChildren().add(courseInfoPane);
        
        //FirstBox First Row
        HBox courseSubNum = new HBox();
        String courseSubjectText = props.getProperty(CSGProp.SUBJECT_TEXT.toString());
        Label courseSubjectLabel = new Label(courseSubjectText);
        courseSubNum.getChildren().add(courseSubjectLabel);
        ComboBox subjectCombo = new ComboBox();
        subjectCombo.setPrefWidth(100);
        courseSubNum.getChildren().add(subjectCombo);
        
        courseSubNum.setSpacing(200);
        String courseNumberText = props.getProperty(CSGProp.NUMBER_TEXT.toString());
        Label courseNumberLabel = new Label(courseNumberText);
        courseSubNum.getChildren().add(courseNumberLabel);
        courseSubNum.setSpacing(89);
        ComboBox numCombo = new ComboBox();
        numCombo.setPrefWidth(100);
        courseSubNum.getChildren().add(numCombo);
        courseDetails.getChildren().add(courseSubNum);
        
        //FirstBox Second Row
        HBox courseSemYear = new HBox();
        String courseSemesterText = props.getProperty(CSGProp.SEMESTER_TEXT.toString());
        Label courseSemesterLabel = new Label(courseSemesterText);
        courseSemYear.getChildren().add(courseSemesterLabel);
        ComboBox semCombo = new ComboBox();
        semCombo.setPrefWidth(100);
        courseSemYear.getChildren().add(semCombo);
        
        String courseYearText = props.getProperty(CSGProp.YEAR_TEXT.toString());
        Label courseYearLabel = new Label(courseYearText);
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
        Label courseTitleLabel = new Label(courseTitleText);
        courseTitle.getChildren().add(courseTitleLabel);
        TextField titleTextField = new TextField();
        titleTextField.setMinWidth(443);
        courseTitle.getChildren().add(titleTextField);
        courseDetails.getChildren().add(courseTitle);
        
        //FirstBox Fourth Row
        HBox courseInsName = new HBox();
        courseInsName.setSpacing(15);
        String courseInsNameText = props.getProperty(CSGProp.INSTRUCTOR_NAME_TEXT.toString());
        Label courseInsNameLabel = new Label(courseInsNameText);
        courseInsName.getChildren().add(courseInsNameLabel);
        TextField insNameTextField = new TextField();
        insNameTextField.setMinWidth(443);
        courseInsName.getChildren().add(insNameTextField);
        courseDetails.getChildren().add(courseInsName);
        
        //FirstBox Fifth Row
        HBox courseInsHome = new HBox();
        courseInsHome.setSpacing(15);
        String courseInsHomeText = props.getProperty(CSGProp.INSTRUCTOR_HOME_TEXT.toString());
        Label courseInsHomeLabel = new Label(courseInsHomeText);
        courseInsHome.getChildren().add(courseInsHomeLabel);
        TextField insHomeTextField = new TextField();
        insHomeTextField.setMinWidth(443);
        courseInsHome.getChildren().add(insHomeTextField);
        courseDetails.getChildren().add(courseInsHome);
        
        //FirstBox Fifth Row
        HBox courseExportDir = new HBox();
        courseExportDir.setSpacing(15);
        courseExportDir.setPadding(new Insets(5,0,0,0));
        String courseExportDirText = props.getProperty(CSGProp.EXPORT_DIR_TEXT.toString());
        Label courseExportDirLabel = new Label(courseExportDirText);
        courseExportDir.getChildren().add(courseExportDirLabel);
        courseDetails.getChildren().add(courseExportDir);
        String changeButtonText = props.getProperty(CSGProp.CHANGE_TEXT.toString());
        Button exportChangeButton = new Button();
        exportChangeButton.setText(changeButtonText);
        exportLabel = new Label();             
        String exportDirLoc= "Select an export location";
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
        courseTemplateHeaderLabel.setText(courseTemplateText);
        courseTemplatePane.getChildren().add(courseTemplateHeaderLabel);
        
        //Directory Info label
        String courseDirInfoText = props.getProperty(CSGProp.DIR_INFO_TEXT.toString());
        Label courseDirInfoLabel = new Label();
        courseDirInfoLabel.setText(courseDirInfoText);
        courseTemplatePane.getChildren().add(courseDirInfoLabel);
        
        //Template Location Label
        String courseTemplateLocText = "Choose a template location";
        courseTemplateLocLabel = new Label();
        courseTemplateLocLabel.setText(courseTemplateLocText);
        courseTemplatePane.getChildren().add(courseTemplateLocLabel);
        
        //SelectTemplateButton
        String courseTemplateButtonString = props.getProperty(CSGProp.SELECT_TEMPLATE_DIRECTORY_TEXT.toString());
        Button courseTemplateButton = new Button(courseTemplateButtonString);
        courseTemplatePane.getChildren().add(courseTemplateButton);
        courseTemplateButton.setOnAction(e ->{
             String exportDirLocation = pickDirectory();
             courseTemplateLocLabel.setText(exportDirLocation); 
        });        
        

        //Site Pages Label
        String courseSitePageText = props.getProperty(CSGProp.SITE_PAGE_TEXT.toString());
        courseSitePageLabel = new Label();
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
        courseTemplatePane.getChildren().add(templateTable);
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
        templateTable.setMaxWidth(533);
        templateTable.setMaxHeight(170);
        
        
        //Third and last pane in the Course Tab
        pageStylePane = new VBox();
        pageStylePane.setSpacing(15);
        pageStylePane.setMaxWidth(900);
        coursePane.getChildren().add(pageStylePane);
        String pageStyleText = props.getProperty(CSGProp.PAGE_STYLE_TEXT.toString());
        pageStyleHeader = new Label(pageStyleText);
        pageStylePane.getChildren().add(pageStyleHeader);
        
        //first row in Page Style Pane
        HBox bannerStylePane = new HBox();
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
        typeColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, String>("type"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, String>("date"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, String>("title"));
        topicColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, String>("topic"));
        typeColumn.setMinWidth(150);
        dateColumn.setMinWidth(150);
        titleColumn.setMinWidth(200);
        topicColumn.setMinWidth(280);
        scheduleTable.setMaxWidth(780);
        scheduleTable.setMaxHeight(300);
        scheduleTable.setPadding(new Insets(0,0,40,0));
        
        //Add Edit Label in Schedule
        scheduleAddEdit = new Label(props.getProperty(CSGProp.ADDEDIT_TEXT.toString()));
        scheduleTablePane.getChildren().add(scheduleAddEdit);
        scheduleAddEdit.setPadding(new Insets(0,0,0,30));
        
        //Type Row in Schedule
        HBox scheduleTypePane = new HBox();
        scheduleTablePane.getChildren().add(scheduleTypePane);
        scheduleTypeLabel = new Label();
        String scheduleTypeText = props.getProperty(CSGProp.TYPE_COLON_TEXT.toString());
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
        String scheduleDateText = props.getProperty(CSGProp.DATE_COLON_TEXT.toString());
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
        String scheduleTimeText = props.getProperty(CSGProp.TIME_COLON_TEXT.toString());
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
        String scheduleTitleText = props.getProperty(CSGProp.TITLE_TEXT.toString());
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
        String scheduleTopicText = props.getProperty(CSGProp.TOPIC_COLON_TEXT.toString());
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
        String schedulelLinkText = props.getProperty(CSGProp.LINK_COLON_TEXT.toString());
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
        String schedulelCriteriaText = props.getProperty(CSGProp.CRITERIA_COLON_TEXT.toString());
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
        scheduleAddUpdateButton.setText(props.getProperty(CSGProp.ADDUPDATE_TEXT.toString()));
        Button scheduleClearButton = new Button();
        scheduleClearButton.setText(props.getProperty(CSGProp.CLEAR_TEXT.toString()));
        scheduleButtonPane.getChildren().add(scheduleAddUpdateButton);
        scheduleButtonPane.getChildren().add(scheduleClearButton);
        
        ((BorderPane) workspace).setCenter(courseTabPane);

        
        
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
    
    
    

    @Override
    public void resetWorkspace() {
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
    

   

    

