package pe.com.smartfinance.models.OperationModels;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

public class Operation {
    @JsonProperty(value = "operationId")
    private Integer operationId;
    @JsonProperty(value = "operation_date")
    private String operationDate;
    private BigDecimal amount;
    private Integer state;
    @JsonProperty(value = "creation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Bogota")
    private Date creationDate;
    @JsonProperty(value = "update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Bogota")
    private Date updateDate;
    @JsonProperty(value = "user_business_id_fk")
    private Integer userBusinessIdFk;
    @JsonProperty(value = "account_id_fk")
    private Integer accountIdFk;
    @JsonProperty(value = "category_id_fk")
    private Integer categoryIdFk;
    @JsonProperty(value = "tag_id_fk")
    private Integer tagIdFk;

    private Account account;
    private Tag tag;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Integer getTagIdFk() {
        return tagIdFk;
    }

    public void setTagIdFk(Integer tagIdFk) {
        this.tagIdFk = tagIdFk;
    }

    public Operation() {
    }

    public Operation(Integer operationId) {
        this.operationId = operationId;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public Integer getUserBusinessIdFk() {
        return userBusinessIdFk;
    }

    public void setUserBusinessIdFk(Integer userBusinessIdFk) {
        this.userBusinessIdFk = userBusinessIdFk;
    }

    public Integer getAccountIdFk() {
        return accountIdFk;
    }

    public void setAccountIdFk(Integer accountIdFk) {
        this.accountIdFk = accountIdFk;
    }

    public Integer getCategoryIdFk() {
        return categoryIdFk;
    }

    public void setCategoryIdFk(Integer categoryIdFk) {
        this.categoryIdFk = categoryIdFk;
    }


    @Override
    public String toString() {
        return "Operation{" +
                "operationId=" + operationId +
                ", operationDate='" + operationDate + '\'' +
                ", amount='" + amount + '\'' +
                ", state=" + state + '\'' +
                ", creationDate=" + creationDate + '\'' +
                ", updateDate=" + updateDate + '\'' +
                ", userBusinessIdFk=" + userBusinessIdFk + '\'' +
                ", accountIdFk=" + accountIdFk + '\'' +
                ", categoryIdFk=" + categoryIdFk + '\'' +
                ", tagIdFk=" + tagIdFk + '\'' +
                '}';
    }


}
