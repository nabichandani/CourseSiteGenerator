/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_bed;

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
import csg.files.TimeSlot;
import csg.ui.CSGWorkspace;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
public class TestSave implements AppFileComponent{
    
    static CSGApp app;
    ArrayList<TimeSlot> officeHours;
    
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
    
    static final String JSON_EXPORTDIR = "exportDirectory";
    static final String JSON_COURSE = "course";
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
    
    public TestSave(CSGApp initApp){
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
        //////////////////////////////
        //dataManager.initHours(startHour, endHour);

        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        //////////////////////////
        //app.getWorkspaceComponent().reloadWorkspace(app.getTADataComponent());

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
        officeHours = new ArrayList();
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            TimeSlot ts = new TimeSlot(day, time, name);
            officeHours.add(ts);
            ///////////////////////////////
            //dataManager.addOfficeHoursReservation(day, time, name);
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
        
        TeachingAssistant ta1 = new TeachingAssistant("SpongeBob", "spongebob@stonybrook.edu", new SimpleBooleanProperty(false));
        TeachingAssistant ta2 = new TeachingAssistant("Patrick", "patrick@stonybrook.edu", new SimpleBooleanProperty(true));
        TeachingAssistant ta3 = new TeachingAssistant("Squidward", "squidward@stonybrook.edu", new SimpleBooleanProperty(false));
        TeachingAssistant ta4 = new TeachingAssistant("Sandy Cheeks", "sandy.cheeks@stonybrook.edu", new SimpleBooleanProperty(true));
        TeachingAssistant ta5 = new TeachingAssistant("Mr. Krabs", "krabs@stonybrook.edu", new SimpleBooleanProperty(false));
        dataManager.getTeachingAssistants().addAll(ta1, ta2, ta3, ta4, ta5);

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
	ArrayList<TimeSlot> officeHours = new ArrayList();
        
        TimeSlot timeSlot1 = new TimeSlot("MONDAY", "10_00am", "SpongeBob");
        TimeSlot timeSlot2 = new TimeSlot("FRIDAY", "12_00am", "Patrick");
        officeHours.add(timeSlot1);
        officeHours.add(timeSlot2);
        
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
        
        Recitation rec1 = new Recitation("R01", "McKenna", "Wed 5:30 pm-6:23pm", "Krusty Krabs", ta5.getName() , ta1.getName());
        Recitation rec2 = new Recitation("R02", "SpongeBob", "Mon 4:00 pm-4:53pm", "Squidward's House", ta3.getName() , ta2.getName());
        Recitation rec3 = new Recitation("R03", "McKenna", "Fri 10:00am-10:53am", "Pineapple", ta1.getName() , ta4.getName());
        
        recitations.addAll(rec1, rec2, rec3);
        
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
        
        ScheduleItem schItem1 = new ScheduleItem("Holiday", "2/10/2017", "5:00pm", "Snow Day", "", "https://en.wikipedia.org/wiki/Snow_day", "Break for students");
        ScheduleItem schItem2 = new ScheduleItem("Lecture", "3/13/2017", "12:00pm", "Class", "Event Programming", "", "Class for students");
        ScheduleItem schItem3 = new ScheduleItem("HW", "2/8/2017", "10:00am", "Class", "UML", "", "Homework");
        
        schedule.addAll(schItem1, schItem2, schItem3);
        
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
        
        Team team1 = new Team("Team 1", "Orange", "White", "www.team1.com");
        Team team2 = new Team("Team 2", "Blue", "Black", "www.team2.com");
        teams.add(team1);
        teams.add(team2);
        
        Student student1 = new Student("Joe", "Shmoe", "Team 1", "Lead Designer");
        Student student2 = new Student("Amy", "Gupta", "Team 2", "Lead Programmer");
        students.add(student1);
        students.add(student2);
        
        
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
        
       
        courseDataManager.setSubject("CSE");
        courseDataManager.setNumber("219");
        courseDataManager.setSemester("Fall");
        courseDataManager.setYear("2017");
        courseDataManager.setTitle("Computer Science III");
        courseDataManager.setInsName("Richard McKenna");
        courseDataManager.setInsHome("http://www3.cs.stonybrook.edu/~richard/");
        courseDataManager.setBannerLink("C:\\Users\\Navin\\CourseSiteGenerator\\CSG\\public_html\\images\\SBUDarkRedShieldLogo.png");
        courseDataManager.setLeftFooterLink("C:\\Users\\Navin\\CourseSiteGenerator\\CSG\\public_html\\images\\CSLogo.png");
        courseDataManager.setRightFooterLink("C:\\Users\\Navin\\CourseSiteGenerator\\CSG\\public_html\\images\\SBUWhiteShieldLogo.jpg");
        courseDataManager.setTemplateDir("Navin\\CourseSiteGenerator\\CSG\\data");
        courseDataManager.setExportDir("C:\\Users\\Navin");
        courseDataManager.setStyleSheet("sea_wolf.css");
        
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
                  
        
       
	JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_COURSE, courseJson)
                .add(JSON_COURSETEMPLATE, courseArray)
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
		.add(JSON_RECITATION, recitaitonArray)
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
    
    //Main Method
    public static void main(String[] args){
        try {
            AppDataComponent data = new TAData(app);
            AppDataComponent recData = new RecitationData(app);
            AppDataComponent schData = new ScheduleData(app);
            AppDataComponent projectData = new ProjectData(app);
            AppDataComponent courseData = new CourseData(app);
            String filePath = "C:\\Users\\Navin\\CourseSiteGenerator\\CSG\\work\\SiteSaveTest.json";
            TestSave instance = new TestSave(app);
            instance.saveData(data, recData, schData, projectData, courseData, filePath);
        } catch (IOException ex) {
            Logger.getLogger(TestSave.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<TimeSlot> getOfficeHours() {
        return officeHours;
    }
    
    
    
    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveTAData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveRecitationData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveScheduleData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveTeamsAndStudentsData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveProjectsData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

