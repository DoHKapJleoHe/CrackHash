package ru.nsu.fit.g20202.vartazaryan.managerproject.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@Service
public class WorkerServiceImpl implements WorkerService
{
    private final TicketStorage ticketStorage;
    private int workersNumber;
    private final ObjectMapper objectMapper;

    @Autowired
    public WorkerServiceImpl(TicketStorage ticketStorage)
    {
        this.workersNumber = Integer.parseInt(System.getenv("WORKERS_NUM"));
        this.ticketStorage = ticketStorage;
        this.objectMapper = new ObjectMapper();
    }

    public void handleTicket(String ticketID)
    {
        Ticket ticket = ticketStorage.getTicket(ticketID);

        int wordCount = 0;
        for (int i = 1; i <= ticket.getMaxLength(); i++)
            wordCount += (int) Math.pow(62, i);

        for(int i = 1; i <= workersNumber; i++)
        {
            TaskDTO newTaskDTO = TaskDTO.builder()
                    .ticketID(ticket.getTicketId().toString())
                    .start(1)
                    .finish(wordCount)
                    .maxLen(ticket.getMaxLength())
                    .build();

            int finalI = i;
            new Thread(() -> sendTaskToWorker(newTaskDTO, finalI)).start();
        }
    }

    private void sendTaskToWorker(TaskDTO dto, int worker)
    {
        String workerHost = "http://worker"+worker+":808"+worker+"/internal/api/manager/hash/crack/request";
        URI uri = URI.create(workerHost);

        HttpClient workerClient = HttpClient.newHttpClient();
        try {
            HttpRequest solveTaskRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(dto)))
                    .build();

            HttpResponse<String> response = workerClient.send(solveTaskRequest, HttpResponse.BodyHandlers.ofString());

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
