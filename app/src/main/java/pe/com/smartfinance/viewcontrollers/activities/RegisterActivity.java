package pe.com.smartfinance.viewcontrollers.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.authModels.AccessSecurity;
import pe.com.smartfinance.models.authModels.Country;
import pe.com.smartfinance.models.authModels.SessionManager;
import pe.com.smartfinance.models.authModels.User;
import pe.com.smartfinance.models.authModels.UserBusiness;
import pe.com.smartfinance.network.AuthApi;

public class RegisterActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    Button registerButton;
    View progressView;
    View registerFormView;
    Spinner businessSpinner;
    CheckBox registerCheckBox;
	SessionManager session;
	Integer businessId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        businessSpinner = (Spinner) findViewById(R.id.businessSpinner);
        registerCheckBox = (CheckBox) findViewById(R.id.registerCheckBox);
        registerButton = (Button) findViewById(R.id.registerButton);

        session = new SessionManager(getApplicationContext());

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.business, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        businessSpinner.setAdapter(adapter);
        businessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                businessId = (int) adapter.getItemId(position + 1);
                setBusinessId(businessId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });

        registerFormView = findViewById(R.id.registerFormScrollView);
        progressView = findViewById(R.id.registerProgressBar);
    }


    public void setBusinessId(Integer businessId){
        this.businessId = businessId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    private void attemptRegister(){
        nameEditText.setError(null);
        emailEditText.setError(null);
        passwordEditText.setError(null);

        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            nameEditText.setError(getString(R.string.error_field_required));
            focusView = nameEditText;
            cancel = true;
        } else if (TextUtils.isEmpty(email)) {
            emailEditText.setError(getString(R.string.error_field_required));
            focusView = emailEditText;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailEditText.setError(getString(R.string.error_invalid_email));
            focusView = emailEditText;
            cancel = true;
        } else if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordEditText.setError(getString(R.string.error_invalid_password));
            focusView = passwordEditText;
            cancel = true;
        } else if(TextUtils.isEmpty(password)){
            passwordEditText.setError(getString(R.string.error_field_required));
            focusView = passwordEditText;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            try {
                if(!registerCheckBox.isChecked()){
                    // TODO: cambiar el 1 por el valor elegido en el combo
                    registerUser(email, password, getBusinessId());
                    registerCheckBox.setError(getString(R.string.error_field_required));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void registerUser(String email, String password, Integer businessId) throws Exception {
        Log.d("SmartFinance", "preparando para registrar");
        User userRequest = new User();
        userRequest.setEmail(email);
        userRequest.setUserPassword(password);
        List<AccessSecurity> accessSecurities = new ArrayList<>();
        accessSecurities.add(new AccessSecurity(password));
        List<UserBusiness> userBusinesses = new ArrayList<>();
        userBusinesses.add(new UserBusiness(businessId));
        userRequest.setUserBusinesses(userBusinesses);
        userRequest.setAccessSecurities(accessSecurities);
        userRequest.setCountry(new Country(6));

        final ObjectMapper mapper = new ObjectMapper();
        AndroidNetworking.post(AuthApi.getUserUrl())
                .addJSONObjectBody(new JSONObject(mapper.writeValueAsString(userRequest)))
                .setPriority(Priority.HIGH)
                .setTag("SmartFinance")
                .setContentType("application/json; charset=utf-8")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("SmartFinance", "registro exitoso, se obtiene datos de usuario. " + String.valueOf(response));
                        User userReponse = null;
                        try {
                            userReponse = mapper.readValue(response.toString(), User.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (userReponse != null && userReponse.getUserId() != null && userReponse.getToken() != null && !userReponse.getToken().isEmpty()) {
                            Log.d("SmartFinance", "registro con exito");
                            session.createLoginSession(userReponse.getToken(), userReponse.getUserId());

                            showProgress(true);
                            Intent businessIntent = new Intent(RegisterActivity.this, BusinessActivity.class);
                            startActivity(businessIntent);
                            finish();
                        }
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

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            registerFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
