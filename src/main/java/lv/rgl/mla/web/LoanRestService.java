package lv.rgl.mla.web;

import lv.rgl.mla.domain.Client;
import lv.rgl.mla.domain.Loan;
import lv.rgl.mla.infrastructure.exceptions.LoanRiskException;
import lv.rgl.mla.service.client.ClientService;
import lv.rgl.mla.service.loan.LoanService;
import lv.rgl.mla.service.loan.extension.LoanExtensionService;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by rihards.gladisevs on 02.12.2014..
 */

@RestController
@RequestMapping(value = "/loans")
public class LoanRestService {

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanExtensionService loanExtensionService;

    @Autowired
    private ClientService clientService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> applyForLoan(@RequestParam("amount") BigDecimal amount,
                                               @RequestParam("term") Integer term,
                                               HttpServletRequest request) throws LoanRiskException {
        final String ip = request.getRemoteAddr();
        Client client = clientService.getClientForLoan(ip);
        loanService.applyForLoan(client, Money.of(CurrencyUnit.EUR, amount), term);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Loan> getLoanHistory(HttpServletRequest request) {
        final String ip = request.getRemoteAddr();
        return clientService.getLoansByClientIp(ip);
    }

    @RequestMapping(value = "/{loanId}/extend", method = RequestMethod.POST)
    public ResponseEntity<String> extendLoan(@PathVariable("loanId") Long loanId, HttpServletRequest request) {
        final String ip = request.getRemoteAddr();
        Loan loan = clientService.getClientLoan(ip, loanId);
        loan = loanService.prepareLoanForExtension(loan);
        loanExtensionService.makeExtension(loan);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
