package ru.nsu.fit.g20202.vartazaryan.managerproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.CrackDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.ResultDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.TicketIdDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.ClientService;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.WorkerService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/hash")
public class ClientController
{
    private final ClientService clientService;
    private final WorkerService workerService;

    @Autowired
    public ClientController(ClientService clientService, WorkerService workerService)
    {
        this.clientService = clientService;
        this.workerService = workerService;
    }

    @PostMapping("/crack")
    public ResponseEntity<TicketIdDTO> crackHash(@RequestBody CrackDTO crackDTO)
    {
        System.out.println("New crack hash request!");

        var ticketIdDTO = clientService.processRequest(crackDTO);

        System.out.println("Registered new ticket. ID = "+ticketIdDTO.getRequestId());

        workerService.handleTicket(ticketIdDTO.getRequestId());

        return ResponseEntity.ok().body(ticketIdDTO);
    }

    @GetMapping("/status")
    public ResponseEntity<ResultDTO> getStatus(@RequestParam(name = "requestId") String id)
    {
        return ResponseEntity.ok().body(clientService.getData(id));
    }
}
