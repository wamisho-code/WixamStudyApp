package com.example.studyapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class AccountFragment extends Fragment {

    ArrayList barArraylist;
    Spinner grade;
    PieChart pieChart;
    TextView nodata;
     String selectedGrade;
    Cursor cursor;
    int correctAnswer,wrongAnswer,skipped;

    public AccountFragment() {

    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_account, container, false);
        pieChart=view.findViewById(R.id.pie_chart);
        nodata=view.findViewById(R.id.noDataFound);
        AnalyticsDBHelper analyticsDBHelper= new AnalyticsDBHelper(getContext());
        grade=view.findViewById(R.id.spinrer_analytics);
        grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGrade=parent.getItemAtPosition(position).toString();
                setAnalytics();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> gradeList =new ArrayList<>();
        gradeList.add("Select Subject");
        gradeList.add("English");
        gradeList.add("Physics");
        gradeList.add("Mathematics");
        gradeList.add("Biology");
        gradeList.add("Chemistry");
        gradeList.add("Aptitude");
        gradeList.add("Civic");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,gradeList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        grade.setAdapter(adapter);
        grade.setSelection(adapter.getPosition("Select Subject"));
        selectedGrade=grade.getSelectedItem().toString();
//        cursor= analyticsDBHelper.getAllData(selectedGrade);
//        System.out.println(selectedGrade);

//        horizontalBarChart.setFitBars(true);
//        Description description= new Description();
//        description.setText("Physics Test");
//        horizontalBarChart.setDescription(description);
//        horizontalBarChart.getXAxis().setLabelCount(4);




        return  view;
    }
    int dataFun(String subject) {
        AnalyticsDBHelper analyticsDBHelper = new AnalyticsDBHelper(getContext());

        Cursor cursor = analyticsDBHelper.getAllData(subject);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                // Verify column indices before accessing
                int correctAnswerIdx = cursor.getColumnIndex("CORRECTLY_ANSWERED");  // Replace with actual column name
                int wrongAnswerIdx = cursor.getColumnIndex("WRONGLY_ANSWERED");      // Replace with actual column name
                int skippedIdx = cursor.getColumnIndex("SKIPPED");
                System.out.println(skippedIdx+" , "+wrongAnswerIdx);
                // Replace with actual column name

                if (correctAnswerIdx != -1 && wrongAnswerIdx != -1 && skippedIdx != -1) {
                    correctAnswer += Integer.parseInt(cursor.getString(correctAnswerIdx));
                    wrongAnswer += Integer.parseInt(cursor.getString(wrongAnswerIdx));
                    skipped += Integer.parseInt(cursor.getString(skippedIdx));
                } else {
                    Log.e("Database", "One or more column indices not found");
                }
            }
            System.out.println("worng: "+wrongAnswer);
            System.out.println("correct: "+correctAnswer);
            System.out.println("skipped: "+skipped);
            cursor.close();
            return 1;
        } else {
            if (cursor != null) {
                cursor.close();
            }
            return 0;
        }

    }
    public void setAnalytics(){
        if (!selectedGrade.equals("Select Subject")&& dataFun(selectedGrade)==1){
            pieChart.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.GONE);
            ArrayList<PieEntry> pieEntries = new ArrayList<>();

            pieEntries.add(new PieEntry(wrongAnswer,"Wrongly Answered"));
            pieEntries.add(new PieEntry(skipped,"Skipped Question"));
            pieEntries.add(new PieEntry(correctAnswer,"Correctly Answered"));

            ArrayList labels=  new ArrayList<>();

            labels.add("Wrong Answer");
            labels.add("Skipped");
            labels.add("Correct Answer");
            PieDataSet barSet= new PieDataSet(pieEntries,"");
            barSet.setColors(ColorTemplate.COLORFUL_COLORS);
            barSet.setValueLinePart1Length(0.5f);
            barSet.setValueLinePart2Length(0.4f);
            barSet.setValueLinePart1OffsetPercentage(80.f);
            barSet.setValueLineVariableLength(true);
            barSet.setValueTextColor(Color.BLACK);
            PieData barData= new PieData(barSet);
            pieChart.setData(barData);
            pieChart.setDrawEntryLabels(false); // Hide entry labels on slices
            pieChart.getDescription().setEnabled(false); // Hide description
            pieChart.setEntryLabelTextSize(12f);
            pieChart.setEntryLabelColor(Color.BLACK); // Label text color

            Legend legend = pieChart.getLegend();
            legend.setEnabled(true);
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            legend.setDrawInside(false);
            legend.setTextColor(Color.BLACK);

            pieChart.animateY(1500);
            pieChart.getDescription().setEnabled(false);
            pieChart.invalidate();

        }
        if ( dataFun(selectedGrade)==0 && !selectedGrade.equals("Select Subject")) {
            pieChart.setVisibility(View.GONE);
            nodata.setVisibility(View.VISIBLE);
        }
        if (selectedGrade.equals("Select Subject")){
            pieChart.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Please select subject", Toast.LENGTH_SHORT).show();
        }
        wrongAnswer=0;
        correctAnswer=0;
        skipped=0;
    }

}