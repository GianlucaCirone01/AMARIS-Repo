package com.amaris.it.paypal.transaction.mapper;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.model.ScheduledTransaction;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScheduledRowMapper implements RowMapper<ScheduledTransaction> {
  @Override
  public ScheduledTransaction mapRow(ResultSet rs, int rowNum) throws SQLException {
    return new ScheduledTransaction(
        rs.getLong("Id"),
        rs.getString("Sender"),
        rs.getString("Receiver"),
        rs.getDouble("Amount"),
        TransactionResult.TransactionStatus.valueOf(rs.getString("TransactionStatus")),
        rs.getTimestamp("CreationDate"),
        rs.getInt("Mode")
    );
  }
}
