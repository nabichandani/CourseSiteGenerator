
package csg.ui;

import CSGTransactions.TransactionStack;
import csg.CSGApp;
import csg.data.TeachingAssistant;
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
import javafx.scene.control.Tab;
import properties_manager.PropertiesManager;

/**
 *
 * @author Navin
 */
public class CSGWorkspace {
    CSGApp app;
    CSGControls controller;
    TransactionStack stack = new TransactionStack();
    
    // Creates the header above the Office hours grid and TA tableview. 
    HBox taHeaderBox;
    Label taHeaderLabel;
    
    // For creating the table of Teaching Assistant Tab.
    TableView <TeachingAssistant> taTable;
    TableColumn<TeachingAssistant, Boolean> underGradColumn;
    TableColumn<TeachingAssistant, String> nameColumn;
    TableColumn<TeachingAssistant, String> emailColumn;
    
    // Items needed to add a TA. 
    TextField taNameTextField;
    TextField taEmailTextField;
    Button taAddButton;
    Button taClearButton;
    
    //For creating the office hours grid
    GridPane OfficeHoursGridPane;
    HashMap<String, Pane> officeHoursGridTimeHeaderPanes;
    HashMap<String, Label> officeHoursGridTimeHeaderLabels;
    HashMap<String, Pane> officeHoursGridDayHeaderPanes;
    HashMap<String, Label> officeHoursGridDayHeaderLabels;
    HashMap<String, Pane> officeHoursGridCellPanes;
    HashMap<String, Label> officeHoursGridCellLabels;

    //For creating the combo boxes to adjust the timings of the grid.
    ComboBox StartTimeComboBox;
    ComboBox endTimeComboBox;
    
    
    public CSGWorkspace(CSGApp initApp){
        app = initApp;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        TabPane taTabPane = new TabPane();
        Tab taTab = new Tab();
        taTab.setText("TA Data");
        taTabPane.getTabs().add(taTab);
        
    }
    
}
