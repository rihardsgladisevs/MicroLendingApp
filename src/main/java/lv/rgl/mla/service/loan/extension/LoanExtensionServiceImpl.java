package lv.rgl.mla.service.loan.extension;

import lv.rgl.mla.domain.Loan;
import lv.rgl.mla.domain.LoanExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

/**
 * Created by rihards.gladisevs on 08.12.2014..
 */
@Service
@Transactional
public class LoanExtensionServiceImpl implements LoanExtensionService {

    @Autowired
    private LoanExtensionRepository loanExtensionRepository;

    @Override
    public LoanExtension makeExtension(Loan loan) {
        LoanExtension loanExtension = new LoanExtension();
        loanExtension.setExtensionDate(LocalDateTime.now());
        loanExtension.setLoan(loan);
        return loanExtensionRepository.save(loanExtension);
    }
}
