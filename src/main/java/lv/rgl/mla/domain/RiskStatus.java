package lv.rgl.mla.domain;

/**
 * Created by rihards.gladisevs on 07.12.2014..
 */
public enum RiskStatus {
    NOT_PROCESSED("Loan is not processed"),
    OK("Loan has no risk"),
    APPLIED_AFTER_MIDNIGHT("Attempt to take loan is made after 00:00 with max possible amount"),
    REACHED_MAX_APPLICATIONS_PER_DAY("Reached maximium applications per day"),
    OVER_MAX_AMOUNT("Amount is over maximum possible amount");

    private String message;

    RiskStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
