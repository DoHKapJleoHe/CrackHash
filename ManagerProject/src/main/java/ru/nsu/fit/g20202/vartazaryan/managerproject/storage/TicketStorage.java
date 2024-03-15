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
    private final ConcurrentHashMap<String, Ticket> ticketStorage;

    private final Object mutex = new Object();
    private final ScheduledExecutorService scheduler;

    private static final Logger logger = LoggerFactory.getLogger(TicketStorage.class);

    public TicketStorage() {
        ticketStorage = new ConcurrentHashMap<>();
        scheduler = Executors.newScheduledThreadPool(1);
        startPeriodicCheck();
    }

    private void startPeriodicCheck() {
        scheduler.scheduleAtFixedRate(() -> ticketStorage.forEach((key, ticket) -> {
            logger.info("Checking tickets");

            if(Duration.between(ticket.getCreationTime(), LocalDateTime.now()).getSeconds() > 3600) {
                logger.info(String.format("Ticket %s didn't change its status for more than 2 min. Changing status to error!", key));
                ticket.setStatus(Status.ERROR);
            }
        }), 0, 1, TimeUnit.MINUTES);
    }

    @Override
    public Ticket addNewTicket(CrackDTO dto) {
        Ticket t;
        synchronized (mutex) {
            UUID uuid = UUID.randomUUID();
            String id = uuid.toString();
            t = new Ticket(uuid, dto.getHash(), dto.getMaxLength());

            ticketStorage.put(id, t);
        }

        return t;
    }

    @Override
    public Ticket getTicket(String id) {
        Ticket t;
        synchronized (mutex) {
            t = ticketStorage.get(id);;
        }

        return t;
    }

    @Override
    public void deleteTicket(String id) {
        synchronized (mutex) {
            ticketStorage.remove(id);
        }
    }

    @Override
    public void deleteAllTickets() {
        synchronized (mutex) {
            ticketStorage.clear();
        }
    }

    @Override
    public void updateTicket(String id, List<String> data) {
        if (data == null) {
            return;
        }

        synchronized (mutex) {
            Ticket blankTicket = new Ticket(UUID.randomUUID(), "blank", 1);
            ticketStorage.merge(id, blankTicket, ((ticket, ticket1) -> {
                ticket.setTasksDone(ticket.getTasksDone()+1);
                logger.info(String.format("Ticket %s: tasks done %d/%d", ticket.getTicketId().toString(), ticket.getTasksDone(), ticket.getTasksNumber()));
                if (!data.isEmpty() && ticket.getStatus() != Status.ERROR) {
                    ticket.setResult(data);
                    logger.info(String.format("Ticket %s was successfully updated!", ticket.getTicketId().toString()));
                }
                if(ticket.getTasksNumber() == ticket.getTasksDone()) {
                    ticket.setStatus(Status.DONE);
                    logger.info(String.format("Ticket %s was successfully done!", ticket.getTicketId().toString()));
                }
                return ticket;
            }));
        }
    }

    @Override
    public int getStorageSize() {
        synchronized (mutex) {
            return ticketStorage.size();
        }
    }
}
