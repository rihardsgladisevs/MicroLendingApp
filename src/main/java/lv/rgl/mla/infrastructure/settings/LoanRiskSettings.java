package lv.rgl.mla.infrastructure.settings;

import org.joda.money.Money;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * Created by rihards.gladisevs on 08.12.2014..
 */
@Component
@ConfigurationProperties(prefix = "loan.risk")
public class LoanRiskSettings {
    @NotNull
    private Money maxAmount;

    @NotNull
    private Integer maxApplicationsPerDay;

    @NotNull
    private Integer nightEndHour;

    public Money getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Money maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Integer getMaxApplicationsPerDay() {
        return maxApplicationsPerDay;
    }

    public void setMaxApplicationsPerDay(Integer maxApplicationsPerDay) {
        this.maxApplicationsPerDay = maxApplicationsPerDay;
    }

    public Integer getNightEndHour() {
        return nightEndHour;
    }

    public void setNightEndHour(Integer nightEndHour) {
        this.nightEndHour = nightEndHour;
    }
}
