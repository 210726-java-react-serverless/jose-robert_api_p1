package com.revature.studentmanagement.services;

import com.revature.studentmanagement.datasource.documents.Faculty;
import com.revature.studentmanagement.datasource.repos.FacultyRepo;
import com.revature.studentmanagement.util.exceptions.AuthenticationException;
import com.revature.studentmanagement.util.exceptions.DataSourceException;
import com.revature.studentmanagement.util.exceptions.InvalidRequestException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FacultyServiceTestSuite {

    FacultyService sut;

    private FacultyRepo mockFacultyRepo;

    @Before
    public void beforeEachTest() {
        mockFacultyRepo = mock(FacultyRepo.class);
        sut = new FacultyService(mockFacultyRepo);
    }

    @After
    public void afterEachTest() {
        sut = null;
    }

    @Test
    public void register_returnsFaculty_givenValidUser() {
        Faculty expectedFaculty = new Faculty(0, "valid", "valid", "valid", "valid", "valid", "valid", "valid", "valid");
        Faculty validFaculty = new Faculty(0, "valid", "valid", "valid", "valid", "valid", "valid", "valid", "valid");

        when(mockFacultyRepo.save(any())).thenReturn(expectedFaculty);

        Faculty testResult = sut.register(validFaculty);

        Assert.assertEquals(expectedFaculty, testResult);
        verify(mockFacultyRepo, times(1)).save(any());
    }

    @Test(expected = InvalidRequestException.class)
    public void register_throwsException_givenInvalidUser() {
        Faculty invalidFaculty = new Faculty(0, "valid", "valid", "valid", "valid", "valid", null, "valid", "valid");

        try {
            sut.register(invalidFaculty);
        } finally {
            verify(mockFacultyRepo, times(0)).save(any());
        }
    }

    @Test
    public void login_returnsFaculty_givenValidUser() {
        Faculty expectedFaculty = new Faculty(0, "valid", "valid", "valid", "valid", "valid", "valid", "valid", "valid");

        String validUser = "valid";
        String validPassword = "valid";

        when(mockFacultyRepo.findUserByCredentials(any(), any())).thenReturn(expectedFaculty);

        Faculty testResult = sut.login(validUser, validPassword);

        Assert.assertEquals(expectedFaculty, testResult);
    }

    @Test(expected = AuthenticationException.class)
    public void login_throwsAuthenticationException_givenEmptyFields() {
        String invalidUser = "";
        String invalidPassword = "";

        when(mockFacultyRepo.findUserByCredentials(any(), any())).thenReturn(null);

        try {
            sut.login(invalidUser, invalidPassword);
        } finally {
            verify(mockFacultyRepo, times(0)).findUserByCredentials(any(), any());
        }
    }
}
