package com.luxoft.trainings.jva005.day_4.hikari;

import com.luxoft.trainings.jva005.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Service
public class JdbcTemplateUserDAO implements UserDAO {

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> User.builder()
                                                                          .userId(rs.getLong("userid"))
                                                                          .name(rs.getString("name"))
                                                                          .address(rs.getString("address"))
                                                                          .username(rs.getString("username"))
                                                                          .password(rs.getString("password"))
                                                                          .accessLevel(rs.getInt("accesslevel"))
                                                                          .rating(rs.getInt("rating"))
                                                                          .build();
    private JdbcTemplate jdbcTemplate;

    @Override
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT userid, name, address, username, password, accesslevel, rating FROM shop.users";
        return jdbcTemplate.query(sql, USER_ROW_MAPPER);
    }

    @Override
    public User findUserById(Long id) {
        String sql = "SELECT userid, name, address, username, password, accesslevel, rating FROM shop.users WHERE userid=?";

        return jdbcTemplate.queryForObject(sql, args(id), USER_ROW_MAPPER);
    }

    @Override
    public User createUser(String name, String address, String username, String password, int accesslevel, int rating) {
        String sql = "INSERT INTO shop.users (name, address, username, password, accesslevel, rating) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"userid"});
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, username);
            ps.setString(4, password);
            ps.setInt(5, accesslevel);
            ps.setInt(6, rating);
            return ps;
        }, keyHolder);
        return User.builder()
                   .userId(Optional.ofNullable(keyHolder.getKey()).map(Number::longValue).orElseThrow(AssertionError::new))
                   .name(name)
                   .address(address)
                   .username(username)
                   .password(password)
                   .accessLevel(accesslevel)
                   .rating(rating)
                   .build();
    }

    @Override
    public void removeUser(Long id) {

    }

    @Override
    public void updateUser(Long id, String name, String specialty, Integer experience) {

    }

    private static Object[] args(Object...args) {
        return args;
    }
}