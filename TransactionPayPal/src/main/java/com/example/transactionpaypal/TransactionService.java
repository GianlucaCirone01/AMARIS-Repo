package com.example.transactionpaypal;

import com.example.paypal_model.Transaction;
import com.example.paypal_model.TransactionPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionService {

    @Autowired
    private TransactionJdbc_Repository jdbc_repository;

    private RestTemplate restTemplate = new RestTemplate();

    public  Void returnTransaction(String user1,String user2, Float saldo)  {

        ResponseEntity<Integer> id1_entity = this.restTemplate.getForEntity("http://localhost:8080/demo/findID/"+ user1, Integer.class);
        ResponseEntity<Integer> id2_entity = this.restTemplate.getForEntity("http://localhost:8080/demo/findID/"+ user2, Integer.class);

        Integer id1 = id1_entity.getBody();
        Integer id2 = id2_entity.getBody();


        return completeTransaction(id1, id2, saldo, user1, user2);

        //return "COMPLETE";

    }
    @Async
    public Void completeTransaction(Integer id1, Integer id2, Float saldo,String user1, String user2) {

        //TransazioneDto dto = new TransazioneDto();
        TransactionMoney dto = new TransactionMoney();
        dto.setUser1(user1);
        dto.setUser2(user2);
        dto.setSaldo(saldo);
        //dto.setStato_transazione(TransazioneDto.Stato.COMPLETE);

        dto.setStato_transazione(TransactionMoney.Stato.CREATED);

        //this.transazioneRepository.save(dto);
        Integer id = this.jdbc_repository.save(dto);

        Transaction t = new Transaction();
        t.setIdTra(id);
        t.setIdStart(id1);
        t.setIdEnd(id2);
        t.setMoney(saldo);

        TransactionMoney transazione_per_modifica_stato = this.jdbc_repository.findById(id);
        transazione_per_modifica_stato.setStato_transazione(TransactionMoney.Stato.PENDING);
        this.jdbc_repository.updateStatus(id,TransactionMoney.Stato.PENDING.name());

        ResponseEntity<String> transazione = this.restTemplate.postForEntity("http://localhost:8080/demo/transaction", t, String.class);

        //return transazione;

        return null;
    }

    public Void updateStatus(TransactionPojo transactionPojo){
        System.out.println("sono arrivato all'update");
        System.out.println(transactionPojo.getTransaction_id());
        System.out.println(transactionPojo.getStatus());

        Integer id= Integer.parseInt(transactionPojo.getTransaction_id());
        System.out.println(id);
        this.jdbc_repository.updateStatus(id, transactionPojo.getStatus());
        System.out.println("update completato");
        return null;
    }
}
