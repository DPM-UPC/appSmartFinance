package pe.com.smartfinance.models.authModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Paolo Ortega on 23/06/2018.
 */
public class User {
    @JsonProperty(value = "user_id")
    private Integer userId;
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "state")
    private String state;
    @JsonProperty(value = "creation_date")
    private Date creationDate;
    @JsonProperty(value = "update_date")
    private Date updateDate;
    @JsonProperty(value = "country")
    private Country country;
    @JsonProperty(value = "access_securities")
    private List<AccessSecurity> accessSecurities;
    @JsonProperty(value = "user_businesses")
    private List<UserBusiness> userBusinesses;

    @JsonProperty(value = "user_password")
    private String userPassword;

    @JsonProperty(value = "token")
    private String token;

    public User() {
    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<AccessSecurity> getAccessSecurities() {
        if (accessSecurities == null) accessSecurities = new ArrayList<>();
        return accessSecurities;
    }

    public void setAccessSecurities(List<AccessSecurity> accessSecurities) {
        this.accessSecurities = accessSecurities;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public List<UserBusiness> getUserBusinesses() {
        return userBusinesses;
    }

    public void setUserBusinesses(List<UserBusiness> userBusinesses) {
        this.userBusinesses = userBusinesses;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
