package ru.nsu.fit.g20202.vartazaryan.workerproject.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ru.nsu.fit.g20202.vartazaryan.workerproject.dto.ResponseDTO;
import ru.nsu.fit.g20202.vartazaryan.workerproject.service.utils.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class HttpManagerSender implements ManagerSender
{
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(HttpManagerSender.class);

    public HttpManagerSender()
    {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void send(List<String> result, String ticketID)
    {
        String managerHost = "http://manager:8080/internal/api/manager/hash/crack/request";
        URI uri = URI.create(managerHost);
        ResponseDTO dto = ResponseDTO.builder()
                .result(result)
                .ticketID(ticketID)
                .build();

        HttpClient managerClient = HttpClient.newHttpClient();
        try {
            HttpRequest taskResponse = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(dto)))
                    .build();

            HttpResponse<String> response = managerClient.send(taskResponse, HttpResponse.BodyHandlers.ofString());
            logger.info("Result was sent!");

            if (response.statusCode() != 200)
                logger.error(String.format("Something went wrong on manager side. Status code: %d", response.statusCode()));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
