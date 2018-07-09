package pe.com.smartfinance.models;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import pe.com.smartfinance.R;

public class BusinessCollection {
    private List<Business> business;

    public List<Business> getBusiness(){
        return business;
    }

    public BusinessCollection(Context context){
        Resources res = context.getResources();
        business = new ArrayList<>();
        business.add(new Business(res.getString(R.string.name_business_1), R.drawable.img_albanileria_construccion));
        business.add(new Business(res.getString(R.string.name_business_2), R.drawable.img_restaurant));
        business.add(new Business(res.getString(R.string.name_business_3), R.drawable.img_cerrajeria));
        business.add(new Business(res.getString(R.string.name_business_4), R.drawable.img_electricista));
        business.add(new Business(res.getString(R.string.name_business_5), R.drawable.img_pintura));
        business.add(new Business(res.getString(R.string.name_business_6), R.drawable.img_gasfiteria));
    }
}
