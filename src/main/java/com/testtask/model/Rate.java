package com.testtask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rate {
    @Id
    @GeneratedValue
    @JsonIgnore
    @Column
    private Long id;
    @Column
    public Currency symbol;
    @Column
    public float price;
    @Column
    public Date date;
    @JsonIgnore
    @Column
    public BankName bankName;
}
