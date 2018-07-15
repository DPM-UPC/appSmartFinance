package pe.com.smartfinance.viewcontrollers.fragments.expenses;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.OperationModels.Operation;
import pe.com.smartfinance.models.authModels.SessionManager;
import pe.com.smartfinance.network.OperationApi;
import pe.com.smartfinance.viewcontrollers.adapters.ExpensesPeriodAdapter;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesPeriodFragment extends Fragment {

    SessionManager session;
    //RecyclerView
    RecyclerView expensesPeriodRecyclerView;
    GridLayoutManager expensesGridLayoutManager;
    ExpensesPeriodAdapter expensesPeriodAdapter;
    //RecyclerView
    List<Operation> operations;

    TextView defaultTextView;

    private static final int ACCOUNT_EXPENSE = 2;

    public ExpensesPeriodFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses_period, container, false);
        session = new SessionManager(getContext());
        session.checkLogin();

        defaultTextView = (TextView) view.findViewById(R.id.defaultTextView);

        //RecyclerView
        expensesPeriodRecyclerView = (RecyclerView) view.findViewById(R.id.expensesPeriodRecyclerView);

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;

        operations = new ArrayList<>();

        expensesPeriodAdapter = new ExpensesPeriodAdapter(operations);
        expensesGridLayoutManager = new GridLayoutManager(
                view.getContext(),
                getSpanCount(getResources().getConfiguration()));
        expensesPeriodRecyclerView.setAdapter(expensesPeriodAdapter);
        expensesPeriodRecyclerView.setLayoutManager(expensesGridLayoutManager);

        //se carga listado de operaciones
        listOperationsFrom(1, ACCOUNT_EXPENSE, month);

        return view;
    }

    private int getSpanCount(Configuration configuration) {
        return configuration.orientation == ORIENTATION_PORTRAIT ? 1 : 2;
    }

    private void listOperationsFrom(Integer userBusinessId, Integer accountId, Integer period) {
        final ObjectMapper mapper = new ObjectMapper();
        AndroidNetworking.get(OperationApi.getOperationUrl())
                .addQueryParameter("user_business_id", userBusinessId.toString())
                .addQueryParameter("account_id", accountId.toString())
                .addQueryParameter("period", period.toString())
                .addHeaders("Authorization", "Bearer " + session.getUserSessionDetails().get("token"))
                .setTag("SmartFinance")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        operations = new ArrayList<>();
                        try {
                            operations = mapper.readValue(response.toString(), new TypeReference<List<Operation>>() {});
                            //Toast.makeText(getContext(), "operations: " + operations, Toast.LENGTH_SHORT).show();
                            Log.d("SmartFinance", "operations: " + operations);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (operations != null){
                            expensesPeriodAdapter.setOperations(operations);
                            expensesPeriodAdapter.notifyDataSetChanged();

                        } else {
                            defaultTextView.setVisibility(TextView.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        if (error.getErrorCode() != 0) {
                            Log.e("SmartFinance", "onError errorCode : " + error.getErrorCode());
                            Log.e("SmartFinance", "onError errorBody : " + error.getErrorBody());
                            Log.e("SmartFinance", "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            Log.e("SmartFinance", "onError errorDetail : " + error.getErrorDetail());
                        }
                        Log.e("SmartFinance", "Error en resumen financiero");
                    }
                });
    }

}
