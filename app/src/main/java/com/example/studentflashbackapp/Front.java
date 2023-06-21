package com.example.studentflashbackapp;
//imports
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;


import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class Front extends AppCompatActivity {
    //private fields
    private DBHandler dbHandler;
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private View mCardFrontLayout;
    private View mCardBackLayout;

   //Object of card class
    Card cardOne;

    //variables for textViews, buttons and imageViews
    TextView tvBackCard, tvNumberDisplay, tvFrontCard, tvBackToMain;
    Button btnDeleteCard, btnUpdateCard, btnAddCard;
    ImageView imgPrev, imgNext;


    //created arraylist to store card names
    ArrayList<Card> cardArrayList;

    //variables
    int positionOfCard=0;
    String cardName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.front_view);

        //getting cardName from previous screen
        cardName = getIntent().getStringExtra("extra");

        // initializing all our variables.
        tvFrontCard=(TextView)findViewById(R.id.tvCdFnt);
        tvBackCard=(TextView)findViewById(R.id.tvCdBck);
        tvNumberDisplay=(TextView)findViewById(R.id.txtNums);
        tvBackToMain=(TextView)findViewById(R.id.lnkGoBack);
        btnDeleteCard=(Button)findViewById(R.id.btnDltFnt);
        imgPrev=(ImageView)findViewById(R.id.imgvPrev);
        imgNext=(ImageView)findViewById(R.id.imgvNext);
        btnAddCard=(Button)findViewById(R.id.btnAddInFnt);
        btnUpdateCard=(Button)findViewById(R.id.btnEditFCd);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler= new DBHandler(this);

        //Gets the selected topic and assign it to arraylist
        cardArrayList=dbHandler.getSelectedTopic(cardName);

        // size of arraylist
        int totalArrayList=cardArrayList.size();

        //initializing card object
        cardOne = cardArrayList.get(positionOfCard);

        //On click listener for imageview next button
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checks the card position
                if(positionOfCard<cardArrayList.size()-1){
                    //increments position of the arraylist
                    positionOfCard++;

                    //Assigning card  current position
                    cardOne=cardArrayList.get(positionOfCard);

                    //Setting text of the front
                    tvFrontCard.setText(cardOne.getFront());
                    tvNumberDisplay.setText((positionOfCard+1)+" of "+totalArrayList);
                }

            }
        });

        //On click listener for imageview next button
        imgPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checks the card position
                if(positionOfCard > 0){

                    //decrements position of the arraylist
                    positionOfCard--;

                    //Assigning card  current position
                    cardOne=cardArrayList.get(positionOfCard);

                    //Setting text of the front
                    tvFrontCard.setText(cardOne.getFront());
                    tvNumberDisplay.setText((positionOfCard+1)+" of "+totalArrayList);

                }
            }
        });

        //Setting text of the front
        tvFrontCard.setText(cardOne.getFront());
        tvNumberDisplay.setText((positionOfCard+1)+" of "+totalArrayList);

        //Calling methods for flipping card
        findViews();
        loadAnimations();
        changeCameraDistance();

        //On click listener for add card button
        btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // opening add new card screen
                Intent intent = new Intent(Front.this, AddNewCard.class);
                intent.putExtra("extra",  cardName);
                startActivity(intent);

            }
        });

        //On click listener for update card button
        btnUpdateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // opening edit card screen
                Intent intent = new Intent(Front.this, EditCard.class);
                intent.putExtra("sampleObject", cardOne);
                startActivity(intent);
            }
        });

        //On click listener for delete card button
        btnDeleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create the object of AlertDialog Builder class
                AlertDialog alertDialog = new AlertDialog.Builder(Front.this).create();

                // Set the title show for the Alert time
                alertDialog.setTitle("Alert");

                // Set the message show for the Alert time
                alertDialog.setMessage("Are you sure you want delete card?");

                // Set the positive button with OK name Lambda OnClickListener method is use of DialogInterface interface.
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                // calling a method to delete our card
                                dbHandler.deleteCard(cardOne.getId());

                                // opening main activity
                                Intent intent = new Intent(Front.this, MainActivity.class);
                                startActivity(intent);
                            }
                        } );

                // Set the Negative button with Cancel name Lambda OnClickListener method is use of DialogInterface interface.
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                // If user click cancel then dialog box is canceled.
                                dialog.dismiss();

                                // opening main activity
                                Intent intent = new Intent(Front.this, MainActivity.class);
                                startActivity(intent);
                            }
                        } );
                // Show the Alert Dialog box
                alertDialog.show();

            }
        });

        //On click listener for back textView
        tvBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // opening main activity
                Intent intent = new Intent(Front.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void changeCameraDistance() {

       // modify the camera scale
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        if (mCardFrontLayout != null) {
            mCardFrontLayout.setCameraDistance(scale);
        }
        if (mCardBackLayout != null) {
            mCardBackLayout.setCameraDistance(scale);
        }
    }

    private void loadAnimations() {
        // set the animations
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.back_animator);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.front_animator);
    }

    private void findViews() {

        // initializing all our variables.
        mCardBackLayout = findViewById(R.id.lytBck);
        mCardFrontLayout = findViewById(R.id.lytFront);
    }

    public void flipCard(View view) {
        //set the event listener
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
            tvBackCard.setText(cardOne.getBack());
        } else {
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }
}

