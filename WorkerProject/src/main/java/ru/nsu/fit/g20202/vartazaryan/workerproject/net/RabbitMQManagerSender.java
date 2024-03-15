package ru.nsu.fit.g20202.vartazaryan.workerproject.net;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ru.nsu.fit.g20202.vartazaryan.workerproject.dto.ResponseDTO;

import java.io.IOException;
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
    public void send(List<String> result, String ticketID, Channel channel, long tag) {
        logger.info("Sending response to queue...");

        ResponseDTO dto = ResponseDTO.builder()
                .result(result)
                .ticketID(ticketID)
                .build();

        rabbitTemplate.convertAndSend("exchange","manager_routing_key", dto);

        try {
            channel.basicAck(tag, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logger.info("Response was sent!");
    }
}
