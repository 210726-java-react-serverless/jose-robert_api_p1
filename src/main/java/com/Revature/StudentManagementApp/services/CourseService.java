package com.Revature.StudentManagementApp.services;


import com.Revature.StudentManagementApp.dataSource.documents.Courses;
import com.Revature.StudentManagementApp.dataSource.repos.CourseRepo;
import com.Revature.StudentManagementApp.util.exceptions.InvalidRequestException;

import java.util.List;

public class CourseService {


    private final CourseRepo courseRepository;
    private final RegistrationService registrationService;


    public CourseService(CourseRepo courseRepository, RegistrationService registrationService){
        this.courseRepository = courseRepository;
        this.registrationService = registrationService;

    }


    public Courses addCourseToCatalog(Courses newCourse){
        if (!isCourseValid(newCourse)) {
            throw new InvalidRequestException("Invalid user data provided!");
        }
        return courseRepository.save(newCourse);
    }



    public boolean isCourseValid(Courses newCourse){
        if (newCourse == null) return false;
        if (newCourse.getCourse_code() == null || newCourse.getCourse_code().trim().equals("")) return false;
        if (newCourse.getCourse_name() == null || newCourse.getCourse_name().trim().equals("")) return false;
        if (newCourse.getStart_date() == null || newCourse.getStart_date().trim().equals("")) return false;
        if (newCourse.getEnd_date() == null || newCourse.getEnd_date().trim().equals("")) return false;
        return true;


    }


    public void deleteCourse(String course){
        courseRepository.deleteById(course);
    }



    public Courses updateCourse(Courses c, String field, String changeTo){

        courseRepository.updateCourse(c, field, changeTo);


        return registrationService.findByCourseCode(c.getCourse_code());

    }

}
