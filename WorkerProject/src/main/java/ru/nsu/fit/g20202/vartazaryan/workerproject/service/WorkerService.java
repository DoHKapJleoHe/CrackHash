package ru.nsu.fit.g20202.vartazaryan.workerproject.service;

import ru.nsu.fit.g20202.vartazaryan.workerproject.dto.TaskDTO;

public interface WorkerService
{
    void handleTask(TaskDTO taskDTO);
}
