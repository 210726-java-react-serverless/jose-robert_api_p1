package com.Revature.StudentManagementApp.dataSource.documents;

public class Address {

    String number;
    String street;
    String city;
    String state;
    String country;
    String zip_code;

    public Address(){};


    public Address(String number, String street, String city, String state, String country, String zip_code) {
        this.number = number;
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zip_code = zip_code;
    }


    @Override
    public String toString() {
        return "Address{" +
                "number='" + number + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zip_code='" + zip_code + '\'' +
                '}';
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }



}
