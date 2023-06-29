package com.example.chatgptpersonal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MasterActivity extends AppCompatActivity {
    private FrameLayout frameLayout;

    private BottomNavigationView bottomNavigationView;

    public static MasterActivity Instance;

    public ChatView chatView;



    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Instance = this;
        setContentView(R.layout.activity_master);

        frameLayout = findViewById(R.id.frameLayout);

        chatView = new ChatView();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case 1000008:
                    loadChatActivity();
                    return true;

            }
            return false;
        });
        frameLayout.addView(chatView.Load(this));
    }

    public void loadChatActivity() {
        frameLayout.removeAllViews();
        frameLayout.addView(chatView.Load(this));
    }
}