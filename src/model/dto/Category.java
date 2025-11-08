package model.dto;

import model.dto.Transaction.TransactionType;

public class Category {

    private int categoryId;
    private Integer appUserId;
    private String name;
    private Transaction.TransactionType transactionType;

    public Category(int categoryId, Integer appUserId, String name, TransactionType transactionType) {
        this.categoryId = categoryId;
        this.appUserId = appUserId;
        this.name = name;
        this.transactionType = transactionType;
    }

    public Category(Integer appUserId, String name, TransactionType transactionType) {
        this.appUserId = appUserId;
        this.name = name;
        this.transactionType = transactionType;
    }

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public Transaction.TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Transaction.TransactionType transactionType) {
        this.transactionType = transactionType;
    }

}
