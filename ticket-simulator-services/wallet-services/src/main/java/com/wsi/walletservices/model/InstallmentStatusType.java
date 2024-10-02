package com.wsi.walletservices.model;

public enum InstallmentStatusType {
    PENDING(0),
    PAID(1),
    LATE(2);

    private final int value;

    InstallmentStatusType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
