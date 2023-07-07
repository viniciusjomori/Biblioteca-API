package com.br.Library.model;

import java.time.LocalDate;

import com.br.Library.enums.LoanStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_loan")
@Data
public class LoanModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    private BookModel book;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserModel client;
    @Column
    private LocalDate loanDate;
    @Column
    private LocalDate deliveryDate;
    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    public LoanModel() {
        this.status = LoanStatus.ACTIVE;
        this.loanDate = LocalDate.now();
    }
}
