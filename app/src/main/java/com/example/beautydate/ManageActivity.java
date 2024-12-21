package com.example.beautydate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageActivity extends AppCompatActivity {
    // database connection
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    ListView collectionLV;
    private ArrayAdapter<String> adapter;

    public ArrayList<MyItem> MyItemCollection = new ArrayList<>();

    int userID = 1;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        collectionLV = findViewById(R.id.collectionLV);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        collectionLV.setAdapter(adapter);

        // ValueEventLIstener
        databaseReference = firebaseDatabase.getReference("users").child("userID_"+userID).child("cosmetics");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                MyItemCollection.clear();
                for (DataSnapshot MyItemData : dataSnapshot.getChildren()) {
                    String mid = MyItemData.getValue().toString();
                    MyItem tempItem = new MyItem(mid);

                    Log.d("checkMyItemData", mid);
                    tempItem.setItemInfo();
                    MyItemCollection.add(tempItem);
                    adapter.add(tempItem.getItemName()+" , [만료] : "+tempItem.getCalculated()+" , [알림] : "+tempItem.getAlarmState());
                }
                for (MyItem temp : MyItemCollection) {
                    Log.d("%%%", temp.itemName);
                }

                adapter.notifyDataSetChanged();
                collectionLV.setSelection(adapter.getCount()-1);
                count = adapter.getCount()+1;
                TextView countTV = findViewById(R.id.countTV);
                countTV.setText("등록된 화장품 수: " + (count-1));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
