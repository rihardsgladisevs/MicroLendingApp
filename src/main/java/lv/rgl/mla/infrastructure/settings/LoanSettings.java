package lv.rgl.mla.infrastructure.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by rihards.gladisevs on 07.12.2014..
 */
@Component
@ConfigurationProperties(prefix = "loan")
public class LoanSettings {
    @NotNull
    private BigDecimal interest;

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }
}
