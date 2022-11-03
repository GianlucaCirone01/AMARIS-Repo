package com.amaris.transactionpaypal.service;



import com.amaris.paypalmodel.model.TransactionPojo;
import com.amaris.paypalmodel.model.TransferBalance;
import com.amaris.transactionpaypal.entity.Transaction;
import com.amaris.transactionpaypal.model.TransferMoney;
import com.amaris.transactionpaypal.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@EnableAsync
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private RestTemplate restTemplate;

    @PersistenceContext
    private EntityManager entityManager;


    public Transaction findOne(int id) {
        return transactionRepository.findById(id).orElse(null);
    }
    public String findStatusById(int id) {
        return entityManager.createQuery("select statoTransizione from Transaction t where t.id = :id").toString();

    }
    public Transaction saveTransaction(Transaction transaction){return transactionRepository.save(transaction);}

    public List<Transaction> getAllTransaction(){
        return transactionRepository.findAll();
    }


    public Optional<Transaction> getById(int id){
        return transactionRepository.findById(id);
    }

    public int getIdByUsername(String username){
        String url = "http://localhost:8080/user/getIdByUsername/";
        String url2 = url+ username;
        return  restTemplate.getForObject(url2,int.class);

    }




    @Async
    public void transferMoneyCallRestTemplate(TransferMoney transferBalance){

        int id = getIdByUsername(transferBalance.getUsernameMittente());
        int id2 = getIdByUsername(transferBalance.getUsernameDestinatario());
        Transaction transaction = new Transaction(transferBalance.getUsernameMittente(), transferBalance.getUsernameDestinatario(), transferBalance.getBalance(), Transaction.StatoTransizione.created);
        saveTransaction(transaction);
        TransferBalance transferBalance1 = new TransferBalance(id,id2, transferBalance.getBalance(),transaction.getId());

        String url = "http://localhost:8080/user/transfermoney/";
        restTemplate.postForEntity(url,transferBalance1,Void.class).getBody();



    }

    public void updateStatus(TransactionPojo transaction){
        Transaction tr = findOne(transaction.getIdTransaction());
        System.out.println("ci rivo");
        if (tr.getStatoTransizione().equals(Transaction.StatoTransizione.created)){
            Transaction tr1 = findOne(transaction.getIdTransaction());
            System.out.println("nope");
            tr.setStatoTransizione(Transaction.StatoTransizione.completed);
            Transaction attachedUser = transactionRepository.save(tr1);
        }else{
            Transaction tr2 = findOne(transaction.getIdTransaction());
            tr.setStatoTransizione(Transaction.StatoTransizione.error);
            Transaction attachedUser = transactionRepository.save(tr2);
        }

        System.out.println("non succede niente"+" "+tr.getStatoTransizione());
    }
}


