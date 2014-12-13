package lv.rgl.mla.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lv.rgl.mla.infrastructure.serializers.JsonDateSerializer;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by rihards.gladisevs on 06.12.2014..
 */

@Entity
@JsonIgnoreProperties({"amount"})
public class Loan implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @Columns(columns = { @Column(name = "CURRENCY"), @Column(name = "AMOUNT") })
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmountAndCurrency")
    private Money amount;

    private LocalDateTime applicationDate;

    private LocalDateTime endDate;

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

    @JsonSerialize(using = JsonDateSerializer.class)
    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
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
