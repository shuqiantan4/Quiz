package com.example.quiz20;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BmAdapter extends RecyclerView.Adapter<BmAdapter.Viewholder>{

    private List<QuestionModel> list;

    public BmAdapter(List<QuestionModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bm_item, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
      holder.setData(list.get(position).getQuestion(), list.get(position).getAns(),position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{
        private TextView question, answer;
        private ImageButton delete;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            question = itemView.findViewById(R.id.question);
            answer = itemView.findViewById(R.id.answer);
            delete = itemView.findViewById(R.id.delete_btn);

        }

        private void setData(String question, String answer, final int position){
            this.question.setText(question);
            this.answer.setText(answer);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  list.remove(position);
                  notifyItemRemoved(position);
                }
            });

        }
    }
}
