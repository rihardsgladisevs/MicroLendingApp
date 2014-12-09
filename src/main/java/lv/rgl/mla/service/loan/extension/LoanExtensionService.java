package lv.rgl.mla.service.loan.extension;

import lv.rgl.mla.domain.Loan;
import lv.rgl.mla.domain.LoanExtension;

/**
 * Created by rihards.gladisevs on 08.12.2014..
 */
public interface LoanExtensionService {
    LoanExtension makeExtension(Loan loan);
}
