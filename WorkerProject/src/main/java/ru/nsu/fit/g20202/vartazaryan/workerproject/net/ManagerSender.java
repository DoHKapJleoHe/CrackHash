package ru.nsu.fit.g20202.vartazaryan.workerproject.net;

import java.util.List;

public interface ManagerSender
{
    void send(List<String> result, String ticketID);
}
