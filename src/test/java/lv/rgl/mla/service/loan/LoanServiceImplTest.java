package lv.rgl.mla.service.loan;

import lv.rgl.mla.domain.Client;
import lv.rgl.mla.domain.Loan;
import lv.rgl.mla.domain.RiskStatus;
import lv.rgl.mla.infrastructure.settings.LoanExtensionSettings;
import lv.rgl.mla.infrastructure.settings.LoanSettings;
import lv.rgl.mla.service.loan.risk.LoanRiskService;
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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceImplTest {

    @Mock
    private LoanRiskService loanRiskService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanSettings loanSettings;

    @Mock
    private LoanExtensionSettings loanExtensionSettings;

    @InjectMocks
    private LoanServiceImpl loanService;

    @Before
    public void setUp() throws Exception {
        when(loanSettings.getInterest()).thenReturn(BigDecimal.TEN);
        when(loanExtensionSettings.getWeeksToIncrease()).thenReturn(1);
        when(loanExtensionSettings.getFactor()).thenReturn(BigDecimal.TEN);
    }

    @Test
    public void testApplyForLoan() throws Exception {
        Client client = mock(Client.class);
        Money money = Money.of(CurrencyUnit.EUR, BigDecimal.TEN);
        Loan loan = mock(Loan.class);
        when(loanRiskService.calculateLoanRisk(any(Loan.class))).thenReturn(RiskStatus.OK);
        when(loanRepository.save(loan)).thenReturn(loan);
        loanService.applyForLoan(client, money, 20);
        verify(loanSettings, times(1)).getInterest();
        verify(loanRiskService, times(1)).calculateLoanRisk(any(Loan.class));
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    public void testAddExtension() throws Exception {
        Loan loan = mock(Loan.class);
        when(loan.getEndDate()).thenReturn(LocalDateTime.now());
        when(loan.getInterest()).thenReturn(BigDecimal.TEN);
        when(loanRepository.save(loan)).thenReturn(loan);
        loanService.addExtension(loan);
        verify(loanExtensionSettings, times(1)).getWeeksToIncrease();
        verify(loanExtensionSettings, times(1)).getFactor();
        verify(loanRepository, times(1)).save(loan);
    }
}