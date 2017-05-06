/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function changeCourse(){
    var json = "./js/CourseData.json";
    loadCourseData(json, makeBanner);
}

function loadCourseData(json, callback){
    $.getJSON(json, function(json){
        callback(json);
    });
}

function makeBanner(json){
    var course = json.course;
    var subject = course.subject;
    var number = course.number;
    var sem = course.semester;
    var title = course.title;
    var year = course.year;
    var banner = course.banner
    var leftFooter = course.leftFooter;
    var rightFooter = course.rightFooter;
    var instructorName = course.instructorName;
    var instructorHome = course.instructorHome;
    
    document.getElementById("banner").innerHTML = subject + " " + number 
    + " - " + sem + " " + year + "<br />" + title;
    
    jvj = "<a><img class='sunysb' src=./images/" + leftFooter.substring(leftFooter.lastIndexOf("\\") + 1) + " style='float:left'></a>" 
        + "<a><img src=./images/" +  rightFooter.substring(rightFooter.lastIndexOf("\\") + 1) + " style='float:right'></a>";

    document.getElementById("footer").innerHTML = "<a><img class='sunysb' src=./images/" + leftFooter.substring(leftFooter.lastIndexOf("\\") + 1) + " style='float:left'></a>" 
        + "<a><img src=./images/" +  rightFooter.substring(rightFooter.lastIndexOf("\\") + 1) + " style='float:right'></a>";
    
    document.getElementById("navbar").innerHTML = "<a><img class='sbu_navbar' src=./images/" + banner.substring(banner.lastIndexOf("\\") + 1) + "></a>" + makeNavBar(json);
            //"<a><img class='sbu_navbar' src=./images/" + banner.substring(banner.lastIndexOf("\\") + 1) + "></a>";
    
    document.getElementById("instructor_link").innerHTML = "<a href=" + instructorHome + ">" + instructorName + "</a></span>";
}

function makeNavBar(json){
    var courseTemplate = json.courseTemplate;
    var pathname = location.pathname.substring(location.pathname.lastIndexOf("/") + 1);
    var string = "";
    
    if(courseTemplate[0].use == true){
        if(pathname == "index.html"){
            string += "<a class='open_nav' href='index.html' id='home_link'>Home</a>"; 
        }
        else{
            string += "<a class='nav' href='index.html' id='home_link'>Home</a>";
        }
    }
    if(courseTemplate[1].use == true){
       if(pathname == "syllabus.html"){
            string += "<a class='open_nav' href='syllabus.html' id='syllabus_link'>Syllabus</a>";
        }
        else{
            string += "<a class='nav' href='syllabus.html' id='syllabus_link'>Syllabus</a>";
        }
    }
    if(courseTemplate[2].use == true){
        if(pathname == "schedule.html"){
            string += "<a class='open_nav' href='schedule.html' id='schedule_link'>Schedule</a>";
        }
        else{
            string += "<a class='nav' href='schedule.html' id='schedule_link'>Schedule</a>";
        }   
    }
    if(courseTemplate[3].use == true){
        if(pathname == "hws.html"){
           string += "<a class='open_nav' href='hws.html' id='hws_link'>HWs</a>"; 
        }
        else{
            string += "<a class='nav' href='hws.html' id='hws_link'>HWs</a>";
        }
    }
    if(courseTemplate[4].use == true){
        if(pathname == "projects.html"){
           string += "<a id='projects_link' class='open_nav' href='projects.html'>Projects</a>";
        }
        else{
            string += "<a id='projects_link' class='nav' href='projects.html'>Projects</a>";
        }
    }
    
    return string;
    
}

