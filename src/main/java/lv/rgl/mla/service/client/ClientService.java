package lv.rgl.mla.service.client;

import lv.rgl.mla.domain.Client;
import lv.rgl.mla.domain.Loan;

import java.util.List;

/**
 * Created by rihards.gladisevs on 07.12.2014..
 */
public interface ClientService {
    Loan getClientLoan(String ip, Long loanId);
    List<Loan> getLoansByClientIp(String ip);
    Client getClientForLoan(String ip);
}
