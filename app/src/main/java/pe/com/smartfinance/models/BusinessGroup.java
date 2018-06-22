package pe.com.smartfinance.models;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import pe.com.smartfinance.R;

public class BusinessGroup {
    private List<Business> business;

    public List<Business> getBusiness(){
        return business;
    }

    public BusinessGroup(Context context){
        Resources res = context.getResources();
        business = new ArrayList<>();
        business.add(new Business(res.getString(R.string.name_business_1), R.drawable.img_albanileria_construccion));
        //Agregar los demás negocios...
    }
}
