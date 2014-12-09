package lv.rgl.mla.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lv.rgl.mla.infrastructure.enums.RiskStatus;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.Money;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by rihards.gladisevs on 06.12.2014..
 */

@Entity
@JsonIgnoreProperties({"amount", "applicationDate", "endDate"})
public class Loan implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @Columns(columns = { @Column(name = "CURRENCY"), @Column(name = "AMOUNT") })
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmountAndCurrency")
    private Money amount;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime applicationDate;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime endDate;

    private BigDecimal interest;

    private RiskStatus riskStatus = RiskStatus.NOT_PROCESSED;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private Client client;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "loan", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LoanExtension> extensions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Money getAmount() {
        return amount;
    }

    public BigDecimal getAmountPrepared() {
        return (amount != null) ? amount.getAmount() : null;
    }

    public String getCurrency() {
        return (amount != null) ? amount.getCurrencyUnit().getCurrencyCode() : null;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public DateTime getApplicationDate() {
        return applicationDate;
    }

    public Date getApplicationDatePrepared() {
        return (applicationDate != null) ? applicationDate.toDate(): null;
    }

    public void setApplicationDate(DateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public Date getEndDatePrepared() {
        return (endDate != null) ? endDate.toDate() : null;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public RiskStatus getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(RiskStatus riskStatus) {
        this.riskStatus = riskStatus;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<LoanExtension> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<LoanExtension> extensions) {
        this.extensions = extensions;
    }
}
