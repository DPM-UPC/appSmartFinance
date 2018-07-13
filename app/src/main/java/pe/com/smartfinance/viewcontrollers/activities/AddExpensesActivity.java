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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import pe.com.smartfinance.R;
import pe.com.smartfinance.viewcontrollers.adapters.ExpensesExpandableListAdapter;

public class AddExpensesActivity extends AppCompatActivity {

    ExpensesExpandableListAdapter listAdapter;
    ExpandableListView expandableListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    EditText expensesAmountEditText;
    DatePickerDialog.OnDateSetListener dateSetListener;
    Button addExpensesButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);
        expensesAmountEditText = (EditText) findViewById(R.id.expensesAmountEditText);

        String amount = expensesAmountEditText.getText().toString();

        expandableListView = (ExpandableListView) findViewById(R.id.expensesExpandibleListView);
        prepareListData();
        listAdapter = new ExpensesExpandableListAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(listAdapter);
        expandableListView.expandGroup(0);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                final String selected = (String) listAdapter.getChild(groupPosition,  childPosition);

                String groupTitle = (String) expandableListView.getItemAtPosition(groupPosition);

                if (selected.equals("Fecha")){
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

                } else {
                    expandableListView.collapseGroup(groupPosition);
                    expandableListView.expandGroup(groupPosition + 1);
                }

                return false;
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("AddExpensesActivity", "onDateSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);
                String date = month + "/" + dayOfMonth + "/" + year;
            }
        };

        addExpensesButton = (Button) findViewById(R.id.addExpensesButton);
        addExpensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding header data
        listDataHeader.add("Categoria");
        listDataHeader.add("Etiquetas");
        listDataHeader.add("Fecha");

        // Adding child data
        List<String> categories = new ArrayList<String>();
        categories.add("Transporte");
        categories.add("Impuestos");
        categories.add("Salud");
        categories.add("Alimentos");
        categories.add("Hogar");
        categories.add("Otros");

        List<String> tags = new ArrayList<String>();
        tags.add("Coche");
        tags.add("Autobús");
        tags.add("Gasolina");
        tags.add("Motocicleta");
        tags.add("Mantenimiento");
        tags.add("Otros");

        List<String> dates = new ArrayList<String>();
        dates.add("Fecha");

        listDataChild.put(listDataHeader.get(0), categories); // Header, Child data
        listDataChild.put(listDataHeader.get(1), tags);
        listDataChild.put(listDataHeader.get(2), dates);
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
