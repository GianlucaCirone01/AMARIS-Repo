package com.amaris.dto;

public class TransferDto {

    int id1;
    int id2;
    Double balanceToTransfer;

    public int getId1() {
        return id1;
    }
    public void setId1(int id1) {
        this.id1 = id1;
    }
    public int getId2() {
        return id2;
    }
    public void setId2(int id2) {
        this.id2 = id2;
    }
    public Double getBalanceToTransfer() {
        return balanceToTransfer;
    }
    public void setBalanceToTransfer(Double balanceToTransfer) {
        this.balanceToTransfer = balanceToTransfer;
    }
}
