package pe.com.smartfinance.models.authModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class UserBusiness {
    @JsonProperty(value = "user_business_id")
    private Integer userBusinessId;
    @JsonProperty(value = "state")
    private Integer state;
    @JsonProperty(value = "creation_date")
    private Date creationDate;
    @JsonProperty(value = "update_date")
    private Date updateDate;
    @JsonProperty(value = "user_id_fk")
    private Integer userIdFk;
    @JsonProperty(value = "business_id_fk")
    private Integer businessIdFk;
    @JsonProperty(value = "fk_users_business_users")
    private Integer fkUsersBusinessUsers;

    public UserBusiness() {
    }

    public UserBusiness(Integer userBusinessId) {
        this.userBusinessId = userBusinessId;
    }

    public Integer getUserBusinessId() {
        return userBusinessId;
    }

    public void setUserBusinessId(Integer userBusinessId) {
        this.userBusinessId = userBusinessId;
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

    public Integer getUserIdFk() {
        return userIdFk;
    }

    public void setUserIdFk(Integer userIdFk) {
        this.userIdFk = userIdFk;
    }

    public Integer getBusinessIdFk() {
        return businessIdFk;
    }

    public void setBusinessIdFk(Integer businessIdFk) {
        this.businessIdFk = businessIdFk;
    }

    public Integer getFkUsersBusinessUsers() {
        return fkUsersBusinessUsers;
    }

    public void setFkUsersBusinessUsers(Integer fkUsersBusinessUsers) {
        this.fkUsersBusinessUsers = fkUsersBusinessUsers;
    }

    @Override
    public String toString() {
        return "UserBusiness{" +
                "userBusinessId=" + userBusinessId +
                ", state=" + state +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", userIdFk=" + userIdFk +
                ", businessIdFk=" + businessIdFk +
                ", fkUsersBusinessUsers=" + fkUsersBusinessUsers +
                '}';
    }
}
