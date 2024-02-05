package ru.nsu.fit.g20202.vartazaryan.managerproject.dto;

import lombok.Data;

@Data
public class ResponseDTO
{
    private String requestId;

    public ResponseDTO(String id)
    {
        this.requestId = id;
    }
}
