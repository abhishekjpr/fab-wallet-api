package com.wallet.walletapi.dao;

import com.wallet.walletapi.model.Transactions;
import com.wallet.walletapi.util.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class TransactionRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int insert(Transactions userRequest) {
        return jdbcTemplate.update("insert into transaction (comments, created_at) " + "values(?, ?)",
                new Object[] {
                        userRequest.getComments(), new Date()
                });
    }

    public List<Transactions> findByDate(String startTime, String endTime) {
        return jdbcTemplate.query("select * from transaction where created_at >= ? and created_at <= ?", new Object[] {
                        startTime, endTime
                },
                new TransactionMapper());
    }
}
