package ru.nsu.fit.g20202.vartazaryan.managerproject.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.Ticket;

public interface TicketsRepository extends MongoRepository<Ticket, String>
{
}
