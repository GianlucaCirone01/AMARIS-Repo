package com.amaris.it.paypal.transaction.mapper;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.model.Transaction;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionRowMapper implements RowMapper<Transaction> {

  @Override
  public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new Transaction(
        rs.getLong("Id"),
        rs.getString("Sender"),
        rs.getString("Receiver"),
        rs.getDouble("Amount"),
        TransactionResult.TransactionStatus.valueOf(rs.getString("TransactionStatus")),
        rs.getTimestamp("executionDate"),
        rs.getTimestamp("CreationDate")
    );
  }
}
