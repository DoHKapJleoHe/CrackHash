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
    private static final int WORDS_PER_TASK = 10_000_000;
    @Setter
    @Getter
    private int workersNumber;
    private Ticket curTicket;

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
        curTicket = ticketStorage.getTicket(ticketID);

        long wordCount = (int)(SYMBOLS_IN_ALPHABET*(Math.pow(SYMBOLS_IN_ALPHABET, curTicket.getMaxLength()) - 1)/(SYMBOLS_IN_ALPHABET - 1));

        long curStart = 1;
        int curWorker = 0;

        while(wordCount > 0)
        {
            curWorker = (curWorker % 3) + 1;
            if (wordCount < WORDS_PER_TASK)
            {
                TaskDTO newTask = makeTask(curStart, wordCount);
                sendTask(newTask, curWorker);
                break;
            }
            else
            {
                TaskDTO newTask = makeTask(curStart, WORDS_PER_TASK);
                sendTask(newTask, curWorker);

                wordCount -= WORDS_PER_TASK;
                curStart += WORDS_PER_TASK;
            }
        }
        /*for(int i = 1; i <= workersNumber; i++)
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
        }*/
    }

    private void sendTask(TaskDTO taskDTO, int worker)
    {
        threadPool.submit(() -> {
            logger.info(String.format("Worker %d: start = %d, to_check = %d", worker, taskDTO.getStart(), taskDTO.getCheckAmount()));
            workerSender.sendTaskToWorker(taskDTO, worker);
        });
    }

    private TaskDTO makeTask(long start, long amount)
    {
        return TaskDTO.builder()
                .ticketID(curTicket.getTicketId().toString())
                .start(start)
                .checkAmount(amount)
                .maxLen(curTicket.getMaxLength())
                .hash(curTicket.getHash())
                .build();
    }
}
