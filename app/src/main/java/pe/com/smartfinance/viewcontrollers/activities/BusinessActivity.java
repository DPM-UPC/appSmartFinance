package pe.com.smartfinance.viewcontrollers.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.Business;
import pe.com.smartfinance.viewcontrollers.adapters.BusinessesAdapter;

public class BusinessActivity extends AppCompatActivity {
    List<Business> business;
    RecyclerView.LayoutManager businessLayoutManager;
    RecyclerView businessRecyclerView;
    BusinessesAdapter businessesAdapter;
    //SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

      //  sessionManagement = new SessionManagement(getApplicationContext());
      //  sessionManagement.checkLogin();

        business = new ArrayList<>();
        Resources res = getApplicationContext().getResources();
        business = new ArrayList<>();
        business.add(new Business(res.getString(R.string.name_business_1), R.drawable.img_albanileria_construccion));
        business.add(new Business(res.getString(R.string.name_business_2), R.drawable.img_restaurant));
        business.add(new Business(res.getString(R.string.name_business_3), R.drawable.img_cerrajeria));
        business.add(new Business(res.getString(R.string.name_business_4), R.drawable.img_electricista));
        business.add(new Business(res.getString(R.string.name_business_5), R.drawable.img_pintura));
        business.add(new Business(res.getString(R.string.name_business_6), R.drawable.img_gasfiteria));
        businessLayoutManager = new GridLayoutManager(this, 2);
        businessesAdapter = new BusinessesAdapter(business);
        businessRecyclerView = (RecyclerView) findViewById(R.id.businessRecyclerView);
        businessRecyclerView.setLayoutManager(businessLayoutManager);
        businessRecyclerView.setAdapter(businessesAdapter);
    }

}
