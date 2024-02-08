package ru.nsu.fit.g20202.vartazaryan.managerproject.service;

import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.CrackDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.ResultDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.TicketIdDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.UpdateDTO;

public interface ClientService
{
    TicketIdDTO processRequest(CrackDTO dto);
    ResultDTO getData(String id);
    void updateTicket(UpdateDTO dto);
}
