package ru.nsu.fit.g20202.vartazaryan.managerproject.service.strats.util;

import lombok.Getter;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.TaskDTO;

public class WorkerTaskPair
{
    @Getter
    private final int worker;
    @Getter
    private final TaskDTO task;

    public WorkerTaskPair(int worker, TaskDTO dto)
    {
        this.worker =  worker;
        this.task = dto;
    }
}
