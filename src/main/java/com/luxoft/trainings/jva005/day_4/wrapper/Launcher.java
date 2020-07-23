package com.luxoft.trainings.jva005.day_4.wrapper;

import com.luxoft.trainings.jva005.day_4.wrapper.wrappers.GetAllUsersSqlWrapper;
import com.luxoft.trainings.jva005.domain.User;

import java.util.List;

public class Launcher {

    public static void main(String[] args) {
        GetAllUsersSqlWrapper getAllUsers = new GetAllUsersSqlWrapper();
        List<User> users = getAllUsers.query();
        System.out.println(users);
    }
}
