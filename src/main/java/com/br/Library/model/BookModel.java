package com.br.Library.model;

import java.time.LocalDate;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_book")
@Data
public class BookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private String publishingCompany;
    @Column
    private LocalDate releaseDate;
    @Column
    private int totalCopies;
    @Column
    private int availableCopies;
    @OneToMany(mappedBy = "book")
    Collection<LoanModel> loans;
    @OneToMany(mappedBy = "book")
    Collection<ReserveModel> reserves;

}