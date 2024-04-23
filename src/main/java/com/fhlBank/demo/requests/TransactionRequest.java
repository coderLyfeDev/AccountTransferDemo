package com.fhlBank.demo.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    @NotNull
    private int toAccount;
    @NotNull
    private int fromAccount;
    @NotNull
    private double amount;
}
