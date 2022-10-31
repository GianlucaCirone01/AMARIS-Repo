package com.amaris.TransactionPayPal.web.controller;


import com.amaris.TransactionPayPal.dao.TransactionMoneyDAO;
import com.amaris.TransactionPayPal.dto.TransactionMoneyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//COMANDO PER AVVIO APPLICAZIONE TRAMITE CMD = java -jar TransactionPaypal-1.0.0.jar

@RestController
public class TransactionMoneyController {

    @Autowired
    private TransactionMoneyDAO tmDao;


    @GetMapping("/transferMoney")
    public ResponseEntity<List<TransactionMoneyDto>> getAll(){
       List<TransactionMoneyDto> result=this.tmDao.getAll();
       return ResponseEntity.ok(result);
    }

    @GetMapping("/transferMoney/{id}")
    public ResponseEntity getById(@PathVariable int id){
        TransactionMoneyDto result=this.tmDao.getById(id);
        return ResponseEntity.ok(result);
    }

    /*
    @PostMapping("/transaction")
    public void saveTransaction(@RequestBody TransactionMoneyDto tmeDto){
        tmDao.callPayPalDemoController(tmeDto);
    }
     */

    @PostMapping("/saveTransaction")
    public ResponseEntity<TransactionMoneyDto> save(TransactionMoneyDto dto){
        TransactionMoneyDto result=this.tmDao.save(dto);
        return ResponseEntity.ok(result);
    }

}
