package com.amaris.paypalmodel.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferBalance {
    int id;
    int id2;
    int balance;
    int idTransaction;


    public int getIdTransaction() {
        return idTransaction;
    }



}
