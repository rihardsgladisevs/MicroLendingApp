package lv.rgl.mla.infrastructure.exceptions;

import lv.rgl.mla.infrastructure.enums.RiskStatus;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoanRiskExceptionTest {

    private static final RiskStatus STATUS = RiskStatus.OVER_MAX_AMOUNT;

    private static final String MESSAGE = "test";

    @Test
    public void testLoanRiskException() {
        try {
            throw new LoanRiskException(STATUS, MESSAGE);
        } catch (LoanRiskException e) {
            assertEquals(STATUS, e.getStatus());
            assertEquals(STATUS + ": " + MESSAGE, e.getMessage());
        }
    }
}