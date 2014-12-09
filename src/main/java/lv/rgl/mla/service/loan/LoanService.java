package lv.rgl.mla.service.loan;

import lv.rgl.mla.domain.Client;
import lv.rgl.mla.domain.Loan;
import lv.rgl.mla.infrastructure.exceptions.LoanRiskException;
import org.joda.money.Money;

/**
 * Created by rihards.gladisevs on 06.12.2014..
 */
public interface LoanService {
    public Loan applyForLoan(Client client, Money amount, Integer term) throws LoanRiskException;

    public Loan prepareLoanForExtension(Loan loan);
}
