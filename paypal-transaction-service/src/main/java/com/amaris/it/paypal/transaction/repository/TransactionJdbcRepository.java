package com.amaris.it.paypal.transaction.repository;

import com.amaris.it.paypal.messages.model.TransactionResult;
import com.amaris.it.paypal.transaction.model.TransactionMoney;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class TransactionJdbcRepository implements TransactionJdbcInterface {

  @Value("${database_transactionMoney.url}")
  private String dbTransactionMoneyUrl;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public Long save(TransactionMoney dto) {

    final KeyHolder keyHolder = new GeneratedKeyHolder();
    final String sql = "INSERT INTO "
        + dbTransactionMoneyUrl
        + " (mittente,destinatario,Money,Stato_transazione) "
        + "VALUES(?, ?, ?, ?)";

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, dto.getUser1());
      ps.setString(2, dto.getUser2());
      ps.setDouble(3, dto.getMoney());
      ps.setString(4, dto.getStatusTransaction().name());
      return ps;
    }, keyHolder);

    return Objects.requireNonNull(keyHolder.getKey()).longValue();
  }

  @Override
  public TransactionMoney findById(Long id) {

    final String sql = "SELECT * FROM "
        + dbTransactionMoneyUrl
        + " WHERE id=?";

    return jdbcTemplate.queryForObject(sql
        , new BeanPropertyRowMapper<>(TransactionMoney.class), id);
  }

  @Override
  public void updateStatus(Long id, TransactionResult.TransactionStatus status) {
    final String sql = "UPDATE "
        + dbTransactionMoneyUrl
        + " SET Stato_transazione = ? "
        + "WHERE ID = ?";

    jdbcTemplate.update(sql, status.name(), id);
  }

}
