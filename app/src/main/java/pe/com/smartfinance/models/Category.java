package pe.com.smartfinance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Category {
    @JsonProperty(value = "category_id")
    private Integer categoryId;
    private String description;
    private Integer state;
    @JsonProperty(value = "creation_date")
    private Date creationDate;
    @JsonProperty(value = "update_date")
    private Date updateDate;
    @JsonProperty(value = "account_idd_fk")
    private Integer accountIdFk;

    public Category() {
    }

    public Category(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getAccountIdFk() {
        return accountIdFk;
    }

    public void setAccountIdFk(Integer accountIdFk) {
        this.accountIdFk = accountIdFk;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", description='" + description + '\'' +
                ", state=" + state +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", accountIdFk=" + accountIdFk +
                '}';
    }
}