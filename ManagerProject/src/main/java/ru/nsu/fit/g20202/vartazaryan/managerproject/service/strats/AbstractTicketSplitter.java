package ru.nsu.fit.g20202.vartazaryan.managerproject.service.strats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.TaskDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.strats.impl.EqualPartsTicketSplitter;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.strats.util.WorkerTaskPair;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.Ticket;

import java.util.List;

public abstract class AbstractTicketSplitter implements TicketSplitter
{
    protected static final int SYMBOLS_IN_ALPHABET = 26;

    protected Ticket curTicket;
    protected Logger logger;

    protected void initializeSplitting(Ticket ticket) {
        this.curTicket = ticket;
        this.logger = LoggerFactory.getLogger(this.getClass());

        logger.info("Splitting ticket " + ticket.getTicketId() + " : started splitting");
    }

    protected TaskDTO makeTask(long start, long amount) {
        return TaskDTO.builder()
                .ticketID(curTicket.getTicketId().toString())
                .start(start)
                .checkAmount(amount)
                .maxLen(curTicket.getMaxLength())
                .hash(curTicket.getHash())
                .build();
    }

    @Override
    public abstract List<WorkerTaskPair> splitTicket(Ticket ticket);
}
