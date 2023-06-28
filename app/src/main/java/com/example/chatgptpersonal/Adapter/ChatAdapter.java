package com.example.chatgptpersonal.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatgptpersonal.R;
import com.theokanning.openai.completion.chat.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<com.theokanning.openai.completion.chat.ChatMessage> messages;

    public ChatAdapter(List<com.theokanning.openai.completion.chat.ChatMessage> messages) {
        this.messages = messages;
    }
    public ChatAdapter() {

    }

    public void addMessage(com.theokanning.openai.completion.chat.ChatMessage message) {
        messages.add(message);
        notifyItemInserted(messages.size()-1);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        Log.d("Adapter", "Message: " + message.getContent() + ", Sender: " + message.getRole());
        holder.tvMessage.setText(message.getContent());
        holder.tvSender.setText(message.getRole());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSender;
        public TextView tvMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSender = itemView.findViewById(R.id.sender);
            tvMessage = itemView.findViewById(R.id.message);
        }

    }
}
