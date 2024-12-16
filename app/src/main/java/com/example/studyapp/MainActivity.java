package com.example.studyapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowInsets;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView goToSearch,account;
    BottomNavigationView bottomNavigationView;
    QuestionFragment questionFragment;
    CommunityFragment communityFragment;
    AccountFragment accountFragment;

    PaymentFragment paymentFragment;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        goToSearch=findViewById(R.id.go_to_search);

        questionFragment = new QuestionFragment();
        accountFragment = new AccountFragment();
        communityFragment= new CommunityFragment();
        paymentFragment = new PaymentFragment();

        bottomNavigationView = findViewById(R.id.top_navigation_bar);
        bottomNavigationView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (insets.isVisible(WindowInsets.Type.navigationBars())) {
                        // Adjust margin if the device has on-screen navigation buttons
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bottomNavigationView.getLayoutParams();
                        params.setMargins(0, 0, 0, -3); // Adjust the bottom margin
                        bottomNavigationView.setLayoutParams(params);
                    }
                }
                return insets;
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if(menuItem.getItemId()==R.id.test){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,questionFragment).commit();
                }
                if(menuItem.getItemId()==R.id.community){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,communityFragment).commit();
                }
                if(menuItem.getItemId()==R.id.account){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,accountFragment).commit();
                }

                if(menuItem.getItemId()==R.id.payment){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,paymentFragment).commit();
                }
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.test);

        goToSearch.setOnClickListener(v -> {
            startActivity(new Intent(this,SearchActivity.class));
        });

    }

    private boolean hasNavigationBar() {
        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        return !hasMenuKey && !hasBackKey;
    }

    public void botClicked(View view) {
        startActivity(new Intent(this, AIActivity.class));
    }
}