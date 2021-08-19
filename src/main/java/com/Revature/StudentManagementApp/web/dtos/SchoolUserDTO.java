package com.Revature.StudentManagementApp.web.dtos;

import com.Revature.StudentManagementApp.dataSource.documents.Address;
import com.Revature.StudentManagementApp.dataSource.documents.SchoolUser;

import java.util.Objects;

public class SchoolUserDTO {


    private String first_name;
    private String last_name;
    private String DOB;
    private String phone_num;
    private String user_name;
    private String email;
    private Address address;



    SchoolUserDTO(SchoolUser user){
        this.first_name=user.getFirst_name();
        this.last_name=user.getLast_name();
        this.DOB=user.getDOB();
        this.phone_num=user.getPhone_num();
        this.user_name = user.getUser_name();
        this.email = user.getEmail();
        this.address=user.getAddress();
    }


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SchoolUserDTO)) return false;
        SchoolUserDTO that = (SchoolUserDTO) o;
        return Objects.equals(getFirst_name(), that.getFirst_name()) && Objects.equals(getLast_name(), that.getLast_name()) && Objects.equals(getDOB(), that.getDOB()) && Objects.equals(getPhone_num(), that.getPhone_num()) && Objects.equals(getUser_name(), that.getUser_name()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getAddress(), that.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirst_name(), getLast_name(), getDOB(), getPhone_num(), getUser_name(), getEmail(), getAddress());
    }


    @Override
    public String toString() {
        return "SchoolUserDTO{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", DOB='" + DOB + '\'' +
                ", phone_num='" + phone_num + '\'' +
                ", user_name='" + user_name + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                '}';
    }
}
