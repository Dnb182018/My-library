package com.library.members.datalayer.Users;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Embeddable
@Getter
@NoArgsConstructor
public class UserAddress {

    private String street_name;
    private String city;
    private String province;
    private String country;
    private String postal_code;

    public UserAddress(@NotNull String street_name,
                       @NotNull String city,
                       @NotNull String province,
                       @NotNull String country,
                       @NotNull String postal_code) {
        this.street_name = street_name;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postal_code = postal_code;
    }
}
