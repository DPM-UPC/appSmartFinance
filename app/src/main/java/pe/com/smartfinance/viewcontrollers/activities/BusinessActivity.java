package pe.com.smartfinance.viewcontrollers.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.Business;
import pe.com.smartfinance.models.BusinessAdapter;
import pe.com.smartfinance.models.BusinessGroup;

public class BusinessActivity extends AppCompatActivity {
    List<Business> business;
    RecyclerView.LayoutManager businessLayoutManager;
    RecyclerView businessRecyclerView;
    BusinessAdapter businessAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BusinessGroup businessGroup = new BusinessGroup(this);
        business = businessGroup.getBusiness();
        businessLayoutManager = new LinearLayoutManager(this);
        businessAdapter = new BusinessAdapter();
        businessAdapter.setBusiness(business);
        businessRecyclerView = (RecyclerView) findViewById(R.id.businessRecyclerView);
        businessRecyclerView.setLayoutManager(businessLayoutManager);
        businessRecyclerView.setAdapter(businessAdapter);

    }

}
