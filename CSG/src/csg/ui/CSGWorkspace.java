
package csg.ui;

import CSGTransactions.TransactionStack;
import csg.CSGApp;
import csg.CSGProp;
import static csg.CSGProp.COURSE_SUBHEADER;
import csg.CSGStyle;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import javafx.geometry.Insets;
import java.awt.Rectangle;
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
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;

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
        
        //VBOX to hold the first box in course tab
        VBox courseDetails = new VBox();
        courseDetails.setSpacing(5);
        
        //FirstBox Header
        courseTab.setContent(courseDetails);
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
        courseYearLabel.setPadding(new Insets(0,10,0,25)); 
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
    

   

    

