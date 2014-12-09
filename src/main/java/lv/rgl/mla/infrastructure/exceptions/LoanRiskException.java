package lv.rgl.mla.infrastructure.exceptions;

import lv.rgl.mla.infrastructure.enums.RiskStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by rihards.gladisevs on 07.12.2014..
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class LoanRiskException extends Exception {
    private RiskStatus status;

    public LoanRiskException(RiskStatus status, String message) {
        super(message);
        this.status = status;
    }

    public RiskStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return String.format("%s: %s", status, super.getMessage());
    }
}
