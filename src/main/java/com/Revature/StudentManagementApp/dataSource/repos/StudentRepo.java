package com.Revature.StudentManagementApp.dataSource.repos;

import com.Revature.StudentManagementApp.dataSource.documents.Student;
import com.Revature.StudentManagementApp.util.MongoConnection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class StudentRepo  implements CrudRepo<Student> {

    public Student findById(String id) {
        return null;
    }


    public Student save(Student user) {
        MongoConnection mc = MongoConnection.getInstance();
        MongoClient mongoClient = mc.getConnection();

        MongoDatabase p0 = mongoClient.getDatabase("jose_project_0");
        MongoCollection<Document> usersCollection = p0.getCollection("students");

        Document addressDoc = new Document("number", user.getUser().getAddress().getNumber())
                .append("street", user.getUser().getAddress().getStreet())
                .append("city", user.getUser().getAddress().getCity())
                .append("state", user.getUser().getAddress().getState())
                .append("country", user.getUser().getAddress().getCountry())
                .append("zip_code", user.getUser().getAddress().getZip_code());

        Document newUserDoc = new Document("first_name", user.getUser().getFirst_name())
                .append("last_name", user.getUser().getLast_name())
                .append("DOB", user.getUser().getDOB())
                .append("phone_num", user.getUser().getPhone_num())
                .append("user_name", user.getUser().getUser_name())
                .append("password", user.getUser().getPassword())
                .append("email", user.getUser().getEmail())
                .append("address", addressDoc);

        Document StuDoc = new Document("major", user.getMajor())
                .append( "user", newUserDoc);
        ;

        usersCollection.insertOne(StuDoc);


        user.setStudent_Id(StuDoc.get("_id").toString());

        return user;

    }


    public boolean update(Student updatedResource) {

        return false;
    }


    public boolean deleteById(int id) {
        return false;
    }

    public Student findUserByCredentials(String username, String password)  {

        MongoConnection mc = MongoConnection.getInstance();
        MongoClient mongoClient = mc.getConnection();

        MongoDatabase p0 = mongoClient.getDatabase("jose_project_0");
        Student student = null;
        MongoCollection<Document> usersCollection = p0.getCollection( "students");
        Document queryDoc = new Document("user.user_name", username).append("user.password", password);
        Document studentDoc = usersCollection.find(queryDoc).first();
        System.out.println(studentDoc);




        ObjectMapper mapper = new ObjectMapper();
        try {
            assert studentDoc != null;
            student = mapper.readValue(studentDoc.toJson(), Student.class);
            student.setStudent_Id(studentDoc.get("_id").toString());
            return student;

        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
