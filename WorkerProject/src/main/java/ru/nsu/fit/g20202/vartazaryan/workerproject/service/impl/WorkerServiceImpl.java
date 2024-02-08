package ru.nsu.fit.g20202.vartazaryan.workerproject.service.impl;

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

    public WorkerServiceImpl()
    {
        executorService = Executors.newFixedThreadPool(5);
    }

    @Override
    public void handleTask(TaskDTO taskDTO)
    {
        Task newTask = new Task(taskDTO);
        executorService.submit(newTask);
        System.out.println("Task execution started...");
    }
}
