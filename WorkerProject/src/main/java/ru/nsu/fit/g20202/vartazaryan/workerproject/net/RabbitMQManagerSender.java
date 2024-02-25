package ru.nsu.fit.g20202.vartazaryan.workerproject.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ru.nsu.fit.g20202.vartazaryan.workerproject.dto.ResponseDTO;

import java.util.List;

@Component
public class RabbitMQManagerSender implements ManagerSender
{
    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQManagerSender.class);

    public RabbitMQManagerSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void send(List<String> result, String ticketID) {
        logger.info("Sending response to queue...");

        ResponseDTO dto = ResponseDTO.builder()
                .result(result)
                .ticketID(ticketID)
                .build();

        rabbitTemplate.convertAndSend("exchange","manager_routing_key", dto);

        logger.info("Response was sent!");
    }
}
