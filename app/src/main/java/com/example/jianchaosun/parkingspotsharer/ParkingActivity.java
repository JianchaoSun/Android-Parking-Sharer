package com.example.jianchaosun.parkingspotsharer;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class ParkingActivity extends AppCompatActivity {
    @BindView(R.id.showList)
    ListView showList;
    @BindView(R.id.imgView)
    ImageView imgV;
    private int choice=0;
    private List<obj> parkingList;
    AvaAdaptar adp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_parking_structure);
        choice= getIntent().getIntExtra("choice",0);
        loadParking();
    }
    private void loadParking(){
        final ListView lv = (ListView)findViewById(R.id.showList);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Parking");
        long cutoff = new Date().getTime() - TimeUnit.MILLISECONDS.convert(20, TimeUnit.MINUTES);
        Query oldBug = mDatabase.child("Old Structure").orderByChild("timeStamp").endAt(cutoff);
        oldBug.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot: snapshot.getChildren()) {
                    itemSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
            if(choice==0) {
                DatabaseReference ref = database.getReference("Parking/Old Structure");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        parkingList = new ArrayList<obj>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            obj o = ds.getValue(obj.class);
                            parkingList.add(o);
                        }
                        adp = new AvaAdaptar(ParkingActivity.this, R.layout.old_parking_structure, parkingList);
                        lv.setAdapter(adp);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }else if(choice == 1){
                DatabaseReference ref = database.getReference("Parking/New Structure");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        parkingList = new ArrayList<obj>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            obj o = ds.getValue(obj.class);
                            parkingList.add(o);
                        }
                        adp = new AvaAdaptar(ParkingActivity.this, R.layout.old_parking_structure, parkingList);
                        lv.setAdapter(adp);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        }else{
                DatabaseReference ref = database.getReference("Parking/Mountain Structure");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        parkingList = new ArrayList<obj>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            obj o = ds.getValue(obj.class);
                            parkingList.add(o);
                        }
                        adp = new AvaAdaptar(ParkingActivity.this, R.layout.old_parking_structure, parkingList);
                        lv.setAdapter(adp);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        }


    }



}
