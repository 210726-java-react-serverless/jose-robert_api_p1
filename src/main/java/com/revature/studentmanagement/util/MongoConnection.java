package com.revature.studentmanagement.util;

import com.revature.studentmanagement.util.exceptions.DataSourceException;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoConnection {


    private final MongoClient mongoClient;
    private static final MongoConnection mongoClientFactory = new MongoConnection();


    private MongoConnection() {

        Properties appProperties = new Properties();

        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            appProperties.load(loader.getResourceAsStream("properties.properties"));

            String ipAddress = appProperties.getProperty("ipAddress");
            int port = Integer.parseInt(appProperties.getProperty("port"));
            String dbName = appProperties.getProperty("dbName");
            String username = appProperties.getProperty("dbUsername");
            char[] password = appProperties.getProperty("dbPassword").toCharArray();

            List<ServerAddress> hosts = Collections.singletonList(new ServerAddress(ipAddress, port));
            MongoCredential credentials = MongoCredential.createScramSha1Credential(username, dbName, password);
            CodecRegistry defaultCodecRegistry = getDefaultCodecRegistry();
            PojoCodecProvider pojoCodecProvider= PojoCodecProvider.builder().automatic(true).build();
            CodecRegistry pojoCodecRegistry = fromRegistries(defaultCodecRegistry, fromProviders(pojoCodecProvider));

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyToClusterSettings(builder -> builder.hosts(hosts))
                    .credential(credentials)
                    .codecRegistry(pojoCodecRegistry)
                    .build();

            this.mongoClient = MongoClients.create(settings);

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace(); // TODO log this to a file
            throw new DataSourceException("Unable to load database properties file.", fnfe);
        } catch(Exception e){
            e.printStackTrace(); // TODO log this to a file
            throw new DataSourceException("An unexpected exception occurred.", e);
        }

    }

    public void closeIt(){
        mongoClient.close();
    }

    public static MongoConnection getInstance(){
        return mongoClientFactory;
    }

    public MongoClient getConnection(){
        return mongoClient;
    }

}
