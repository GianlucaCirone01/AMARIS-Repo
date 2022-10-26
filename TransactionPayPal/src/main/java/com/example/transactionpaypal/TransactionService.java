package com.example.transactionpaypal;

import com.example.gestionebalance.UserService;
import com.example.paypal_model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransazioneRepository transazioneRepository;

    @Autowired
    private UserService userService;


    public String returnTransaction(ResponseEntity<UserUserSaldo> response) throws NoSuchFieldException {

        UserUserSaldo userPojo = response.getBody();

        Integer id1 = Integer.valueOf(String.valueOf(userService.getbyUsername(userPojo.getUser1())));
        Integer id2 = Integer.valueOf(String.valueOf(userService.getbyUsername(userPojo.getUser2())));

        return completeTransaction(id1,id2,response);
    }
    @Async
    public String completeTransaction(Integer id1, Integer id2, ResponseEntity<UserUserSaldo> response) throws NoSuchFieldException {

        Transaction t = new Transaction();
        t.setIdStart(id1);
        t.setIdEnd(id2);
        t.setMoney(response.getBody().getSaldo());

        ResponseEntity<String> transazione = userService.moveMoney(t);

        TransazioneDto dto = new TransazioneDto();
        dto.setUser1(response.getBody().getUser1());
        dto.setUser2(response.getBody().getUser2());
        dto.setSaldo(response.getBody().getSaldo());

        this.transazioneRepository.save(dto);

        return String.valueOf(transazione);
    }

}
