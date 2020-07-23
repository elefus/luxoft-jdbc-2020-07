package com.luxoft.trainings.jva005.day_4.hikari;

import com.luxoft.trainings.jva005.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDAO dao;

    public UserService(UserDAO dao) {
        this.dao = dao;
    }

    public List<User> findAll() {
        return dao.findAll();
    }

    public User createUser(String name, String address, String username, String password, int accesslevel, int rating) {
        return dao.createUser(name, address, username, password, accesslevel, rating);
    }
}
