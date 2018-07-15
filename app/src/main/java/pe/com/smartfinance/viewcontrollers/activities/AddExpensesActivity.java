package pe.com.smartfinance.viewcontrollers.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.Category;
import pe.com.smartfinance.models.OperationModels.Operation;
import pe.com.smartfinance.models.Tag;
import pe.com.smartfinance.models.authModels.SessionManager;
import pe.com.smartfinance.network.OperationApi;

public class AddExpensesActivity extends AppCompatActivity {

    EditText expensesAmountEditText;
    RadioGroup categoryRadioGroup;
    RadioButton transportRadioButton;
    RadioButton taxRadioButton;
    RadioGroup tagRadioGroup;
    RadioButton carRadioButton;
    RadioButton busRadioButton;
    TextView dateTextView;
    DatePickerDialog.OnDateSetListener dateSetListener;
    Button addExpensesButton;
    String date;
    SessionManager session;

    private static final int ACCOUNT_EXPENSE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        expensesAmountEditText = (EditText) findViewById(R.id.expensesAmountEditText);
        categoryRadioGroup = (RadioGroup) findViewById(R.id.categoryRadioGroup);
        transportRadioButton = (RadioButton) findViewById(R.id.transportRadioButton);
        taxRadioButton = (RadioButton) findViewById(R.id.taxRadioButton);
        tagRadioGroup = (RadioGroup) findViewById(R.id.tagRadioGroup);
        carRadioButton = (RadioButton) findViewById(R.id.carRadioButton);
        busRadioButton = (RadioButton) findViewById(R.id.busRadioButton);
        dateTextView = (TextView) findViewById(R.id.dateTextView);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddExpensesActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("AddExpensesActivity", "onDateSet: dd/mm/yyyy: " + dayOfMonth + "/" + ((month + 1) < 10 ? "0" + month : "" + month) + "/" + year);
                String date = (dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth) + "-" + (++month < 10 ? "0" + month : "" + month) + "-" + year;
                setDate(date);
                dateTextView.setText(date);
            }
        };

        addExpensesButton = (Button) findViewById(R.id.addExpensesButton);
        addExpensesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String amount = expensesAmountEditText.getText().toString();
                Category category = new Category();
                category.setAccountIdFk(ACCOUNT_EXPENSE);

                if (transportRadioButton.isChecked()){
                    category.setCategoryId(1);
                } else if(taxRadioButton.isChecked()) {
                    category.setCategoryId(2);
                }

                Tag tag = new Tag();
                if (carRadioButton.isChecked()){
                    tag.setTagId(1);
                } else if(busRadioButton.isChecked()) {
                    tag.setTagId(2);
                }

                //se grabar el gasto
                try {
                    boolean cancel = false;
                    if (TextUtils.isEmpty(expensesAmountEditText.getText())) {
                        cancel = true;
                    } else if (category.getCategoryId() == null) {
                        cancel = true;
                    } else if (tag.getTagId() == null) {
                        cancel = true;
                    } else if (TextUtils.isEmpty(dateTextView.getText())) {
                        cancel = true;
                    }

                    if (!cancel) {
                        registerOperation(category.getAccountIdFk(), new BigDecimal(amount), 1, category.getCategoryId(), tag.getTagId(), getDate());

                        onBackPressed();
                    } else {
                        Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void registerOperation(Integer accountId, BigDecimal amount, Integer businessId, Integer categoryId, Integer tagId, String operationDate) throws Exception {
        Log.d("SmartFinance", "preparando para registrar");

        Operation operationReq = new Operation();
        operationReq.setAccountIdFk(accountId);
        operationReq.setAmount(amount);
        operationReq.setUserBusinessIdFk(businessId);
        operationReq.setCategoryIdFk(categoryId);
        operationReq.setTagIdFk(tagId);
        operationReq.setOperationDate(operationDate);

        final ObjectMapper mapper = new ObjectMapper();
        AndroidNetworking.post(OperationApi.getOperationUrl())
                .addJSONObjectBody(new JSONObject(mapper.writeValueAsString(operationReq)))
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + session.getUserSessionDetails().get("token"))
                .setTag("SmartFinance")
                .setContentType("application/json; charset=utf-8")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("SmartFinance", "registro exitoso, se obtiene datos de la operacion. " + String.valueOf(response));
                        Operation operationReponse = null;
                        try {
                            operationReponse = mapper.readValue(response.toString(), Operation.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), "Operación de gasto por el monto de \"" + operationReponse.getAmount() + " nuevos soles\", registrado con éxito."
                                , Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(ANError error) {
                        if (error.getErrorCode() != 0) {
                            Log.d("SmartFinance", "onError errorCode : " + error.getErrorCode());
                            Log.d("SmartFinance", "onError errorBody : " + error.getErrorBody());
                            Log.d("SmartFinance", "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.e("SmartFinance", "onError errorDetail : " + error.getErrorDetail());
                        }
                        Log.e("SmartFinance", "Error de registro, errorCode: " + error.getErrorCode());
                    }
                });
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddExpensesActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(AddExpensesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }
}
