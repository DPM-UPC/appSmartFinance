package pe.com.smartfinance.models;

public class Business {
    private String name;
    private int pictureid;

    public Business(String name, int pictureid){
        this.name = name;
        this.pictureid = pictureid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPictureid() {
        return pictureid;
    }

    public void setPictureid(int pictureid) {
        this.pictureid = pictureid;
    }
}
