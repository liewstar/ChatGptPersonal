package com.example.chatgptpersonal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatgptpersonal.Adapter.ChatAdapter;
import com.theokanning.openai.completion.chat.ChatCompletionChunk;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    public Button btnSend;
    public EditText edMessage;
    public ChatAdapter adapter;

    public List<com.theokanning.openai.completion.chat.ChatMessage> messages = new ArrayList<>();

    String token = "";


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

        adapter = new ChatAdapter(messages);
        btnSend.setOnClickListener(view -> {
            adapter.addMessage(new com.theokanning.openai.completion.chat.ChatMessage("user",edMessage.getText().toString()));
            request(messages);
        });
        recyclerView.setAdapter(adapter);

    }
    public void request(List<com.theokanning.openai.completion.chat.ChatMessage> messages) {
            new Thread(()->{
                StringBuilder text = new StringBuilder();
                try {
                    OpenAiService service = new OpenAiService(token);
                    ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                            .builder()
                            .model("gpt-3.5-turbo")
                            .messages(messages)
                            .n(1)
                            .maxTokens(50)
                            .logitBias(new HashMap<>())
                            .build();
                    List<ChatCompletionChunk> results = service.streamChatCompletion(chatCompletionRequest)
                            .doOnError(Throwable::printStackTrace)
                            .toList()
                            .blockingGet();
                    for(ChatCompletionChunk result:results) {
                        text.append(result.getChoices().get(0).getMessage().getContent());
                    }
                    runOnUiThread(()->{
                        adapter.addMessage(new ChatMessage("system",text.toString()));
                    });
                }catch (Exception e) {
                    e.printStackTrace();
                    request(messages);
                }

            }).start();
        }

}