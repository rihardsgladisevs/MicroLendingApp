package lv.rgl.mla.service.loan.risk;

import lv.rgl.mla.domain.Loan;
import lv.rgl.mla.domain.LoanRisk;
import lv.rgl.mla.infrastructure.enums.RiskStatus;
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
    public LoanRisk calculateLoanRisk(Loan loan) {
        if (loan.getAmount().isGreaterThan(loanRiskSettings.getMaxAmount())) {
            return new LoanRisk(RiskStatus.OVER_MAX_AMOUNT, "Amount is over maximum possible amount");
        } else if (reachedMaxApplicationsPerDay(loan)) {
            return new LoanRisk(RiskStatus.REACHED_MAX_APPLICATIONS_PER_DAY, "Reached maximium applications per day");
        } else if (loan.getAmount().isEqual(loanRiskSettings.getMaxAmount()) && applicationAtNight(loan)) {
            return new LoanRisk(RiskStatus.APPLIED_AFTER_MIDNIGHT, "Attempt to take loan is made after 00:00 with max possible amount");
        } else {
            return new LoanRisk(RiskStatus.OK, "Loan has no risk");
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
