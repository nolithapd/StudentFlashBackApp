package com.example.studentflashbackapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditCard extends AppCompatActivity {

    //Object of card class
    Card card;

    //private fields
    private DBHandler dbHandler;

    //variables for editTexts, buttons
    EditText edtTopic, edtFront, edtBack;
    Button btnEdit, btnCancel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_card);


        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler= new DBHandler(this);

        //new intent for passing object
        Intent i = getIntent();
        card = (Card) i.getSerializableExtra("sampleObject");

        // initializing all our variables.
        edtTopic=(EditText)findViewById(R.id.edtTopicEdit);
        edtFront=(EditText)findViewById(R.id.edtFrontEdit);
        edtBack=(EditText)findViewById(R.id.edtBackEdit);
        btnEdit=(Button) findViewById(R.id.btnUpt);
        btnCancel=(Button) findViewById(R.id.btnClose);

        //setting text for edit Texts
        edtTopic.setText(card.getTopic());
        edtFront.setText(card.getFront());
        edtBack.setText(card.getBack());

        //On click listener for edit button
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get data from all edit text fields.
                String tpc=edtTopic.getText().toString();
                String fnt=edtFront.getText().toString();
                String bck=edtBack.getText().toString();

                // validating if the text fields are empty or not.
                if(tpc.isEmpty()||fnt.isEmpty()||bck.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter all values", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    //calling a method to update card to sqlite data and pass all our values to it.
                    dbHandler.updateCard(card.getId(),tpc,fnt,bck);
                    Toast.makeText(getApplicationContext(), "Column with ID is : "+card.getId()+" Updated", Toast.LENGTH_SHORT).show();

                    //opening main activity
                    Intent intent = new Intent(EditCard.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        //On click listener for cancel button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // opening main activity
                Intent intent = new Intent(EditCard.this, MainActivity.class);
                startActivity(intent);
            }

        });

    }
}
