package pe.com.smartfinance.viewcontrollers.fragments.incomes;


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
import pe.com.smartfinance.viewcontrollers.activities.AddIncomesActivity;
import pe.com.smartfinance.viewcontrollers.fragments.expenses.ExpensesCategoryFragment;
import pe.com.smartfinance.viewcontrollers.fragments.expenses.ExpensesPeriodFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomesFragment extends Fragment {


    private TabLayout tabLayout;
    public ViewPager viewPager;

    public IncomesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_incomes, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.incomesViewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.incomesTabs);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIncomesIntent = new Intent(getContext(), AddIncomesActivity.class);
                startActivity(addIncomesIntent);
            }
        });

        return view;
    }

    private void setupViewPager(ViewPager viewPager){
        IncomesFragment.ViewPagerAdapter adapter = new IncomesFragment.ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new IncomesPeriodFragment(), "POR FECHA");
        adapter.addFragment(new IncomesCategoryFragment(), "POR CATEGORIA");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> incomesFragments = new ArrayList<>();
        private final List<String> incomesFragmentsTitle = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return incomesFragments.get(position);
        }

        @Override
        public int getCount() {
            return incomesFragments.size();
        }

        public void addFragment(Fragment fragment, String title){
            incomesFragments.add(fragment);
            incomesFragmentsTitle.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return incomesFragmentsTitle.get(position);
        }
    }

}
