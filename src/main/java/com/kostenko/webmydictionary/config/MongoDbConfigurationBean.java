package com.kostenko.webmydictionary.config;

import com.kostenko.webmydictionary.config.AppConfig.Property;
import com.kostenko.webmydictionary.dao.RoleRepository;
import com.kostenko.webmydictionary.dao.UnitRepository;
import com.kostenko.webmydictionary.dao.UserRepository;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class MongoDbConfigurationBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDbConfigurationBean.class);

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        final String host = AppConfig.getProperty(Property.DB_HOST);
        final String port = AppConfig.getProperty(Property.DB_PORT);
        final String dbName = AppConfig.getProperty(Property.DB_NAME);
        final String username = AppConfig.getProperty(Property.DB_USER);
        final String password = AppConfig.getProperty(Property.DB_PASSWORD);

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
