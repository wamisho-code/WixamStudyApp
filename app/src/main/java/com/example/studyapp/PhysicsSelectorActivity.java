package com.example.studyapp;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class PhysicsSelectorActivity extends AppCompatActivity {
    ImageView back_btn;
    LinearLayout layout;
    LinearLayout layout1, layout3, layout4, layout5;
    Spinner timer;
    String subject;
    String data="don't show",selectedTime;

    boolean allSelected = false;
    TextView checkallCheckbox, noofQuestion;
    int minute;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_physics_selector);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView labelTitle=findViewById(R.id.label_title);
        subject=labelTitle.getText().toString().substring(0,labelTitle.getText().toString().indexOf(" "));
        layout = findViewById(R.id.grade_units_content);
        layout1 = findViewById(R.id.grade_9_holder);
        layout3 = findViewById(R.id.grade_10_holder);
        layout4 = findViewById(R.id.grade_11_holder);
        layout5 = findViewById(R.id.grade_12_holder);
        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        minute = 0;
        noofQuestion = findViewById(R.id.no_of_question);
        checkallCheckbox = findViewById(R.id.select_txt);
        checkallCheckbox.setOnClickListener(v -> {
            entranceMode(true);
        });
        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        timer = findViewById(R.id.Timerspinner);
        timer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTime=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedTime=parent.getItemAtPosition(0).toString();
            }
        });
        ArrayAdapter<String> adapter = SelectorUtility.returnTimerAdapter(getApplicationContext());
        adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        timer.setAdapter(adapter);

    }

    public void expand(View view) {
        String selectedGrade = ((TextView) view).getText().toString();
        System.out.println(selectedGrade);
        if (selectedGrade.equals("Grade 9")) {
            SelectorUtility.visibilityController(layout1, (TextView) view);
        }
        if (selectedGrade.equals("Grade 10")) {
            SelectorUtility.visibilityController(layout3, (TextView) view);
        }
        if (selectedGrade.equals("Grade 11")) {
            SelectorUtility.visibilityController(layout4, (TextView) view);
        }
        if (selectedGrade.equals("Grade 12")) {
            SelectorUtility.visibilityController(layout5, (TextView) view);
        }
    }

    public void selectNumberOfQuestion(View view) {
        SelectorUtility.numberOfQuestion((TextView) view, noofQuestion);
    }

    public boolean entranceMode(boolean operation) {
        String subject = ((TextView) findViewById(R.id.label_title)).getText().toString();
        SelectorUtility selectorUtility  = new SelectorUtility(layout1, layout3, layout4,
                layout5, timer, allSelected, checkallCheckbox, noofQuestion, subject, getApplicationContext());

        if (operation){
            allSelected = selectorUtility.entranceMode(true);
            return true;
        }
        else {
            return selectorUtility.contentComposer(true,PhysicsSelectorActivity.this);

        }
    }

    public void goToExam(View view) {
        boolean check= entranceMode(false);
        System.out.println("check "+check);
        if (check){
            System.out.println(data);
            System.out.println(subject);
            System.out.println(selectedTime);
            System.out.println(noofQuestion);
          SelectorUtility.fullContent(data, subject, selectedTime, noofQuestion.getText().toString(), this);}

    }
}