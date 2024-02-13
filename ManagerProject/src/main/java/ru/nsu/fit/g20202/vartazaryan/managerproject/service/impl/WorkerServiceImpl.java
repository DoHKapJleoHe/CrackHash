package ru.nsu.fit.g20202.vartazaryan.managerproject.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.g20202.vartazaryan.managerproject.net.WorkerSender;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.WorkerService;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.TaskDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.Ticket;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.TicketStorage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class WorkerServiceImpl implements WorkerService
{
    private static final int SYMBOLS_IN_ALPHABET = 36;
    private static final int THREAD_POOL_SIZE = 10;
    @Setter
    @Getter
    private int workersNumber;
    private final TicketStorage ticketStorage;
    private final ExecutorService threadPool;
    private final WorkerSender workerSender;
    private static final Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class);

    @Autowired
    public WorkerServiceImpl(TicketStorage ticketStorage, WorkerSender workerSender)
    {
        String workersNum = System.getenv("WORKERS_NUM");
        this.workerSender = workerSender;
        this.workersNumber = workersNum != null ? Integer.parseInt(workersNum) : 2;
        this.ticketStorage = ticketStorage;
        this.threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public void handleTicket(String ticketID)
    {
        Ticket ticket = ticketStorage.getTicket(ticketID);

        long wordCount = (int)(SYMBOLS_IN_ALPHABET*(Math.pow(SYMBOLS_IN_ALPHABET, ticket.getMaxLength()) - 1)/(SYMBOLS_IN_ALPHABET - 1));

        long wordsPerWorker = wordCount / workersNumber;
        long remainingWords = wordCount % workersNumber;

        long curStart = 1;
        for(int i = 1; i <= workersNumber; i++)
        {
            long curFinish;
            if (i > 1 && i == workersNumber) {
                curFinish = curStart + wordsPerWorker - 1 + remainingWords;
            } else {
                curFinish = curStart + wordsPerWorker - 1;
            }

            logger.info(String.format("Worker %d: start = %d, finish = %d", i, curStart, curFinish));

            TaskDTO newTaskDTO = TaskDTO.builder()
                    .ticketID(ticket.getTicketId().toString())
                    .start(curStart)
                    .finish(curFinish)
                    .maxLen(ticket.getMaxLength())
                    .hash(ticket.getHash())
                    .build();

            int finalI = i;
            threadPool.submit(() -> {
                workerSender.sendTaskToWorker(newTaskDTO, finalI);
            });
            curStart = curFinish + 1;
        }
    }
}
