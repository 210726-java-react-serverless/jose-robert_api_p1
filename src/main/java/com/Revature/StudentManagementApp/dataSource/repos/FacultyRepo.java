package com.Revature.StudentManagementApp.dataSource.repos;

import com.Revature.StudentManagementApp.dataSource.documents.Faculty;
import com.Revature.StudentManagementApp.dataSource.documents.Student;
import com.Revature.StudentManagementApp.util.MongoConnection;
import com.Revature.StudentManagementApp.util.exceptions.DataSourceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class FacultyRepo implements CrudRepo<Faculty> {

    @Override
    public Faculty findById(String id) {
        return null;
    }


    @Override
    public Faculty save(Faculty user) {
        MongoConnection cm = MongoConnection.getInstance();
        MongoClient mongoClient = cm.getConnection();

        MongoDatabase p0 = mongoClient.getDatabase("project");
        MongoCollection<Document> usersCollection = p0.getCollection("faculty");

        Document newUserDoc;
        if (user.getUser().getAddress() == null) {
            newUserDoc = new Document("first_name", user.getUser().getFirst_name())
                    .append("last_name", user.getUser().getLast_name())
                    .append("DOB", user.getUser().getDOB())
                    .append("phone_num", user.getUser().getPhone_num())
                    .append("user_name", user.getUser().getUser_name())
                    .append("password", user.getUser().getPassword())
                    .append("email", user.getUser().getEmail());

        } else {
            Document addressDoc = new Document("number", user.getUser().getAddress().getNumber())
                    .append("street", user.getUser().getAddress().getStreet())
                    .append("city", user.getUser().getAddress().getCity())
                    .append("state", user.getUser().getAddress().getState())
                    .append("country", user.getUser().getAddress().getCountry())
                    .append("zip_code", user.getUser().getAddress().getZip_code());

            newUserDoc = new Document("first_name", user.getUser().getFirst_name())
                    .append("last_name", user.getUser().getLast_name())
                    .append("DOB", user.getUser().getDOB())
                    .append("phone_num", user.getUser().getPhone_num())
                    .append("user_name", user.getUser().getUser_name())
                    .append("password", user.getUser().getPassword())
                    .append("email", user.getUser().getEmail())
                    .append("address", addressDoc);
        }

        Document facultyDoc = new Document("Salary", user.getSalary())
                .append("department", user.getDepartment())
                .append( "user", newUserDoc);

        usersCollection.insertOne(facultyDoc);



        user.setId(facultyDoc.get("_id").toString());
        System.out.println(user);

        return user;
    }

    @Override
    public boolean update(Faculty updatedResource) {
        return false;
    }


    public boolean update(Student updatedResource) {
        return false;
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }

    public Faculty findUserByCredentials(String username, String password) {
        try {
            MongoClient mongoClient = MongoConnection.getInstance().getConnection();
            MongoDatabase p0 = mongoClient.getDatabase("project");
            MongoCollection<Document> usersCollection = p0.getCollection("faculty");

            Document queryDoc = new Document("user.user_name", username)
                    .append("user.password", password);

            Document facultyDoc = usersCollection.find(queryDoc).first();

            if (facultyDoc == null) {
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();
            Faculty authUser = mapper.readValue(facultyDoc.toJson(), Faculty.class);
            authUser.setId(facultyDoc.get("_id").toString());

            return authUser;

        } catch (JsonMappingException e) {
            throw new DataSourceException("An exception occurred while mapping the document", e);

        } catch (JsonProcessingException e) {
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }
}
