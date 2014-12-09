package lv.rgl.mla.service.loan.extension;

import lv.rgl.mla.domain.Loan;
import lv.rgl.mla.domain.LoanExtension;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        loanExtension.setExtensionDate(DateTime.now());
        loanExtension.setLoan(loan);
        return loanExtensionRepository.save(loanExtension);
    }
}
