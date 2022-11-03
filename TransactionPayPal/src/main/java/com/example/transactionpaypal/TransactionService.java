package com.example.transactionpaypal;

import com.example.paypal_model.Transaction;
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

    public  ResponseEntity<String> returnTransaction(String user1,String user2, Float saldo)  {

        ResponseEntity<Integer> id1_entity = this.restTemplate.getForEntity("http://localhost:8080/demo/findID/"+ user1, Integer.class);
        ResponseEntity<Integer> id2_entity = this.restTemplate.getForEntity("http://localhost:8080/demo/findID/"+ user2, Integer.class);

        Integer id1 = id1_entity.getBody();
        Integer id2 = id2_entity.getBody();

        return completeTransaction(id1, id2, saldo, user1, user2);

        //return "COMPLETE";

    }
    @Async
    public ResponseEntity<String> completeTransaction(Integer id1, Integer id2, Float saldo,String user1, String user2) {

        Transaction t = new Transaction();
        t.setIdStart(id1);
        t.setIdEnd(id2);
        t.setMoney(saldo);


        ResponseEntity<String> transazione = this.restTemplate.postForEntity("http://localhost:8080/demo/transaction", t, String.class);

        //TransazioneDto dto = new TransazioneDto();
        TransactionMoney dto = new TransactionMoney();
        dto.setUser1(user1);
        dto.setUser2(user2);
        dto.setSaldo(saldo);
        //dto.setStato_transazione(TransazioneDto.Stato.COMPLETE);

        if(transazione.getBody().compareTo("Credito insufficiente")==0) {
            dto.setStato_transazione(TransactionMoney.Stato.PENDING);
        }
        else {dto.setStato_transazione(TransactionMoney.Stato.COMPLETE);
        }

        //this.transazioneRepository.save(dto);
        this.jdbc_repository.save(dto);

        return transazione;
    }

}
