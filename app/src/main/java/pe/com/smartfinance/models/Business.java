package pe.com.smartfinance.models;

import android.os.Bundle;

public class Business {
    private String name;
    private int pictureId;

    public Business(String name, int pictureId){
        this.name = name;
        this.pictureId = pictureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public Bundle toBundle(){
        Bundle bundle = new Bundle();
        bundle.putString("name", this.getName());
        bundle.putInt("pictureId", this.getPictureId());
        return bundle;
    }
}
