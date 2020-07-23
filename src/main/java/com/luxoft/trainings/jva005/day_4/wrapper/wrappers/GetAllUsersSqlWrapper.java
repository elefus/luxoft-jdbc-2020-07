package com.luxoft.trainings.jva005.day_4.wrapper.wrappers;

import com.luxoft.trainings.jva005.day_4.wrapper.db.SqlWrapper;
import com.luxoft.trainings.jva005.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetAllUsersSqlWrapper extends SqlWrapper<User> {

    @Override
    protected String getQuery() {
        return "SELECT userid, name, address, username, password, accesslevel, rating FROM shop.users";
    }

    @Override
    protected User parse(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("userid"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getInt("accesslevel"),
                rs.getInt("rating")
        );
    }

    private static class ResultQueryClass {

        private long id;
        private String value;
        private String value2;
    }
}
