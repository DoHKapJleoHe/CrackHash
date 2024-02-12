package ru.nsu.fit.g20202.vartazaryan.managerproject.storage;

import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.CrackDTO;

import java.util.List;

public interface Storage
{
    String addNewTicket(CrackDTO dto);
    Ticket getTicket(String id);
    void deleteTicket(String id);
    void deleteAllTickets();
    void updateTicket(String id, List<String> data);
    int getStorageSize();
}
