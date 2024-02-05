package ru.nsu.fit.g20202.vartazaryan.managerproject.storage;

import org.springframework.stereotype.Component;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.CrackDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class TicketStorage
{
    private Map<String, Ticket> ticketStorage;

    public TicketStorage()
    {
        ticketStorage = new HashMap<>();
    }

    public String addNewTicket(CrackDTO dto)
    {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        Ticket t = new Ticket(uuid, dto.getHash(), dto.getMaxLength());

        ticketStorage.put(id, t);

        return id;
    }
}
