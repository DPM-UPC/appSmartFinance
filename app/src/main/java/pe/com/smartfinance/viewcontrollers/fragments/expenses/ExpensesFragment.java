package pe.com.smartfinance.viewcontrollers.fragments.expenses;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pe.com.smartfinance.R;
import pe.com.smartfinance.viewcontrollers.activities.AddExpensesActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesFragment extends Fragment {

    private TabLayout tabLayout;
    public ViewPager viewPager;

    public ExpensesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.expensesViewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.expensesTabs);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addExpensesIntent = new Intent(getContext(), AddExpensesActivity.class);
                startActivity(addExpensesIntent);
            }
        });

        return view;
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ExpensesPeriodFragment(), "POR FECHA");
        adapter.addFragment(new ExpensesCategoryFragment(), "POR CATEGORIA");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> expensesFragments = new ArrayList<>();
        private final List<String> expensesFragmentsTitle = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return expensesFragments.get(position);
        }

        @Override
        public int getCount() {
            return expensesFragments.size();
        }

        public void addFragment(Fragment fragment, String title){
            expensesFragments.add(fragment);
            expensesFragmentsTitle.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return expensesFragmentsTitle.get(position);
        }
    }
}
