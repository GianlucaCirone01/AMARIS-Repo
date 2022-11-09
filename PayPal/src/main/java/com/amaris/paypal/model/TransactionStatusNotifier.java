package com.amaris.paypal.model;

import com.amaris.paypalmodel.model.TransactionPojo;

public interface TransactionStatusNotifier {

    void notify(int TransactionId,String status);
    void updateStatus(TransactionPojo transaction);
}
