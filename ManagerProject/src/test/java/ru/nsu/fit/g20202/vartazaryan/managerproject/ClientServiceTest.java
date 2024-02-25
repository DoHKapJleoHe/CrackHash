package ru.nsu.fit.g20202.vartazaryan.managerproject;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.nsu.fit.g20202.vartazaryan.managerproject.dto.ResultDTO;
import ru.nsu.fit.g20202.vartazaryan.managerproject.service.ClientService;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.Status;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.Ticket;
import ru.nsu.fit.g20202.vartazaryan.managerproject.storage.TicketStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientServiceTest
{
    /*@Autowired
    private ClientService clientService;
    @MockBean
    private TicketStorage ticketStorage;

    private Ticket createMockTicket(Status status)
    {
        Ticket mockTicket = new Ticket(UUID.randomUUID(), "abc9-01zf", 3);

        if (status == Status.DONE)
        {
            mockTicket.setStatus(status);
            mockTicket.setResult(new ArrayList<>(){{
                add("abs");
            }});
        }
        if (status == Status.ERROR)
            mockTicket.setStatus(status);

        return mockTicket;
    }

    @Test
    void getDataInProgressTest()
    {
        Ticket mockTicket = createMockTicket(Status.IN_PROGRESS);
        when(ticketStorage.getTicket(anyString())).thenReturn(mockTicket);

        var res = clientService.getData("abcd");
        assertThat(res).isEqualTo(new ResultDTO(Status.IN_PROGRESS, null));
    }

    @Test
    void getDataDoneTest()
    {
        Ticket mockTicket = createMockTicket(Status.DONE);
        when(ticketStorage.getTicket(anyString())).thenReturn(mockTicket);

        var res = clientService.getData("abcd");
        assertThat(res).isEqualTo(new ResultDTO(Status.DONE, new ArrayList<>(){{add("abs");}}));
    }

    @Test
    void getDataErrorTest()
    {
        Ticket mockTicket = createMockTicket(Status.ERROR);
        when(ticketStorage.getTicket(anyString())).thenReturn(mockTicket);

        var res = clientService.getData("abcd");
        assertThat(res).isEqualTo(new ResultDTO(Status.ERROR, null));
    }*/
}
