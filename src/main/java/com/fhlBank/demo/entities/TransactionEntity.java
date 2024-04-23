package com.fhlBank.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Table(name="transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int transactionId;
    int fromAccount;
    int toAccount;
    double transferAmount;
    Timestamp created;
}
