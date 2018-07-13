package pe.com.smartfinance.viewcontrollers.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.Business;
import pe.com.smartfinance.models.authModels.SessionManager;
import pe.com.smartfinance.viewcontrollers.adapters.BusinessesAdapter;

public class BusinessActivity extends AppCompatActivity {
    List<Business> business;
    RecyclerView.LayoutManager businessLayoutManager;
    RecyclerView businessRecyclerView;
    BusinessesAdapter businessesAdapter;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            session.logoutUser();
        }

        return super.onOptionsItemSelected(item);
    }
}
