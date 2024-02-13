package ru.nsu.fit.g20202.vartazaryan.managerproject.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.TaskDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class HttpWorkerSender implements WorkerSender
{
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(HttpWorkerSender.class);

    public HttpWorkerSender()
    {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public int sendTaskToWorker(TaskDTO task, int worker)
    {
        String workerHost = "http://worker"+worker+":8080"+"/internal/api/worker/hash/crack/task";
        logger.info("Worker host: "+workerHost);

        URI uri = URI.create(workerHost);
        try {
            HttpRequest solveTaskRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(task)))
                    .build();

            HttpResponse<String> response = httpClient.send(solveTaskRequest, HttpResponse.BodyHandlers.ofString());
            logger.info("Request was sent to worker");

            if (response.statusCode() != 200)
                logger.error(String.format("Something went wrong on worker side. Status code: %d", response.statusCode()));

            return response.statusCode();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
