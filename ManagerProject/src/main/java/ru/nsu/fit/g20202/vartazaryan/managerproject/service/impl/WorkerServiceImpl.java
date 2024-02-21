package ru.nsu.fit.g20202.vartazaryan.managerproject.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.nsu.fit.g20202.vartazaryan.managerproject.net.WorkerSender;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.WorkerService;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.TaskDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.strats.AbstractTicketSplitter;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.strats.TicketSplitter;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.strats.util.WorkerTaskPair;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.Ticket;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.TicketStorage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class WorkerServiceImpl implements WorkerService
{
    private static final int THREAD_POOL_SIZE = 10;
    @Setter
    @Getter
    private int workersNumber;

    private final TicketStorage ticketStorage;
    private final ExecutorService threadPool;
    private final WorkerSender workerSender;
    private final AbstractTicketSplitter ticketSplitter;
    private static final Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class);

    @Autowired
    public WorkerServiceImpl(TicketStorage ticketStorage, @Qualifier("rabbitMQWorkerSender") WorkerSender workerSender, @Qualifier("fixed") AbstractTicketSplitter ticketSplitter)
    {
        this.ticketSplitter = ticketSplitter;
        String workersNum = System.getenv("WORKERS_NUM");
        this.workerSender = workerSender;
        this.workersNumber = workersNum != null ? Integer.parseInt(workersNum) : 2;
        this.ticketStorage = ticketStorage;
        this.threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public void handleTicket(String ticketID)
    {
        Ticket curTicket = ticketStorage.getTicket(ticketID);
        ticketSplitter.splitTicket(curTicket).stream().forEach(this::sendTask);
    }

    private void sendTask(WorkerTaskPair dto)
    {
        threadPool.submit(() -> {
            logger.info(String.format("Worker %d: start = %d, to_check = %d", dto.getWorker(), dto.getTask().getStart(), dto.getTask().getCheckAmount()));
            workerSender.sendTaskToWorker(dto.getTask(), dto.getWorker());
        });
    }
}
