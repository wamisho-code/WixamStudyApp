package com.example.studyapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AIUtility {
    Context context;
    String userAsked;
    String valued;
    private int index = 0;
    private long delay = 80;
    public AIUtility(Context context,String userAsked) {
        this.context=context;
        this.userAsked=userAsked;
    }
    public interface GeminiCallback {
        void onResult(String result);
    }
    @SuppressLint("NewApi")
    public void answerWithGemini(final GeminiCallback callback) {
        String api = BuildConfig.API_KEY;
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", api);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);
        Content content = new Content.Builder().addText(userAsked).build();
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                callback.onResult(resultText);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                callback.onResult("I can't continue this chat");
            }
        }, context.getMainExecutor());
    }

}