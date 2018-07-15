package pe.com.smartfinance.models.OperationModels;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Account {
    @JsonProperty(value = "account_id")
    private Integer accountId;
    private String description;
    @JsonProperty(value = "main_account")
    private Integer mainAccount;
    private Integer sign;
    private Integer state;
    @JsonProperty(value = "creation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Bogota")
    private Date creationDate;
    @JsonProperty(value = "update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Bogota")
    private Date updateDate;

    public Account(Integer accountId) {
        this.accountId = accountId;
    }

    public Account() {
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMainAccount() {
        return mainAccount;
    }

    public void setMainAccount(Integer mainAccount) {
        this.mainAccount = mainAccount;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
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


    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", description='" + description + '\'' +
                ", mainAccount='" + mainAccount + '\'' +
                ", sign='" + sign + '\'' +
                ", state='" + state + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", updateDate=" + updateDate +
                '}';
    }


}
