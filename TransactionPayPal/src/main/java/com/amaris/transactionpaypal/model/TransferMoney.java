package com.amaris.transactionpaypal.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferMoney {
    String usernameMittente;
    String usernameDestinatario;
    int balance;
}
