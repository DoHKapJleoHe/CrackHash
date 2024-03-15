package ru.nsu.fit.g20202.vartazaryan.workerproject.consumers;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import ru.nsu.fit.g20202.vartazaryan.workerproject.dto.TaskDTO;
import ru.nsu.fit.g20202.vartazaryan.workerproject.service.WorkerService;

@Component
@EnableRabbit
public class ManagerConsumer
{
    private final WorkerService workerService;
    private static final Logger logger = LoggerFactory.getLogger(ManagerConsumer.class);

    public ManagerConsumer(WorkerService workerService) {
        this.workerService = workerService;
    }

    @RabbitListener(queues = "worker_queue")
    public void handleManagerTask(TaskDTO dto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)
    {
        String info = String.format("""
                New task received!
                Task info:
                ID= %s
                Start= %d
                Checks num = %d
                Hash= %s
                Max length= %d
                """, dto.getTicketID(), dto.getStart(), dto.getCheckAmount(), dto.getHash(), dto.getMaxLen());
        logger.info(info);

        workerService.handleTask(dto, channel, tag);
    }
}
