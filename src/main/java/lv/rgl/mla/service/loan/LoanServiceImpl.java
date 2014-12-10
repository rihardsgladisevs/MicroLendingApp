package lv.rgl.mla.service.loan;

import lv.rgl.mla.domain.Client;
import lv.rgl.mla.domain.Loan;
import lv.rgl.mla.domain.LoanRisk;
import lv.rgl.mla.infrastructure.enums.RiskStatus;
import lv.rgl.mla.infrastructure.exceptions.LoanRiskException;
import lv.rgl.mla.infrastructure.settings.LoanExtensionSettings;
import lv.rgl.mla.infrastructure.settings.LoanSettings;
import lv.rgl.mla.service.loan.risk.LoanRiskService;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Created by rihards.gladisevs on 06.12.2014..
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanExtensionSettings loanExtensionSettings;

    @Autowired
    private LoanSettings loanSettings;

    @Autowired
    private LoanRiskService loanRiskService;

    @Override
    public Loan applyForLoan(Client client, Money amount, Integer term) throws LoanRiskException {
        Loan loan = new Loan();
        loan.setClient(client);
        loan.setAmount(amount);
        LocalDateTime applicationDate = LocalDateTime.now();
        loan.setApplicationDate(applicationDate);
        loan.setEndDate(applicationDate.plusDays(term));
        loan.setInterest(loanSettings.getInterest());
        LoanRisk loanRisk = loanRiskService.calculateLoanRisk(loan);
        loan.setRiskStatus(loanRisk.getRiskStatus());
        loan = loanRepository.save(loan);
        if (loanRisk.getRiskStatus() != RiskStatus.OK) {
            throw new LoanRiskException(loanRisk.getRiskStatus(), loanRisk.getMessage());
        }
        return loan;
    }

    @Override
    public Loan prepareLoanForExtension(Loan loan) {
        loan.setEndDate(loan.getEndDate().plusWeeks(loanExtensionSettings.getWeeksToIncrease()));
        loan.setInterest(loan.getInterest().multiply(loanExtensionSettings.getFactor()));
        return loanRepository.save(loan);
    }
}
