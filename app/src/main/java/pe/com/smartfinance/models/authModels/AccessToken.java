package pe.com.smartfinance.models.authModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class AccessToken {
    @JsonProperty(value = "token")
    private String token;
    @JsonProperty(value = "expiration")
    private Date expiration;
    @JsonProperty(value = "user_id")
    private Integer userId;

    public AccessToken() {
    }

    public AccessToken(String token, Date expiration, Integer userId) {
        this.token = token;
        this.expiration = expiration;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "token='" + token + '\'' +
                ", expiration=" + expiration +
                ", userId=" + userId +
                '}';
    }
}
