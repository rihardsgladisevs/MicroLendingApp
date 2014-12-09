package lv.rgl.mla.service.client;

import lv.rgl.mla.domain.Client;
import lv.rgl.mla.domain.Loan;
import lv.rgl.mla.infrastructure.enums.RiskStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplTest {

    private static final String CLIENT_IP = "0.0.0.0";

    @Mock
    private Client client;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Before
    public void setUp() throws Exception {
        client = mock(Client.class);
        when(clientRepository.findByIp(CLIENT_IP)).thenReturn(client);
    }

    @Test
    public void testGetClientLoan() throws Exception {
        Long loanId = 1L;
        Loan loan = mock(Loan.class);
        List<Loan> expectedLoans = Arrays.asList(loan);
        when(loan.getRiskStatus()).thenReturn(RiskStatus.OK);
        when(loan.getId()).thenReturn(loanId);
        when(client.getLoans()).thenReturn(expectedLoans);
        Loan actualLoan = clientService.getClientLoan(CLIENT_IP, loanId);
        verify(clientRepository, times(1)).findByIp(CLIENT_IP);
        assertEquals(loan, actualLoan);
    }

    @Test
    public void testGetLoansByClientIp() throws Exception {
        Loan loan = mock(Loan.class);
        List<Loan> expectedLoans = Arrays.asList(loan, loan, loan);
        when(client.getLoans()).thenReturn(expectedLoans);
        List<Loan> actualLoans = clientService.getLoansByClientIp(CLIENT_IP);
        verify(clientRepository, times(1)).findByIp(CLIENT_IP);
        assertEquals(expectedLoans, actualLoans);
    }

    @Test
    public void testGetClientForLoan() throws Exception {
        when(clientRepository.findByIp(CLIENT_IP)).thenReturn(client);
        Client actualClient = clientService.getClientForLoan(CLIENT_IP);
        verify(clientRepository, times(1)).findByIp(CLIENT_IP);
        verify(clientRepository, never()).save(any(Client.class));

        assertEquals(client, actualClient);

        when(clientRepository.findByIp(CLIENT_IP)).thenReturn(null);
        when(clientRepository.save(client)).thenReturn(client);
        clientService.getClientForLoan(CLIENT_IP);
        verify(clientRepository, times(1)).save(any(Client.class));
    }
}