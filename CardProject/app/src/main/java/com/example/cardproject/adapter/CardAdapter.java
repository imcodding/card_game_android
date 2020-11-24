package com.example.cardproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardproject.interfaces.OnItemClickListener;
import com.example.cardproject.R;
import com.example.cardproject.model.Card;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {

    ArrayList<Card> cards;
    OnItemClickListener listener;


    public CardAdapter(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, null);
        CardHolder holder = new CardHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        Card card = cards.get(position);
        if(card.isFront()) {
            holder.ivCard.setImageResource(card.getDefaultImgId());
            card.setFront(false);
        } else {
            holder.ivCard.setImageResource(card.getBackImgId());
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class CardHolder extends RecyclerView.ViewHolder {
        ImageView ivCard;

        public CardHolder(@NonNull View itemView) {
            super(itemView);

            ivCard = itemView.findViewById(R.id.iv_card);
            ivCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        listener.onItemClick(itemView, getAdapterPosition());
                    }
                }
            });
        }
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void reset() {
        for(Card card: cards) {
            card.setFront(false);
        }
    }

    public void start() {
        for(Card card: cards) {
            card.setFront(true);
            card.setDefaultImgId(R.drawable.card_default);
        }
    }
}
