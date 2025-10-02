package com.library.fines.datalayer;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Embeddable
@Getter
@NoArgsConstructor
public class FinePayment {

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
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
