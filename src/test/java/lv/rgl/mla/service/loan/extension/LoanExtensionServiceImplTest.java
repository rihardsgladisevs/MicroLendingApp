package lv.rgl.mla.service.loan.extension;

import lv.rgl.mla.domain.Loan;
import lv.rgl.mla.domain.LoanExtension;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoanExtensionServiceImplTest {

    @Mock
    private LoanExtensionRepository loanExtensionRepository;

    @InjectMocks
    private LoanExtensionServiceImpl loanExtensionService;

    @Test
    public void testMakeExtension() throws Exception {
        Loan loan = mock(Loan.class);
        LoanExtension loanExtension = mock(LoanExtension.class);
        when(loanExtensionRepository.save(loanExtension)).thenReturn(loanExtension);
        loanExtensionService.makeExtension(loan);
        verify(loanExtensionRepository, times(1)).save(any(LoanExtension.class));
    }
}