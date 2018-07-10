package pe.com.smartfinance.viewcontrollers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.Business;
import pe.com.smartfinance.utilitarian.SessionManagement;
import pe.com.smartfinance.viewcontrollers.adapters.BusinessAdapter;
import pe.com.smartfinance.models.BusinessCollection;

public class BusinessActivity extends AppCompatActivity {
    List<Business> business;
    RecyclerView.LayoutManager businessLayoutManager;
    RecyclerView businessRecyclerView;
    BusinessAdapter businessAdapter;
    //SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

      //  sessionManagement = new SessionManagement(getApplicationContext());
      //  sessionManagement.checkLogin();

        BusinessCollection businessCollection = new BusinessCollection(this);
        business = businessCollection.getBusiness();
        businessLayoutManager = new GridLayoutManager(this, 2);
        businessAdapter = new BusinessAdapter();
        businessAdapter.setBusiness(business);
        businessRecyclerView = (RecyclerView) findViewById(R.id.businessRecyclerView);
        businessRecyclerView.setLayoutManager(businessLayoutManager);
        businessRecyclerView.setAdapter(businessAdapter);
    }

}
