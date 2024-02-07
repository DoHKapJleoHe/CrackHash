package ru.nsu.fit.g20202.vartazaryan.managerproject.dto;

import lombok.Data;

@Data
public class TicketIdDTO
{
    private String requestId;

    public TicketIdDTO(String id)
    {
        this.requestId = id;
    }
}
