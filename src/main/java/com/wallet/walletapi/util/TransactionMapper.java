package com.wallet.walletapi.util;

import com.wallet.walletapi.model.Transactions;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionMapper implements RowMapper<Transactions> {

    @Override
    public Transactions mapRow(ResultSet rs, int rowNum) throws SQLException {
        Transactions transactions = new Transactions();
        transactions.setId(rs.getInt("id"));
        transactions.setComments(rs.getString("comments"));
        transactions.setDate(rs.getDate("created_at"));
        return transactions;
    }
}
