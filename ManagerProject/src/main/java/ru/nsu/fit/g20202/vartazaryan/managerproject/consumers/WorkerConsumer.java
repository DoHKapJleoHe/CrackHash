package ru.nsu.fit.g20202.vartazaryan.managerproject.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.UpdateDTO;

@Component
@EnableRabbit
public class WorkerConsumer
{
    private static final Logger logger = LoggerFactory.getLogger(WorkerConsumer.class);

    @RabbitListener(queues = "manager_queue")
    public void handleWorkerResponse(UpdateDTO dto)
    {
        logger.info("Updating ticket. Ticket id: "+dto.getTicketID()+" data: "+dto.getResult());
    }
}
