package pe.com.smartfinance.viewcontrollers.fragments.expenses;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.OperationModels.Operation;
import pe.com.smartfinance.models.authModels.SessionManager;
import pe.com.smartfinance.network.OperationApi;
import pe.com.smartfinance.utils.DateFormatter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesCategoryFragment extends Fragment {

    SessionManager session;
    List<Operation> operations;

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


    public ExpensesCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenses_category, container, false);
        session = new SessionManager(getContext());
        session.checkLogin();

        defaultTextView = (TextView) view.findViewById(R.id.defaultTextView);

        categoryExpenseTextView = (TextView) view.findViewById(R.id.categoryExpenseTextView);
        tagExpenseTextView = (TextView) view.findViewById(R.id.tagExpenseTextView);
        amountExpenseTextView = (TextView) view.findViewById(R.id.amountExpenseTextView);

        categoryExpenseTextView2 = (TextView) view.findViewById(R.id.categoryExpenseTextView2);
        tagExpenseTextView2 = (TextView) view.findViewById(R.id.tagExpenseTextView2);
        amountExpenseTextView2 = (TextView) view.findViewById(R.id.amountExpenseTextView2);

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
                // .addHeaders("token", "1234")
                .setTag("SmartFinance")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        operations = new ArrayList<>();
                        try {
                            operations = mapper.readValue(response.toString(), new TypeReference<List<Operation>>() {
                            });
                            Toast.makeText(getContext(), "operations: " + operations, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (operations != null){

                            for (Operation operation : operations){
                                operation.getCategoryIdFk();
                                operation.getTagIdFk();
                                operation.getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN);
                            }

                            categoryExpenseTextView.setText(operations.get(0).getCategoryIdFk().toString());
                            tagExpenseTextView.setText(operations.get(0).getTagIdFk().toString());
                            amountExpenseTextView.setText(operations.get(0).getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());

                            categoryExpenseTextView2.setText(operations.get(1).getCategoryIdFk().toString());
                            tagExpenseTextView2.setText(operations.get(1).getTagIdFk().toString());
                            amountExpenseTextView2.setText(operations.get(1).getAmount().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());

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
