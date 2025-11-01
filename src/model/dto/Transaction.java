package model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    public enum TransactionType {
        expense, income
    }

    private int appUserId;
    private int transactionId;
    private int categoryId;
    private int paymentMethodId;
    private BigDecimal amount;
    private TransactionType transactionType;
    private LocalDateTime createdAt;

    public Transaction(int transactionId, int categoryId, int appUserId, int paymentMethodId, BigDecimal amount,
            TransactionType transactionType, LocalDateTime createdAt) {
        this.transactionId = transactionId;
        this.categoryId = categoryId;
        this.appUserId = appUserId;
        this.paymentMethodId = paymentMethodId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public Transaction(int categoryId, int appUserId, int paymentMethodId, BigDecimal amount,
            TransactionType transactionType, LocalDateTime createdAt) {
        this.categoryId = categoryId;
        this.appUserId = appUserId;
        this.paymentMethodId = paymentMethodId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public Transaction(BigDecimal amount, TransactionType transactionType, LocalDateTime createdAt) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public Transaction() {
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getAppuserId() {
        return appUserId;
    }

    public void setAppuserId(int appuserId) {
        this.appUserId = appuserId;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
