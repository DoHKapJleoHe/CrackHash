package ru.nsu.fit.g20202.vartazaryan.managerproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.CrackDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.ResponseDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.TicketStorage;

@Service
public class ClientService
{
    private final TicketStorage ticketStorage;

    @Autowired
    public ClientService(TicketStorage ticketStorage)
    {
        this.ticketStorage = ticketStorage;
    }

    public ResponseDTO processRequest(CrackDTO dto)
    {
        String taskId = ticketStorage.addNewTicket(dto);

        return new ResponseDTO(taskId);
    }
}
