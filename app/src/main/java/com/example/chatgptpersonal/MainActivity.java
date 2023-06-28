package com.example.chatgptpersonal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.chatgptpersonal.Adapter.ChatAdapter;
import com.example.chatgptpersonal.Model.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public Button btnSend;
    public EditText edMessage;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.sendMessageView);
        btnSend = findViewById(R.id.btnSend);
        edMessage = findViewById(R.id.edMessage);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("hello","me"));
        messages.add(new ChatMessage("i am rob","rob"));
        messages.add(new ChatMessage("i am me","me"));
        messages.add(new ChatMessage("i am rob two","rob2"));
        ChatAdapter adapter = new ChatAdapter(messages);
        recyclerView.setAdapter(adapter);
    }
}