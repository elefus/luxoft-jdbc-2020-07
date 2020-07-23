package com.luxoft.trainings.jva005.day_4.helper.service;

import com.luxoft.trainings.jva005.day_4.helper.dao.DAO;
import com.luxoft.trainings.jva005.day_4.helper.dao.UserDAO;
import com.luxoft.trainings.jva005.domain.User;

import java.util.Optional;

public class Service {

    public static void main(String[] args) {
        DAO<User, Long> userDAO = new UserDAO();

        Optional<User> user = userDAO.get(1L);
        user.ifPresent(System.out::println);


        User newUser = new User(-1, "NAME", "ADDRESS", "USERNAME", "PASSWORD", 0, 0);
        userDAO.create(newUser);
        System.out.println(userDAO.getAll());
    }
}
