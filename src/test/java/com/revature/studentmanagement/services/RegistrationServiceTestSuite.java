package com.revature.studentmanagement.services;

import com.revature.studentmanagement.datasource.documents.Courses;
import com.revature.studentmanagement.datasource.repos.RegistrationRepo;
import com.revature.studentmanagement.util.exceptions.DataSourceException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegistrationServiceTestSuite {

    RegistrationService sut;

    private RegistrationRepo mockRegistrationRepo;

    @Before
    public void beforeEachTest() {
        mockRegistrationRepo = mock(RegistrationRepo.class);
        sut = new RegistrationService(mockRegistrationRepo);
    }

    @After
    public void afterEachTest() {
        sut = null;
    }

    @Test
    public void findByCourseCode_returnsCourse_givenValidInput() {
        Courses expectedCourse = new Courses("valid", "valid", "valid", "valid");
        String validCourseCode = "valid";

        when(mockRegistrationRepo.findByCourseCode(any())).thenReturn(expectedCourse);

        Courses testResult = sut.findByCourseCode(validCourseCode);

        Assert.assertEquals(expectedCourse, testResult);
    }

    @Test(expected = DataSourceException.class)
    public void findByCourseCode_throwsException_whenNoClassFound() {
        String invalidCourseCode = "";

        when(mockRegistrationRepo.findByCourseCode(any())).thenThrow(DataSourceException.class);
        try {
            sut.findByCourseCode(invalidCourseCode);
        } finally {
            verify(mockRegistrationRepo, times(1)).findByCourseCode(any());
        }
    }

    @Test
    public void listCoursesAvailable_returnsCourseList() {
        List<Courses> validCourseList = new ArrayList<>();

        when(mockRegistrationRepo.getAllCourses()).thenReturn(validCourseList);

        List<Courses> testResult = sut.listCoursesAvailable();

        Assert.assertEquals(validCourseList, testResult);
    }

    @Test
    public void registerForClass_success_givenValidInfo() {
        Courses validCourse = new Courses("valid", "valid", "valid", "valid");
        String validUser = "valid";
        String validCourseCode = "valid";

        when(mockRegistrationRepo.findByCourseCode(any())).thenReturn(validCourse);

        try {
            sut.registerForClass(validUser, validCourseCode);
        } finally {
            verify(mockRegistrationRepo, times(1)).findByCourseCode(any());
            verify(mockRegistrationRepo, times(1)).addRegisteredCourses(any(), any());
        }
    }

    @Test
    public void unregisterFromCourse_success_givenValidInfo() {
        String validUser = "valid";
        String validCourseCode = "valid";

        try {
            sut.unregisterFromCourse(validUser, validCourseCode);
        } finally {
            verify(mockRegistrationRepo, times(1)).unRegisterCourse(any(), any());
        }
    }

    @Test
    public void coursesRegisteredTo_returnsCoursesList() {
        List<Courses> expectedResult = new ArrayList<>();

        String validUsername = "valid";
        List<String> validStringOfCourses = new ArrayList<>();
        Courses validCourse = new Courses("valid", "valid", "valid", "valid");

        when(mockRegistrationRepo.coursesRegisteredFor(any())).thenReturn(validStringOfCourses);
        when(mockRegistrationRepo.findByCourseCode(any())).thenReturn(validCourse);

        List<Courses> testResult = sut.coursesRegisteredTo(validUsername);

        Assert.assertEquals(expectedResult, testResult);
    }

    @Test(expected = DataSourceException.class)
    public void coursesRegisteredTo_throwsException_givenInvalidUsername() {
        String invalidUsername = "";

        when(mockRegistrationRepo.coursesRegisteredFor(any())).thenThrow(DataSourceException.class);

        try {
            List<Courses> testResult = sut.coursesRegisteredTo(invalidUsername);
        } finally {
            verify(mockRegistrationRepo, times(0)).findByCourseCode(any());
        }
    }

    @Test
    public void removeStudentsRegisteredToCourse_success_givenValidCourseCode() {
        String validCourseCode = "valid";

        try {
            mockRegistrationRepo.unRegisterStudentsFromDeletedCourse(validCourseCode);
        } finally {
            verify(mockRegistrationRepo, times(1)).unRegisterStudentsFromDeletedCourse(any());
        }
    }
}
