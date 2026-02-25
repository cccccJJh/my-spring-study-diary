package com.study.myspringstudydiary.auth.dao;

import com.study.myspringstudydiary.auth.entity.User;
import com.study.myspringstudydiary.auth.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Slf4j
@Repository
@Primary
public class UserDao {
    private final JdbcTemplate jdbcTemplate;


    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByUserName(String userName){
        String sql = "SELECT * FROM users WHERE username = ?";

        try{
            User user = jdbcTemplate.queryForObject(sql, userRowMapper(), userName);
            log.debug("FindByUserName   >>>>   {} ", userName);
            return Optional.ofNullable(user);
        }catch (Exception e) {
            log.debug("no user   >>>>   {} ", userName);
            return Optional.empty();
        }
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try {
            User user = jdbcTemplate.queryForObject(sql,userRowMapper(), id);
            log.debug("Found user by ID: {}", id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            log.debug("No user found with ID: {}", id);
            return Optional.empty();
        }
    }


    // ⭐ JdbcTemplate을 위한 포장 규칙 (RowMapper)
    // DB에서 한 줄(Row)을 읽어올 때마다 이 규칙대로 User 객체를 조립합니다.
    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> User.builder()
                .id(rs.getLong("id"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .username(rs.getString("username"))
                .role(UserRole.valueOf(rs.getString("role")))
                .enabled(rs.getBoolean("enabled"))
                .build();
    }
}
