package ru.nsu.fit.g20202.vartazaryan.managerproject.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.CrackDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.TicketIdDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.ResultDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.UpdateDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.exceptions.NoHashException;
import ru.nsu.fit.g20202.vartazaryan.managerproject.exceptions.NoMaxLengthException;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.ClientService;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.Status;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.Storage;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.Ticket;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.TicketStorage;

@Service
public class ClientServiceImpl implements ClientService
{
    private final Storage storage;
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    public ClientServiceImpl(@Qualifier("mongoStorage") Storage storage) {
        this.storage = storage;
    }

    public TicketIdDTO processRequest(CrackDTO dto) {
        if (dto.getHash() == null) {
            throw new NoHashException();
        }
        if (dto.getMaxLength() == 0) {
            throw new NoMaxLengthException();
        }

        var task = storage.addNewTicket(dto);
        logger.info("New ticket registered!");

        return new TicketIdDTO(task.getTicketId().toString());
    }

    public ResultDTO getData(String id) {
        Ticket ticket = storage.getTicket(id);
        switch (ticket.getStatus()) {
            case DONE -> {
                var res = ticket.getResult();
                storage.deleteTicket(id);

                return new ResultDTO(Status.DONE, res);
            }
            case IN_PROGRESS -> {
                return new ResultDTO(Status.IN_PROGRESS, null);
            }
            case ERROR -> {
                return new ResultDTO(Status.ERROR, null);
            }
        }

        return null;
    }

    @Override
    public void updateTicket(UpdateDTO dto) {
        storage.updateTicket(dto.getTicketID(), dto.getResult());
    }
}
