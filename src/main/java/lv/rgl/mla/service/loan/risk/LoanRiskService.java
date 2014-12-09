package lv.rgl.mla.service.loan.risk;

import lv.rgl.mla.domain.Loan;
import lv.rgl.mla.domain.LoanRisk;

/**
 * Created by rihards.gladisevs on 08.12.2014..
 */
public interface LoanRiskService {
    LoanRisk calculateLoanRisk(Loan loan);
}
