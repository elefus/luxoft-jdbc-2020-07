package com.luxoft.trainings.jva005.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {

    private long userId;
    private String name;
    private String address;
    private String username;
    private String password;
    private int accessLevel;
    private int rating;
}
