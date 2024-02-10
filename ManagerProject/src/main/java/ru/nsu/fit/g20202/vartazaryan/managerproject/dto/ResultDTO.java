package ru.nsu.fit.g20202.vartazaryan.managerproject.dto;

import lombok.Data;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.Status;

import java.util.List;

@Data
public class ResultDTO
{
    private Status status;
    private List<String> data;

    public ResultDTO(Status status, List<String> data)
    {
        this.status = status;
        this.data = data;
    }
}
