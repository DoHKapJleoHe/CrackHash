package ru.nsu.fit.g20202.vartazaryan.managerproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.UpdateDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.ClientService;

@RestController
@RequestMapping("/internal/api/manager/hash/crack/request")
public class WorkerController
{
    private final ClientService clientService;
    private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @Autowired
    public WorkerController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PatchMapping
    public void updateTicket(@RequestBody UpdateDTO dto)
    {
        logger.info("Updating ticket. Ticket id: "+dto.getTicketID()+" data: "+dto.getResult());

        clientService.updateTicket(dto);
    }
}
