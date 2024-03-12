package ru.nsu.fit.g20202.vartazaryan.managerproject.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableMongoRepositories(basePackages = "ru.nsu.fit.g20202.vartazaryan.managerproject")
public class MongoConfig extends AbstractMongoClientConfiguration
{
    private static final String databaseName = "ticketsdb";
    @Override
    protected String getDatabaseName() {
        return databaseName;
    }
    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb://mongodb1:27017,mongodb2:27018,mongodb3:27019/?replicaSet=rs0");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }
    @Override
    public Collection getMappingBasePackages() {
        return Collections.singleton("ru.nsu.fit.g20202.vartazaryan.managerproject");
    }
}