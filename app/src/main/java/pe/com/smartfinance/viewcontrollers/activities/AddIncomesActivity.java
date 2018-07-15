package pe.com.smartfinance.viewcontrollers.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.Category;
import pe.com.smartfinance.models.Tag;
import pe.com.smartfinance.models.authModels.SessionManager;
import pe.com.smartfinance.viewcontrollers.adapters.IncomesExpandableListAdapter;

public class AddIncomesActivity extends AppCompatActivity {

    EditText incomesAmountEditText;
    RadioGroup categoryRadioGroup;
    RadioButton salaryRadioButton;
    RadioButton loanRadioButton;
    RadioGroup tagRadioGroup;
    RadioButton ticketRadioButton;
    RadioButton receiptRadioButton;
    TextView dateTextView;
    DatePickerDialog.OnDateSetListener dateSetListener;
    Button addIncomesButton;
    String date;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_incomes);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        incomesAmountEditText = (EditText) findViewById(R.id.incomesAmountEditText);
        categoryRadioGroup = (RadioGroup) findViewById(R.id.categoryRadioGroup);
        salaryRadioButton = (RadioButton) findViewById(R.id.salaryRadioButton);
        loanRadioButton = (RadioButton) findViewById(R.id.loanRadioButton);
        tagRadioGroup = (RadioGroup) findViewById(R.id.tagRadioGroup);
        ticketRadioButton = (RadioButton) findViewById(R.id.ticketRadioButton);
        receiptRadioButton = (RadioButton) findViewById(R.id.receiptRadioButton);
        dateTextView = (TextView) findViewById(R.id.dateTextView);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddIncomesActivity.this,
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
                Log.d("AddExpensesActivity", "onDateSet: dd/mm/yyyy: " + dayOfMonth + "/" + month + "/" + year);
                String date = dayOfMonth + "-" + month + "-" + year;
                setDate(date);
                dateTextView.setText(date);
            }
        };

        addIncomesButton = (Button) findViewById(R.id.addIncomesButton);
        addIncomesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String amount = incomesAmountEditText.getText().toString();
                Category category = new Category();
                category.setAccountIdFk(2);

                if (salaryRadioButton.isChecked()){
                    category.setCategoryId(7);
                } else if(loanRadioButton.isChecked()) {
                    category.setCategoryId(8);
                }

                Tag tag = new Tag();
                if (ticketRadioButton.isChecked()){
                    tag.setTagId(7);
                } else if(receiptRadioButton.isChecked()) {
                    tag.setTagId(8);
                }

                onBackPressed();

                Toast.makeText(getApplicationContext(), "Id account: " + category.getAccountIdFk() + "\nCantidad: " + amount + "\nCategoría: " + category.getCategoryId()
                        + "\nEtiqueta: " + tag.getTagId()
                        + "\nFecha: " + getDate(), Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(AddIncomesActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(AddIncomesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }
}
