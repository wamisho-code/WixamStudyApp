package com.example.studyapp;



import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;

public class EnglishSelectorActivity extends AppCompatActivity {
    ImageView back_btn;
    LinearLayout layout;
    LinearLayout layout1,layout3,layout4,layout5;
    Spinner timer;
    String subject;
    String data="don't show",selectedTime;
    boolean allSelected=false;
    TextView checkallCheckbox, noofQuestion,entranceTitle;
    Switch showAnswerSwitch;
    int minute;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_english_selector);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        layout=findViewById(R.id.grade_units_content);
        layout1=findViewById(R.id.grade_9_holder);
        TextView labelTitle=findViewById(R.id.label_title);
        subject=labelTitle.getText().toString().substring(0,labelTitle.getText().toString().indexOf(" "));
        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        minute=0;
        showAnswerSwitch=findViewById(R.id.show_answer_toggle);
        noofQuestion=findViewById(R.id.no_of_question);
        checkallCheckbox=findViewById(R.id.select_txt);
        checkallCheckbox.setOnClickListener(v -> {
            entranceMode(true);
        });
        showAnswerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    data="show";
                }
            }
        });
        back_btn=findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v -> {
            SelectorUtility.naviagteToMain(getApplicationContext());
            finish();
        });
        timer=findViewById(R.id.Timerspinner);
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

    public void selectNumberOfQuestion(View view) {
        SelectorUtility.numberOfQuestion((TextView) view,noofQuestion);}
    public boolean entranceMode(boolean operation) {
        System.out.println("opp "+operation);
        String subject=((TextView)findViewById(R.id.label_title)).getText().toString();
        SelectorUtility selectorUtility= new SelectorUtility(layout1,timer,allSelected,checkallCheckbox,noofQuestion,subject,getApplicationContext());
        if (operation){
            allSelected=selectorUtility.entranceMode(false);
            return true;}
        else {
            return selectorUtility.contentComposer(false,EnglishSelectorActivity.this);

        }

    }

//    public void startExam() {
//        System.out.println("startExam");
//        boolean yool=entranceMode(false);
//        System.out.println("clciked: "+yool);
//        if (yool){
//            Intent intent = new Intent(EnglishSelectorActivity.this,PaperActivity.class);
//            SelectorUtility.fullContent(intent,data,selectedTime,noofQuestion.getText().toString(),getApplicationContext());
//        }
//    }

    public void goToExam(View view) {
        boolean check= entranceMode(false);
        if (check){
            SelectorUtility.fullContent(data,subject,selectedTime,noofQuestion.getText().toString(),this);
            ;}
    }
}