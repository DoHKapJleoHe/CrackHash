package ru.nsu.fit.g20202.vartazaryan.managerproject.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.WorkerService;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.TaskDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.Ticket;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.TicketStorage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class WorkerServiceImpl implements WorkerService
{
    private static final int SYMBOLS_IN_ALPHABET = 36;
    @Setter
    @Getter
    private int workersNumber;
    private final TicketStorage ticketStorage;
    private final ObjectMapper objectMapper;
    private final ExecutorService threadPool;
    private static final Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class);

    @Autowired
    public WorkerServiceImpl(TicketStorage ticketStorage)
    {
        String workersNum = System.getenv("WORKERS_NUM");
        this.workersNumber = workersNum != null ? Integer.parseInt(workersNum) : 2;
        this.ticketStorage = ticketStorage;
        this.objectMapper = new ObjectMapper();
        threadPool = Executors.newFixedThreadPool(workersNumber);
    }

    public void handleTicket(String ticketID)
    {
        Ticket ticket = ticketStorage.getTicket(ticketID);

        int wordCount = (int)(SYMBOLS_IN_ALPHABET*(Math.pow(SYMBOLS_IN_ALPHABET, ticket.getMaxLength()) - 1)/(SYMBOLS_IN_ALPHABET - 1));

        int wordsPerWorker = wordCount / workersNumber;
        int remainingWords = wordCount % workersNumber;

        int curStart = 1;
        for(int i = 1; i <= workersNumber; i++)
        {
            int curFinish;
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
                sendTaskToWorker(newTaskDTO, finalI);
            });
            curStart = curFinish + 1;
        }
    }

    private void sendTaskToWorker(TaskDTO dto, int worker)
    {
        String workerHost = "http://worker"+worker+":8080"+"/internal/api/worker/hash/crack/task";
        logger.info("Worker host: %s"+workerHost);

        URI uri = URI.create(workerHost);

        HttpClient workerClient = HttpClient.newHttpClient();
        try {
            HttpRequest solveTaskRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(dto)))
                    .build();

            HttpResponse<String> response = workerClient.send(solveTaskRequest, HttpResponse.BodyHandlers.ofString());
            logger.info("Request was sent to worker");

            if (response.statusCode() != 200)
                logger.error(String.format("Something went wrong on worker side. Status code: %d", response.statusCode()));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
