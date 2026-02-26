package com.study.myspringstudydiary.auth.dao;

import com.study.myspringstudydiary.auth.entity.User;
import com.study.myspringstudydiary.auth.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Optional;
@Slf4j
@Repository
@Primary
public class UserDao {
    private final JdbcTemplate jdbcTemplate;


    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByUserName(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try{
            User user = jdbcTemplate.queryForObject(sql, userRowMapper(), username);
            log.debug("FindByUserName   >>>>   {} ", username);
            return Optional.ofNullable(user);
        }catch (EmptyResultDataAccessException e) {
            log.debug("no user   >>>>   {} ", username);
            return Optional.empty();
        }catch (Exception e) {
            log.debug("알수 없는 오류   >>>>   {} ", username);
            return Optional.empty();
        }
    }

    public boolean existsByUsername(String username){
        String sql = "SELECT * FROM users WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0 ;
    }

    public boolean existsByEmail(String email){
        String sql = "SELECT * FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0 ;
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

    // ---- save --
    public User save(User user){
        String sql = """
                INSERT INTO users (email, password, username, role, enabled, created_at, updated_at) 
                VALUES (?, ?, ?, ?, ?, NOW(), NOW())""";

        // 자동 생성된 ID(PK)를 가져오기 위한 KeyHolder
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword()); // 이미 암호화된 비밀번호여야 함
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getRole().name()); // Enum일 경우 name() 사용
            ps.setBoolean(5, user.isEnabled());
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            user.setId(keyHolder.getKey().longValue());
        }

        return user;
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
