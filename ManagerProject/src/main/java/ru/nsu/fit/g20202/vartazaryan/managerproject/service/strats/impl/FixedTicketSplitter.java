package ru.nsu.fit.g20202.vartazaryan.managerproject.service.strats.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.TaskDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.strats.AbstractTicketSplitter;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.strats.util.WorkerTaskPair;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.Ticket;

import java.util.ArrayList;
import java.util.List;

import static ru.nsu.fit.g20202.vartazaryan.managerproject.constants.Constants.WORKERS_NUMBER;

@Component
@Qualifier("fixed")
public class FixedTicketSplitter extends AbstractTicketSplitter
{
    private static final int WORDS_PER_TASK = 10_000_000;

    @Override
    public List<WorkerTaskPair> splitTicket(Ticket ticket)
    {
        initializeSplitting(ticket);
        List<WorkerTaskPair> tasks = new ArrayList<>();


        long wordCount = (int)(SYMBOLS_IN_ALPHABET*(Math.pow(SYMBOLS_IN_ALPHABET, ticket.getMaxLength()) - 1)/(SYMBOLS_IN_ALPHABET - 1));
        long curStart = 1;
        int curWorker = 0;

        while(wordCount > 0)
        {
            curWorker = (curWorker % WORKERS_NUMBER) + 1;
            if (wordCount < WORDS_PER_TASK)
            {
                TaskDTO newTask = makeTask(curStart, wordCount);
                curTicket.setTasksNumber(curTicket.getTasksNumber()+1);
                tasks.add(new WorkerTaskPair(curWorker, newTask));

                break;
            }
            else
            {
                TaskDTO newTask = makeTask(curStart, WORDS_PER_TASK);
                curTicket.setTasksNumber(curTicket.getTasksNumber()+1);
                tasks.add(new WorkerTaskPair(curWorker, newTask));

                wordCount -= WORDS_PER_TASK;
                curStart += WORDS_PER_TASK;
            }
        }

        logger.info("Splitting ticket "+ticket.getTicketId()+" : splitting finished");

        return tasks;
    }
}
