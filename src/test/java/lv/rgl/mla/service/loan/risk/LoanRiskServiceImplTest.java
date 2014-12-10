package lv.rgl.mla.service.loan.risk;

import lv.rgl.mla.domain.Client;
import lv.rgl.mla.domain.Loan;
import lv.rgl.mla.domain.LoanRisk;
import lv.rgl.mla.infrastructure.enums.RiskStatus;
import lv.rgl.mla.infrastructure.producers.DateTimeProducer;
import lv.rgl.mla.infrastructure.settings.LoanRiskSettings;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoanRiskServiceImplTest {

    private static final Money MAX_AMOUNT = Money.of(CurrencyUnit.EUR, new BigDecimal(400));
    private static final Money GOOD_AMOUNT = Money.of(CurrencyUnit.EUR, new BigDecimal(300));
    private static final Money BAD_AMOUNT = Money.of(CurrencyUnit.EUR, new BigDecimal(500));
    private static final Integer MAX_APP_PER_DAY = 3;
    private static final Integer NIGHT_END_HOUR = 6;

    private static final LocalDateTime DATE_DAY = LocalDateTime.now().withHour(8);
    private static final LocalDateTime DATE_NIGHT = LocalDateTime.now().withHour(1);

    @Mock
    private LoanRiskSettings loanRiskSettings;

    @Mock
    private DateTimeProducer dateTimeProducer;

    @InjectMocks
    private LoanRiskServiceImpl loanRiskService;

    @Before
    public void setUp() throws Exception {
        when(loanRiskSettings.getMaxAmount()).thenReturn(MAX_AMOUNT);
        when(loanRiskSettings.getMaxApplicationsPerDay()).thenReturn(MAX_APP_PER_DAY);
        when(loanRiskSettings.getNightEndHour()).thenReturn(NIGHT_END_HOUR);
        when(dateTimeProducer.getCurrentDateTime()).thenReturn(DATE_DAY);
    }

    @Test
    public void testCalculateLoanRiskOK() throws Exception {
        Loan loan = mock(Loan.class);
        Client client = mock(Client.class);
        Loan clientLoan = mock(Loan.class);
        List<Loan> clientLoans = Arrays.asList(loan, loan);

        when(loan.getAmount()).thenReturn(GOOD_AMOUNT);
        when(clientLoan.getApplicationDate()).thenReturn(DATE_DAY);
        when(client.getLoans()).thenReturn(clientLoans);
        when(loan.getClient()).thenReturn(client);
        when(loan.getApplicationDate()).thenReturn(DATE_DAY);

        LoanRisk loanRisk = loanRiskService.calculateLoanRisk(loan);

        assertEquals(RiskStatus.OK, loanRisk.getRiskStatus());
    }

    @Test
    public void testCalculateLoanRiskAtNight() throws Exception {
        Loan loan = mock(Loan.class);
        Client client = mock(Client.class);
        Loan clientLoan = mock(Loan.class);
        List<Loan> clientLoans = Arrays.asList(loan, loan);

        when(loan.getAmount()).thenReturn(MAX_AMOUNT);
        when(clientLoan.getApplicationDate()).thenReturn(DATE_DAY);
        when(client.getLoans()).thenReturn(clientLoans);
        when(loan.getClient()).thenReturn(client);
        when(loan.getApplicationDate()).thenReturn(DATE_NIGHT);

        LoanRisk loanRisk = loanRiskService.calculateLoanRisk(loan);

        assertEquals(RiskStatus.APPLIED_AFTER_MIDNIGHT, loanRisk.getRiskStatus());
    }

    @Test
    public void testCalculateLoanRiskMaxApplicationsPerDay() throws Exception {
        Loan loan = mock(Loan.class);
        Client client = mock(Client.class);
        Loan clientLoan = mock(Loan.class);
        List<Loan> clientLoans = Arrays.asList(loan, loan, loan);

        when(loan.getAmount()).thenReturn(MAX_AMOUNT);
        when(clientLoan.getApplicationDate()).thenReturn(DATE_DAY);
        when(client.getLoans()).thenReturn(clientLoans);
        when(loan.getClient()).thenReturn(client);
        when(loan.getApplicationDate()).thenReturn(DATE_NIGHT);

        LoanRisk loanRisk = loanRiskService.calculateLoanRisk(loan);

        assertEquals(RiskStatus.REACHED_MAX_APPLICATIONS_PER_DAY, loanRisk.getRiskStatus());
    }

    @Test
    public void testCalculateLoanRiskOverMaxAmount() throws Exception {
        Loan loan = mock(Loan.class);
        Client client = mock(Client.class);
        Loan clientLoan = mock(Loan.class);
        List<Loan> clientLoans = Arrays.asList(loan, loan, loan);

        when(loan.getAmount()).thenReturn(BAD_AMOUNT);
        when(clientLoan.getApplicationDate()).thenReturn(DATE_DAY);
        when(client.getLoans()).thenReturn(clientLoans);
        when(loan.getClient()).thenReturn(client);
        when(loan.getApplicationDate()).thenReturn(DATE_NIGHT);

        LoanRisk loanRisk = loanRiskService.calculateLoanRisk(loan);

        assertEquals(RiskStatus.OVER_MAX_AMOUNT, loanRisk.getRiskStatus());
    }
}