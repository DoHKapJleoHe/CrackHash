package ru.nsu.fit.g20202.vartazaryan.managerproject.net;

import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.TaskDTO;

public interface WorkerSender
{
    int sendTaskToWorker(TaskDTO task, int worker);
}
