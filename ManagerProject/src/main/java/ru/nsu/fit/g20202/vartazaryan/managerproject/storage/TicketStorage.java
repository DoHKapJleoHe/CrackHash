package ru.nsu.fit.g20202.vartazaryan.managerproject.storage;

import org.springframework.stereotype.Component;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.CrackDTO;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TicketStorage
{
    private ConcurrentHashMap<String, Ticket> ticketStorage;

    public TicketStorage()
    {
        ticketStorage = new ConcurrentHashMap<>();
    }

    public String addNewTicket(CrackDTO dto)
    {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        Ticket t = new Ticket(uuid, dto.getHash(), dto.getMaxLength());

        ticketStorage.put(id, t);

        return id;
    }

    public Ticket getTicket(String id)
    {
        return ticketStorage.get(id);
    }

    public void deleteTicket(String id)
    {
        ticketStorage.remove(id);
    }
    public void updateTicket(String id, String data)
    {
        var ticket = ticketStorage.get(id);
        if (data != null)
        {
            ticket.setStatus(Status.DONE);
            ticket.setResult(data);
            System.out.println("Ticket "+ticket.getTicketId()+" was successfully updated!");
        }
    }
}
