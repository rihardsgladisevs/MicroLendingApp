package lv.rgl.mla.service.loan.risk;

import lv.rgl.mla.domain.Loan;
import lv.rgl.mla.domain.RiskStatus;
import lv.rgl.mla.infrastructure.producers.DateTimeProducer;
import lv.rgl.mla.infrastructure.settings.LoanRiskSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rihards.gladisevs on 08.12.2014..
 */
@Service
public class LoanRiskServiceImpl implements LoanRiskService {

    @Autowired
    private LoanRiskSettings loanRiskSettings;

    @Autowired
    private DateTimeProducer dateTimeProducer;

    @Override
    public RiskStatus calculateLoanRisk(Loan loan) {
        if (loan.getAmount().isGreaterThan(loanRiskSettings.getMaxAmount())) {
            return RiskStatus.OVER_MAX_AMOUNT;
        } else if (reachedMaxApplicationsPerDay(loan)) {
            return RiskStatus.REACHED_MAX_APPLICATIONS_PER_DAY;
        } else if (loan.getAmount().isEqual(loanRiskSettings.getMaxAmount()) && applicationAtNight(loan)) {
            return RiskStatus.APPLIED_AFTER_MIDNIGHT;
        } else {
            return RiskStatus.OK;
        }
    }

    private boolean reachedMaxApplicationsPerDay (Loan loan) {
        LocalDate today = dateTimeProducer.getCurrentDateTime().toLocalDate();
        List<Loan> loans = loan.getClient().getLoans();
        if (loans != null) {
            List<Loan> todayLoans = loans.stream().filter(l -> today.compareTo(l.getApplicationDate().toLocalDate()) == 0).collect(Collectors.toList());
            return todayLoans.size() >= loanRiskSettings.getMaxApplicationsPerDay();
        }
        return false;
    }

    private boolean applicationAtNight (Loan loan) {
        LocalDateTime localApplicationDate = loan.getApplicationDate();
        return localApplicationDate.getHour() < loanRiskSettings.getNightEndHour();
    }
}
