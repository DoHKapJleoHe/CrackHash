package ru.nsu.fit.g20202.vartazaryan.managerproject.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.CrackDTO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@Component
public class TicketStorage implements Storage
{
    private ConcurrentHashMap<String, Ticket> ticketStorage;
    private final ScheduledExecutorService scheduler;
    private static final Logger logger = LoggerFactory.getLogger(TicketStorage.class);

    public TicketStorage()
    {
        ticketStorage = new ConcurrentHashMap<>();
        scheduler = Executors.newScheduledThreadPool(1);
        startPeriodicCheck();
    }

    private void startPeriodicCheck()
    {
        scheduler.scheduleAtFixedRate(() -> ticketStorage.forEach((key, ticket) -> {
            logger.info("Checking tickets");

            if(Duration.between(ticket.getCreationTime(), LocalDateTime.now()).getSeconds() > 120)
            {
                logger.info(String.format("Ticket %s didn't change its status for more than 2 min. Changing status to error!", key));
                ticket.setStatus(Status.ERROR);
            }
        }), 0, 1, TimeUnit.MINUTES);
    }

    @Override
    public String addNewTicket(CrackDTO dto)
    {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        Ticket t = new Ticket(uuid, dto.getHash(), dto.getMaxLength());

        ticketStorage.put(id, t);

        return id;
    }

    @Override
    public Ticket getTicket(String id)
    {
        return ticketStorage.get(id);
    }

    @Override
    public void deleteTicket(String id)
    {
        ticketStorage.remove(id);
    }

    @Override
    public void deleteAllTickets()
    {
        ticketStorage.clear();
    }

    //TODO: отслеживать сколкьо воркеров вернуло ответ, чтобы всегда IN_PROGRESS не было
    @Override
    public void updateTicket(String id, List<String> data)
    {
        if (data == null)
            return;

        Ticket blankTicket = new Ticket(UUID.randomUUID(), "blank", 1);
        ticketStorage.merge(id, blankTicket, ((ticket, ticket1) -> {
            if (!data.isEmpty() && ticket.getStatus() != Status.ERROR)
            {
                ticket.setStatus(Status.DONE);
                ticket.setResult(data);

                System.out.println("Ticket "+ticket.getTicketId()+" was successfully updated!");
            }

            return ticket;
        }));
    }

    @Override
    public int getStorageSize()
    {
        return ticketStorage.size();
    }
}
