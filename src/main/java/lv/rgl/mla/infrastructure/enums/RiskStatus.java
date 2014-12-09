package lv.rgl.mla.infrastructure.enums;

/**
 * Created by rihards.gladisevs on 07.12.2014..
 */
public enum RiskStatus {
    NOT_PROCESSED,
    OK,
    APPLIED_AFTER_MIDNIGHT,
    REACHED_MAX_APPLICATIONS_PER_DAY,
    OVER_MAX_AMOUNT
}
