package com.kostenko.webmydictionary.config;

import com.kostenko.webmydictionary.dao.RoleRepository;
import com.kostenko.webmydictionary.dao.UnitRepository;
import com.kostenko.webmydictionary.dao.UserRepository;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMongoRepositories(basePackageClasses = {RoleRepository.class, UserRepository.class, UnitRepository.class})
public class DbConfig {
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        String host = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
        String port = System.getenv("OPENSHIFT_MONGODB_DB_PORT");
        String dbName = System.getenv("OPENSHIFT_APP_NAME");
        String username = "admin";
        String password = "Uv5C5gJrZQyS";
        String serverAddress = String.format("%s:%s", host, port);
        ServerAddress address = new ServerAddress(serverAddress);
        MongoCredential credential = MongoCredential.createCredential(username, dbName, password.toCharArray());
        List<MongoCredential> credentialList = new ArrayList<>();
        credentialList.add(credential);
        MongoClient mongo = new MongoClient(address, credentialList);
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo, dbName);
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
        return mongoTemplate;
    }
}
