package com.amaris.paypal.paypal.dao;

import com.amaris.paypal.paypal.error.ApplicationException;
import com.amaris.paypal.paypal.error.CustomError;
import com.amaris.paypal.paypal.model.TransferBalance;
import com.amaris.paypal.paypal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class UserRepositoryImpl implements  UserRepository {

    private static final String INSERT_USER_QUERY="INSERT INTO user(id,username,name,surname,balance) values(?,?,?,?,?)";
    private static final String UPDATE_USER_BY_ID_QUERY="UPDATE user SET username=? where id=?";
    private static final String GET_USER_BY_ID_QUERY="SELECT * FROM user WHERE ID = ?";
    private static final String DELETE_USER_BY_ID_QUERY="DELETE FROM user WHERE ID= ?";
    private static  final String GET_USERS_QUERY="SELECT * FROM user";
    private static final String CHARGE_MONEY_QUERY = "update user set balance=balance + ? where username=?";

    private static final String TRANSFER_MONEY_QUERY ="update user set balance=balance - ? where username=?";

    //private static final String GET_USER_BY_USERNAME_QUERY="Select from user where username=?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

//DA SISTEMARE IL SAVEUSER PERCHE SI
    @Override
    public User saveUser(User user) {


        //User username = getById(user.getId());

        //if (!(user.getUsername().equals(username.getUsername()))){
            jdbcTemplate.update(INSERT_USER_QUERY,user.getId(),user.getUsername(),user.getName(),user.getSurname(),0);
            return user;
        //}else{
           // throw new ApplicationException( new CustomError(500, "Username gia in uso prova con un altro", "Username gia in uso"));
        //}



    }

    @Override
    public User updateUser(User user) {
        jdbcTemplate.update(UPDATE_USER_BY_ID_QUERY,user.getUsername(),user.getId());
        return user;
    }

    @Override
    public User getById(int id) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID_QUERY,(rs,rowNum)->{

            return new User(rs.getInt("id"),rs.getString("username"),rs.getString("name"),rs.getString("surname"),rs.getInt("balance"));
        },id);
    }



    @Override
    public String deleteById(int id) {
        jdbcTemplate.update(DELETE_USER_BY_ID_QUERY,id);
        return "User got Deleted with id:"+id;
    }

    @Override
    public List<User> allUsers() {
        return jdbcTemplate.query(GET_USERS_QUERY,(rs,rowNum)->{

            return new User(rs.getInt("id"),rs.getString("username"),rs.getString("name"),rs.getString("surname"),rs.getInt("balance"));
        });
    }

    @Override
    public User chargeMoney(User user) {


        if (user.getBalance() >= 1){
            jdbcTemplate.update(CHARGE_MONEY_QUERY,user.getBalance(),user.getUsername());
            return user;
        }else{
            throw new ApplicationException( new CustomError(500, "Non puoi inserire un saldo negativo", "Hai inserito un saldo negativo"));
        }

    }


    //ALLORA LE QUERY LE HO FATTO PERò NON CAPISCO COME FAR PASSARE PIU USERNAME IN POSTMAN
    @Override
    public void transferMoney(TransferBalance transferBalance) {

        User id1 = getById(transferBalance.getId());
        User id2 = getById(transferBalance.getId2());

        if (id1.getBalance() > transferBalance.getBalance()){

            jdbcTemplate.update(TRANSFER_MONEY_QUERY,transferBalance.getBalance(),id1.getUsername());
            jdbcTemplate.update(CHARGE_MONEY_QUERY,transferBalance.getBalance(),id2.getUsername());
        }else{
            throw new ApplicationException( new CustomError(500, "Saldo negativo non puoi trasferire soldi", "Hai inserito un saldo negativo"));
        }



    }

    /*@Override
    public User getByUsername(User user) {
        return jdbcTemplate.queryForObject(GET_USER_BY_USERNAME_QUERY,(rs,rowNum)->{

            return new User(rs.getInt("id"),rs.getString("username"),rs.getString("name"),rs.getString("surname"),rs.getInt("balance"));
        },user);
    }*/
}
