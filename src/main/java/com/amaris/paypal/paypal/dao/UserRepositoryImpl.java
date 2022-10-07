package com.amaris.paypal.paypal.dao;

import com.amaris.paypal.paypal.model.Utenti;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserRepositoryImpl implements  UserRepository {

    private static final String INSERT_USER_QUERY="INSERT INTO UTENTI(id,username,nome,cognome,saldo) values(?,?,?,?,?)";
    private static final String UPDATE_USER_BY_ID_QUERY="UPDATE UTENTI SET username=? where id=?";
    private static final String GET_USER_BY_ID_QUERY="SELECT * FROM UTENTI WHERE ID =?";
    private static final String DELETE_USER_BY_ID_QUERY="DELETE FROM UTENTI WHERE ID=?";
    private static  final String GET_USERS_QUERY="SELECT * FROM UTENTI";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public Utenti saveUser(Utenti user) {
        jdbcTemplate.update(INSERT_USER_QUERY,user.getId(),user.getUsername(),user.getNome(),user.getCognome(),user.getSaldo());
        return user;
    }

    @Override
    public Utenti updateUser(Utenti user) {
        jdbcTemplate.update(UPDATE_USER_BY_ID_QUERY,user.getUsername(),user.getId());
        return user;
    }

    @Override
    public Utenti getById(int id) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID_QUERY,(rs,rowNum)->{

            return new Utenti(rs.getInt("id"),rs.getString("username"),rs.getString("nome"),rs.getString("cognome"),rs.getInt("saldo"));
        });
    }

    @Override
    public String deleteById(int id) {
        jdbcTemplate.update(DELETE_USER_BY_ID_QUERY,id);
        return "User got Deleted with id:"+id;
    }

    @Override
    public List<Utenti> allUsers() {
        return jdbcTemplate.query(GET_USERS_QUERY,(rs,rowNum)->{

            return new Utenti(rs.getInt("id"),rs.getString("username"),rs.getString("nome"),rs.getString("cognome"),rs.getInt("saldo"));
        });
    }
}
