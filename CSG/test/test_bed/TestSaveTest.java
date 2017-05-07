/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_bed;

import csg.CSGApp;
import csg.data.CourseData;
import csg.data.ProjectData;
import csg.data.RecitationData;
import csg.data.ScheduleData;
import csg.data.TAData;
import csg.data.TeachingAssistant;
import csg.files.CSGFiles;
import csg.files.TimeSlot;
import csg.ui.CSGWorkspace;
import djf.components.AppDataComponent;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static test_bed.TestSave.JSON_BANNER;
import static test_bed.TestSave.JSON_COLOR;
import static test_bed.TestSave.JSON_COURSE;
import static test_bed.TestSave.JSON_COURSETEMPLATE;
import static test_bed.TestSave.JSON_CRITERIA;
import static test_bed.TestSave.JSON_DATE;
import static test_bed.TestSave.JSON_DAY;
import static test_bed.TestSave.JSON_DAYTIME;
import static test_bed.TestSave.JSON_EMAIL;
import static test_bed.TestSave.JSON_ENDDAY;
import static test_bed.TestSave.JSON_ENDMONTH;
import static test_bed.TestSave.JSON_ENDYEAR;
import static test_bed.TestSave.JSON_END_HOUR;
import static test_bed.TestSave.JSON_EXPORTDIR;
import static test_bed.TestSave.JSON_FILENAME;
import static test_bed.TestSave.JSON_FIRSTNAME;
import static test_bed.TestSave.JSON_FIRSTTA;
import static test_bed.TestSave.JSON_INSTRUCTOR;
import static test_bed.TestSave.JSON_INSTRUCTORHOME;
import static test_bed.TestSave.JSON_INSTRUCTORNAME;
import static test_bed.TestSave.JSON_LASTNAME;
import static test_bed.TestSave.JSON_LEFTFOOTER;
import static test_bed.TestSave.JSON_LINK;
import static test_bed.TestSave.JSON_LOCATION;
import static test_bed.TestSave.JSON_MONTH;
import static test_bed.TestSave.JSON_NAME;
import static test_bed.TestSave.JSON_NAVBAR;
import static test_bed.TestSave.JSON_NUMBER;
import static test_bed.TestSave.JSON_OFFICE_HOURS;
import static test_bed.TestSave.JSON_RECITATION;
import static test_bed.TestSave.JSON_RIGHTFOOTER;
import static test_bed.TestSave.JSON_ROLE;
import static test_bed.TestSave.JSON_SCHEDULEITEM;
import static test_bed.TestSave.JSON_SCRIPT;
import static test_bed.TestSave.JSON_SECONDTA;
import static test_bed.TestSave.JSON_SECTION;
import static test_bed.TestSave.JSON_SEMESTER;
import static test_bed.TestSave.JSON_STARTDAY;
import static test_bed.TestSave.JSON_STARTMONTH;
import static test_bed.TestSave.JSON_STARTYEAR;
import static test_bed.TestSave.JSON_START_HOUR;
import static test_bed.TestSave.JSON_STUDENTS;
import static test_bed.TestSave.JSON_STYLESHEET;
import static test_bed.TestSave.JSON_TEAM;
import static test_bed.TestSave.JSON_TEAMS;
import static test_bed.TestSave.JSON_TEMPLATEDIR;
import static test_bed.TestSave.JSON_TEXTCOLOR;
import static test_bed.TestSave.JSON_TIME;
import static test_bed.TestSave.JSON_TITLE;
import static test_bed.TestSave.JSON_TOPIC;
import static test_bed.TestSave.JSON_TYPE;
import static test_bed.TestSave.JSON_UG;
import static test_bed.TestSave.JSON_UNDERGRAD_TAS;
import static test_bed.TestSave.JSON_USE;
import static test_bed.TestSave.JSON_YEAR;

/**
 *
 * @author Navin
 */
public class TestSaveTest {
    
    CSGApp app; 
    public TestSaveTest() {
        app = new CSGApp();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of loadData method, of class TestSave.
     */
    @Test
    public void testLoadData() throws Exception {
        System.out.println("loadData");
        TAData data = new TAData(app);
        RecitationData recData = new RecitationData(app);
        ScheduleData schData = new ScheduleData(app);
        ProjectData projectData = new ProjectData(app);
        CourseData courseData = new CourseData(app);
        String filePath = "./work/SiteSaveTest.json";
        
        TestSave instance = new TestSave(app);
        instance.loadData(data, recData, schData, projectData, courseData, filePath);
        
        InputStream is = new FileInputStream(filePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
        
        assertEquals(data.getMIN_START_HOUR(),Integer.parseInt(json.getString(JSON_START_HOUR)));
        assertEquals(data.getMAX_END_HOUR(),Integer.parseInt(json.getString(JSON_END_HOUR)));
        
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            boolean ug = jsonTA.getBoolean(JSON_UG);
            BooleanProperty isUndergrad = new SimpleBooleanProperty();
            isUndergrad.setValue(ug);
            assertEquals(data.getTA(name).getName(), name);
            assertEquals(data.getTA(name).getEmail(), email);
            assertEquals(data.getTA(name).isUndergrad().getValue(), ug);
        }
        
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            TimeSlot ts = new TimeSlot(day, time, name);
            assertEquals(instance.getOfficeHours().get(i).getDay(), day);
            assertEquals(instance.getOfficeHours().get(i).getTime(), time);
            assertEquals(instance.getOfficeHours().get(i).getName(), name);
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
            assertEquals(recData.getRecitation(section).getSection(), section);
            assertEquals(recData.getRecitation(section).getInstructor(), instructor);
            assertEquals(recData.getRecitation(section).getDayTime(), dayTime);
            assertEquals(recData.getRecitation(section).getLocation(), location);
            assertEquals(recData.getRecitation(section).getFirstTA(), firstTA);
            assertEquals(recData.getRecitation(section).getSecondTA(), secondTA);
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
            LocalDate date = LocalDate.of(year, month, day);
            assertEquals(schData.getScheduleItem(date).getType(), type);
            assertEquals(schData.getScheduleItem(date).getDate(), date);
            assertEquals(schData.getScheduleItem(date).getTime(), time);
            assertEquals(schData.getScheduleItem(date).getTitle(), title);
            assertEquals(schData.getScheduleItem(date).getTopic(), topic);
            assertEquals(schData.getScheduleItem(date).getLink(), link);
        }
        
        JsonArray jsonTeamArray = json.getJsonArray(JSON_TEAMS);
        for (int i = 0; i < jsonTeamArray.size(); i++) {
            JsonObject jsonTeam = jsonTeamArray.getJsonObject(i);
            String name = jsonTeam.getString(JSON_NAME);
            String color = jsonTeam.getString(JSON_COLOR);
            String textColor = jsonTeam.getString(JSON_TEXTCOLOR);  
            String link = jsonTeam.getString(JSON_LINK);
            assertEquals(projectData.getTeam(name).getName(), name);
            assertEquals(projectData.getTeam(name).getColor(), color);
            assertEquals(projectData.getTeam(name).getTextColor(), textColor);
            assertEquals(projectData.getTeam(name).getLink(), link);
        }
        
        JsonArray jsonStudentArray = json.getJsonArray(JSON_STUDENTS);
        for (int i = 0; i < jsonStudentArray.size(); i++) {
            JsonObject jsonStudent = jsonStudentArray.getJsonObject(i);
            String firstName = jsonStudent.getString(JSON_FIRSTNAME);
            String lastName = jsonStudent.getString(JSON_LASTNAME);
            String team = jsonStudent.getString(JSON_TEAM);  
            String role = jsonStudent.getString(JSON_ROLE);
            assertEquals(projectData.getStudent(firstName, lastName).getFirstName(), firstName);
            assertEquals(projectData.getStudent(firstName,lastName).getLastName(), lastName);
            assertEquals(projectData.getStudent(firstName,lastName).getTeam(), team);
            assertEquals(projectData.getStudent(firstName,lastName).getRole(), role);
        }
        
        JsonArray jsonTemplateArray = json.getJsonArray(JSON_COURSETEMPLATE);
        for (int i = 0; i < jsonTemplateArray.size(); i++) {
            JsonObject jsonTemplate = jsonTemplateArray.getJsonObject(i);
            boolean use = jsonTemplate.getBoolean(JSON_USE);
            String navbar = jsonTemplate.getString(JSON_NAVBAR);
            String fileName = jsonTemplate.getString(JSON_FILENAME);  
            String script = jsonTemplate.getString(JSON_SCRIPT);
            assertEquals(courseData.getTemplates().get(i).isUse().getValue(), use);
            assertEquals(courseData.getTemplates().get(i).getNavbarTitle(), navbar); 
            assertEquals(courseData.getTemplates().get(i).getFileName(), fileName);
            assertEquals(courseData.getTemplates().get(i).getScript(), script);
        }
        JsonObject courseJson = json.getJsonObject(JSON_COURSE);
        assertEquals(courseData.getSemester(), courseJson.getString(JSON_SEMESTER));
        assertEquals(courseData.getNumber(), courseJson.getString(JSON_NUMBER));
        assertEquals(courseData.getSemester(), courseJson.getString(JSON_SEMESTER));
        assertEquals(courseData.getYear(), courseJson.getString(JSON_YEAR));
        assertEquals(courseData.getTitle(), courseJson.getString(JSON_TITLE));
        assertEquals(courseData.getInsName(), courseJson.getString(JSON_INSTRUCTORNAME));
        assertEquals(courseData.getInsHome(), courseJson.getString(JSON_INSTRUCTORHOME));
        assertEquals(courseData.getBannerLink(), courseJson.getString(JSON_BANNER));
        assertEquals(courseData.getLeftFooterLink(), courseJson.getString(JSON_LEFTFOOTER));
        assertEquals(courseData.getRightFooterLink(), courseJson.getString(JSON_RIGHTFOOTER));
        assertEquals(courseData.getExportDir(), courseJson.getString(JSON_EXPORTDIR));
        assertEquals(courseData.getTemplateDir(),courseJson.getString(JSON_TEMPLATEDIR));
        assertEquals(courseData.getStyleSheet(), courseJson.getString(JSON_STYLESHEET));
        assertEquals(courseData.getStartDay(), json.getInt(JSON_STARTDAY));
        assertEquals(courseData.getStartMonth(), json.getInt(JSON_STARTMONTH));
        assertEquals(courseData.getStartYear(), json.getInt(JSON_STARTYEAR));
        assertEquals(courseData.getEndDay(), json.getInt(JSON_ENDDAY));
        assertEquals(courseData.getEndMonth(), json.getInt(JSON_ENDMONTH));
        assertEquals(courseData.getEndYear(), json.getInt(JSON_ENDYEAR));
        
    }


    
}
