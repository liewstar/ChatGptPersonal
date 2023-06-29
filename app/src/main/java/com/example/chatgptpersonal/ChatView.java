package com.example.chatgptpersonal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class ChatView {
    public RecyclerView recyclerView;
    public Button btnSend;
    public EditText edMessage;
    public ChatAdapter adapter;

    public List<ChatMessage> messages = new ArrayList<>();

    String token = "";
    public View Load(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View chatView = inflater.inflate(R.layout.activity_main,null);

        recyclerView = chatView.findViewById(R.id.sendMessageView);
        btnSend = chatView.findViewById(R.id.btnSend);
        edMessage = chatView.findViewById(R.id.edMessage);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(messages);
        btnSend.setOnClickListener(view -> {
            String userMessage = edMessage.getText().toString();
            edMessage.setText("");
            adapter.addMessage(new com.theokanning.openai.completion.chat.ChatMessage("user",userMessage));
            //request(messages);
        });
        recyclerView.setAdapter(adapter);

        return chatView;
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

                /*runOnUiThread(()->{
                    adapter.addMessage(new ChatMessage("system",text.toString()));
                });*/
            }catch (Exception e) {
                e.printStackTrace();
                request(messages);
            }

        }).start();
    }
}
