package com.revature.studentmanagement.datasource.repos;

import com.revature.studentmanagement.datasource.documents.Student;
import com.revature.studentmanagement.util.MongoConnection;
import com.revature.studentmanagement.util.exceptions.DataSourceException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudentRepo  implements CrudRepo<Student> {

    private final Logger logger = LoggerFactory.getLogger(StudentRepo.class);

    @Override
    public Student findById(String id) {
        return null;
    }

    @Override
    public Student save(Student user) {
        try {
            MongoConnection mc = MongoConnection.getInstance();
            MongoClient mongoClient = mc.getConnection();

            MongoDatabase p0 = mongoClient.getDatabase("project");
            MongoCollection<Document> usersCollection = p0.getCollection("students");

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

            Document StuDoc = new Document("major", user.getMajor())
                    .append( "user", newUserDoc);

            usersCollection.insertOne(StuDoc);

            user.setStudent_Id(StuDoc.get("_id").toString());

            return user;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }


    @Override
    public boolean update(Student updatedResource) {
        return false;
    }


    @Override
    public boolean deleteById(String id) {
        return false;
    }

    public Student findUserByCredentials(String username, String password)  {
        try {
            MongoClient mongoClient = MongoConnection.getInstance().getConnection();
            MongoDatabase p0 = mongoClient.getDatabase("project");
            MongoCollection<Document> usersCollection = p0.getCollection( "students");

            Document queryDoc = new Document("user.user_name", username)
                    .append("user.password", password);

            Document authDoc = usersCollection.find(queryDoc).first();

            if (authDoc == null) {
                return null;
            }

            System.out.println("authDoc is not null!");

            ObjectMapper mapper = new ObjectMapper();
            Student student = mapper.readValue(authDoc.toJson(), Student.class);
            student.setStudent_Id(authDoc.get("_id").toString());

            return student;

        } catch (JsonMappingException e) {
            logger.error(e.getMessage());
            throw new DataSourceException("An exception occurred while mapping the object", e);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DataSourceException("An unexpected error occurred", e);
        }
    }
}
