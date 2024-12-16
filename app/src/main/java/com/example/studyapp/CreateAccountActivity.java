package com.example.studyapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.Arrays;

public class CreateAccountActivity extends AppCompatActivity {
    FirebaseAuth auth;
    CallbackManager callbackManager;
    GoogleSignInClient  googleSignInClient;
    private int requestCode = 64206;

    CountryCodePicker countryCodePicker;
    private final ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if ( o.getResultCode()==RESULT_OK){
                        Task<GoogleSignInAccount> accountTask= GoogleSignIn.getSignedInAccountFromIntent(o.getData());
                        try{
                            GoogleSignInAccount signInAccount=accountTask.getResult(ApiException.class);
                            AuthCredential authCredential= GoogleAuthProvider.getCredential(signInAccount.getIdToken(),null);

                            auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser user=auth.getCurrentUser();


                                        Toast.makeText(CreateAccountActivity.this, "Google Sign-in successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(CreateAccountActivity.this,SignInActivity.class));
                                    }else {
                                        Toast.makeText(CreateAccountActivity.this, "Google Sign-in Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }catch (ApiException e){
                            e.printStackTrace();
                        }
                    }
                }
            });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth=FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken=AccessToken.getCurrentAccessToken();
        if (accessToken!=null && accessToken.isExpired()==false){
            startActivity(new Intent(CreateAccountActivity.this,MainActivity.class));
            finish();
        }
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        startActivity(new Intent(CreateAccountActivity.this,MainActivity.class));
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(CreateAccountActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(CreateAccountActivity.this, "failed3", Toast.LENGTH_SHORT).show();
                    }
                });

        GoogleSignInOptions options=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient=GoogleSignIn.getClient(CreateAccountActivity.this,options);

    }
    public void signInGoogle(View view){
        Intent intent=googleSignInClient.getSignInIntent();
        activityResultLauncher.launch(intent);

    }
    public void signInFaceBook(View view){
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        System.out.println("2error");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void gotoMain(View v){
        startActivity(new Intent(this,MainActivity.class));
    }


}