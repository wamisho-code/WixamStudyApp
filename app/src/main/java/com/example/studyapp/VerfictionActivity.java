package com.example.studyapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerfictionActivity extends AppCompatActivity {
    TextView verifytext,resendBtn;
    FirebaseAuth auth;
    String verificationCode;

    PhoneAuthProvider.ForceResendingToken resendingToken;
    Long timeOutSecond=60L;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verfiction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth=FirebaseAuth.getInstance();
        String phone=getIntent().getStringExtra("Phone");
        String firstName=getIntent().getStringExtra("first_name");
        String lastName=getIntent().getStringExtra("last_name");
        String gender=getIntent().getStringExtra("gender");
        String grade=getIntent().getStringExtra("grade");
        verifytext=findViewById(R.id.verify_text);
        resendBtn=findViewById(R.id.resend_otp);
        verifytext.setText(phone);
        sendOtp(phone,false);
    }

    private void sendOtp(String phone, boolean isResend) {
        PhoneAuthOptions.Builder builder=PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phone)
                .setTimeout(timeOutSecond,TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signIn(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                            verifytext.setText("Failed :"+phone);
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verifytext.setText("We have sent six digit code to "+phone);
                        verificationCode=s;
                        resendingToken=forceResendingToken;
                    }
                });
        if (isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
    }


}