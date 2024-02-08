package ru.nsu.fit.g20202.vartazaryan.workerproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    public ManagerController(WorkerService workerService)
    {
        this.workerService = workerService;
    }

    @PostMapping
    public void handleNewTask(@RequestBody TaskDTO taskDTO)
    {
        System.out.println("New task received!");
        System.out.println("Task info:");
        System.out.println("ID= "+taskDTO.getTicketID());
        System.out.println("Start= "+taskDTO.getStart());
        System.out.println("Finish= "+taskDTO.getFinish());
        System.out.println("Hash to find= "+taskDTO.getHash());
        System.out.println("Word maxLen= "+taskDTO.getMaxLen());

        workerService.handleTask(taskDTO);
    }
}
