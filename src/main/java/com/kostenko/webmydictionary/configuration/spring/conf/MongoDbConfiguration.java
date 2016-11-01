package com.kostenko.webmydictionary.configuration.spring.conf;

import com.kostenko.webmydictionary.configuration.AppConfigLoader;
import com.kostenko.webmydictionary.configuration.AppConfigLoader.Property;
import com.kostenko.webmydictionary.dao.RoleRepository;
import com.kostenko.webmydictionary.dao.UnitRepository;
import com.kostenko.webmydictionary.dao.UserRepository;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MongoDbConfiguration {
    private final AppConfigLoader appConfigLoader;

    @Autowired
    public MongoDbConfiguration(AppConfigLoader appConfigLoader) {
        this.appConfigLoader = appConfigLoader;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        final String host = appConfigLoader.getProperty(Property.DB_HOST);
        final String port = appConfigLoader.getProperty(Property.DB_PORT);
        final String dbName = appConfigLoader.getProperty(Property.DB_NAME);
        final String username = appConfigLoader.getProperty(Property.DB_USER);
        final String password = appConfigLoader.getProperty(Property.DB_PASSWORD);
        final String serverFormattedString = String.format("%s:%s", host, port);
        final ServerAddress serverAddress = new ServerAddress(serverFormattedString);
        final MongoCredential mongoCredential = MongoCredential.createCredential(username, dbName, password.toCharArray());
        final List<MongoCredential> mongoCredentialList = new ArrayList<>();
        mongoCredentialList.add(mongoCredential);
        final MongoClient mongo = new MongoClient(serverAddress, mongoCredentialList);
        final MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongo, dbName);
        return new MongoTemplate(mongoDbFactory);
    }
}
