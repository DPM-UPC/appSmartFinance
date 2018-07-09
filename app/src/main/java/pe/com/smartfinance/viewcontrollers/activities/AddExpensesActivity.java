package pe.com.smartfinance.viewcontrollers.activities;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import pe.com.smartfinance.R;
import pe.com.smartfinance.viewcontrollers.adapters.ExpensesExpandableListAdapter;

public class AddExpensesActivity extends AppCompatActivity {

    ExpensesExpandableListAdapter listAdapter;
    ExpandableListView expandableListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    EditText expensesAmountEditText;
    DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

        expensesAmountEditText = (EditText) findViewById(R.id.expensesAmountEditText);
        expandableListView = (ExpandableListView) findViewById(R.id.expensesExpandibleListView);
        prepareListData();
        listAdapter = new ExpensesExpandableListAdapter(this, listDataHeader, listDataChild);
        expandableListView.setAdapter(listAdapter);
        expandableListView.expandGroup(0);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                final String selected = (String) listAdapter.getChild(groupPosition,  childPosition);
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

}
