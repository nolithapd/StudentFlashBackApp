package com.example.studentflashbackapp;

import java.io.Serializable;

public class Card  implements Serializable
{
    //variables
    public int id;

    private String topic;

    private String front;

    private String back;


    // getter method for returning the ID
    public int getId() {
        return id;
    }

    // getter method for returning the topic
    public String getTopic() {
        return topic;
    }

    // getter method for returning the front
    public String getFront() {
        return front;
    }

    // getter method for returning the back
    public String getBack() {
        return back;
    }

    // setter method for returning the topic
    public void setTopic(String topic) {
        this.topic = topic;
    }

    // setter method for returning the front
    public void setFront(String front) {
        this.front = front;
    }

    // setter method for returning the
    public void setBack(String back) {
        this.back = back;
    }

    // create constructor to set the values for all the parameters of the each
    public Card(int id,String topic,String front, String back){
        this.id=id;
        this.topic=topic;
        this.front=front;
        this.back=back;
    }
}
