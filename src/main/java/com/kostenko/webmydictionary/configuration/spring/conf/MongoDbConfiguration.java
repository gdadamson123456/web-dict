package com.kostenko.webmydictionary.configuration.spring.conf;

import com.kostenko.webmydictionary.configuration.AppConfigLoader;
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
public class MongoDbConfiguration {

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        final String host = AppConfigLoader.getProperty(AppConfigLoader.Property.DB_HOST);
        final String port = AppConfigLoader.getProperty(AppConfigLoader.Property.DB_PORT);
        final String dbName = AppConfigLoader.getProperty(AppConfigLoader.Property.DB_NAME);
        final String username = AppConfigLoader.getProperty(AppConfigLoader.Property.DB_USER);
        final String password = AppConfigLoader.getProperty(AppConfigLoader.Property.DB_PASSWORD);
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
