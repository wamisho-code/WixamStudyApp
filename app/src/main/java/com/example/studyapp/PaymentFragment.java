package com.example.studyapp;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

public class PaymentFragment extends Fragment {

    EditText ques, a, b, c, d, desc, subect, unit, correct;
    Button button;
    TextView textView;
    private DatabaseHelper dbHelper;
    public PaymentFragment() {
        // Required empty public constructor
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        textView = view.findViewById(R.id.just_txt);
         //dbHelper = new DatabaseHelper(getActivity(),);

        return view;

    }


}