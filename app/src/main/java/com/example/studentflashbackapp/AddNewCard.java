package com.example.studentflashbackapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddNewCard extends AppCompatActivity {
    //private fields
    private DBHandler dbHandler;

    //variables for editTexts, buttons
    TextView tv1;
    EditText edtFront,edtBack;
    Button btnAdd, btnCan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_card);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DBHandler(AddNewCard.this);

        //getting cardName from previous screen
        String cardName = getIntent().getStringExtra("extra");

        // initializing all our variables.
        tv1=(TextView)findViewById(R.id.tvCard);
        edtFront=(EditText)findViewById(R.id.txtFrontCard);
        edtBack=(EditText)findViewById(R.id.txtBackCard);
        btnAdd=(Button)findViewById(R.id.btnAddcard);
        btnCan=(Button)findViewById(R.id.btnCancel);

        //setting text for edit Texts
        tv1.setText(cardName);

        //On click listener for add button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get data from all edit text fields.
                String frontName=edtFront.getText().toString();
                String backName=edtBack.getText().toString();

                // validating if the text fields are empty or not.
              if(frontName.isEmpty()||backName.isEmpty()){
                  Toast.makeText(getApplicationContext(), "Enter both Cards", Toast.LENGTH_SHORT).show();
                  return;
              }
                else {
                  //calling a method to add new card to sqlite data and pass all our values to it.
                  dbHandler.addNewCard(cardName, frontName, backName);

                  // displaying a toast message that our card has been add.
                  Toast.makeText(AddNewCard.this, "Card has been added.", Toast.LENGTH_SHORT).show();

                  //clear the edit texts
                  edtFront.setText("");
                  edtBack.setText("");
              }

            }
        });

        btnCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewCard.this, MainActivity.class);
                startActivity(intent);
            }

    });

            }

}
