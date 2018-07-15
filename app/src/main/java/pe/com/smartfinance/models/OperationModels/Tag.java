package pe.com.smartfinance.models.OperationModels;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import pe.com.smartfinance.models.Category;

public class Tag {
    @JsonProperty(value = "tag_id")
    private Integer tagId;
    private String description;
    private Integer state;
    @JsonProperty(value = "creation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Bogota")
    private Date creationDate;
    @JsonProperty(value = "update_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "America/Bogota")
    private Date updateDate;
    @JsonProperty(value = "category_id_fk")
    private Integer categoryIdFk;
    private Category category;

    public Tag() {
    }

    public Tag(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
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

    public Integer getCategoryIdFk() {
        return categoryIdFk;
    }

    public void setCategoryIdFk(Integer categoryIdFk) {
        this.categoryIdFk = categoryIdFk;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + tagId +
                ", description='" + description + '\'' +
                ", state=" + state +
                ", creationDate=" + creationDate +
                ", updateDate=" + updateDate +
                ", categoryIdFk=" + categoryIdFk +
                ", category=" + category +
                '}';
    }
}
