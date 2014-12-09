package lv.rgl.mla.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by rihards.gladisevs on 06.12.2014..
 */

@Entity
@JsonIgnoreProperties({"extensionDate"})
public class LoanExtension implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime extensionDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private Loan loan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getExtensionDate() {
        return extensionDate;
    }

    public Date getExtensionDatePrepared() {
        return (extensionDate != null) ? extensionDate.toDate() : null;
    }

    public void setExtensionDate(DateTime extensionDate) {
        this.extensionDate = extensionDate;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
