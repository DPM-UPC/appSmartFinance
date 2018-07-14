package pe.com.smartfinance.viewcontrollers.fragments.expenses;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.Category;
import pe.com.smartfinance.models.authModels.SessionManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesPeriodFragment extends Fragment {

    SessionManager session;

    public ExpensesPeriodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses_period, container, false);
        session = new SessionManager(getContext());
        session.checkLogin();


        return view;
    }

}
