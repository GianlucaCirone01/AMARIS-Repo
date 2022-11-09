package com.amaris.paypal.dao;


import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.amaris.paypal.error.ApplicationException;
import com.amaris.paypal.error.CustomError;
import com.amaris.paypal.model.TransactionStatusNotifier;
import com.amaris.paypalmodel.model.TransactionPojo;
import com.amaris.paypalmodel.model.TransferBalance;
import com.amaris.paypal.model.User;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@EnableAsync
public class UserService implements  UserRepository, TransactionStatusNotifier {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Value("${updateStatus.url}")
    private String updateStatusUrl;

    @Value("${findStatusById.url}")
    private String findStatusByIdUrl;


    Logger logger = (Logger) LoggerFactory.getLogger(UserService.class);



    RestTemplate restTemplate = new RestTemplate();
    private static final String INSERT_USER_QUERY="INSERT INTO user(id,username,name,surname,balance) values(?,?,?,?,?)";
    private static final String UPDATE_USER_BY_ID_QUERY="UPDATE user SET username=? where id=?";
    private static final String GET_USER_BY_ID_QUERY="SELECT * FROM user WHERE ID = ?";
    private static final String DELETE_USER_BY_ID_QUERY="DELETE FROM user WHERE ID= ?";
    private static  final String GET_USERS_QUERY="SELECT * FROM user";
    private static final String CHARGE_MONEY_QUERY = "update user set balance=balance + ? where username=?";
    private static final String TRANSFER_MONEY_QUERY ="update user set balance=balance - ? where username=?";
    private static final String GET_ID_BY_USERNAME = "SELECT id FROM user WHERE username = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //metodo che va a salvare nel db l'utente
    @Override
    public User saveUser(User user) {

        if (!(user.getUsername().equals(getById(user.getId()).getUsername()))){
            jdbcTemplate.update(INSERT_USER_QUERY,user.getId(),user.getUsername(),user.getName(),
                    user.getSurname(),0);
            return user;
        }else{
            throw new ApplicationException( new CustomError(500, "Username gia in uso prova con un altro",
                    "Username gia in uso"));
        }



    }
    //metodo che aggiorna l'user
    @Override
    public User updateUser(User user) {
        jdbcTemplate.update(UPDATE_USER_BY_ID_QUERY,user.getUsername(),user.getId());
        return user;
    }
    //metodo che recupera l'utente tramite id
    @Override
    public User getById(int id) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID_QUERY,(rs,rowNum)->{

            return new User(rs.getInt("id"),rs.getString("username"),rs.getString("name")
                    ,rs.getString("surname"),rs.getInt("balance"));
        },id);
    }


    //Metodo che va eliminare l'utente tramite id
    @Override
    public String deleteById(int id) {
        jdbcTemplate.update(DELETE_USER_BY_ID_QUERY,id);
        return "User got Deleted with id:"+id;
    }
    //metodo che recupera tutti gli utenti
    @Override
    public List<User> allUsers() {
        return jdbcTemplate.query(GET_USERS_QUERY,(rs,rowNum)->{

            return new User(rs.getInt("id"),rs.getString("username"),
                    rs.getString("name"),rs.getString("surname"),
                    rs.getInt("balance"));
        });
    }


    //Metodo che aggiunge soldi al balance dell'user
    @Override
    public User chargeMoney(User user) {
        if (user.getBalance() >= 1){
            jdbcTemplate.update(CHARGE_MONEY_QUERY,user.getBalance(),user.getUsername());
            return user;
        }else{
            throw new ApplicationException( new CustomError(500, "Non puoi inserire un saldo negativo",
                    "Hai inserito un saldo negativo"));
        }

    }

    //Metodo che va a recuperare l'id dell utente tramite l'username
    @Override
    public int getIdByUsername(String username) throws NullPointerException {
        return jdbcTemplate.queryForObject(GET_ID_BY_USERNAME,(rs,rowNum)->{
            return (rs.getInt("id"));
        },username);


    }
    //Metodo restemplate che recupera lo status della transazione
    @Override
    public String getTransactionStatus(int idTransaction) {
        String url = findStatusByIdUrl+ idTransaction;
        return restTemplate.getForObject(url,String.class);
    }

    //Metodo restemplate che aggiorna lo status della transazione
    public void updateStatus(TransactionPojo transaction) {
        restTemplate.postForEntity(updateStatusUrl, transaction, Void.class);
    }

    //Metodo che trasferice i soldi da un utente all'altro
    @Transactional
    @Override
    public void transferMoney(TransferBalance transferBalance) {

        User id1 = getById(transferBalance.getId());
        User id2 = getById(transferBalance.getId2());

        //Controllo Sul saldo, se è positivo allora salva sul db lo status su completed
        if (id1.getBalance() >= transferBalance.getBalance()) {

            jdbcTemplate.update(TRANSFER_MONEY_QUERY,transferBalance.getBalance(),id1.getUsername());
            jdbcTemplate.update(CHARGE_MONEY_QUERY,transferBalance.getBalance(),id2.getUsername());
            //TODO 3 linee sotto spostare in un altra classe
            String transactionId = getTransactionStatus(transferBalance.getIdTransaction());
            TransactionPojo transactionPojo1 = new TransactionPojo(transferBalance.getIdTransaction(),
                    "created");
            updateStatus(transactionPojo1);
        }else{
            TransactionPojo transactionPojo1 = new TransactionPojo(transferBalance.getIdTransaction(),
                    "error");
            updateStatus(transactionPojo1);
            throw new ApplicationException( new CustomError(500
                    , "Saldo negativo non puoi trasferire soldi",
                    "Hai inserito un saldo negativo"));
        }

        //Informa dello stato della transazione e dell'id
        TransactionPojo transactionPojo1 = new TransactionPojo(transferBalance.getIdTransaction()
                ,"created");
        notify(transferBalance.getIdTransaction(),transactionPojo1.getStatoTransazione());
    }

    //Informa dello stato della transazione e dell'id
    @Override
    public void notify(int transactionId, String status) {
        System.out.println("TransactionId: "+transactionId+" "+" Status: "+status);

    }




}
