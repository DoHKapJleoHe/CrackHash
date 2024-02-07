package ru.nsu.fit.g20202.vartazaryan.managerproject.service;

import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.CrackDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.ResultDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.TicketIdDTO;

public interface ClientService
{
    TicketIdDTO processRequest(CrackDTO dto);
    ResultDTO getData(String id);
}
