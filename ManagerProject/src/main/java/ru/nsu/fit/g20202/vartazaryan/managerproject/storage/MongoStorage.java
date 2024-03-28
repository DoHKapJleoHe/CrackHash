package ru.nsu.fit.g20202.vartazaryan.managerproject.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.CrackDTO;

import java.util.List;
import java.util.UUID;

@Component
public class MongoStorage implements Storage {
    private final MongoTemplate mongoTemplate;
    private final Logger logger = LoggerFactory.getLogger(MongoStorage.class);

    public MongoStorage(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Ticket addNewTicket(CrackDTO dto) {
        UUID id = UUID.randomUUID();
        Ticket t = new Ticket(id, dto.getHash(), dto.getMaxLength());

        return mongoTemplate.insert(t);
    }

    @Override
    public Ticket getTicket(String id) {
        return mongoTemplate.findById(id, Ticket.class);
    }

    @Override
    public void deleteTicket(String id) {
        mongoTemplate.remove(
                Query.query(Criteria.where("ticketId").is(UUID.fromString(id))),
                Ticket.class
        );
        //mongoTemplate.remove(id);
    }

    @Override
    public void deleteAllTickets() {

    }

    @Override
    public void updateTicket(String id, List<String> data) {

    }

    @Override
    public int getStorageSize() {
        return 0;
    }
}
