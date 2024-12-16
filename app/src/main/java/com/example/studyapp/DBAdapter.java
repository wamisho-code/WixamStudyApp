package com.example.studyapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class DBAdapter extends RecyclerView.Adapter<DBAdapter.MyViewHolder> {
    private Context context;
    String data;
    int [] correct_answers ,wrong_answers,wrongPositiones;
    RecyclerView recyclerView;
    int i = 1,h=0;

    MyViewHolder holder2;
    private ArrayList id, question, choiceA, choiceB, choiceC, choiceD, desc, topics;
    private HashMap<Integer, Integer> selectedItems = new HashMap<>();
    DBAdapter(Context context, ArrayList id, ArrayList question, ArrayList choiceA, ArrayList choiceB,
              ArrayList choiceC, ArrayList choiceD, ArrayList desc, ArrayList topics, String data, int noQ, RecyclerView recyclerView) {
        this.context = context;
        this.id = id;
        this.topics = topics;
        this.data = data;
        this.question = question;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
        h=question.size();
        this.recyclerView = recyclerView;
        this.desc = desc;
        correct_answers=new int[question.size()];
        wrong_answers=new int[question.size()];
        wrongPositiones=new int[question.size()];
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("onCreate", "Binding data at position: " + viewType);
        View view = LayoutInflater.from(context).inflate(R.layout.displaydb, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder2=holder;
        holder.A.setChecked(false);
        holder.B.setChecked(false);
        holder.C.setChecked(false);
        holder.D.setChecked(false);
        holder.topic.setText(String.valueOf(topics.get(position)));
        holder.questNo.setText(String.valueOf(i) + ".");
        i++;
        holder.quest.setText(String.valueOf(question.get(position)));
        holder.A.setText("A. " + String.valueOf(choiceA.get(position)));
        holder.B.setText("B. " + String.valueOf(choiceB.get(position)));
        holder.C.setText("C. " + String.valueOf(choiceC.get(position)));
        holder.D.setText("D. " + String.valueOf(choiceD.get(position)));
        int quesNo = Integer.parseInt(holder.questNo.getText().toString().replaceAll("[^\\d]", ""));

        holder.A.setOnClickListener(v -> {
            holder.A.setChecked(true);
            isAnswered(holder, position, holder.A.getText().toString(), quesNo);
        });
        holder.B.setOnClickListener(v -> {
            holder.B.setChecked(true);
            isAnswered(holder, position, holder.B.getText().toString(), quesNo);
        });
        holder.C.setOnClickListener(v -> {
            holder.C.setChecked(true);
            isAnswered(holder, position, holder.C.getText().toString(), quesNo);
        });
        holder.D.setOnClickListener(v -> {
            holder.D.setChecked(true);
            isAnswered(holder, position, holder.D.getText().toString(), quesNo);
        });
        if (data.equals("show")) {
            holder.DESC.setText(String.valueOf("Answer"));
        } else {
            holder.DESC.setVisibility(View.GONE);
        }
    }

    private void isAnswered(MyViewHolder holder, int position, String answer, int quesNo) {
        holder.DESC.setText(String.valueOf(desc.get(position)));

        if ((answer.substring(3)).equals(holder.DESC.getText().toString())){
            correct_answers[quesNo-1]=1;
            wrong_answers[quesNo-1]=0;
            System.out.println("Correct: "+quesNo+"="+1);
        }else {
            wrong_answers[quesNo-1]=1;
            correct_answers[quesNo-1]=0;
            System.out.println("Wrong: "+quesNo+"="+1);

        }

    }

    @Override
    public int getItemCount() {
        Log.d("Item Count", "Item count: " + id.size());
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView questNo, quest, DESC, topic;
        RadioButton A, B, C, D;

        ImageView wrong_icon,Quesimage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.topic);
            wrong_icon=itemView.findViewById(R.id.is_wrong_icon);
            questNo = itemView.findViewById(R.id.question_no);
            quest = itemView.findViewById(R.id.quesHolder);
            A = itemView.findViewById(R.id.choiceA);
            B = itemView.findViewById(R.id.choiceB);
            C = itemView.findViewById(R.id.choiceC);
            D = itemView.findViewById(R.id.choiceD);
            DESC = itemView.findViewById(R.id.descrp);
        }
    }

    public int[]  examData() {
        int right_answer=0;
        int wronged=0;
        int skipped_an=0;
        for (int i :correct_answers){
            right_answer+=i;
        }
        for (int i :wrong_answers){
            wronged+=i;
        }
        skipped_an=(question.size())-(right_answer+wronged);
        System.out.println(right_answer+" - "+wronged+" - "+skipped_an);
       return SelectorUtility.markCalculator(right_answer,wronged,skipped_an);
    }

}
