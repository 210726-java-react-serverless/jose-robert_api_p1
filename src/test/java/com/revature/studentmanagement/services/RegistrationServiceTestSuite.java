package com.revature.studentmanagement.services;

import com.revature.studentmanagement.datasource.documents.Courses;
import com.revature.studentmanagement.datasource.repos.RegistrationRepo;
import com.revature.studentmanagement.util.exceptions.DataSourceException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
}
