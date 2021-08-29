package com.revature.studentmanagement.services;


import com.revature.studentmanagement.datasource.documents.Courses;
import com.revature.studentmanagement.datasource.repos.CourseRepo;
import com.revature.studentmanagement.util.exceptions.DataSourceException;
import com.revature.studentmanagement.util.exceptions.InvalidRequestException;

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


    public boolean deleteCourse(String course){
        return courseRepository.deleteById(course);
    }



    public Courses updateCourse(Courses c, String field, String changeTo){
        try {
            courseRepository.updateCourse(c, field, changeTo);
            return registrationService.findByCourseCode(c.getCourse_code());
        } catch (Exception e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }

}
