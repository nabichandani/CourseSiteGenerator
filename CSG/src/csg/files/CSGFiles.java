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
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import djf.controller.AppFileController;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

/**
 *
 * @author Navin
 */
public class CSGFiles implements AppFileComponent{
    CSGApp app;
    
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
    
    static final String JSON_RECITATION = "recitation";
    static final String JSON_SECTION = "section";
    static final String JSON_INSTRUCTOR = "instructor";
    static final String JSON_DAYTIME = "day/time"; 
    static final String JSON_LOCATION = "location";
    static final String JSON_FIRSTTA = "firstTa";
    static final String JSON_SECONDTA = "secondTa";
    
    static final String JSON_SCHEDULEITEM = "scheduleItems";
    static final String JSON_TYPE = "type";
    static final String JSON_DATE = "date";
    static final String JSON_TITLE = "title";
    static final String JSON_TOPIC = "topic";

    static final String JSON_TEAMS = "teams";
    static final String JSON_COLOR = "color";
    static final String JSON_TEXTCOLOR = "textColor";
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
    
    static final String JSON_SUBJECT = "subject";
    static final String JSON_NUMBER = "number";
    static final String JSON_SEMESTER = "semester";
    static final String JSON_YEAR = "year";
    static final String JSON_INSTRUCTORNAME = "instructorName";
    static final String JSON_INSTRUCTORHOME = "instructorHome";
    static final String JSON_TEMPLATEDIR = "templateDirectory";
    static final String JSON_BANNER = "banner";
    static final String JSON_LEFTFOOTER = "leftFooter";
    static final String JSON_RIGHTFOOTER = "rightFooter";
    static final String JSON_STYLESHEET = "styleSheet";
    
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

	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);

	// LOAD THE START AND END HOURS
	String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        dataManager.initHours(startHour, endHour);

        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        app.getWorkspaceComponent().reloadWorkspace(app.getTADataComponent());

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
            String date = jsonSch.getString(JSON_DATE);
            String time = jsonSch.getString(JSON_TIME);  
            String title = jsonSch.getString(JSON_TITLE);
            String topic = jsonSch.getString(JSON_TOPIC);
            String link = jsonSch.getString(JSON_LINK);
            String criteria = jsonSch.getString(JSON_CRITERIA); 
            schDataManager.addScheduleItem(type, date, time, title, 
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
        
        
        
        RecitationData recDataManager = (RecitationData)recData;
        JsonArrayBuilder recArrayBuilder = Json.createArrayBuilder();
	ObservableList<Recitation> recitations = recDataManager.getRecitations();
        
//        TeachingAssistant ta1 = new TeachingAssistant("em", "emdss", new SimpleBooleanProperty(true));
//        Recitation rec1 = new Recitation("sec", "color", "time", "loc", ta1.getName() , ta1.getName());
//        recitations.add(rec1);
        
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
        
//        schedule.add(new ScheduleItem("JSON_TYPE", "JSON_DATE", "JSON_TIME", "JSON_TITLE", "JSON_TOPIC", "JSON_LINK", "JSON_CRITERIA"));
        
        for (ScheduleItem scheduleItem : schedule) {	    
	    JsonObject scheduleJson = Json.createObjectBuilder()
		    .add(JSON_TYPE, scheduleItem.getType())
                    .add(JSON_DATE, scheduleItem.getDate())
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
        
//        Team team1 = new Team("sa", "asd", "asdf", "adsf");
//        Team team2 = new Team("adfsdfsa", "asdsdsad", "sdfadasdf", "adsfdsf");
//        teams.add(team1);
//        teams.add(team2);
//        
//        Student studesdnt = new Student("asdf", "aasdf", "asdf", "asdf");
//        Student studeasnt = new Student("asdfa", "aasdsaf", "asasddf", "assdf");
//        students.add(studesdnt);
//        students.add(studeasnt);
        
        
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
        
        
	// THEN PUT IT ALL RECITATIONS IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
		.add(JSON_RECITATION, recitaitonArray)
                .add(JSON_SCHEDULEITEM, scheduleArray)
                .add(JSON_TEAMS, teamArray)
                .add(JSON_STUDENTS, studentArray)
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
    
    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
