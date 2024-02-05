package ru.nsu.fit.g20202.vartazaryan.managerproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.CrackDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.ResponseDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.ClientService;

@RestController
@RequestMapping("/api/hash")
public class ClientController
{
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService)
    {
        this.clientService = clientService;
    }

    @PostMapping("/crack")
    public ResponseEntity<ResponseDTO> crackHash(@RequestBody CrackDTO crackDTO)
    {
        return ResponseEntity.ok().body(clientService.processRequest(crackDTO));
    }

    @GetMapping("/status")
    public void getStatus(@RequestParam(name = "requestId") int id)
    {

    }
}
