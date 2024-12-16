package com.example.studyapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class QuestionFragment extends Fragment {
    TextView maths,english,physics,chemistry,biology,aptitude,civic;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_question,container,false);
        maths=view.findViewById(R.id.math_btn);
        LinearLayout layout= view.findViewById(R.id.subject_holder);
        english=view.findViewById(R.id.english_btn);
        physics=view.findViewById(R.id.physics_btn);
        chemistry=view.findViewById(R.id.chemistry_btn);
        biology=view.findViewById(R.id.biology_btn);
        aptitude=view.findViewById(R.id.aptitude_btn);
        civic=view.findViewById(R.id.civic_btn);
        maths.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(),MathsSelectorActivity.class));
        });
        physics.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(),PhysicsSelectorActivity.class));
        });
        english.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(),EnglishSelectorActivity.class));
        });
        chemistry.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(),ChemistrySelecorActivity.class));
        });
        biology.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(),BiologySelectorActivity.class));
        });
        aptitude.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(),AptitudeSelectorActivity.class));
        });
        civic.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(),SearchActivity.class));
        });

        return view;
    }

}