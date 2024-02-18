package ru.nsu.fit.g20202.vartazaryan.managerproject.service.strats.impl;

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
@Qualifier("equal")
public class EqualPartsTicketSplitter extends AbstractTicketSplitter
{
    @Override
    public List<WorkerTaskPair> splitTicket(Ticket ticket)
    {
        initializeSplitting(ticket);
        List<WorkerTaskPair> tasks = new ArrayList<>();

        long wordCount = (int)(SYMBOLS_IN_ALPHABET*(Math.pow(SYMBOLS_IN_ALPHABET, ticket.getMaxLength()) - 1)/(SYMBOLS_IN_ALPHABET - 1));
        long wordsPerWorker = wordCount / WORKERS_NUMBER;
        long remainingWords = wordCount % WORKERS_NUMBER;
        long curStart = 1;

        for(int i = 1; i <= WORKERS_NUMBER; i++)
        {
            long curFinish;
            if (i > 1 && i == WORKERS_NUMBER)
            {
                curFinish = curStart + wordsPerWorker - 1 + remainingWords;
            } else {
                curFinish = curStart + wordsPerWorker - 1;
            }

            logger.info(String.format("Worker %d: start = %d, finish = %d", i, curStart, curFinish));

            TaskDTO dto = makeTask(curStart, curFinish-curStart);
            tasks.add(new WorkerTaskPair(i, dto));
        }

        return tasks;
    }
}
