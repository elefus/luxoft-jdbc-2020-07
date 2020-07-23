package com.luxoft.trainings.jva005.day_4.helper.dao;

import com.luxoft.trainings.jva005.day_4.helper.jdbc.JDBCHelper;
import com.luxoft.trainings.jva005.domain.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDAO implements DAO<User, Long> {

    private final JDBCHelper helper = new JDBCHelper();

    @Override
    public Optional<User> get(Long id) {
        return helper.queryUnique(
                "SELECT userid, name, address, username, password, accesslevel, rating FROM shop.users WHERE userid=?",
                rs -> { try { return new User(
                        rs.getLong("userid"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("accesslevel"),
                        rs.getInt("rating")
                    ); } catch (SQLException ex) {
                        throw new RuntimeException("Failed to parse ResultSet as User instance!", ex);
                    }},
                id);
    }

    @Override
    public List<User> getAll() {
        return helper.query("SELECT userid, name, address, username, password, accesslevel, rating FROM shop.users",
                rs -> { try { return new User(
                        rs.getLong("userid"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("accesslevel"),
                        rs.getInt("rating")
                ); } catch (SQLException ex) {
                    throw new RuntimeException("Failed to parse ResultSet as User instance!", ex);
                }});
    }

    @Override
    public void create(User account) {
        int createdRows = helper.executeUpdate(
                "INSERT INTO shop.users (name, address, username, password, accesslevel, rating) VALUES (?, ?, ?, ?, ?, ?)",
                account.getName(),
                account.getAddress(),
                account.getUsername(),
                account.getPassword(),
                account.getAccessLevel(),
                account.getRating()
        );
        if (createdRows != 1) {
            throw new IllegalStateException();
        }
    }

    @Override
    public void update(User account) {
        // TODO continue
    }

    @Override
    public void delete(Long aLong) {
        // TODO continue
    }
}
