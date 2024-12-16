package com.example.studyapp;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AptitudeSelectorActivity extends AppCompatActivity {
    ImageView back_btn;
    LinearLayout layout;
    LinearLayout layout1,layout3,layout4,layout5;
    Spinner timer;
    boolean allSelected=false;
    TextView checkallCheckbox, noofQuestion;
    int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aptitude_selector);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        layout=findViewById(R.id.grade_units_content);
        layout1=findViewById(R.id.grade_9_holder);
        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        minute=0;
        noofQuestion=findViewById(R.id.no_of_question);
        checkallCheckbox=findViewById(R.id.select_txt);
        checkallCheckbox.setOnClickListener(v -> {
            entranceMode();
        });
        back_btn=findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v -> {
            Intent intent=new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        timer=findViewById(R.id.Timerspinner);
        timer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTime=parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<String> adapter = SelectorUtility.returnTimerAdapter(getApplicationContext());
        adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        timer.setAdapter(adapter);
    }

    public void selectNumberOfQuestion(View view) {
        SelectorUtility.numberOfQuestion((TextView) view,noofQuestion);}
    public void entranceMode() {
        String subject=((TextView)findViewById(R.id.label_title)).getText().toString();
        SelectorUtility selectorUtility= new SelectorUtility(layout1,timer,allSelected,checkallCheckbox,noofQuestion,subject,getApplicationContext());
        allSelected=selectorUtility.entranceMode(false);
    }}