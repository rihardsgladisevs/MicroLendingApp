package lv.rgl.mla.service.loan;

import lv.rgl.mla.domain.Client;
import lv.rgl.mla.domain.Loan;
import lv.rgl.mla.domain.RiskStatus;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by rihards.gladisevs on 06.12.2014..
 */
interface LoanRepository extends CrudRepository<Loan, Long> {
    Loan findByIdAndClientAndRiskStatus(Long Id, Client client, RiskStatus riskStatus);
}
