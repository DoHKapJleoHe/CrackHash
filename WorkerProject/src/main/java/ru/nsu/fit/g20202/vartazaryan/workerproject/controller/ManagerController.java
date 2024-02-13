package ru.nsu.fit.g20202.vartazaryan.workerproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.g20202.vartazaryan.workerproject.dto.TaskDTO;
import ru.nsu.fit.g20202.vartazaryan.workerproject.service.WorkerService;

@RestController
@RequestMapping("/internal/api/worker/hash/crack/task")
public class ManagerController
{
    private final WorkerService workerService;
    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    public ManagerController(WorkerService workerService)
    {
        this.workerService = workerService;
    }

    @PostMapping
    public void handleNewTask(@RequestBody TaskDTO taskDTO)
    {
        String info = String.format("""
                New task received!
                Task info:
                ID= %s
                Start= %d
                Finish= %d
                Hash= %s
                Max length= %d
                Words = %d
                """, taskDTO.getTicketID(), taskDTO.getStart(), taskDTO.getFinish(), taskDTO.getHash(), taskDTO.getMaxLen(), taskDTO.getFinish()- taskDTO.getStart());
        logger.info(info);

        workerService.handleTask(taskDTO);
    }
}
