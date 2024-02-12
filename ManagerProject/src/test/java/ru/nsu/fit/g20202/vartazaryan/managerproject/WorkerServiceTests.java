package ru.nsu.fit.g20202.vartazaryan.managerproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.WorkerService;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.impl.WorkerServiceImpl;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.Ticket;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.TicketStorage;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class WorkerServiceTests
{
    @Autowired
    private WorkerServiceImpl workerServiceImpl;
    @MockBean
    private TicketStorage ticketStorage;
    @MockBean
    private ObjectMapper objectMapper;
    @MockBean
    private ExecutorService executorService;

    @BeforeEach
    public void setUp()
    {
        ticketStorage.deleteAllTickets();
        workerServiceImpl.setWorkersNumber(2);
    }

    @Test
    public void handleTicketTest() //not done yet
    {
        UUID id = UUID.randomUUID();
        Ticket ticket = new Ticket(id, "hash", 3);
        when(ticketStorage.getTicket(anyString())).thenReturn(ticket);

        workerServiceImpl.handleTicket(id.toString());

        //verify(executorService, times(workerServiceImpl.getWorkersNumber())).submit(any(Runnable.class));
    }
}
