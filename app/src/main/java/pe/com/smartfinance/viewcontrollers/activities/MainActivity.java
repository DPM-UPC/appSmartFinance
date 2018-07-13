package pe.com.smartfinance.viewcontrollers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.authModels.SessionManager;
import pe.com.smartfinance.viewcontrollers.fragments.expenses.ExpensesFragment;
import pe.com.smartfinance.viewcontrollers.fragments.incomes.IncomesFragment;
import pe.com.smartfinance.viewcontrollers.fragments.summary.SummaryFragment;

public class MainActivity extends AppCompatActivity {

    SessionManager session;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            return navigateTo(item);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        if (intent == null) return;

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigateTo(navigation.getMenu().findItem(R.id.navigation_expenses));
    }

    private Fragment getFragmentFor(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_expenses:
                return new ExpensesFragment();
            case R.id.navigation_incomes:
                return new IncomesFragment();
            case R.id.navigation_summary:
                return new SummaryFragment();
        }
        return null;
    }

    private boolean navigateTo(MenuItem item) {
        item.setChecked(true);
        return getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, getFragmentFor(item))
                .commit() > 0;

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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MainActivity.this, BusinessActivity.class);
        startActivity(intent);
        finish();

    }
}
