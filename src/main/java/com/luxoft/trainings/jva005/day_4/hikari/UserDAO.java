package com.luxoft.trainings.jva005.day_4.hikari;

import com.luxoft.trainings.jva005.domain.User;

import javax.sql.DataSource;
import java.util.List;

public interface UserDAO {

    void setDataSource(DataSource dataSource);

    List<User> findAll();

    User findUserById(Long id);

    User createUser(String name, String address, String username, String password, int accesslevel, int rating);

    void removeUser(Long id);

    void updateUser(Long id, String name, String specialty, Integer experience);
}
