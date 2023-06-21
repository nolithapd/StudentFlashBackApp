package com.example.studentflashbackapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class CardView {

    //variables
    public String cardTopic;

    public int numberOfCards;


    // getter method for returning the Topic
    public String getCardTopic() {
        return cardTopic;
    }

    // getter method for returning the number of cards
    public int getNumberOfCards() {
        return numberOfCards;
    }

    public CardView( String cardTopic, int numberOfCards){
        this.cardTopic=cardTopic;
        this.numberOfCards=numberOfCards;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.cardTopic);
        return hash;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CardView other = (CardView) obj;
        if (!Objects.equals(this.cardTopic, other.cardTopic)) {
            return false;
        }

        return true;
    }
}
