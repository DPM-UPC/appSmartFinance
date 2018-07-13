package pe.com.smartfinance.models.authModels;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Paolo Ortega on 23/06/2018.
 */
public class Country {
    @JsonProperty(value = "country_id")
    private Integer countryId;
    @JsonProperty(value = "country_name")
    private String countryName;
    @JsonProperty(value = "state")
    private Integer state;

    public Country() {
    }

    public Country(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Country{" +
                "countryId=" + countryId +
                ", countryName='" + countryName + '\'' +
                ", state=" + state +
                '}';
    }
}
