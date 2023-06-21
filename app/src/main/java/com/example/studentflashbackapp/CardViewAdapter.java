package com.example.studentflashbackapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CardViewAdapter extends ArrayAdapter<CardView> {
    public CardViewAdapter(@NonNull Context context, ArrayList<CardView> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        CardView currentNumberPosition = getItem(position);

        TextView txtTopic = currentItemView.findViewById(R.id.textTopic);
        txtTopic.setText(currentNumberPosition.getCardTopic());

        // then according to the position of the view assign the desired TextView 1 for the same

        // then according to the position of the view assign the desired TextView 2 for the same
        TextView txtBack = currentItemView.findViewById(R.id.textNumberOfCards);
        txtBack.setText(currentNumberPosition.getNumberOfCards()+" cards");

        // then return the recyclable view
        return currentItemView;
    }
}
