package com.luxoft.trainings.jva005.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
// @NoArgsConstructor
// @Getter
// @Setter
// @EqualsAndHashCode
// @ToString
public class User {

    private long userId;
    private String name;
    private String address;
    private String username;
    private String password;
    private int accessLevel;
    private int rating;
}
