package pe.com.smartfinance.viewcontrollers.fragments.expenses;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.Category;
import pe.com.smartfinance.models.OperationModels.Operation;
import pe.com.smartfinance.models.Tag;
import pe.com.smartfinance.models.authModels.SessionManager;
import pe.com.smartfinance.network.OperationApi;
import pe.com.smartfinance.utils.DateFormatter;
import pe.com.smartfinance.viewcontrollers.adapters.ExpensesPeriodAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesPeriodFragment extends Fragment {

    SessionManager session;
    //RecyclerView
    RecyclerView expensesPeriodRecyclerView;
    RecyclerView.LayoutManager expensesLayoutManager;
    ExpensesPeriodAdapter expensesPeriodAdapter;
    List<Operation>       operations;
    //RecyclerView

    TextView defaultTextView;
    TextView dateExpenseTextView;
    TextView categoryExpenseTextView;
    TextView tagExpenseTextView;
    TextView amountExpenseTextView;

    TextView dateExpenseTextView2;
    TextView categoryExpenseTextView2;
    TextView tagExpenseTextView2;
    TextView amountExpenseTextView2;

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
        Resources resources = getContext().getResources();
        operations = new ArrayList<>();
        //operations.add(resources.getString(R.string.dateExpenseTextView));

        expensesPeriodAdapter = new ExpensesPeriodAdapter(operations);
        expensesLayoutManager = new LinearLayoutManager(getContext());
        expensesPeriodRecyclerView.setAdapter(expensesPeriodAdapter);
        expensesPeriodRecyclerView.setLayoutManager(expensesLayoutManager);
        //RecyclerView

     /*   dateExpenseTextView = (TextView) view.findViewById(R.id.dateExpenseTextView);
        categoryExpenseTextView = (TextView) view.findViewById(R.id.categoryExpenseTextView);
        tagExpenseTextView = (TextView) view.findViewById(R.id.tagExpenseTextView);
        amountExpenseTextView = (TextView) view.findViewById(R.id.amountExpenseTextView);

        dateExpenseTextView2 = (TextView) view.findViewById(R.id.dateExpenseTextView2);
        categoryExpenseTextView2 = (TextView) view.findViewById(R.id.categoryExpenseTextView2);
        tagExpenseTextView2 = (TextView) view.findViewById(R.id.tagExpenseTextView2);
        amountExpenseTextView2 = (TextView) view.findViewById(R.id.amountExpenseTextView2);*/

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;

        //se carga listado de operaciones
        listOperationsFrom(1, ACCOUNT_EXPENSE, month);

        return view;
    }

    private void listOperationsFrom(Integer userBusinessId, Integer accountId, Integer period) {
        final ObjectMapper mapper = new ObjectMapper();
        AndroidNetworking.get(OperationApi.getOperationUrl())
                .addQueryParameter("user_business_id", userBusinessId.toString())
                .addQueryParameter("account_id", accountId.toString())
                .addQueryParameter("period", period.toString())
                // .addHeaders("apiKey", "1234")
                .setTag("SmartFinance")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        operations = new ArrayList<>();
                        try {
                            operations = mapper.readValue(response.toString(), new TypeReference<List<Operation>>() {});
                            Toast.makeText(getContext(), "operations: " + operations, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (operations != null){

                            for (Operation operation : operations){
                                operation.getCreationDate();
                                operation.getTag().getCategory().getDescription();
                                operation.getTag().getDescription();
                                operation.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN);
                            }

                         /*   dateExpenseTextView.setText(DateFormatter.getFormatDate(operations.get(0).getCreationDate(), "dd-M-yyyy"));
                            categoryExpenseTextView.setText(operations.get(0).getTag().getCategory().getDescription().toString());
                            tagExpenseTextView.setText(operations.get(0).getTag().getDescription().toString());
                            amountExpenseTextView.setText(operations.get(0).getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());

                            dateExpenseTextView2.setText(DateFormatter.getFormatDate(operations.get(1).getCreationDate(), "dd-M-yyyy"));
                            categoryExpenseTextView2.setText(operations.get(1).getTag().getCategory().getDescription().toString());
                            tagExpenseTextView2.setText(operations.get(1).getTag().getDescription().toString());
                            amountExpenseTextView2.setText(operations.get(1).getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());*/

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
