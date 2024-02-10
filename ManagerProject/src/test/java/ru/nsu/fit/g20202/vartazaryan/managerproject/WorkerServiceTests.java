package ru.nsu.fit.g20202.vartazaryan.managerproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.WorkerService;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.TicketStorage;

import java.util.concurrent.ExecutorService;

@SpringBootTest
public class WorkerServiceTests
{
    @Autowired
    private WorkerService workerService;
    @MockBean
    private TicketStorage ticketStorage;
    @MockBean
    private ObjectMapper objectMapper;
    @MockBean
    private ExecutorService executorService;


    @Test
    public void handleTicketTest()
    {

    }
}
