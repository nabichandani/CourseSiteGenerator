/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.files;

import csg.CSGApp;
import csg.data.CourseData;
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
import csg.ui.CSGWorkspace;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import djf.components.AppWorkspaceComponent;
import djf.controller.AppFileController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Navin
 */
public class CSGFiles implements AppFileComponent{
    CSGApp app;
    CSGWorkspace workspace;
    
    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
    static final String JSON_EMAIL = "email";
    static final String JSON_UG = "undergrad";
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    
    static final String JSON_RECITATION = "recitations";
    static final String JSON_SECTION = "section";
    static final String JSON_INSTRUCTOR = "instructor";
    static final String JSON_DAYTIME = "day_time"; 
    static final String JSON_LOCATION = "location";
    static final String JSON_FIRSTTA = "ta_1";
    static final String JSON_SECONDTA = "ta_2";
    
    
    static final String JSON_MONDAYMON = "startingMondayMonth";
    static final String JSON_MONDAYDAY = "startingMondayDay";
    static final String JSON_FRIDAYMONTH = "endingFridayMonth";
    static final String JSON_FRIDAYDAY = "endingFridayDay";
    
    static final String JSON_MONTH = "month";
    static final String JSON_HOLIDAYS = "holidays";
    static final String JSON_LECTURES = "lectures";
    static final String JSON_REF = "references"; 
    static final String JSON_HW= "hws";
    
    static final String JSON_SCHEDULEITEM = "scheduleItems";
    static final String JSON_TYPE = "type";
    static final String JSON_DATE = "date";
    static final String JSON_TITLE = "title";
    static final String JSON_TOPIC = "topic";

    static final String JSON_TEAMS = "teams";
    static final String JSON_COLOR = "color";
    static final String JSON_TEXTCOLOR = "text_color";
    static final String JSON_LINK = "link";
    
    static final String JSON_STUDENTS = "students";
    static final String JSON_FIRSTNAME = "firstName";
    static final String JSON_LASTNAME = "lastName";
    static final String JSON_TEAM = "team";
    static final String JSON_ROLE = "role";
    static final String JSON_CRITERIA = "criteria";
    
    static final String JSON_COURSETEMPLATE = "courseTemplate";
    static final String JSON_USE = "use";
    static final String JSON_NAVBAR = "navbarTitle";
    static final String JSON_FILENAME = "fileName";
    static final String JSON_SCRIPT = "script";
    
    static final String JSON_COURSE = "course";
    static final String JSON_SUBJECT = "subject";
    static final String JSON_NUMBER = "number";
    static final String JSON_SEMESTER = "semester";
    static final String JSON_YEAR = "year";
    static final String JSON_INSTRUCTORNAME = "instructorName";
    static final String JSON_INSTRUCTORHOME = "instructorHome";
    static final String JSON_TEMPLATEDIR = "templateDirectory";
    static final String JSON_EXPORTDIR = "exportDirectory";
    static final String JSON_BANNER = "banner";
    static final String JSON_LEFTFOOTER = "leftFooter";
    static final String JSON_RIGHTFOOTER = "rightFooter";
    static final String JSON_STYLESHEET = "styleSheet";
    
    
    static final String JSON_STARTDAY = "startDay";
    static final String JSON_STARTMONTH = "startMonth";
    static final String JSON_STARTYEAR = "startYear";
    static final String JSON_ENDDAY = "endDay";
    static final String JSON_ENDMONTH = "endMonth";
    static final String JSON_ENDYEAR = "endYear";
    
    static final String JSON_RED = "red";
    static final String JSON_GREEN = "green";
    static final String JSON_BLUE = "blue";
    
    static final String JSON_WORK = "work";
    static final String JSON_PROJECTS = "projects";
                        
    
    
    
    public CSGFiles(CSGApp initApp){
    app = initApp;
}
    @Override
    public void loadData(AppDataComponent data, AppDataComponent recData, 
        AppDataComponent schData, AppDataComponent projectData, 
        AppDataComponent courseData, String filePath) throws IOException {
	// CLEAR THE OLD DATA OUT
	TAData dataManager = (TAData)data;
        ScheduleData schDataManager = (ScheduleData)schData;
        RecitationData recDataManager = (RecitationData)recData;
        ProjectData projectDataManager = (ProjectData)projectData;
        CourseData courseDataManager = (CourseData)courseData;
        workspace = (CSGWorkspace)app.getWorkspaceComponent();

	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);

	// LOAD THE START AND END HOURS
	String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        dataManager.initHours(startHour, endHour);

        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        app.getWorkspaceComponent().reloadWorkspace(app.getTADataComponent());
        
        
        int startDay = json.getInt(JSON_STARTDAY);
        int startMonth = json.getInt(JSON_STARTMONTH);
        int startYear = json.getInt(JSON_STARTYEAR);
        
        courseDataManager.setStartDay(startDay);
        courseDataManager.setStartMonth(startMonth);
        courseDataManager.setStartYear(startYear);
        
        if(startDay != 0 && startMonth != 0 && startYear != 0){
            LocalDate startDate = LocalDate.of(startYear, startMonth,startDay);
            workspace.getMonStartDatePicker().setValue(startDate);
        }
        else{
            workspace.getMonStartDatePicker().setValue(null);
        }
        
        int endDay = json.getInt(JSON_ENDDAY);
        int endMonth = json.getInt(JSON_ENDMONTH);
        int endYear = json.getInt(JSON_ENDYEAR);
        
        courseDataManager.setEndDay(endDay);
        courseDataManager.setEndMonth(endMonth);
        courseDataManager.setEndYear(endYear);
        
        if(endDay != 0 && endMonth != 0 && endYear != 0){
            LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);
            workspace.getFriEndDatePicker().setValue(endDate);
        }
        else{
            workspace.getFriEndDatePicker().setValue(null); 
        }

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            boolean ug = jsonTA.getBoolean(JSON_UG);
            BooleanProperty isUndergrad = new SimpleBooleanProperty();
            isUndergrad.setValue(ug);
            dataManager.addTA(name, email, isUndergrad);
        }

        // AND THEN ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            dataManager.addOfficeHoursReservation(day, time, name);
        }
        
        JsonArray jsonRecitationArray = json.getJsonArray(JSON_RECITATION);
        for (int i = 0; i < jsonRecitationArray.size(); i++) {
            JsonObject jsonRec = jsonRecitationArray.getJsonObject(i);
            String section = jsonRec.getString(JSON_SECTION);
            String instructor = jsonRec.getString(JSON_INSTRUCTOR);
            String dayTime = jsonRec.getString(JSON_DAYTIME);
            String location = jsonRec.getString(JSON_LOCATION);
            String firstTA = jsonRec.getString(JSON_FIRSTTA);
            String secondTA = jsonRec.getString(JSON_SECONDTA);
            recDataManager.addRecitation(section, instructor, dayTime,
                location, firstTA, secondTA);   
        }
        
        JsonArray jsonScheduleArray = json.getJsonArray(JSON_SCHEDULEITEM);
        for (int i = 0; i < jsonScheduleArray.size(); i++) {
            JsonObject jsonSch = jsonScheduleArray.getJsonObject(i);
            String type = jsonSch.getString(JSON_TYPE);
            int month = jsonSch.getInt(JSON_MONTH);  
            int day = jsonSch.getInt(JSON_DAY);
            int year = jsonSch.getInt(JSON_YEAR);
            String time = jsonSch.getString(JSON_TIME);  
            String title = jsonSch.getString(JSON_TITLE);
            String topic = jsonSch.getString(JSON_TOPIC);
            String link = jsonSch.getString(JSON_LINK);
            String criteria = jsonSch.getString(JSON_CRITERIA); 
            schDataManager.addScheduleItem(type, LocalDate.of(year, month, day), time, title, 
                topic, link, criteria);
        }
        
        JsonArray jsonTeamArray = json.getJsonArray(JSON_TEAMS);
        for (int i = 0; i < jsonTeamArray.size(); i++) {
            JsonObject jsonTeam = jsonTeamArray.getJsonObject(i);
            String name = jsonTeam.getString(JSON_NAME);
            String color = jsonTeam.getString(JSON_COLOR);
            String textColor = jsonTeam.getString(JSON_TEXTCOLOR);  
            String link = jsonTeam.getString(JSON_LINK);
            projectDataManager.addTeam(name, color, textColor, link);
        }
        
        JsonArray jsonStudentArray = json.getJsonArray(JSON_STUDENTS);
        for (int i = 0; i < jsonStudentArray.size(); i++) {
            JsonObject jsonStudent = jsonStudentArray.getJsonObject(i);
            String firstName = jsonStudent.getString(JSON_FIRSTNAME);
            String lastName = jsonStudent.getString(JSON_LASTNAME);
            String team = jsonStudent.getString(JSON_TEAM);  
            String role = jsonStudent.getString(JSON_ROLE);
            projectDataManager.addStudent(firstName, lastName, team, role);
        }
        
        JsonArray jsonTemplateArray = json.getJsonArray(JSON_COURSETEMPLATE);
        courseDataManager.getTemplates().clear();
        for (int i = 0; i < jsonTemplateArray.size(); i++) {
            JsonObject jsonTemplate = jsonTemplateArray.getJsonObject(i);
            boolean use = jsonTemplate.getBoolean(JSON_USE);
            String navbar = jsonTemplate.getString(JSON_NAVBAR);
            String fileName = jsonTemplate.getString(JSON_FILENAME);  
            String script = jsonTemplate.getString(JSON_SCRIPT);
            courseDataManager.addTemplate(use, navbar, fileName, script);
        }
        
        JsonObject courseJson = json.getJsonObject(JSON_COURSE);
        courseDataManager.setNumber(courseJson.getString(JSON_NUMBER));
        courseDataManager.setSemester(courseJson.getString(JSON_SEMESTER));
        courseDataManager.setSubject(courseJson.getString(JSON_SUBJECT));
        courseDataManager.setYear(courseJson.getString(JSON_YEAR));
        courseDataManager.setTitle(courseJson.getString(JSON_TITLE));
        courseDataManager.setInsName(courseJson.getString(JSON_INSTRUCTORNAME));
        courseDataManager.setInsHome(courseJson.getString(JSON_INSTRUCTORHOME));
        courseDataManager.setBannerLink(courseJson.getString(JSON_BANNER));
        courseDataManager.setLeftFooterLink(courseJson.getString(JSON_LEFTFOOTER));
        courseDataManager.setRightFooterLink(courseJson.getString(JSON_RIGHTFOOTER));
        courseDataManager.setTemplateDir(courseJson.getString(JSON_TEMPLATEDIR));
        courseDataManager.setExportDir(courseJson.getString(JSON_EXPORTDIR));
        courseDataManager.setStyleSheet(courseJson.getString(JSON_STYLESHEET));
          
        
        workspace.getSubjectCombo().setValue(courseDataManager.getSubject());
        workspace.getNumCombo().setValue(courseDataManager.getNumber());
        workspace.getSemCombo().setValue(courseDataManager.getSemester());
        workspace.getYearCombo().setValue(courseDataManager.getYear());
        workspace.getTitleTextField().setText(courseDataManager.getTitle());
        workspace.getInsNameTextField().setText(courseDataManager.getInsName());
        workspace.getInsHomeTextField().setText(courseDataManager.getInsHome());
        workspace.getStyleSheetCombo().setValue(courseDataManager.getStyleSheet());
        
        
        if(!courseDataManager.getBannerLink().isEmpty()){
            FileInputStream bannerLocation = new FileInputStream(courseJson.getString(JSON_BANNER));
            Image newImg = new Image(bannerLocation);
            workspace.getBannerImage().setImage(newImg);
        }
        else{
            workspace.getBannerImage().setImage(null);
        }
        
        if(!courseDataManager.getLeftFooterLink().isEmpty()){
            FileInputStream leftFooterLocation = new FileInputStream(courseJson.getString(JSON_LEFTFOOTER));
            Image newImg2 = new Image(leftFooterLocation);
            workspace.getLeftFooterImage().setImage(newImg2);
        }
        else{
            workspace.getLeftFooterImage().setImage(null);
        }
        
        if(!courseDataManager.getRightFooterLink().isEmpty()){
            FileInputStream rightFooterLocation = new FileInputStream(courseJson.getString(JSON_RIGHTFOOTER));
            Image newImg3 = new Image(rightFooterLocation);
            workspace.getRightFooterImage().setImage(newImg3);
        }
        else{
            workspace.getRightFooterImage().setImage(null);
        }
        workspace.getCourseTemplateLocLabel().setText(courseJson.getString(JSON_TEMPLATEDIR));
        workspace.getExportLabel().setText(courseJson.getString(JSON_EXPORTDIR));
        
    }
    
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }

    @Override
    public void saveData(AppDataComponent data, AppDataComponent recData, 
        AppDataComponent schData, AppDataComponent projectData, 
        AppDataComponent courseData, String filePath) throws IOException {
	       // GET THE DATA
        TAData dataManager = (TAData) data;
        CourseData cd = (CourseData) courseData;
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();

        if (workspace.getTitleTextField().getText().equals("")) {
            cd.setTitle("");
        } else {
            cd.setTitle(workspace.getTitleTextField().getText());
        }
        if (workspace.getInsNameTextField().getText().equals("")) {
            cd.setInsName("");
        } else {
            cd.setInsName(workspace.getInsNameTextField().getText());
        }
        if (workspace.getInsHomeTextField().getText().equals("")) {
            cd.setInsHome("");
        } else {
            cd.setInsHome(workspace.getInsHomeTextField().getText());
        }

        // NOW BUILD THE TA JSON OBJCTS TO SAVE
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
        ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
        for (TeachingAssistant ta : tas) {	    
	    JsonObject taJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail())
                    .add(JSON_UG, ta.isUndergrad().get())
                    .build();
	    taArrayBuilder.add(taJson);
	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
	ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
        
        
        RecitationData recDataManager = (RecitationData)recData;
        JsonArrayBuilder recArrayBuilder = Json.createArrayBuilder();
	ObservableList<Recitation> recitations = recDataManager.getRecitations();
        
        
        for (Recitation recitation : recitations) {	    
	    JsonObject recitationJson = Json.createObjectBuilder()
		    .add(JSON_SECTION, recitation.getSection())
                    .add(JSON_INSTRUCTOR, recitation.getInstructor())
                    .add(JSON_DAYTIME, recitation.getDayTime())
                    .add(JSON_LOCATION, recitation.getLocation())        
                    .add(JSON_FIRSTTA, recitation.getFirstTA())
                    .add(JSON_SECONDTA, recitation.getSecondTA())
                    .build();
	    recArrayBuilder.add(recitationJson);
	}
        JsonArray recitaitonArray = recArrayBuilder.build();
        
        ScheduleData schDataManager = (ScheduleData)schData;
        JsonArrayBuilder schArrayBuilder = Json.createArrayBuilder();
	ObservableList<ScheduleItem> schedule = schDataManager.getSchedule();
        
        
        for (ScheduleItem scheduleItem : schedule) {	    
	    JsonObject scheduleJson = Json.createObjectBuilder()
		    .add(JSON_TYPE, scheduleItem.getType())
                    .add(JSON_DAY, scheduleItem.getDate().getDayOfMonth())
                    .add(JSON_MONTH, scheduleItem.getDate().getMonthValue())
                    .add(JSON_YEAR, scheduleItem.getDate().getYear())
                    .add(JSON_TIME, scheduleItem.getTime())
                    .add(JSON_TITLE, scheduleItem.getTitle())        
                    .add(JSON_TOPIC, scheduleItem.getTopic())
                    .add(JSON_LINK, scheduleItem.getLink())
                    .add(JSON_CRITERIA, scheduleItem.getCriteria())
                    .build();
	    schArrayBuilder.add(scheduleJson);
	}
        JsonArray scheduleArray = schArrayBuilder.build();
        
        ProjectData projectDataManager = (ProjectData)projectData;
        JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
	ObservableList<Team> teams = projectDataManager.getTeams();
        ObservableList<Student> students = projectDataManager.getStudents();
        
        for (Team team : teams) {	    
	    JsonObject teamsJson = Json.createObjectBuilder()
		    .add(JSON_NAME, team.getName())
                    .add(JSON_COLOR, team.getColor())
                    .add(JSON_TEXTCOLOR, team.getTextColor())
                    .add(JSON_LINK, team.getLink())        
                    .build();
	    teamArrayBuilder.add(teamsJson);
	}
        JsonArray teamArray = teamArrayBuilder.build();
        
        for (Student student : students) {	    
	    JsonObject studentsJson = Json.createObjectBuilder()
		    .add(JSON_FIRSTNAME, student.getFirstName())
                    .add(JSON_LASTNAME, student.getLastName())
                    .add(JSON_TEAM, student.getTeam())
                    .add(JSON_ROLE, student.getRole())        
                    .build();
	    studentArrayBuilder.add(studentsJson);
	}
        JsonArray studentArray = studentArrayBuilder.build();
        
        CourseData courseDataManager = (CourseData)courseData;
        JsonArrayBuilder courseArrayBuilder = Json.createArrayBuilder();
	ObservableList<CourseTemplate> templates = courseDataManager.getTemplates();
        
        for (CourseTemplate template : templates) {	    
	    JsonObject courseJson = Json.createObjectBuilder()
		    .add(JSON_USE, template.isUse().getValue())
                    .add(JSON_NAVBAR, template.getNavbarTitle())
                    .add(JSON_FILENAME, template.getFileName())
                    .add(JSON_SCRIPT, template.getScript())        
                    .build();
	    courseArrayBuilder.add(courseJson);
	}
        JsonArray courseArray = courseArrayBuilder.build();
        
        JsonObject courseJson = Json.createObjectBuilder()
                .add(JSON_SUBJECT, courseDataManager.getSubject())
                .add(JSON_NUMBER, courseDataManager.getNumber())
                .add(JSON_SEMESTER, courseDataManager.getSemester())
                .add(JSON_YEAR, courseDataManager.getYear())
                .add(JSON_TITLE, courseDataManager.getTitle())
                .add(JSON_INSTRUCTORNAME, courseDataManager.getInsName())
                .add(JSON_INSTRUCTORHOME, courseDataManager.getInsHome())
                .add(JSON_BANNER, courseDataManager.getBannerLink())
                .add(JSON_LEFTFOOTER, courseDataManager.getLeftFooterLink())
                .add(JSON_RIGHTFOOTER, courseDataManager.getRightFooterLink())
                .add(JSON_EXPORTDIR, courseDataManager.getExportDir())
                .add(JSON_TEMPLATEDIR, courseDataManager.getTemplateDir())
                .add(JSON_STYLESHEET, courseDataManager.getStyleSheet())
                .build();
                  
        
        
	// THEN PUT IT ALL RECITATIONS IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_COURSE, courseJson)
                .add(JSON_COURSETEMPLATE, courseArray)
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
		.add(JSON_RECITATION, recitaitonArray)
                .add(JSON_STARTDAY, courseDataManager.getStartDay())
                .add(JSON_STARTMONTH,courseDataManager.getStartMonth())
                .add(JSON_STARTYEAR, courseDataManager.getStartYear())
                .add(JSON_ENDDAY, courseDataManager.getEndDay())
                .add(JSON_ENDMONTH,courseDataManager.getEndMonth())
                .add(JSON_ENDYEAR,courseDataManager.getEndYear())
                .add(JSON_SCHEDULEITEM, scheduleArray)
                .add(JSON_TEAMS, teamArray)
                .add(JSON_STUDENTS, studentArray)
		.build();       
        
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
        
        
    }
    
    public void saveTAData(AppDataComponent data, String filePath) throws IOException{
        TAData dataManager = (TAData)data;

	// NOW BUILD THE TA JSON OBJCTS TO SAVE
	JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
	for (TeachingAssistant ta : tas) {	    
	    JsonObject taJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail())
                    .add(JSON_UG, ta.isUndergrad().get())
                    .build();
	    taArrayBuilder.add(taJson);
	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
	ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
		.build();
        
        	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    public void saveRecitationData(AppDataComponent recData, String filePath) throws IOException{
                
        RecitationData recDataManager = (RecitationData)recData;
        JsonArrayBuilder recArrayBuilder = Json.createArrayBuilder();
	ObservableList<Recitation> recitations = recDataManager.getRecitations();
        
        
        for (Recitation recitation : recitations) {	    
	    JsonObject recitationJson = Json.createObjectBuilder()
		    .add(JSON_SECTION, recitation.getSection())
                    .add(JSON_INSTRUCTOR, recitation.getInstructor())
                    .add(JSON_DAYTIME, recitation.getDayTime())
                    .add(JSON_LOCATION, recitation.getLocation())        
                    .add(JSON_FIRSTTA, recitation.getFirstTA())
                    .add(JSON_SECONDTA, recitation.getSecondTA())
                    .build();
	    recArrayBuilder.add(recitationJson);
	}
        JsonArray recitaitonArray = recArrayBuilder.build();
        
        
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_RECITATION, recitaitonArray)
		.build();
        
        
        	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    public void saveScheduleData(AppDataComponent schData, String filePath) throws IOException{
        ScheduleData schDataManager = (ScheduleData)schData;
        JsonArrayBuilder schArrayBuilder = Json.createArrayBuilder();
	ObservableList<ScheduleItem> schedule = schDataManager.getSchedule();
        
        ArrayList<ScheduleItem> holidays = new ArrayList();
        ArrayList<ScheduleItem> lectures = new ArrayList();
        ArrayList<ScheduleItem> references = new ArrayList();
        ArrayList<ScheduleItem> recitations = new ArrayList();
        ArrayList<ScheduleItem> hws = new ArrayList();
        
        for (int i = 0; i < schedule.size(); i++) {
            if(schedule.get(i).getType().equals("holiday")){
                holidays.add(schedule.get(i));
            }
            else if(schedule.get(i).getType().equals("lecture")){
                lectures.add(schedule.get(i));
            }
            else if(schedule.get(i).getType().equals("reference")){
                references.add(schedule.get(i));
            }
            else if(schedule.get(i).getType().equals("recitation")){
                recitations.add(schedule.get(i));
            }
            else if(schedule.get(i).getType().equals("hw")){
                hws.add(schedule.get(i));
            }        
        }
        
        JsonArrayBuilder holidayArrayBuilder = Json.createArrayBuilder();
        for (ScheduleItem scheduleItem : holidays) {
            JsonObject holidayJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, scheduleItem.getDateMon())
                    .add(JSON_DAY, scheduleItem.getDateDay())
                    .add(JSON_TITLE, scheduleItem.getTitle())
                    .add(JSON_LINK, scheduleItem.getLink())
                    .build();
            holidayArrayBuilder.add(holidayJson);
        }	
        JsonArray holidayArray = holidayArrayBuilder.build();
        
        JsonArrayBuilder lectureArrayBuilder = Json.createArrayBuilder();
        for (ScheduleItem scheduleItem : lectures) {
            JsonObject lecJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, scheduleItem.getDateMon())
                    .add(JSON_DAY, scheduleItem.getDateDay())
                    .add(JSON_TITLE, scheduleItem.getTitle())
                    .add(JSON_TOPIC, scheduleItem.getTopic())
                    .add(JSON_LINK, scheduleItem.getLink())
                    .build();
            lectureArrayBuilder.add(lecJson);
        }
        JsonArray lectureArray = lectureArrayBuilder.build();
        
        JsonArrayBuilder referencesArrayBuilder = Json.createArrayBuilder();
        for (ScheduleItem scheduleItem : references) {
            JsonObject lecJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, scheduleItem.getDateMon())
                    .add(JSON_DAY, scheduleItem.getDateDay())
                    .add(JSON_TITLE, scheduleItem.getTitle())
                    .add(JSON_TOPIC, scheduleItem.getTopic())
                    .add(JSON_LINK, scheduleItem.getLink())
                    .build();
            referencesArrayBuilder.add(lecJson);
        }
        JsonArray referenceArray = referencesArrayBuilder.build();
        
        JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
        for (ScheduleItem scheduleItem : recitations) {
            JsonObject lecJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, scheduleItem.getDateMon())
                    .add(JSON_DAY, scheduleItem.getDateDay())
                    .add(JSON_TITLE, scheduleItem.getTitle())
                    .add(JSON_TOPIC, scheduleItem.getTopic())
                    .build();
            recitationArrayBuilder.add(lecJson);
        }
        JsonArray recitaitonArray = recitationArrayBuilder.build();
        
        JsonArrayBuilder hwArrayBuilder = Json.createArrayBuilder();
        for (ScheduleItem scheduleItem : hws) {
            JsonObject lecJson = Json.createObjectBuilder()
                    .add(JSON_MONTH, scheduleItem.getDateMon())
                    .add(JSON_DAY, scheduleItem.getDateDay())
                    .add(JSON_TITLE, scheduleItem.getTitle())
                    .add(JSON_TOPIC, scheduleItem.getTopic())
                    .add(JSON_LINK, scheduleItem.getLink())
                    .add(JSON_TIME, scheduleItem.getTime())
                    .add(JSON_CRITERIA, scheduleItem.getCriteria())
                    .build();
            hwArrayBuilder.add(lecJson);
        }
        JsonArray hwArray = hwArrayBuilder.build();
        
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_MONDAYMON, workspace.getMonMonthDate())
                .add(JSON_MONDAYDAY, workspace.getMonDayDate())
                .add(JSON_FRIDAYMONTH, workspace.getFriMonthDate())
                .add(JSON_FRIDAYDAY, workspace.getFriDayDate())
                .add(JSON_HOLIDAYS, holidayArray)
                .add(JSON_LECTURES, lectureArray)
                .add(JSON_REF, referenceArray)
                .add(JSON_RECITATION, recitaitonArray)
                .add(JSON_HW, hwArray)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    public void saveCourseData(AppDataComponent courseData, String filePath) throws IOException{
        CourseData courseDataManager = (CourseData)courseData;
        JsonArrayBuilder courseArrayBuilder = Json.createArrayBuilder();
        JsonArray courseArray = courseArrayBuilder.build();
        
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        JsonObject courseJson = Json.createObjectBuilder()
                .add(JSON_SUBJECT, courseDataManager.getSubject())
                .add(JSON_NUMBER, courseDataManager.getNumber())
                .add(JSON_SEMESTER, courseDataManager.getSemester())
                .add(JSON_YEAR, courseDataManager.getYear())
                .add(JSON_TITLE, courseDataManager.getTitle())
                .add(JSON_INSTRUCTORNAME, courseDataManager.getInsName())
                .add(JSON_INSTRUCTORHOME, courseDataManager.getInsHome())
                .add(JSON_BANNER, courseDataManager.getBannerLink())
                .add(JSON_LEFTFOOTER, courseDataManager.getLeftFooterLink())
                .add(JSON_RIGHTFOOTER, courseDataManager.getRightFooterLink())
                .add(JSON_STYLESHEET, courseDataManager.getStyleSheet())
                .build();
        
        ObservableList<CourseTemplate> templates = courseDataManager.getTemplates();
        
        for (CourseTemplate template : templates) {	    
	    JsonObject cJson = Json.createObjectBuilder()
		    .add(JSON_USE, template.isUse().getValue())
                    .add(JSON_NAVBAR, template.getNavbarTitle())
                    .add(JSON_FILENAME, template.getFileName())
                    .add(JSON_SCRIPT, template.getScript())        
                    .build();
	    courseArrayBuilder.add(cJson);
	}
        courseArray = courseArrayBuilder.build();
        
        	JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_COURSE, courseJson)
                .add(JSON_COURSETEMPLATE, courseArray)
                        .build();
        
        	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    
    
    public void saveTeamsAndStudentsData(AppDataComponent projectData, String filePath) throws IOException{
        ProjectData projectDataManager = (ProjectData)projectData;
        JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
	ObservableList<Team> teams = projectDataManager.getTeams();
        ObservableList<Student> students = projectDataManager.getStudents();
        
        for (Team team : teams) {	    
	    JsonObject teamsJson = Json.createObjectBuilder()
		    .add(JSON_NAME, team.getName())
                    .add(JSON_RED, (Integer.parseInt(team.getColor().toString().substring(0,2), 16)))
                    .add(JSON_GREEN, (Integer.parseInt(team.getColor().toString().substring(2,4), 16)))
                    .add(JSON_BLUE, (Integer.parseInt(team.getColor().toString().substring(4,6), 16)))
                    .add(JSON_TEXTCOLOR, "#" + team.getTextColor())        
                    .build();
	    teamArrayBuilder.add(teamsJson);
	}
        JsonArray teamArray = teamArrayBuilder.build();
        
        for (Student student : students) {	    
	    JsonObject studentsJson = Json.createObjectBuilder()
                    .add(JSON_LASTNAME, student.getLastName())
		    .add(JSON_FIRSTNAME, student.getFirstName())
                    .add(JSON_TEAM, student.getTeam())
                    .add(JSON_ROLE, student.getRole())        
                    .build();
	    studentArrayBuilder.add(studentsJson);
	}
        JsonArray studentArray = studentArrayBuilder.build();
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_TEAMS, teamArray)
                .add(JSON_STUDENTS, studentArray)
		.build();

	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    public void saveProjectsData(AppDataComponent courseData, AppDataComponent projectData, String filePath) throws IOException{
        
        CourseData courseDataManager = (CourseData)courseData;
        ProjectData projectDataManager = (ProjectData)projectData;
        
        ObservableList<Student> students = projectDataManager.getStudents(); 
        
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
	ObservableList<Team> teams = projectDataManager.getTeams();
        
        for (Team team : teams) {
            for (Student student : students) {
                if(student.getTeam().equals(team.getName())){
                        studentArrayBuilder
                        .add(student.getFirstName() + " " + student.getLastName());
                }
            }
            JsonArray studentArray = studentArrayBuilder.build();
            JsonObject teamsJson = Json.createObjectBuilder()
                    .add(JSON_NAME, team.getName())
                    .add(JSON_STUDENTS, studentArray)
                    .add(JSON_LINK, team.getLink())
                    .build();
            teamArrayBuilder.add(teamsJson);
        }
        JsonArray teamArray = teamArrayBuilder.build();
        
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        JsonArrayBuilder courseJsonBuilder = Json.createArrayBuilder();
                JsonObject coursesJson = Json.createObjectBuilder()
                .add(JSON_SEMESTER, courseDataManager.getSemester() + " " + courseDataManager.getYear())
                .add(JSON_PROJECTS, teamArray)
                .build();
        courseJsonBuilder.add(coursesJson);
        
        JsonArray courseJsonArr = courseJsonBuilder.build();
        
        JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_WORK, courseJsonArr)
		.build();
        
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
    
    
    
    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData() throws IOException {
            File selectedFile = new File((workspace.getCourseTemplateLocLabel().getText()));
            File destFile = new File(workspace.getExportLabel().getText());
            CourseData courseData = (CourseData)app.getCourseDataComponent();
            File bannerFile = new File(courseData.getBannerLink());
            File selectedImgFile = new File(selectedFile.getAbsolutePath() + "/images/");
            File publicHTMLFile = new File("../CourseGenTester/public_html/images/");
            
            
            File leftFooterFile = new File(courseData.getLeftFooterLink());
            File rightFooterFile = new File(courseData.getRightFooterLink());
            
            if(!bannerFile.getPath().contains(selectedFile.getAbsolutePath())){
                FileUtils.copyFileToDirectory(bannerFile, selectedImgFile);
            }
            if(!leftFooterFile.getPath().contains(selectedFile.getAbsolutePath())){
                FileUtils.copyFileToDirectory(leftFooterFile, selectedImgFile);
            }
            if(!rightFooterFile.getPath().contains(selectedFile.getAbsolutePath())){
                FileUtils.copyFileToDirectory(rightFooterFile, selectedImgFile);
            }          
            if(!bannerFile.getPath().contains("public_html")){
                FileUtils.copyFileToDirectory(bannerFile, publicHTMLFile);
            }
            if(!leftFooterFile.getPath().contains("public_html")){
                FileUtils.copyFileToDirectory(leftFooterFile, publicHTMLFile);
            }
            if(!rightFooterFile.getPath().contains("public_html")){
                FileUtils.copyFileToDirectory(rightFooterFile, publicHTMLFile);
            }
            
            CourseData cd = (CourseData)app.getCourseDataComponent();
            
            if(workspace.getTitleTextField().getText().isEmpty()){
                cd.setTitle("");
            }
            else{
                cd.setTitle(workspace.getTitleTextField().getText());
            }
            if(workspace.getInsNameTextField().getText().isEmpty()){
                cd.setInsName("");
            }
            else{
                cd.setInsName(workspace.getInsNameTextField().getText());
            }
            if(workspace.getInsHomeTextField().getText().isEmpty()){
                cd.setInsHome("");
            }
            else{
                cd.setInsHome(workspace.getInsHomeTextField().getText());
            }
            
//        String newBanner = bannerFile.getName();
//        courseData.setBannerLink("./images/" + newBanner);
//
//        String newLeft = leftFooterFile.getName();
//        courseData.setLeftFooterLink("./images/" + newLeft);
//
//        String newRight = rightFooterFile.getName();
//        courseData.setRightFooterLink("./images/" + newRight); sa
            
         
            String path = "../CourseGenTester/public_html/js/OfficeHoursGridData.json";
            saveData(app.getTADataComponent(), 
                app.getRecitationDataComponent(), app.getScheduleDataComponent(),
                app.getProjectDataComponent(), app.getCourseDataComponent(), path);
            
            String pathTA = "../CourseGenTester/public_html/js/TAsData.json";
            saveTAData(app.getTADataComponent(), pathTA);
            
            String pathRec = "../CourseGenTester/public_html/js/RecitationsData.json";
            saveRecitationData(app.getRecitationDataComponent(), pathRec);
            
            String pathSch = "../CourseGenTester/public_html/js/ScheduleData.json";
            saveScheduleData(app.getScheduleDataComponent(), pathSch);
            
            String pathCourseData = "../CourseGenTester/public_html/js/CourseData.json";
            saveCourseData(app.getCourseDataComponent(), pathCourseData);
            
            String pathTeamsStudents =  "../CourseGenTester/public_html/js/TeamsAndStudents.json";
            saveTeamsAndStudentsData(app.getProjectDataComponent(), pathTeamsStudents);
            
            String pathCourse =  "../CourseGenTester/public_html/js/ProjectsData.json";
            saveProjectsData(app.getCourseDataComponent(),app.getProjectDataComponent(), pathCourse);
            
            
            String path2 = selectedFile.getAbsolutePath() + "/js/OfficeHoursGridData.json";
            saveData(app.getTADataComponent(), 
                app.getRecitationDataComponent(), app.getScheduleDataComponent(),
                app.getProjectDataComponent(), app.getCourseDataComponent(), path2);
            
            String path2TA = selectedFile.getAbsolutePath() + "/js/TAsData.json";
            saveTAData(app.getTADataComponent(), path2TA);
            
            String path2Rec =  selectedFile.getAbsolutePath() + "/js/RecitationsData.json";
            saveRecitationData(app.getRecitationDataComponent(), path2Rec);
            
            String path2Sch = selectedFile.getAbsolutePath() + "/js/ScheduleData.json";
            saveScheduleData(app.getScheduleDataComponent(), path2Sch);
            
            String path2CourseData =  selectedFile.getAbsolutePath() + "/js/CourseData.json";
            saveCourseData(app.getCourseDataComponent(), path2CourseData);
            
            String path2TeamsStudents =   selectedFile.getAbsolutePath() + "/js/TeamsAndStudents.json";
            saveTeamsAndStudentsData(app.getProjectDataComponent(), path2TeamsStudents);
            
            String path2Course =   selectedFile.getAbsolutePath() + "/js/ProjectsData.json";
            saveProjectsData(app.getCourseDataComponent(),app.getProjectDataComponent(), path2Course);
            
            
            FileUtils.copyDirectory(selectedFile, destFile);
    }

}
