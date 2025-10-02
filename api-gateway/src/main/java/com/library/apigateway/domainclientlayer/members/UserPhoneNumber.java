package com.library.apigateway.domainclientlayer.members;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor

public class UserPhoneNumber {

    private PhoneType type;
    private String number;

    public UserPhoneNumber(@NotNull PhoneType type, @NotNull String number) {
        this.type = type;
        this.number = number;
    }
}
