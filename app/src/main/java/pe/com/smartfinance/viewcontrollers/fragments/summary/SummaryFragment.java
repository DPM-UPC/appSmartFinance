package pe.com.smartfinance.viewcontrollers.fragments.summary;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pe.com.smartfinance.R;
import pe.com.smartfinance.models.OperationSummary;
import pe.com.smartfinance.models.authModels.SessionManager;
import pe.com.smartfinance.network.ReportApi;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryFragment extends Fragment {
    SessionManager session;
    Spinner monthSpinner;
    TextView totalExpensesTextView;
    TextView totalIncomesTextView;
    TextView utilityTextViewTextView;
    String totalIngresos;
    String totalGastos;
    List<String> totalUtilities;

    BarChart barChart;

    String[] summaryMonths = new String[]{"mayo", "junio", "julio"};
    int[] sale = new int[]{25, 35, 40};
    int[] colors = new int[]{Color.BLACK, Color.RED, Color.BLUE};

    public SummaryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        session = new SessionManager(getContext());
        session.checkLogin();

        monthSpinner = (Spinner) view.findViewById(R.id.monthSpinner);
        totalExpensesTextView = (TextView) view.findViewById(R.id.totalExpensesTextView);
        totalIncomesTextView = (TextView) view.findViewById(R.id.totalIncomesTextView);
        utilityTextViewTextView = view.findViewById(R.id.utilityTextView);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                        R.array.months, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(adapter);
        monthSpinner.setSelection(setCurrentMonthSpinner());
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String positionMonth = String.valueOf(monthSpinner.getSelectedItemPosition() + 1);
                String userBusinessId = "1";
                getReportMetrics(positionMonth, userBusinessId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        barChart = (BarChart) view.findViewById(R.id.barChart);
        return view;
    }

    private void getReportMetrics(final String position, String userBusinessId) {
        final ObjectMapper mapper = new ObjectMapper();
        AndroidNetworking.get(ReportApi.getReportUrl())
                .addQueryParameter("user_business_id_fk", userBusinessId)
                .addQueryParameter("num_month", position)
               // .addHeaders("token", "1234")
                .setTag("SmartFinance")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<OperationSummary> operationSummaries = new ArrayList<>();
                        try {
                            operationSummaries = mapper.readValue(response.toString(), new TypeReference<List<OperationSummary>>(){});
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (operationSummaries != null){
                            totalUtilities = new ArrayList<>();

                            int i = 0;
                            for (OperationSummary operationSummary : operationSummaries){
                                summaryMonths[i++] = operationSummary.getMonthDescription();
                                totalUtilities.add(operationSummary.getTotalIncome().add(operationSummary.getTotalExpense())
                                        .setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
                            }

                            //Toast.makeText(getContext(), "Meses: " + Arrays.toString(summaryMonths), Toast.LENGTH_SHORT).show();

                            totalIngresos = operationSummaries.get(2).getTotalIncome().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();
                            totalGastos = operationSummaries.get(2).getTotalExpense().setScale(2, BigDecimal.ROUND_HALF_EVEN).toString();

                            totalIncomesTextView.setText(new BigDecimal(totalIngresos).setScale(2, BigDecimal.ROUND_HALF_EVEN).toString());
                            totalExpensesTextView.setText(totalGastos);
                            utilityTextViewTextView.setText(totalUtilities.get(2));

                            createChart();
                        }

                    }
                    @Override
                    public void onError(ANError error) {
                        if (error.getErrorCode() != 0) {
                            Log.d("SmartFinance", "onError errorCode : " + error.getErrorCode());
                            Log.d("SmartFinance", "onError errorBody : " + error.getErrorBody());
                            Log.d("SmartFinance", "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            Log.d("SmartFinance", "onError errorDetail : " + error.getErrorDetail());
                        }
                        Log.d("SmartFinance", "Error en resumen financiero");
                    }
                });
    }


    public int setCurrentMonthSpinner() {
        String[] months = getResources().getStringArray(R.array.months);
        for (int i = 0; i < months.length; i++){
            String month = getCurrentMonth();

            switch (month){
                case "January":
                    month = "enero";
                    break;
                case "February":
                    month = "febrero";
                    break;
                case "March":
                    month = "marzo";
                    break;
                case "April":
                    month = "abril";
                    break;
                case "May":
                    month = "mayo";
                    break;
                case "June":
                    month = "junio";
                    break;
                case "July":
                    month = "julio";
                    break;
                case "August":
                    month = "agosto";
                    break;
                case "September":
                    month = "septiembre";
                    break;
                case "October":
                    month = "octubre";
                    break;
                case "November":
                    month = "noviembre";
                    break;
                case "December":
                    month = "diciembre";
                    break;
            }

            if (months[i].equals(month)){
                return i;
            }
        }
        return 0;
    }

    public String getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat month = new SimpleDateFormat("MMMM");
        return month.format(calendar.getTime());
    }

    //data set de utilidades y su ubicacion
    private ArrayList<BarEntry> getBarEntries() {
        Log.d("SmartFinance", "total 1: " + totalUtilities.get(0));
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(Float.parseFloat(totalUtilities.get(0)), 0));
        entries.add(new BarEntry(Float.parseFloat(totalUtilities.get(1)), 1));
        entries.add(new BarEntry(Float.parseFloat(totalUtilities.get(2)), 2));
        /*for (int i = 0; i < sale.length; i++){
            entries.add(new BarEntry(i, sale[i]));
       }*/
        return entries;
    }

    private void axisX(XAxis axis){
        //axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //axis.setValueFormatter(new IndexAxisValueFormatter(summaryMonths));
    }

    private void axisLeft(YAxis axis){
        axis.setSpaceTop(30);
        //axis.setAxisMinimum(0);
    }

    private void axisRight(YAxis axis){
        axis.setEnabled(false);
    }

    public void createChart(){
        //barChart = (BarChart) getSameChart(barChart, "Series", Color.RED, Color.CYAN, 3000);
        barChart.setDrawGridBackground(true);
        barChart.setDrawBarShadow(true);
        barChart.setData(getBarData());
        barChart.setDescription("Utilidades");
        barChart.animateY(3000);
        barChart.invalidate();

        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());
    }

    private DataSet getData(DataSet dataSet){
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(Color.WHITE);
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    private BarData getBarData(){
        ArrayList<String> labels = new ArrayList<>();
        labels.add(summaryMonths[0]);
        labels.add(summaryMonths[1]);
        labels.add(summaryMonths[2]);
        BarDataSet barDataSet = (BarDataSet) getData(new BarDataSet(getBarEntries(), "Monto total de utilidades del mes"));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData = new BarData(labels, barDataSet);
        //barData.setBarWidth(0.45f);
        return barData;
    }


}
