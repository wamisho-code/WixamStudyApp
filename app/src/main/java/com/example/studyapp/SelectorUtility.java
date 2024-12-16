package com.example.studyapp;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class SelectorUtility {
//    LinearLayout layout;
    LinearLayout layout1,layout3,layout4,layout5;
    Spinner timer;
    boolean allSelected;
    TextView checkallCheckbox, noofQuestion;
    private static  ArrayList<String> selectedContent;
    String subject;
    private static  Context context;

    public SelectorUtility(LinearLayout layout1, LinearLayout layout3, LinearLayout layout4,
                           LinearLayout layout5, Spinner timer, boolean allSelected, TextView checkallCheckbox,
                           TextView noofQuestion,String subject,Context context) {
        this.layout1 = layout1;
        this.subject=subject.toLowerCase();
        this.layout3 = layout3;
        this.layout4 = layout4;
        this.context=context;
        this.layout5 = layout5;
        this.timer = timer;

        this.allSelected = allSelected;
        this.checkallCheckbox = checkallCheckbox;

        this.noofQuestion = noofQuestion;
    }

    public SelectorUtility(LinearLayout layout1,Spinner timer, boolean allSelected, TextView checkallCheckbox,
                           TextView noofQuestion,String subject,Context context) {
        this.layout1 = layout1;
        this.subject=subject.toLowerCase();
        this.timer = timer;
        this.context=context;
        this.allSelected = allSelected;
        this.checkallCheckbox = checkallCheckbox;
        this.noofQuestion = noofQuestion;
    }

    public SelectorUtility() {
    }

    public static ArrayAdapter<String> returnTimerAdapter(Context context){
        ArrayList<String> gradeList =new ArrayList<>();
        gradeList.add("no timer");
        gradeList.add("10 min");
        gradeList.add("20 min");
        gradeList.add("45 min");
        gradeList.add("1 hr");
        gradeList.add("1:30 hr");
        gradeList.add("1:45 hr");
        gradeList.add("2 hr");
        gradeList.add("2:30 hr");
        gradeList.add("2:45 hr");
        gradeList.add("3 hr");
        gradeList.add("3:30 hr");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,gradeList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        return  adapter;
    }
    public static  void visibilityController(LinearLayout layout2, TextView view){
        System.out.println(view.getText().toString());
        int v= (layout2.getVisibility()==View.GONE)?View.VISIBLE:View.GONE;
        AutoTransition transition = new AutoTransition();
        TextView textView = (TextView)view;
        transition.setDuration(300);
        TransitionManager.beginDelayedTransition(layout2, transition);

        if (v==8){
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.dropdown_icon, 0);
        }else {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_drop_icon, 0);}
        layout2.setVisibility(v);
    }
    public static void numberOfQuestion(TextView view,TextView noofQuestion) {
        String sign=view.getText().toString();
        int selectedno=Integer.parseInt(noofQuestion.getText().toString());
        if(sign.equals("-")&& !(selectedno<=5)){
            selectedno=selectedno-5;
        }
        if(sign.equals("+")&& !(selectedno>195)){
            selectedno=selectedno+5;
        }
        noofQuestion.setText(String.valueOf(selectedno));

    }
    public  boolean entranceMode(boolean haveGrades) {
        LinearLayout[] layouts;
        View child;
        if (haveGrades){
        layouts = new LinearLayout[]{layout1, layout3, layout4, layout5};}else {
            layouts = new LinearLayout[]{layout1};
        }

        for (LinearLayout layout : layouts) {
            String no_question;
            for(int i=0;i<layout.getChildCount();i++){
                  child= layout.getChildAt(i);
                if (child instanceof CheckBox && !allSelected){
                    ((CheckBox) child).setChecked(true);
                    checkallCheckbox.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#73B8FA")));

                    if(subject.equals("mathematics exam")){
                        timer.setSelection(10);
                    no_question="65";}
                    else if (subject.equals("physics exam")) {
                        timer.setSelection(8);
                        no_question="50";
                        timer.setSelection(8);}
                    else if(subject.equals("aptitude exam")){
                        timer.setSelection(8);
                        no_question="60";}
                    else if(subject.equals("english exam")){
                        timer.setSelection(8);
                        no_question="120";
                    }else {no_question="100";
                        timer.setSelection(7);}
                    noofQuestion.setText(no_question);


                }else {
                    ((CheckBox) child).setChecked(false);
                    checkallCheckbox.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F2F3F2")));
                }
            }

        }
        allSelected=!allSelected;
        return  allSelected;
    }
     public static int[] markCalculator(int correctAn,int wrongAn ,int skipped){
         int[] result=new int[3];
         result[0]=correctAn;
         result[1]=wrongAn;
         result[2]=skipped;
        return result;

    }
    public static void naviagteToMain(Context context){
        Intent intent=new Intent(context,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public boolean contentComposer(boolean haveGrades,Context context){
        LinearLayout[] layouts;
        System.out.println("contentcomposer-2");
        if (haveGrades){
            layouts = new LinearLayout[]{layout1, layout3, layout4, layout5};
        }
        else {
            System.out.println("contentcomposer-1");
            layouts = new LinearLayout[]{layout1};
        }
        boolean check =add_content(layouts);
        return check;

    }
    public static void fullContent(String data,String subject,String selectedTime,String noofQuestion,Context context){

        Bundle contentData= new Bundle();
        contentData.putSerializable("ARRAYLIST",(Serializable) selectedContent);
        Intent intent = new Intent(context.getApplicationContext(),PaperActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("timerValue", selectedTime);
        intent.putExtra("noQ",noofQuestion);
        intent.putExtra("subject",subject);
        intent.putExtra("Bundle",contentData);

        context.startActivity(intent);
    }
    public boolean add_content(LinearLayout[] layouts){
        selectedContent=new ArrayList<>();
        System.out.println("contentcomposer-3");
        int j=0;
        for (LinearLayout layout : layouts) {
            String no_question;
            for(int i=0;i<layout.getChildCount();i++){
               View  child= layout.getChildAt(i);
                if (child instanceof CheckBox ){
                    String content=((CheckBox) child).getText().toString();
                    if (((CheckBox) child).isChecked()){
                        selectedContent.add(content);
                        System.out.println("checked "+content);
                        j++;
                    }

                }
            }
        }
        if (j==0){
            Toast.makeText(context, "You should chose at least one Content(unit)", Toast.LENGTH_SHORT).show();
             return false;
        }else {
        return true;}
    }

}
// Bundle contentData= new Bundle();
// contentData.putSerializable("ARRAYLIST",(Serializable) selectedContent);
//        System.out.println("fullContent");
//
////        intent.putExtra("data",data);
////        intent.putExtra("timerValue",selectedTime);
////        intent.putExtra("noQ",noofQuestions);
////        //intent.putExtra("Bundle",contentData);
//        context.startActivity(intent);