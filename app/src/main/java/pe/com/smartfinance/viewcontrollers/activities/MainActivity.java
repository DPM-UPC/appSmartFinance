package pe.com.smartfinance.viewcontrollers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import pe.com.smartfinance.R;
import pe.com.smartfinance.viewcontrollers.fragments.expenses.ExpensesFragment;
import pe.com.smartfinance.viewcontrollers.fragments.incomes.IncomesFragment;
import pe.com.smartfinance.viewcontrollers.fragments.summary.SummaryFragment;

public class MainActivity extends AppCompatActivity {

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
        if (intent == null) return;

        if (intent.getExtras() != null) {
            setTitle(intent.getExtras().getString("name"));
        }

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

}
