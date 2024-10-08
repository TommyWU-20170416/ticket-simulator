package com.wsi.walletservices.model;

public enum TransactionType {
    DEPOSIT(1),
    WITHDRAW(2),
    TRANSFER(3),
    INSTALLMENT(4);

    private final int value;

    TransactionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
