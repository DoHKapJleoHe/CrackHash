package ru.nsu.fit.g20202.vartazaryan.managerproject.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final TicketStorage ticketStorage;

    private int workersNumber = 2;
    private final ObjectMapper objectMapper;

    private final ExecutorService threadPool;

    @Autowired
    public WorkerServiceImpl(TicketStorage ticketStorage)
    {
        String workersNum = System.getenv("WORKERS_NUM");
        this.workersNumber = workersNum != null ? Integer.parseInt(workersNum) : 1;
        this.ticketStorage = ticketStorage;
        this.objectMapper = new ObjectMapper();
        threadPool = Executors.newFixedThreadPool(workersNumber);
    }

    public void handleTicket(String ticketID)
    {
        threadPool.submit(() -> {
            Ticket ticket = ticketStorage.getTicket(ticketID);

            int wordCount = 0;
            for (int i = 1; i <= ticket.getMaxLength(); i++)
                wordCount += (int) Math.pow(36, i);

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

                System.out.println("Worker"+i+": start= "+curStart+" finish= "+curFinish);

                TaskDTO newTaskDTO = TaskDTO.builder()
                        .ticketID(ticket.getTicketId().toString())
                        .start(curStart)
                        .finish(curFinish)
                        .maxLen(ticket.getMaxLength())
                        .hash(ticket.getHash())
                        .build();

                sendTaskToWorker(newTaskDTO, i);

                curStart = curFinish + 1;
            }
        });
    }

    private void sendTaskToWorker(TaskDTO dto, int worker)
    {
        String workerHost = "http://worker"+worker+":8080"+"/internal/api/worker/hash/crack/task";
        System.out.println("Worker host: "+workerHost);
        URI uri = URI.create(workerHost);

        HttpClient workerClient = HttpClient.newHttpClient();
        try {
            HttpRequest solveTaskRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(dto)))
                    .build();

            HttpResponse<String> response = workerClient.send(solveTaskRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println("Request was sent to worker");

            // if not ok
            if (response.statusCode() != 200)
            {
                System.out.println("Something went wrong on worker side. Status code: "+response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
