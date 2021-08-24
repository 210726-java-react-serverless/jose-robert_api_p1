package com.Revature.StudentManagementApp.services;

import com.Revature.StudentManagementApp.dataSource.documents.Courses;
import com.Revature.StudentManagementApp.dataSource.documents.Student;
import com.Revature.StudentManagementApp.dataSource.repos.RegistrationRepo;
import com.Revature.StudentManagementApp.util.isWithinRegistrationDate;

import java.util.ArrayList;
import java.util.List;

public class RegistrationService {
    private final RegistrationRepo registrationRepository;


    public RegistrationService(RegistrationRepo registrationRepository) {
        this.registrationRepository = registrationRepository;

    }

    //TODO add methods to interact with courses models



    public Courses findByCourseCode(String s){

        try{
            return registrationRepository.findByCourseCode(s);
        }catch (Exception o){
            o.printStackTrace();

        }

        return null;
    }



    //TODO changed this to return list instead of void
    public List<Courses> listCoursesOffered(){
        List<Courses> coursesList = new ArrayList();
        coursesList = registrationRepository.getAllCourses();

        for( int i = 0; i < coursesList.size(); i++)
        {
            System.out.println("\n"+(i+1) + ")" +coursesList.get(i).getCourse_name() + "        Course code--> " + coursesList.get(i).getCourse_code());
            System.out.println("Registration and Drop Window:   " + coursesList.get(i).getStart_date() + "-" + coursesList.get(i).getEnd_date());
        }


        return coursesList;
    }


    public List<Courses> listCoursesAvailable(){
        List<Courses> coursesList = new ArrayList();

        List<Courses> availableCourses = new ArrayList();
        coursesList = registrationRepository.getAllCourses();


        for (int i =0; i < coursesList.size(); i++) {

            String date = coursesList.get(i).getStart_date();
            String dateEnd = coursesList.get(i).getEnd_date();

            ///When a student wants to register for a class this will check if it is open for registration
            isWithinRegistrationDate IWR = new isWithinRegistrationDate(date, dateEnd);

            if (IWR.check()){
                availableCourses.add(coursesList.get(i));
            }
        }
        for( int i = 0; i < availableCourses.size(); i++)
        {
            System.out.println(i + ")" +availableCourses.get(i).getCourse_name());
            System.out.println("Course code : "+ availableCourses.get(i).getCourse_code() + "\n");
        }
        return availableCourses;
    }


    public void registerForClass(String c, String s){
        Courses course = registrationRepository.findByCourseCode(c);
        registrationRepository.addRegisteredCourses(course, s);
    }



    public void unregisterFromCourse(String c, String s){
        registrationRepository.unRegisterCourse(c, s);
    }



    public List<String> coursesRegisteredTo(String username){
        List<String> courses = registrationRepository.coursesRegisteredFor(username);
        return courses;
    }


    public void removeStudentsRegisteredToCourse(String course_code){
        registrationRepository.unRegisterStudentsFromDeletedCourse(course_code);
    }

}
