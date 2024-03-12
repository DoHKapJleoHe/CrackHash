package ru.nsu.fit.g20202.vartazaryan.managerproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.nsu.fit.g20202.vartazaryan.managerproject.mongo.TicketsRepository;

@SpringBootApplication
public class ManagerProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerProjectApplication.class, args);
    }

}
