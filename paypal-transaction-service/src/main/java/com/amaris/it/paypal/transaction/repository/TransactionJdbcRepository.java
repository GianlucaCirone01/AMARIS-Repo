package com.amaris.it.paypal.transaction.repository;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.mapper.TransactionRowMapper;
import com.amaris.it.paypal.transaction.model.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Repository
public class TransactionJdbcRepository implements TransactionRepository {

  private static final String TRANSACTION_TABLE = "transaction";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public Long save(Transaction dto) {

    final KeyHolder keyHolder = new GeneratedKeyHolder();
    final String sql = "INSERT INTO "
        + TRANSACTION_TABLE
        + " (Sender,Receiver,Amount,TransactionStatus,executionDate,CreationDate) "
        + "VALUES(?, ?, ?, ?, ?, ?)";

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, dto.getSenderUsername());
      ps.setString(2, dto.getReceiverUsername());
      ps.setDouble(3, dto.getAmount());
      ps.setString(4, dto.getTransactionStatus().name());
      ps.setTimestamp(5, dto.getExecutionDate());
      ps.setTimestamp(6, dto.getCreationDate());
      return ps;
    }, keyHolder);

    return Objects.requireNonNull(keyHolder.getKey()).longValue();
  }

  @Override
  public Transaction findById(Long id) {

    final String sql = "SELECT * FROM "
        + TRANSACTION_TABLE
        + " WHERE id=?";

    return jdbcTemplate.queryForObject(sql
        , new BeanPropertyRowMapper<>(Transaction.class), id);
  }

  @Override
  public void updateStatus(Long id, TransactionResult.TransactionStatus status) {
    final String sql = "UPDATE "
        + TRANSACTION_TABLE
        + " SET TransactionStatus = ? "
        + "WHERE ID = ?";

    jdbcTemplate.update(sql, status.name(), id);
  }

  @Override
  public List<Transaction> selectForADate(Timestamp now,
      TransactionResult.TransactionStatus status) {
    final String sql = "SELECT * FROM "
        + TRANSACTION_TABLE
        + " WHERE TransactionStatus = ? "
        + " AND executionDate <= ? "
        + "AND executionDate IS NOT NULL";

    return jdbcTemplate.query(sql
        , new TransactionRowMapper()
        , status.name()
        , now);
  }

  @Override
  public List<Transaction> selectByStatus(TransactionResult.TransactionStatus status) {
    final String sql = "SELECT * FROM "
        + TRANSACTION_TABLE
        + " WHERE TransactionStatus = ? "
        + "AND executionDate IS NULL";

    return jdbcTemplate.query(sql
        , new TransactionRowMapper()
        , status.name());
  }

  @Override
  public List<Transaction> selectByStatusAndCreationDate(TransactionResult.TransactionStatus status
      , Timestamp threshold) {

    final String sql = "SELECT * FROM "
        + TRANSACTION_TABLE
        + " WHERE TransactionStatus = ? "
        + "AND CreationDate <= ? ";

    return jdbcTemplate.query(sql
        , new TransactionRowMapper()
        , status.name()
        , threshold);
  }
}
