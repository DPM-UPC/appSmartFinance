package pe.com.smartfinance.viewcontrollers.fragments.expenses;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.com.smartfinance.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesPeriodFragment extends Fragment {


    public ExpensesPeriodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expenses_period, container, false);
    }

}
