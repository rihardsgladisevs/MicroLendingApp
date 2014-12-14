package lv.rgl.mla.web;

import lv.rgl.mla.domain.Client;
import lv.rgl.mla.domain.Loan;
import lv.rgl.mla.infrastructure.producers.DateTimeProducer;
import lv.rgl.mla.service.client.ClientService;
import lv.rgl.mla.service.loan.LoanService;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoanRestServiceTest {

    private static final String USER_IP = "0.0.0.0";

    @Mock
    private HttpServletRequest request;

    @Mock
    private DateTimeProducer dateTimeProducer;

    @Mock
    private LoanService loanService;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private LoanRestService loanRestService;

    @Before
    public void setUp() throws Exception {
        when(request.getRemoteAddr()).thenReturn(USER_IP);
    }

    @Test
    public void testApplyForLoan() throws Exception {
        Integer term = 30;
        BigDecimal amount = new BigDecimal("300.00");
        when(dateTimeProducer.getCurrentDateTime()).thenReturn(LocalDateTime.now().withHour(8));
        ResponseEntity<String> result = loanRestService.applyForLoan(amount, term, request);
        verify(clientService, times(1)).getClientForLoan(USER_IP);
        verify(loanService, times(1)).applyForLoan(any(Client.class), any(Money.class), eq(term));
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testGetLoanHistory() throws Exception {
        Loan loan = mock(Loan.class);
        List<Loan> loans = Arrays.asList(loan, loan, loan);
        when(clientService.getLoansByClientIp(USER_IP)).thenReturn(loans);
        List<Loan> actualLoans = loanRestService.getLoanHistory(request);
        verify(clientService, times(1)).getLoansByClientIp(USER_IP);
        assertEquals(loans, actualLoans);
    }

    @Test
    public void testExtendLoan() throws Exception {
        Long loanId = 123L;
        Loan loan = mock(Loan.class);
        when(clientService.getClientLoan(USER_IP, loanId)).thenReturn(loan);
        when(loanService.addExtension(loan)).thenReturn(loan);
        ResponseEntity<String> result = loanRestService.extendLoan(loanId, request);
        verify(clientService, times(1)).getClientLoan(USER_IP, loanId);
        verify(loanService, times(1)).addExtension(loan);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}