package ru.nsu.fit.g20202.vartazaryan.workerproject.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.support.AmqpHeaders;
import ru.nsu.fit.g20202.vartazaryan.workerproject.dto.TaskDTO;

public interface WorkerService
{
    void handleTask(TaskDTO taskDTO, Channel channel, long tag);
}
