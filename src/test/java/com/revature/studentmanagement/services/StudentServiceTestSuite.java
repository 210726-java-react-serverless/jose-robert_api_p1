package com.revature.studentmanagement.services;

import com.revature.studentmanagement.datasource.documents.Student;
import com.revature.studentmanagement.datasource.repos.StudentRepo;
import com.revature.studentmanagement.util.exceptions.AuthenticationException;
import com.revature.studentmanagement.util.exceptions.DataSourceException;
import com.revature.studentmanagement.util.exceptions.InvalidRequestException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StudentServiceTestSuite {

    StudentService sut;

    private StudentRepo mockStudentRepo;

    @Before
    public void beforeEachTest() {
        mockStudentRepo = mock(StudentRepo.class);
        sut = new StudentService(mockStudentRepo);
    }

    @After
    public void afterEachTest() {
        sut = null;
    }

    @Test
    public void register_returnsStudent_givenValidUser() {
        Student expectedResult = new Student("valid", "valid", "valid", "valid", "valid", "valid", "valid", "valid");
        Student validStudent = new Student("valid", "valid", "valid", "valid", "valid", "valid", "valid", "valid");

        when(mockStudentRepo.save(any())).thenReturn(expectedResult);

        Student testResult = sut.register(validStudent);

        Assert.assertEquals(expectedResult, testResult);
    }

    @Test(expected = InvalidRequestException.class)
    public void register_throwsException_givenInvalidUser() {
        Student invalidStudent = new Student("valid", "valid", "valid", "valid", "valid", "valid", null, "valid");

        try {
            sut.register(invalidStudent);
        } finally {
            verify(mockStudentRepo, times(0)).save(any());
        }
    }

    @Test(expected = DataSourceException.class)
    public void register_throwsException_givenDuplicateUser() {
        Student duplicateUser = new Student("valid", "valid", "valid", "valid", "valid", "valid", "valid", "valid");

        when(mockStudentRepo.save(any())).thenThrow(DataSourceException.class);

        Student testResult = sut.register(duplicateUser);

        Assert.assertEquals(null, testResult);
    }

    @Test
    public void login_returnsStudent_givenValidCredentials() {
        Student expectedResult = new Student("valid", "valid", "valid", "valid", "valid", "valid", "valid", "valid");

        String validUser = "valid";
        String validPassword = "valid";

        when(mockStudentRepo.findUserByCredentials(any(), any())).thenReturn(expectedResult);

        Student testResult = sut.login(validUser, validPassword);

        Assert.assertEquals(expectedResult, testResult);
    }

    @Test(expected = InvalidRequestException.class)
    public void login_throwsException_givenEmptyFields() {
        String invalidUser = "";
        String invalidPass = "";

        Student testResult = sut.login(invalidUser, invalidPass);

        Assert.assertEquals(null, testResult);
        verify(mockStudentRepo, times(0)).findUserByCredentials(any(), any());
    }

    @Test(expected = AuthenticationException.class)
    public void login_throwsException_givenBadCredentials() {
        String badUser = "valid";
        String badPass = "valid";

        when(mockStudentRepo.findUserByCredentials(any(), any())).thenReturn(null);

        Student testResult = sut.login(badUser, badPass);

        Assert.assertEquals(null, testResult);
    }
}
