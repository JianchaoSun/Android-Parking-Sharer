package com.example.jianchaosun.parkingspotsharer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private int choice = -1;
    private List<parkingLot> Parking;
    private String uri = null;
    private String bitmap = null;
    private static int hour;
    private static int min;


    @BindView(R.id.add)
    Button addb;
    @BindView(R.id.check)
    ListView lv;
    @BindView(R.id.chose)
    Spinner sp;
  //  @BindView(R.id.time)
  //  TimePicker tp;
    @BindView(R.id.write)
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* Parking = new ArrayList<parkingLot>();
        newParkingLot = new parkingLot();
        oldParkingLot = new parkingLot();
        mountainParkingLot = new parkingLot();
        Parking.add(newParkingLot);
        Parking.add(oldParkingLot);
        Parking.add(mountainParkingLot); */
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.parking, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sp = (Spinner)findViewById(R.id.chose);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
               if(position == 0){
                    choice = 0;
                }else if(position == 1){
                    choice = 1;
                }else{
                    choice = 2;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        ListView lv = (ListView) findViewById(R.id.check);
        lv.setAdapter(adapter);
        ButterKnife.bind(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent next = new Intent(MainActivity.this, ParkingActivity.class);
                if (position == 0) {
                    int x = 0;
                    next.putExtra("choice", x);
                    startActivity(next);
                } else if (position == 1) {
                    int x = 1;
                    next.putExtra("choice", x);
                    startActivity(next);
                } else {
                    int x = 2;
                    next.putExtra("choice", x);
                    startActivity(next);
                }
            }
        });
   /*   DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Parking");
        long cutoff = new Date().getTime() - TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES);
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
      /*  FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ttlRef = database.getReference("Parking/Old Structure");


       ttlRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    obj o = ds.getValue(obj.class);
                    long cutoff = o.getCutoff();
                    long time = new Date().getTime();
                    addb.setText(String.valueOf(cutoff));

                    Query oldItems = ttlRef.child("Parking").orderByChild("cutoff").endAt(time);
                    //Query oldItems = ttlRef.child("Parking").orderByChild("timeStamp").endAt(cutoff);
                    oldItems.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                                itemSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            throw databaseError.toException();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       /*     long cutoff = new Date().getTime() - TimeUnit.MILLISECONDS.convert(2, TimeUnit.MINUTES);
            addb.setText(String.valueOf(cutoff));
            Query oldItems = ttlRef.child("Parking").orderByChild("timeStamp").endAt(cutoff);
            oldItems.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        itemSnapshot.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    throw databaseError.toException();
                }
            }); */

        }

    @OnClick(R.id.add)
    public void addButtonClicked(){

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if(choice == -1){
            AlertDialog dial = new AlertDialog.Builder(this).setTitle("missing field")
                    .setMessage("You need to select a Parking Lot").setNeutralButton
                            ("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();

        }else if(choice == 0){


            obj o = new obj(hour,min,et.getText().toString(),"Old Structure",uri,bitmap);
            DatabaseReference myRef = database.getReference("Parking/Old Structure/" +
                    o.getTimeStamp());
            myRef.setValue(o);
        }else if(choice == 1){


            obj o = new obj(hour,min,et.getText().toString(),"New Structure",uri,bitmap);
            DatabaseReference myRef = database.getReference("Parking/New Structure/" +
                    o.getTimeStamp());
            myRef.setValue(o);

        }else if(choice == 2){


            obj o = new obj(hour,min,et.getText().toString(),"Mountain Structure",uri,bitmap);
            DatabaseReference myRef = database.getReference("Parking/Mountain Structure/" +
                    o.getTimeStamp());
            myRef.setValue(o);

        }

    }
    @OnClick(R.id.addpic)
    public void addButtonClick(){
        //chooseImage();
       // dispatchTakePictureIntent();
        String[] way = {"Take a new picture", "Choose one from gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose one");
        builder.setItems(way, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which ==0){
                    dispatchTakePictureIntent();
                }else{
                    chooseImage();
                }
            }
        });
        builder.show();
    }

    private static final int PICK_IMAGE_REQUEST = 2;

    static final int REQUEST_IMAGE_CAPTURE = 1;



    //require camera
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
        onActivityResult(1,1,takePictureIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bitmap = BitMapToString(imageBitmap);
        }
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            Uri filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                uri  = filePath.toString();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        onActivityResult(2, 2, intent);
    }
    /*
    protected void onActivityResult1(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            Uri filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                uri  = filePath.toString();
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }*/
   public String BitMapToString(Bitmap bitmap){
       ByteArrayOutputStream baos=new  ByteArrayOutputStream();
       bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
       byte [] b=baos.toByteArray();
       String temp=Base64.encodeToString(b, Base64.DEFAULT);
       return temp;
   }
    //show time picker
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            min = minute;
        }
    }







}
