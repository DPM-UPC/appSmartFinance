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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.authModels.AccessToken;
import pe.com.smartfinance.models.authModels.SessionManager;
import pe.com.smartfinance.models.authModels.User;
import pe.com.smartfinance.network.AuthApi;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    View loginProgressView;
    View loginFormView;
    TextView errorLoginTextView;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        errorLoginTextView = findViewById(R.id.errorLogin);

        Button emailSignInButton = (Button) findViewById(R.id.emailSignInButton);

        session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()) {
            Intent businessIntent = new Intent(LoginActivity.this, BusinessActivity.class);
            startActivity(businessIntent);
            finish();
        }

        emailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        TextView registerSignUpButton = (TextView) findViewById(R.id.registerSignUpTextView);

        registerSignUpButton.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                 startActivity(registerIntent);
             }
        });


        loginFormView = findViewById(R.id.loginFormScrollView);
        loginProgressView = findViewById(R.id.loginProgressBar);
    }

    private void attemptLogin() {
        errorLoginTextView.setVisibility(View.INVISIBLE);
        emailEditText.setError(null);
        passwordEditText.setError(null);

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordEditText.setError(getString(R.string.error_invalid_password));
            focusView = passwordEditText;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError(getString(R.string.error_field_required));
            focusView = emailEditText;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailEditText.setError(getString(R.string.error_invalid_email));
            focusView = emailEditText;
            cancel = true;
        } else if(TextUtils.isEmpty(password)){
            passwordEditText.setError(getString(R.string.error_field_required));
            focusView = passwordEditText;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            //se envia datos para logear y obtener token
            try {
                getAuthToken(email, password);
            } catch (Exception e) {
                Log.d("SmartFinance", "Error durante el logeo");
                errorLoginTextView.setVisibility(View.VISIBLE);
                finish();
            }
        }
    }

    private void getAuthToken(String email, String password) throws Exception {
        Log.d("SmartFinance", "preparando para autenticar");
        User userRequest = new User();
        userRequest.setEmail(email);
        userRequest.setUserPassword(password);
        final ObjectMapper mapper = new ObjectMapper();
        AndroidNetworking.post(AuthApi.getAccessTokensUrl())
                .addJSONObjectBody(new JSONObject(mapper.writeValueAsString(userRequest)))
                .setPriority(Priority.HIGH)
                .setTag("SmartFinance")
                .setContentType("application/json; charset=utf-8")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("SmartFinance", "logeo exitoso, se obtiene token. " + String.valueOf(response));
                        AccessToken accessToken = null;
                        try {
                            accessToken = mapper.readValue(response.toString(), AccessToken.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        accessToken.setToken(accessToken.getToken());
                        accessToken.setExpiration(accessToken.getExpiration());

                        if (accessToken != null && accessToken.getToken() != null && !accessToken.getToken().isEmpty()) {
                            Log.d("SmartFinance", "logeo con exito");
                            session.createLoginSession(accessToken.getToken(), accessToken.getUserId());

                            showProgress(true);
                            Intent businessIntent = new Intent(LoginActivity.this, BusinessActivity.class);
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
                            Log.d("SmartFinance", "onError errorDetail : " + error.getErrorDetail());
                        }
                        Log.d("SmartFinance", "Error de logeo");
                        errorLoginTextView.setVisibility(View.VISIBLE);
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

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            loginProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    loginProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            loginProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}

