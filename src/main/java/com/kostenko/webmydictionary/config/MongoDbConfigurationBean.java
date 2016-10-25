package com.kostenko.webmydictionary.config;

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
    private static final Logger logger = LoggerFactory.getLogger(MongoDbConfigurationBean.class);
    private static final String MONGODB_HOST = "OPENSHIFT_MONGODB_DB_HOST";
    private static final String MONGODB_PORT = "OPENSHIFT_MONGODB_DB_PORT";
    private static final String MONGODB_DB_NAME = "OPENSHIFT_APP_NAME";

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        final String host = System.getenv(MONGODB_HOST);
        logger.debug("mongoHost: " + host);
        final String port = System.getenv(MONGODB_PORT);
        logger.debug("mongoPort: " + port);
        final String dbName = System.getenv(MONGODB_DB_NAME);
        logger.debug("mongoDbName: " + dbName);
        final String username = "admin"; //TODO: move all credentials to the properties
        final String password = "Uv5C5gJrZQyS";//TODO: move all credentials to the properties
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
