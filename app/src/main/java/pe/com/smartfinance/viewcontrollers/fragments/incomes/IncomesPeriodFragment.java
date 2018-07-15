package pe.com.smartfinance.viewcontrollers.fragments.incomes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class IncomesPeriodFragment extends Fragment {

    SessionManager session;
    List<Operation> operations;

    private static final int ACCOUNT_INCOME = 1;


    public IncomesPeriodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_incomes_period, container, false);
        session = new SessionManager(getContext());
        session.checkLogin();

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;

        //se carga listado de operaciones
        listOperationsFrom(1, ACCOUNT_INCOME, month);

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
