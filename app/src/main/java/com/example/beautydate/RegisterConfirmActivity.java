package com.example.beautydate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterConfirmActivity extends AppCompatActivity {
    // database connection
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    ArrayList<String> recommendList = new ArrayList<>();

    TextView itemNameTV;
    TextView expDateTV;
    TextView openDateTV;
    TextView calculatedTV;
    TextView alarmStateTV;
    TextView recommendTV;

    String itemName;
    String expDate;
    String openDate;
    String calculated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_confirm);

        Intent intent = getIntent();

        itemName = intent.getStringExtra("itemName");
        expDate = (intent.getStringExtra("expDate"));
        openDate = (intent.getStringExtra("openDate"));
        calculated = (intent.getStringExtra("calculated"));

        Log.d("name:", itemName);
        Log.d("expDate:", String.valueOf(expDate));
        Log.d("openDate:", String.valueOf(openDate));

        itemNameTV = findViewById(R.id.itemNameTV);
        expDateTV = findViewById(R.id.expDateTV);
        openDateTV = findViewById(R.id.openDateTV);
        calculatedTV = findViewById(R.id.calculatedTV);
        alarmStateTV = findViewById(R.id.alarmStateTV);
        recommendTV = findViewById(R.id.recommendTV);

        itemNameTV.setText(itemName);
        expDateTV.setText((expDate));
        openDateTV.setText((openDate));
        calculatedTV.setText((calculated));
        alarmStateTV.setText("켬");

        // ValueEventLIstener
        databaseReference = firebaseDatabase.getReference("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapData : dataSnapshot.getChildren()) {
                    String sd = snapData.getValue().toString();

                    if (sd.contains(itemName)) {
                        String tempSt = sd;
                        String cutSt;

                        Log.d("snapData", sd);

                        while (tempSt.contains("name")) {
                            cutSt = tempSt.substring(tempSt.indexOf("name") + 5, tempSt.indexOf(", SN="));
                            recommendList.add(cutSt);
                            tempSt = tempSt.substring(tempSt.indexOf(", SN=") + 2);
                        }
                    }
                }

                for (String st : recommendList) {
                    Log.d("RRRRRR", st);
                }

                int maxScore = 0;
                int maxIndex = -1;

                for (int i = 0; i < recommendList.size(); i++) {
                    int score = 0;

                    for (int j = i; j < recommendList.size() - i; j++) {
                        if (recommendList.get(i).equals(recommendList.get(j))) {
                            score++;
                        }
                    }
                    if (score >= maxScore) {
                        maxScore = score;
                        maxIndex = i;
                    }
                }
                Log.d("MAX_RESULT", recommendList.get(maxIndex));
                recommendTV.setText("\""+recommendList.get(maxIndex)+"\"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
