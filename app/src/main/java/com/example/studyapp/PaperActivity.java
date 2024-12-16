package com.example.studyapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.airbnb.lottie.LottieAnimationView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PaperActivity extends AppCompatActivity {
    ArrayList<String>id,question,choice_A,choice_B,choice_C,choice_D,corrects,description,topics,content;
    RecyclerView examLoader;
    ImageView back_btn;
    TextView timerShow;
    String data,selectedTime;

    boolean isTimeShown=true;
    Long givenTime,hour,minutes ;
    LottieAnimationView animationView;
    Button submit;
    Cursor[] res;
    Dialog result_Dialog;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_paper);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String SUBJECT=getIntent().getStringExtra("subject");
        System.out.println(SUBJECT);
        TextView titleExam=findViewById(R.id.entranceTitle);
        titleExam.setText(SUBJECT+" Exam");
        assert SUBJECT != null;
        DatabaseHelper Database=new DatabaseHelper(this,SUBJECT.toLowerCase());

        question=new ArrayList<>();
        back_btn=findViewById(R.id.back_btn);
        timerShow=findViewById(R.id.time_show);
        back_btn.setOnClickListener(v -> {
            SelectorUtility.naviagteToMain(PaperActivity.this);
            finish();
        });
        result_Dialog=new Dialog(this);
        result_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        data= getIntent().getStringExtra("data");
        selectedTime=getIntent().getStringExtra("timerValue");

        Bundle bundle= getIntent().getBundleExtra("Bundle");
        content=(ArrayList<String>) bundle.getSerializable("ARRAYLIST");
        int noQ=Integer.parseInt(getIntent().getStringExtra("noQ"));
        System.out.println("Time: "+selectedTime);
        id=new ArrayList<>();
        examLoader=findViewById(R.id.exam_loader_recyclerview);
        examLoader.setHasFixedSize(true);
        examLoader.setItemViewCacheSize(20);
        choice_A=new ArrayList<>();
        choice_B=new ArrayList<>();
        choice_C=new ArrayList<>();
        choice_D=new ArrayList<>();
        topics=new ArrayList<>();
        description=new ArrayList<>();
        corrects=new ArrayList<>();
        res=Database.getAllData(content,noQ);


        display();
        DBAdapter adapter= new DBAdapter(PaperActivity.this,id,question,choice_A,choice_B,
                choice_C,choice_D,corrects,topics,data, noQ,examLoader);
        examLoader.setAdapter(adapter);
        examLoader.setLayoutManager(new LinearLayoutManager(PaperActivity.this));
        String part1,part2 ;


        if (selectedTime.substring(1,2).equals(":")){
             part1 = selectedTime.substring(0, selectedTime.indexOf(':'));
             part2 = selectedTime.substring(selectedTime.indexOf(':') + 1, selectedTime.indexOf(' '));
            givenTime=(Long.parseLong(part1)*60)+Long.parseLong(part2);
            startTime();
        }else if (selectedTime.equals("no timer")){
            givenTime=0L;
            timerShow.setText("");
        }
        else if (selectedTime.substring(2,3).equals("h")){
            part2 = selectedTime.substring(0, selectedTime.indexOf(' '));
            givenTime=(Long.parseLong(part2)*60);
            startTime();
        }
        else {
            part2 = selectedTime.substring(0, selectedTime.indexOf(' '));
            givenTime=Long.parseLong(part2);
            startTime();
        }
        hour = givenTime/60;
        minutes=givenTime%60;
        timerShow.setOnClickListener(v -> {
            isTimeShown=!isTimeShown;
            setTimeValue();

            Toast.makeText(this,String.valueOf(isTimeShown), Toast.LENGTH_SHORT).show();
        });

        submit=findViewById(R.id.submit_btn);

        submit.setOnClickListener(v -> {
            submit.setEnabled(false);
            submit.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#818181")));
            String status=null;
            int Result[]=adapter.examData();
            AnalyticsDBHelper analyticsDBHelper= new AnalyticsDBHelper(this);
            analyticsDBHelper.insertData(SUBJECT,Result[0],Result[1],Result[2]);
            result_Dialog.setContentView(R.layout.below_50_percent);

            int anime_id=0;
            int outof=(Result[0]+Result[1]+Result[2]);

            if (Result[0]>(outof*0.75)){
                anime_id=R.raw.happier;
                status ="Yeah! You did it \nBirhanu Nega can't get you anymore.";
                if (Result[0]>(outof*0.85)){
                    status ="Genius! You are a Legend\nBirhanu Nega is afraid of you.";
                }
            }
            else if (Result[0]>(outof/2)){
               anime_id=R.raw.happy;
               status ="Congrats! You are on fire\nBirhanu Nega has no chance over you.";
            }
            else {
                anime_id=R.raw.sad;
                status ="It is okay! You just need a little more practice.";
            }
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            TextView result_total = result_Dialog.findViewById(R.id.result_txt);
            TextView details=result_Dialog.findViewById(R.id.result_detail);
            TextView noCorrect=result_Dialog.findViewById(R.id.correct_no);
            TextView noWrong=result_Dialog.findViewById(R.id.wrong_no);
            TextView noSkipped=result_Dialog.findViewById(R.id.skipped_no);
            TextView theStatus=result_Dialog.findViewById(R.id.status);
            RelativeLayout  relativeLayout= result_Dialog.findViewById(R.id.detail_holder);
           LottieAnimationView animationView1=result_Dialog.findViewById(R.id.lottie_animation);

                result_total.setText(String.valueOf(Result[0]) + "/" + String.valueOf(outof));
                theStatus.setText(status);
                animationView1.setAnimation(anime_id);
                animationView1.loop(true);
                details.setOnClickListener(v1 -> {
                    if (relativeLayout.getVisibility()==View.GONE){
                    noCorrect.setText(String.valueOf(Result[0]));
                    noWrong.setText(String.valueOf(Result[1]));
                    noSkipped.setText(String.valueOf(Result[2]));
                    relativeLayout.setAnimation(animation);
                    relativeLayout.setVisibility(View.VISIBLE);}
                });
                Button Return= result_Dialog.findViewById(R.id.retake_exam);
                Return.setOnClickListener(v2 -> {
                    Intent intent =new Intent(PaperActivity.this,EnglishSelectorActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                });
            result_Dialog.show();

        }
        );

    }
    private void startTime() {
            Timer timer= new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    hour = givenTime/60;
                    minutes=givenTime%60;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (givenTime <= 0) {
                                timerShow.setText("Times up");
                                timerShow.setEnabled(false);
                                timer.cancel();
                            }else {
                                setTimeValue();}
                        }
                    });
                    givenTime--;
                }
            },0,60000);
    }

    void  display(){
        for (Cursor cursor:res){
        if (cursor.getCount()==0){
           // showMessage("Error","Noting found");
            return;
        }
        while (cursor.moveToNext()){
            id.add(cursor.getString(0));
            question.add(cursor.getString(1));
            choice_A.add(cursor.getString(2));
            choice_B.add(cursor.getString(3));
            choice_C.add(cursor.getString(4));
            choice_D.add(cursor.getString(5));
            corrects.add(cursor.getString(6));
            description.add(cursor.getString(7));
            topics.add(cursor.getString(9));
        }}

    }
    void setTimeValue(){
        if (isTimeShown) {
            if (hour != 0) {
                timerShow.setText(hour + " hr:" + minutes + " min");
            } else {
                timerShow.setText(minutes + " min");
            }
        }else {
            timerShow.setText("Hiden");
        }

    }


}