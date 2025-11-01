package model.dto;

import java.math.BigDecimal;

public class CategoryAmount {

    private int categoryId;
    private String name;
    private BigDecimal amount;

    public CategoryAmount(int categoryId, String name, BigDecimal amount) {
        this.categoryId = categoryId;
        this.name = name;
        this.amount = amount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
