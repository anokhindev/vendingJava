package com.anokhin.vending.purchase.utils;

public final class PaymentResult {
    private final boolean success;
    private final String message;

    public PaymentResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean success() {
        return success;
    }

    public String message() {
        return message;
    }
}