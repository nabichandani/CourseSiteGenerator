/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg;

import csg.data.TeachingAssistant;
import csg.ui.CSGWorkspace;
import djf.AppTemplate;
import djf.components.AppStyleComponent;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Navin
 */
public class CSGStyle extends AppStyleComponent{
    
     // FIRST WE SHOULD DECLARE ALL OF THE STYLE TYPES WE PLAN TO USE
    
    // WE'LL USE THIS FOR ORGANIZING LEFT AND RIGHT CONTROLS
    public static String CLASS_PLAIN_PANE = "plain_pane";
    
    // THESE ARE THE HEADERS FOR EACH SIDE
    public static String CLASS_HEADER_PANE = "header_pane";
    public static String CLASS_HEADER_LABEL = "header_label";

    // ON THE LEFT WE HAVE THE TA ENTRY
    public static String CLASS_TA_TABLE = "ta_table";
    public static String CLASS_TA_TABLE_COLUMN_HEADER = "ta_table_column_header";
    public static String CLASS_ADD_TA_PANE = "add_ta_pane";
    public static String CLASS_ADD_TA_TEXT_FIELD = "add_ta_text_field";
    public static String CLASS_ADD_TA_BUTTON = "add_ta_button";
    public static String CLASS_CLEAR_BUTTON = "clear_button";

    // ON THE RIGHT WE HAVE THE OFFICE HOURS GRID
    public static String CLASS_OFFICE_HOURS_GRID = "office_hours_grid";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE = "office_hours_grid_time_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL = "office_hours_grid_time_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE = "office_hours_grid_day_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL = "office_hours_grid_day_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE = "office_hours_grid_time_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL = "office_hours_grid_time_cell_label";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE = "office_hours_grid_ta_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL = "office_hours_grid_ta_cell_label";
    
    public static String CLASS_COURSE_INFO_PANE = "course_info_pane";
    public static String CLASS_COURSE_INFO_LABEL = "course_info_header";
    public static String CLASS_COURSE_FIRST_BOX = "course_details_pane";
    public static String CLASS_COURSE_PANE = "course_pane";
    public static String CLASS_COURSE_EXPORT_LABEL = "export_label";
    public static String CLASS_COURSE_TEMPLATE_PANE = "course_template_pane";
    public static String CLASS_COURSE_TEMPLATE_HEADER = "course_template_header";
    public static String CLASS_COURSE_TEMPLATE_LOCATION = "course_template_location";
    public static String CLASS_COURSE_SITE_PAGE = "course_site_page_label";
    public static String CLASS_PAGE_STYLE_PANE = "page_style_pane";
    public static String CLASS_PAGE_STYLE_LABEL = "page_style_label";
    public static String CLASS_BANNER_LABEL = "style_banner_label";
    public static String CLASS_LEFT_FOOTER_LABEL = "style_left_footer_label";
    public static String CLASS_RIGHT_FOOTER_LABEL = "style_right_footer_label";
    public static String CLASS_STYLE_NOTE_LABEL = "page_style_note_label";
    
    public static String CLASS_RECITATION_PANE = "recitation_pane";
    public static String CLASS_RECITATION_HEADER = "tab_header";
    public static String CLASS_RECITATION_ADD_LABEL = "recitation_addedit_label";
    public static String CLASS_RECITATION_ADD_PANE = "recitation_add_pane";
    
    public static String CLASS_RECITATION_SECTION_LABEL = "recitation_section_label";
    public static String CLASS_RECITATION_INSTRUCTOR_LABEL = "recitation_instructor_label";
    public static String CLASS_RECITATION_DAYTIME_LABEL = "recitation_daytime_label";
    public static String CLASS_RECITATION_LOCATION_LABEL = "recitation_location_label";
    public static String CLASS_RECITATION_TA1_LABEL = "recitation_ta1_label";
    public static String CLASS_RECITATION_TA2_LABEL = "recitation_ta2_label";
    
    public static String CLASS_SCHEDULE_PANE = "schedule_pane";
    public static String CLASS_SCHEDULE_HEADER = "tab_header";
    public static String CLASS_CALENDER_PANE = "schedule_helper_pane";
    public static String CLASS_CALENDER_HEADER= "schedule_helper_pane_header";
    public static String CLASS_SCHEDULE_START_MON_LABEL= "schedule_reg_label";
    public static String CLASS_SCHEDULE_END_FRIDAY_LABEL= "schedule_reg_label";
    public static String CLASS_SCHEDULE_TABLE_PANE= "schedule_helper_pane";
    public static String CLASS_SCHEDULE_ITEM_LABEL= "schedule_helper_pane_header";
    
    public static String CLASS_SCHEDULE_ADDEDIT_LABEL= "schedule_helper_pane_header";
    public static String CLASS_SCHEDULE_REG_SCHEDULE_LABEL= "schedule_reg_label";
    
    
    // THIS PROVIDES ACCESS TO OTHER COMPONENTS
    private AppTemplate app;
    
    /**
     * This constructor initializes all style for the application.
     * 
     * @param initApp The application to be stylized.
     */
    public CSGStyle(AppTemplate initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // LET'S USE THE DEFAULT STYLESHEET SETUP
        super.initStylesheet(app);

        // INIT THE STYLE FOR THE FILE TOOLBAR
        app.getGUI().initFileToolbarStyle();

        // AND NOW OUR WORKSPACE STYLE
        initTAWorkspaceStyle();
    }

    /**
     * This function specifies all the style classes for
     * all user interface controls in the workspace.
     */
    private void initTAWorkspaceStyle() {
        // LEFT SIDE - THE HEADER
        CSGWorkspace workspaceComponent = (CSGWorkspace)app.getWorkspaceComponent();
        workspaceComponent.getTAsHeaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getTAsHeaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);

        // LEFT SIDE - THE TABLE
        TableView<TeachingAssistant> taTable = workspaceComponent.getTATable();
        taTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : taTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }

        // LEFT SIDE - THE TA DATA ENTRY
        workspaceComponent.getAddBox().getStyleClass().add(CLASS_ADD_TA_PANE);
        workspaceComponent.getNameTextField().getStyleClass().add(CLASS_ADD_TA_TEXT_FIELD);
        workspaceComponent.getAddButton().getStyleClass().add(CLASS_ADD_TA_BUTTON);
        workspaceComponent.getClearButton().getStyleClass().add(CLASS_CLEAR_BUTTON);

        // RIGHT SIDE - THE HEADER
        workspaceComponent.getOfficeHoursSubheaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getOfficeHoursSubheaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);
        
        workspaceComponent.getCourseInfoPane().getStyleClass().add(CLASS_COURSE_INFO_PANE);
        workspaceComponent.getCourseInfoLabel().getStyleClass().add(CLASS_COURSE_INFO_LABEL);
        
        workspaceComponent.getCourseDetails().getStyleClass().add(CLASS_COURSE_FIRST_BOX);
        workspaceComponent.getCoursePane().getStyleClass().add(CLASS_COURSE_PANE);
        workspaceComponent.getExportLabel().getStyleClass().add(CLASS_COURSE_EXPORT_LABEL);
        workspaceComponent.getCourseTemplatePane().getStyleClass().add(CLASS_COURSE_TEMPLATE_PANE);
        workspaceComponent.getCourseTemplateHeaderLabel().getStyleClass().add(CLASS_COURSE_TEMPLATE_HEADER);
        workspaceComponent.getCourseTemplateLocLabel().getStyleClass().add(CLASS_COURSE_TEMPLATE_LOCATION);
        workspaceComponent.getCourseSitePageLabel().getStyleClass().add(CLASS_COURSE_SITE_PAGE);
        workspaceComponent.getPageStylePane().getStyleClass().add(CLASS_PAGE_STYLE_PANE);
        workspaceComponent.getPageStyleHeader().getStyleClass().add(CLASS_PAGE_STYLE_LABEL);
        
        workspaceComponent.getBannerLabel().getStyleClass().add(CLASS_BANNER_LABEL);
        workspaceComponent.getLeftFooterLabel().getStyleClass().add(CLASS_LEFT_FOOTER_LABEL);
        workspaceComponent.getRightFooterLabel().getStyleClass().add(CLASS_RIGHT_FOOTER_LABEL);
        workspaceComponent.getStyleSheetLabel().getStyleClass().add(CLASS_STYLE_NOTE_LABEL);
        
        workspaceComponent.getRecitationHeaderLabel().getStyleClass().add(CLASS_RECITATION_HEADER);
        workspaceComponent.getRecitationPane().getStyleClass().add(CLASS_RECITATION_PANE);
        workspaceComponent.getRecitationAddLabel().getStyleClass().add(CLASS_RECITATION_ADD_LABEL);
        workspaceComponent.getRecitationAddPane().getStyleClass().add(CLASS_RECITATION_ADD_PANE);
        
        workspaceComponent.getRecSection().getStyleClass().add(CLASS_RECITATION_SECTION_LABEL);
        workspaceComponent.getRecInstructor().getStyleClass().add(CLASS_RECITATION_INSTRUCTOR_LABEL);
        workspaceComponent.getRecDayTime().getStyleClass().add(CLASS_RECITATION_DAYTIME_LABEL);
        workspaceComponent.getRecLocation().getStyleClass().add(CLASS_RECITATION_LOCATION_LABEL);
        workspaceComponent.getRecTA1().getStyleClass().add(CLASS_RECITATION_TA1_LABEL);  
        workspaceComponent.getRecTA2().getStyleClass().add(CLASS_RECITATION_TA2_LABEL);
        
        workspaceComponent.getSchedulePane().getStyleClass().add(CLASS_SCHEDULE_PANE);
        workspaceComponent.getScheduleHeaderLabel().getStyleClass().add(CLASS_SCHEDULE_HEADER);
        workspaceComponent.getCalenderPane().getStyleClass().add(CLASS_CALENDER_PANE);
        workspaceComponent.getCalenderBoundariesLabel().getStyleClass().add(CLASS_CALENDER_HEADER);
        workspaceComponent.getStartMondayLabel().getStyleClass().add(CLASS_SCHEDULE_START_MON_LABEL);
        workspaceComponent.getEndFridayLabel().getStyleClass().add(CLASS_SCHEDULE_END_FRIDAY_LABEL);
        workspaceComponent.getScheduleTablePane().getStyleClass().add(CLASS_SCHEDULE_TABLE_PANE);
        workspaceComponent.getScheduleItemLabel().getStyleClass().add(CLASS_SCHEDULE_ITEM_LABEL);
        
        workspaceComponent.getScheduleAddEdit().getStyleClass().add(CLASS_SCHEDULE_ADDEDIT_LABEL);
        workspaceComponent.getScheduleTypeLabel().getStyleClass().add(CLASS_SCHEDULE_REG_SCHEDULE_LABEL);
        workspaceComponent.getScheduleDateLabel().getStyleClass().add(CLASS_SCHEDULE_REG_SCHEDULE_LABEL);
        workspaceComponent.getScheduleTimeLabel().getStyleClass().add(CLASS_SCHEDULE_REG_SCHEDULE_LABEL);
        workspaceComponent.getScheduleTitleLabel().getStyleClass().add(CLASS_SCHEDULE_REG_SCHEDULE_LABEL);
        workspaceComponent.getScheduleTopicLabel().getStyleClass().add(CLASS_SCHEDULE_REG_SCHEDULE_LABEL);
        workspaceComponent.getScheduleLinkLabel().getStyleClass().add(CLASS_SCHEDULE_REG_SCHEDULE_LABEL);
        workspaceComponent.getScheduleCriteriaLabel().getStyleClass().add(CLASS_SCHEDULE_REG_SCHEDULE_LABEL);
    }
    
    /**
     * This method initializes the style for all UI components in
     * the office hours grid. Note that this should be called every
     * time a new TA Office Hours Grid is created or loaded.
     */
    public void initOfficeHoursGridStyle() {
        // RIGHT SIDE - THE OFFICE HOURS GRID TIME HEADERS
        CSGWorkspace workspaceComponent = (CSGWorkspace)app.getWorkspaceComponent();
        workspaceComponent.getOfficeHoursGridPane().getStyleClass().add(CLASS_OFFICE_HOURS_GRID);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeHeaderPanes(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeHeaderLabels(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridDayHeaderPanes(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridDayHeaderLabels(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeCellPanes(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTimeCellLabels(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTACellPanes(), CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        setStyleClassOnAll(workspaceComponent.getOfficeHoursGridTACellLabels(), CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL);
    }
    
    /**
     * This helper method initializes the style of all the nodes in the nodes
     * map to a common style, styleClass.
     */
    private void setStyleClassOnAll(HashMap nodes, String styleClass) {
        for (Object nodeObject : nodes.values()) {
            Node n = (Node)nodeObject;
            n.getStyleClass().add(styleClass);
        }
    }
}
