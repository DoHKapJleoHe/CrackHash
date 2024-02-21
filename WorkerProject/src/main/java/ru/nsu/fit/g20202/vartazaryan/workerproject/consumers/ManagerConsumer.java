package ru.nsu.fit.g20202.vartazaryan.workerproject.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.nsu.fit.g20202.vartazaryan.workerproject.dto.TaskDTO;

@Component
@EnableRabbit
public class ManagerConsumer
{
    private static final Logger logger = LoggerFactory.getLogger(ManagerConsumer.class);

    @RabbitListener(queues = "worker_queue")
    public void handleManagerTask(TaskDTO dto)
    {
        logger.info("Got request from Rabbit");
    }
}
