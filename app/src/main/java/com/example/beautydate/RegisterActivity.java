package com.example.beautydate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    // database connection
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    //ArrayList<CosmeticDescription> descriptions = new ArrayList<>();
    CosmeticDescription tempCd;

    int userID = 1;
    int serialNumber = 1;

    String itemName;
    String expDate;
    String openDate;

    String calculated;

    Button nextBT;
    EditText itemNameET;
    EditText expDateET;
    EditText openDateET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tempCd = new CosmeticDescription();

        nextBT = findViewById(R.id.nextBT);
        itemNameET = findViewById(R.id.nameET);
        expDateET = findViewById(R.id.expET);
        openDateET = findViewById(R.id.openET);

        nextBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemName = itemNameET.getText().toString();
                expDate = (expDateET.getText().toString());
                openDate = (openDateET.getText().toString());

                Log.d("name:", itemName);
                Log.d("expMonth:", (expDate));
                Log.d("openDate:", (openDate));

                // getting cosmeticDescription
                databaseReference = firebaseDatabase.getReference("cometicDescription");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //descriptions.clear();
                        for (DataSnapshot descSnap : dataSnapshot.getChildren()) {
                            String descString = descSnap.getValue().toString();
                            CosmeticDescription cd = new CosmeticDescription(descString);

                            Log.d("checkDescription", descString);
                            cd.setDescription();

                            if (cd.getItemName().equals(itemName)) {
                                String category = cd.getItemCategory();

                                switch (category) {
                                    case "마스카라":
                                    case "자외선차단제":
                                        tempCd.setLifePeriodOfItem(6);
                                        break;

                                    case "에센스":
                                        tempCd.setLifePeriodOfItem(8);
                                        break;

                                    case "쿠션":
                                        tempCd.setLifePeriodOfItem(36);
                                        break;

                                    default:
                                        tempCd.setLifePeriodOfItem(12);
                                }
                            }
                            //descriptions.add(tempCd);
                        }

                        Log.d("foundLife", Integer.toString(tempCd.getLifePeriodOfItem()));
                        databaseReference = firebaseDatabase.getReference();
                        databaseReference.child("users").child("userID_"+userID).child("cosmetics").child("SN_"+serialNumber).child("life").setValue(tempCd.getLifePeriodOfItem());

                        /**** after Upload ****/
                        Intent intent = new Intent(getBaseContext(), RegisterConfirmActivity.class);
                        intent.putExtra("itemName", itemName);

                        // date transformation
                        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");

                        Date expDateFormat = null;
                        try {
                            expDateFormat = transFormat.parse(expDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Date openDateFormat = null;
                        try {
                            openDateFormat = transFormat.parse(openDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        // date setting and calculation
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                        Calendar expCal = Calendar.getInstance();
                        expCal.setTime(expDateFormat);
                        expDate = df.format(expCal.getTime());
                        intent.putExtra("expDate", expDate);

                        Calendar openCal = Calendar.getInstance();
                        openCal.setTime(openDateFormat);
                        openDate = df.format(openCal.getTime());
                        intent.putExtra("openDate", openDate);

                        openCal.add(Calendar.MONTH, tempCd.getLifePeriodOfItem());

                        // compare two days
                        if (expDateFormat.compareTo(openCal.getTime()) < 0) {
                            calculated = expDate;
                        }
                        else calculated = df.format(openCal.getTime());

                        intent.putExtra("calculated", calculated);
                        databaseReference.child("users").child("userID_"+userID).child("cosmetics").child("SN_"+serialNumber).child("calculated").setValue(calculated);
                        serialNumber++;

                        Log.d("afterEXP@@@: ", df.format(expCal.getTime()));
                        Log.d("afterOPEN@@@: ", df.format(openCal.getTime()));

                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                // date transformation
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");

                Date expDateFormat = null;
                try {
                    expDateFormat = transFormat.parse(expDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Date openDateFormat = null;
                try {
                    openDateFormat = transFormat.parse(openDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // date setting and calculation
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                Calendar expCal = Calendar.getInstance();
                expCal.setTime(expDateFormat);
                String formatExpDate = df.format(expCal.getTime());

                Calendar openCal = Calendar.getInstance();
                openCal.setTime(openDateFormat);
                String formatOpenDate = df.format(openCal.getTime());

                //openCal.add(Calendar.MONTH, avail);
                //calculated = df.format(openCal.getTime());

                Log.d("afterEXP: ", df.format(expCal.getTime()));
                Log.d("afterOPEN: ", df.format(openCal.getTime()));

                // data upload
                databaseReference = firebaseDatabase.getReference();
                databaseReference.child("users").child("userID_"+userID).child("userID").setValue(userID);
                databaseReference.child("users").child("userID_"+userID).child("cosmetics").child("SN_"+serialNumber).child("SN").setValue(serialNumber);
                databaseReference.child("users").child("userID_"+userID).child("cosmetics").child("SN_"+serialNumber).child("name").setValue(itemName);
                databaseReference.child("users").child("userID_"+userID).child("cosmetics").child("SN_"+serialNumber).child("expDate").setValue(formatExpDate);
                databaseReference.child("users").child("userID_"+userID).child("cosmetics").child("SN_"+serialNumber).child("openDate").setValue(formatOpenDate);
                Log.d("dataUploaded", "00");
            }
        });
    }
}
