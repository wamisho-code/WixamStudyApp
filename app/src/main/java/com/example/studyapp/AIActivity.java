package com.example.studyapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class AIActivity extends AppCompatActivity {
    ImageView back_btn;
    TextView answerGemini;
    EditText userInput;
    TextView ai_answer,user_question;
    LinearLayout ai_holder,user_holder;
    private int index = 0;
    private long delay = 50;
    LottieAnimationView lottieAnimationView;

    RecyclerView recyclerView;
    private RelativeLayout relativeLayout;

    private int textViewCount = 0;
    ImageView send;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aiactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        send=findViewById(R.id.sendButton);
        InputMethodManager imm = (InputMethodManager) getSystemService(AIActivity.INPUT_METHOD_SERVICE);

        back_btn=findViewById(R.id.back_btn);
        userInput=findViewById(R.id.user_input);

        ai_holder=findViewById(R.id.ai_answer_holder);
        user_holder=findViewById(R.id.user_ask_holder);
        ai_answer=findViewById(R.id.ai_answer);
        user_question=findViewById(R.id.user_ask);
        lottieAnimationView=findViewById(R.id.loadingbar);

        back_btn.setOnClickListener(v -> {
            SelectorUtility.naviagteToMain(getApplicationContext());
            finish();
        });
        send.setOnClickListener(v -> {
            String userInputText=userInput.getText().toString();
            View view = getCurrentFocus();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);}

            if (!userInputText.isEmpty()){
                userInput.setText("");
                user_holder.setVisibility(View.VISIBLE);
                user_question.setText(userInputText);
                lottieAnimationView.setVisibility(View.VISIBLE);
                AIUtility aiUtility = new AIUtility(this,userInputText);
                aiUtility.answerWithGemini(new AIUtility.GeminiCallback() {
                    @Override
                    public void onResult(String result) {
                        lottieAnimationView.setVisibility(View.GONE);
                        ai_holder.setVisibility(View.VISIBLE);

                        runOnUiThread(() -> {
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    if (index <= result.length()) {
                                        displayText(ai_answer, result.substring(0, index));
                                        index++;
                                        handler.postDelayed(this, delay);
                                    }
                                }
                            }, delay);

                        });
                    }
                });


            }
        });


    }

    private void displayText(TextView textView, String text) {
        String markdownText = text.replace("##", "").replace("**", "");
        Spannable spannable = new SpannableString(markdownText);

        int start, end;
        while ((start = markdownText.indexOf("**")) != -1 && (end = markdownText.indexOf("**", start + 2)) != -1) {
            spannable.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            markdownText = markdownText.replaceFirst("\\*\\*", "").replaceFirst("\\*\\*", "");
        }

        textView.setText(spannable);
    }

}