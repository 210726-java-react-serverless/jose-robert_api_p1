package com.revature.studentmanagement.services;

import com.revature.studentmanagement.datasource.documents.Courses;
import com.revature.studentmanagement.datasource.repos.CourseRepo;
import com.revature.studentmanagement.datasource.repos.RegistrationRepo;
import com.revature.studentmanagement.util.exceptions.InvalidRequestException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CourseServiceTestSuite {

    com.revature.studentmanagement.services.CourseService sut;

    private CourseRepo mockCourseRepo;
    private com.revature.studentmanagement.services.RegistrationService mockRegistrationService;

    @Before
    public void beforeEachTest() {
        mockCourseRepo = mock(CourseRepo.class);
        mockRegistrationService = mock(com.revature.studentmanagement.services.RegistrationService.class);
        sut = new com.revature.studentmanagement.services.CourseService(mockCourseRepo, mockRegistrationService);
    }

    @After
    public void afterEachTest() {
        sut = null;
    }

    @Test
    public void addCourseToCatalog_returnsCourse_givenValidCourse() {
        Courses expectedCourse = new Courses("valid", "valid", "valid", "valid");
        Courses validCourse = new Courses("valid", "valid", "valid", "valid");

        when(mockCourseRepo.save(any())).thenReturn(expectedCourse);

        Courses testResult = sut.addCourseToCatalog(validCourse);

        Assert.assertEquals(expectedCourse, testResult);
        verify(mockCourseRepo, times(1)).save(any());
    }

    @Test(expected = InvalidRequestException.class)
    public void addCourseToCatalog_throwsException_givenInvalidCourse() {
        Courses invalidCourse = new Courses("", "valid", "valid", "valid");

        try {
            sut.addCourseToCatalog(invalidCourse);
        } finally {
            verify(mockCourseRepo, times(0)).save(any());
        }
    }

    @Test
    public void deleteCourse_returnsTrue_givenValidCourse() {
        String validCourse = "valid";

        when(mockCourseRepo.deleteById(any())).thenReturn(true);

        boolean testResult = sut.deleteCourse(validCourse);

        Assert.assertTrue(testResult);
        verify(mockCourseRepo, times(1)).deleteById(any());
    }

    @Test
    public void deleteCourse_returnsFalse_givenInvalidCourse() {
        String invalidCourse = "";

        when(mockCourseRepo.deleteById(any())).thenReturn(false);

        boolean testResult = sut.deleteCourse(invalidCourse);

        Assert.assertFalse(testResult);
        verify(mockCourseRepo, times(1)).deleteById(any());
    }
}
