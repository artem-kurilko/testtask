package com.testtask.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rate {
    @Id
    @Column
    public Currency symbol;
    @Column
    public float price;
    @Column
    public Date date;
    @Column
    public BankName bankName;
}
