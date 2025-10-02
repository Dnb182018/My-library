package com.library.apigateway.domainclientlayer.fines;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

@Getter
@NoArgsConstructor
public class FinePayment {

    private BigDecimal amount;

    private Currency currency;
    private LocalDate paymentDate;

    private PaymentStatus status;

    private PaymentMethod paymentMethod;

    public FinePayment(@NotNull BigDecimal amount,
                       @NotNull Currency currency,
                       @NotNull LocalDate paymentDate,
                       @NotNull PaymentStatus status,
                       @NotNull PaymentMethod paymentMethod) {
        this.amount = amount;
        this.currency = currency;
        this.paymentDate = paymentDate;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }
}
