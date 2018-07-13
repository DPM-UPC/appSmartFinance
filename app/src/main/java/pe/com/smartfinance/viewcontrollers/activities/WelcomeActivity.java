package pe.com.smartfinance.viewcontrollers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.authModels.SessionManager;

public class WelcomeActivity extends AppCompatActivity {

    Button startButton;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        startButton = (Button) findViewById(R.id.startButton);

        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            Intent businessIntent = new Intent(WelcomeActivity.this, BusinessActivity.class);
            startActivity(businessIntent);
            finish();
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }
}
