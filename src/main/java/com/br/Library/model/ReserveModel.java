package com.br.Library.model;

import java.time.LocalDate;

import com.br.Library.enums.ReserveStatus;

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
@Table(name = "tb_reserve")
@Data
public class ReserveModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    private BookModel book;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private UserModel client;
    @Column
    private LocalDate reserveDate;
    @Column
    private LocalDate expirationDate;
    @Enumerated(EnumType.STRING)
    private ReserveStatus status;

    public ReserveModel() {
        this.reserveDate = LocalDate.now();
        this.status = ReserveStatus.ACTIVE;
    }
}
