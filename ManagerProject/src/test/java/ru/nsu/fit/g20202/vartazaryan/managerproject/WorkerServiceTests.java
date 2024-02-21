package ru.nsu.fit.g20202.vartazaryan.managerproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.TaskDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.net.HttpWorkerSender;
import ru.nsu.fit.g20202.vartazaryan.managerproject.net.WorkerSender;
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
    /*@Autowired
    private WorkerServiceImpl workerServiceImpl;
    @MockBean
    private TicketStorage ticketStorage;
    @MockBean
    private HttpWorkerSender workerSender;
    @MockBean
    private ExecutorService executorService;

    @BeforeEach
    public void setUp()
    {
        reset(ticketStorage, workerSender);
        ticketStorage.deleteAllTickets();
    }

    @Test
    public void handleTicket_1WorkersTest()
    {
        workerServiceImpl.setWorkersNumber(1);

        UUID id = UUID.randomUUID();
        Ticket ticket = new Ticket(id, "abc", 3);
        when(ticketStorage.getTicket(anyString())).thenReturn(ticket);

        workerServiceImpl.handleTicket(id.toString());
        verify(ticketStorage).getTicket(eq(id.toString()));

        ArgumentCaptor<TaskDTO> argumentCaptor = ArgumentCaptor.forClass(TaskDTO.class);
        verify(workerSender, times(workerServiceImpl.getWorkersNumber())).sendTaskToWorker(argumentCaptor.capture(), anyInt());
    }

    /*@Test
    public void handleTicket_2WorkersTest()
    {
        workerServiceImpl.setWorkersNumber(2);

        UUID id = UUID.randomUUID();
        Ticket ticket = new Ticket(id, "hash", 3);
        when(ticketStorage.getTicket(anyString())).thenReturn(ticket);

        workerServiceImpl.handleTicket(id.toString());
        verify(ticketStorage).getTicket(eq(id.toString()));

        ArgumentCaptor<TaskDTO> argumentCaptor = ArgumentCaptor.forClass(TaskDTO.class);
        verify(workerSender, times(workerServiceImpl.getWorkersNumber())).sendTaskToWorker(argumentCaptor.capture(), anyInt());
    }

    @Test
    public void handleTicketRandomWorkersTest()
    {
        workerServiceImpl.setWorkersNumber(new Random().nextInt(1, 8));

        UUID id = UUID.randomUUID();
        Ticket ticket = new Ticket(id, "hash", 3);
        when(ticketStorage.getTicket(anyString())).thenReturn(ticket);

        workerServiceImpl.handleTicket(id.toString());
        verify(ticketStorage).getTicket(eq(id.toString()));

        ArgumentCaptor<TaskDTO> argumentCaptor = ArgumentCaptor.forClass(TaskDTO.class);
        verify(workerSender, times(workerServiceImpl.getWorkersNumber())).sendTaskToWorker(argumentCaptor.capture(), anyInt());
    }*/
}
