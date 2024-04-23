package com.fhlBank.demo.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    @NotBlank
    String accountType;
    @NotNull
    Double depositAmount;
}
