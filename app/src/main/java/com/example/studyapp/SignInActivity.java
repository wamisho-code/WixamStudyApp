package com.example.studyapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hbb20.CountryCodePicker;

import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity {
    EditText firstName,lastName,phoneNumber;
    Spinner grade;
    String selectedGrade,phone;
    TextView haveAccount;
    RadioGroup radioGroup;
    CountryCodePicker countryCodePicker;
    RadioButton genderMale,genderFemale;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        firstName=findViewById(R.id.first_name);
        countryCodePicker=findViewById(R.id.country_code_picker);
        radioGroup=findViewById(R.id.radio_group);
        haveAccount=findViewById(R.id.haveAccount);
        lastName=findViewById(R.id.last_name);
        genderMale=findViewById(R.id.gender_male);
        genderFemale=findViewById(R.id.gender_female);
        grade=findViewById(R.id.spinner);
        phoneNumber=findViewById(R.id.phone_number);
        countryCodePicker.registerCarrierNumberEditText(phoneNumber);
        grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGrade=parent.getItemAtPosition(position).toString();

                        }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> gradeList =new ArrayList<>();
        gradeList.add("Grade 12");
        gradeList.add("Grade 11");
        gradeList.add("Grade 10");
        gradeList.add("Grade 9");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,gradeList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        grade.setAdapter(adapter);

        haveAccount.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this,HaveAccountActivity.class));
            finish();
        });

    }
    public void nextClicked(View v){
        int isGenderSelected =radioGroup.getCheckedRadioButtonId();

        if(firstName.getText().toString().isEmpty()||lastName.getText().toString().isEmpty()
        ||isGenderSelected==-1 || !countryCodePicker.isValidFullNumber()){
            if(!countryCodePicker.isValidFullNumber()){
                phoneNumber.setError("invalid phone number!");
            }else {
            Toast.makeText(this, "Please fill all information!", Toast.LENGTH_SHORT).show();}
        }else {
            String gender;
            if (genderMale.isChecked()){
                gender=genderMale.getText().toString();
            }else {
                gender=genderFemale.getText().toString();
            }
                Intent intent =new Intent(SignInActivity.this,VerfictionActivity.class);
                intent.putExtra("Phone",countryCodePicker.getFullNumberWithPlus());
                intent.putExtra("first_name",firstName.getText().toString());
                intent.putExtra("first_name",firstName.getText().toString());
                intent.putExtra("last_name",lastName.getText().toString());
                intent.putExtra("gender",gender);
                intent.putExtra("grade",selectedGrade);
                startActivity(intent);

        }
    }
}