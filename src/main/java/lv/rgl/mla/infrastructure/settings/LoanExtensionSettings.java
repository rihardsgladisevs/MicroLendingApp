package lv.rgl.mla.infrastructure.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by rihards.gladisevs on 07.12.2014..
 */
@Component
@ConfigurationProperties(prefix = "loan.extension")
public class LoanExtensionSettings {
    @NotNull
    private BigDecimal factor;

    @NotNull
    private Integer weeksToIncrease;

    public BigDecimal getFactor() {
        return factor;
    }

    public void setFactor(BigDecimal factor) {
        this.factor = factor;
    }

    public Integer getWeeksToIncrease() {
        return weeksToIncrease;
    }

    public void setWeeksToIncrease(Integer weeksToIncrease) {
        this.weeksToIncrease = weeksToIncrease;
    }
}
