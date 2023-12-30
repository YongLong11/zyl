package com.zyl.service;

import com.zyl.orm.jpa.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class dataSource {

    @Resource
    private DataSource ZYL;

    @Test
    public void testJdbc(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ZYL);
//        jdbcTemplate.query("select name, code from user ", new RowMapper<User>() {
//            @Override
//            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//                User user = new User();
//                rs.
//                user.setUserName(rs.getString(rowNum));
//                return null;
//            }
//        });
        List<User> query = jdbcTemplate.query("select name, code from user ", new BeanPropertyRowMapper<User>(User.class));
        System.out.println(query);
        System.out.println("=======================");
        List<User> query1 = jdbcTemplate.query("select name, code from user ", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                user.setUserName(rs.getString("name"));
                return user;
            }
        });
        System.out.println(query1);
    }

}
