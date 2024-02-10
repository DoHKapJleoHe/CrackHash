package ru.nsu.fit.g20202.vartazaryan.managerproject.storage;

import org.springframework.stereotype.Component;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.CrackDTO;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;

@Component
public class TicketStorage
{
    //TODO: любое изменение хранилище нужно выполнять в защищённом блоке
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

    //TODO: отслеживать сколкьо воркеров вернуло ответ, чтобы всегда IN_PROGRESS не было
    public void updateTicket(String id, List<String> data)
    {
        Ticket blankTicket = new Ticket(UUID.randomUUID(), "blank", 1);
        ticketStorage.merge(id, blankTicket, ((ticket, ticket1) -> {
            if (!data.isEmpty())
            {
                ticket.setStatus(Status.DONE);
                ticket.setResult(data);

                System.out.println("Ticket "+ticket.getTicketId()+" was successfully updated!");
            }

            return ticket;
        }));
    }

    public int getStorageSize()
    {
        return ticketStorage.size();
    }
}
