package lv.rgl.mla.domain;

import lv.rgl.mla.infrastructure.enums.RiskStatus;

/**
 * Created by rihards.gladisevs on 08.12.2014..
 */
public class LoanRisk {
    private RiskStatus riskStatus = RiskStatus.NOT_PROCESSED;
    private String message;

    public LoanRisk (RiskStatus riskStatus, String message) {
        this.riskStatus = riskStatus;
        this.message = message;
    }

    public RiskStatus getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(RiskStatus riskStatus) {
        this.riskStatus = riskStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
