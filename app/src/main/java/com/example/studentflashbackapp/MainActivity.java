package com.example.studentflashbackapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    //private fields
    private DBHandler dbHandler;

    //variables for imageViews
    ImageView imgAdd;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing all our variable
        imgAdd=(ImageView)findViewById(R.id.imageView2);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler= new DBHandler(this);

        //initialising arraylist
        ArrayList<CardView> arrayListView = new ArrayList<CardView>();

        //Gets the selected topic and assign it to arraylist
        ArrayList<Card> arrayList = dbHandler.getAllCards();

        //grouping arraylist of cards
        Map<String, List<Card>> cardByTopic
                = arrayList.stream()
                .collect(Collectors.groupingBy(Card::getTopic));
        cardByTopic.entrySet()
                    .forEach(e -> arrayListView.add(new CardView(e.getKey() , e.getValue().size())));

        // Now create the instance of the NumebrsViewAdapter and pass the context and arrayList
        CardViewAdapter carsArrayAdapter = new CardViewAdapter(this, arrayListView);

        // create the instance of the ListView to set the numbersViewAdapter
        ListView cardsListView = findViewById(R.id.listView);

        // set the numbersViewAdapter for ListView
        cardsListView.setAdapter(carsArrayAdapter);

        //On click listener for listviewitem next button
        cardsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // declaring and initializing all our variable
                TextView txtTpC= (TextView)view.findViewById(R.id.textTopic);
                String tpc=txtTpC.getText().toString();

                // opening front screen
                Intent intent = new Intent(MainActivity.this, Front.class);
               intent.putExtra("extra", tpc);
                startActivity(intent);
            }
        });

        //On click listener for imageview add button
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.add_card_set, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                //popup window location
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                popupWindow.setTouchable(true);

                // declaring and initializing all our variable
                EditText edtCards=(EditText)popupView.findViewById(R.id.ectCardSet);
                Button btnAddSet= (Button)popupView.findViewById(R.id.btnAdd);
                Button btnCancelSet= (Button)popupView.findViewById(R.id.btnCancel);

                //On click listener for add set button
                btnAddSet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //setting text for card set
                        String cardSet = edtCards.getText().toString();
                        //converting cardset to small letters
                        cardSet= cardSet.toLowerCase();

                        //checks if textview is empty
                        if(cardSet.isEmpty()){
                            //showing error toast
                            Toast.makeText(MainActivity.this, "Please enter card name", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else{

                            //checks if card exists
                            boolean objectExists=dbHandler.hasObject(cardSet);
                            if(!objectExists){

                                //opening new card screen
                                Intent intent = new Intent(MainActivity.this, AddNewCard.class);
                                intent.putExtra("extra", cardSet);
                                startActivity(intent);
                            }
                            else{
                                //showing error toast
                                Toast.makeText(MainActivity.this, "Card already exist.Add new Card", Toast.LENGTH_SHORT).show();
                                edtCards.setText(" ");
                                return;
                            }
                        }

                    }
                });

                //On click listener for cancel button
                btnCancelSet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //dismiss window
                      popupWindow.dismiss();

                    }
                });
            }
        });


    }

}