package ru.nsu.fit.g20202.vartazaryan.managerproject.dto;

import lombok.Data;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.Status;

@Data
public class ResultDTO
{
    private Status status;
    private String data;

    public ResultDTO(Status status, String data)
    {
        this.status = status;
        this.data = data;
    }
}
