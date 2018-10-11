package com.wallet.walletapi.dao;

import com.wallet.walletapi.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class WalletRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int insert(UserRequest userRequest) {
        return jdbcTemplate.update("insert into account (name, balance, created_at) " + "values(?, ?, ?)",
                new Object[] {
                        userRequest.getName(), userRequest.getBalance(), new Date()
                });
    }

    public UserRequest findById(long id) {
        return jdbcTemplate.queryForObject("select * from account where id=?", new Object[] {
                        id
                },
                new BeanPropertyRowMapper< UserRequest >(UserRequest.class));
    }

    public int update(UserRequest userRequest) {
        return jdbcTemplate.update("update account " + " set balance = ? " + " where id = ?",
                new Object[] {
                        userRequest.getBalance(), userRequest.getId()
                });
    }
}
