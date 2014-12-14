package lv.rgl.mla.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lv.rgl.mla.infrastructure.serializers.JsonDateSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by rihards.gladisevs on 06.12.2014..
 */

@Entity
public class LoanExtension {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime extensionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Loan loan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public LocalDateTime getExtensionDate() {
        return extensionDate;
    }

    public void setExtensionDate(LocalDateTime extensionDate) {
        this.extensionDate = extensionDate;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
