package ru.nsu.fit.g20202.vartazaryan.workerproject.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nsu.fit.g20202.vartazaryan.workerproject.dto.TaskDTO;
import ru.nsu.fit.g20202.vartazaryan.workerproject.service.WorkerService;
import ru.nsu.fit.g20202.vartazaryan.workerproject.service.utils.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class WorkerServiceImpl implements WorkerService
{
    private final ExecutorService executorService;
    private static final Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class);

    public WorkerServiceImpl()
    {
        executorService = Executors.newFixedThreadPool(5);
    }

    @Override
    public void handleTask(TaskDTO taskDTO)
    {
        Task newTask = new Task(taskDTO);
        executorService.submit(newTask);

        logger.info("Task execution started...");
    }
}
