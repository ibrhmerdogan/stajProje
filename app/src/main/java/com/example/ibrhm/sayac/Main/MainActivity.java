package com.example.ibrhm.sayac.Main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibrhm.sayac.Data.CallStateDB;
import com.example.ibrhm.sayac.Data.DbOperations.LocationDBOperation;
import com.example.ibrhm.sayac.Data.DbOperations.SmsDBOperations;
import com.example.ibrhm.sayac.Data.LocationDB;
import com.example.ibrhm.sayac.Data.SmsStateDB;
import com.example.ibrhm.sayac.R;
import com.example.ibrhm.sayac.services.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS =1905 ;
    Button start;
    Button stop;
    Context context;
    TextView textView;
    Button Open, Close;
    Button display, display1, display2;
    CallStateDB database;
    SmsStateDB smsStateDB;
    LocationDB locationDB;
    SmsDBOperations smsDBOperations;
    LocationDBOperation locationDBOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        this.start = (Button) findViewById(R.id.button);
        this.stop = (Button) findViewById(R.id.button2);
        display = (Button) findViewById(R.id.button3);
        display1 = (Button) findViewById(R.id.button4);
        display2 = (Button) findViewById(R.id.button5);
        textView = (TextView) findViewById(R.id.textView2);
        this.start.setOnClickListener(new Start());
        this.stop.setOnClickListener(new Close());
        smsStateDB = new SmsStateDB(this);
        database = new CallStateDB(this);
        locationDB = new LocationDB(this);
        display.setOnClickListener(new Display());
        display1.setOnClickListener(new display1());
        display2.setOnClickListener(new display2());


        if (!checkAndRequestPermissions()) {
            return;
        }

    }


    class Start implements View.OnClickListener {
        Start() {
        }

        public void onClick(View v) {
            try {
                MainActivity.this.startService(new Intent(MainActivity.this.getApplicationContext(), LocationServices.class));
            } catch (Exception e) {
                Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show();
            }
        }
    }
    class Close implements View.OnClickListener {
        Close() {
        }

        public void onClick(View v) {
            MainActivity.this.stopService(new Intent(MainActivity.this.getApplicationContext(), LocationServices.class));
        }
    }


    class Display implements View.OnClickListener {
        Display() {
        }

        StringBuilder builder = new StringBuilder();

        public void onClick(View v) {
            try {
                SQLiteDatabase db = smsStateDB.getReadableDatabase();
                Cursor cursor = db.query("informationDB", new String[]{"smsID", "address"}, null, null, null, null, null);


                StringBuilder builder = new StringBuilder();


                while (cursor.moveToNext()) {

                    int id = cursor.getInt(cursor.getColumnIndex("smsID"));
                    String ad = cursor.getString((cursor.getColumnIndex("address")));
                    builder.append(id).append(" Adı: ");
                    builder.append(ad).append(" Soyadı: \n");
                    textView.setText(builder);
                }
            } catch (Exception e) {
                Toast.makeText(context, "diplayerror" + e, Toast.LENGTH_LONG).show();
            }
        }


    }

    class display1 implements View.OnClickListener {
        display1() {
        }

        StringBuilder builder = new StringBuilder();

        public void onClick(View v) {
            try {
                textView.setText("");
                SQLiteDatabase db = database.getReadableDatabase();
                Cursor cursor = db.query("informationDB", new String[]{"pID", "phoneNumber"}, null, null, null, null, null);


                StringBuilder builder = new StringBuilder();


                while (cursor.moveToNext()) {

                    int id = cursor.getInt(cursor.getColumnIndex("pID"));
                    String ad = cursor.getString((cursor.getColumnIndex("phoneNumber")));
                    builder.append(id).append(" Adı: ");
                    builder.append(ad).append(" Soyadı: \n");
                    textView.setText(builder);
                }
            } catch (Exception e) {
                Toast.makeText(context, "diplayerror" + e, Toast.LENGTH_LONG).show();
            }
        }


    }

    class display2 implements View.OnClickListener {
        display2() {
        }

        StringBuilder builder = new StringBuilder();

        public void onClick(View v) {
            try {
                textView.setText("");
                SQLiteDatabase db = locationDB.getReadableDatabase();
                Cursor cursor = db.query("informationDB", new String[]{"id", "date"}, null, null, null, null, null);


                StringBuilder builder = new StringBuilder();


                while (cursor.moveToNext()) {

                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String ad = cursor.getString((cursor.getColumnIndex("date")));
                    builder.append(id).append(" Adı: ");
                    builder.append(ad).append(" Soyadı: \n");
                    textView.setText(builder);
                }
            } catch (Exception e) {
                Toast.makeText(context, "diplayerror" + e, Toast.LENGTH_LONG).show();
            }
        }


    }
    private boolean checkAndRequestPermissions() {
        int permissionINTERNET = ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET);
        int permissionACCESS_NETWORK_STATE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_NETWORK_STATE);
        int permissionACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionCAMERA = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        int permissionACCESS_LOCATION_EXTRA_COMMANDS = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS);
        int permissionACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionWRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionACCESS_WIFI_STATE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_WIFI_STATE);
        int permissionCHANGE_WIFI_STATE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CHANGE_WIFI_STATE);
        int permissionREAD_PHONE_STATE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);
        int permissionRECEIVE_BOOT_COMPLETED = ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_BOOT_COMPLETED);
        int permissionBLUETOOTH_ADMIN = ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_ADMIN);
        int permissionBLUETOOTH = ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionINTERNET != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.INTERNET);
        }
        if (permissionACCESS_NETWORK_STATE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (permissionACCESS_FINE_LOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }

        if (permissionACCESS_COARSE_LOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (permissionINTERNET != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.INTERNET);
        }
        if (permissionACCESS_LOCATION_EXTRA_COMMANDS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS);
        }
        if (permissionWRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionACCESS_WIFI_STATE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_WIFI_STATE);
        }
        if (permissionCHANGE_WIFI_STATE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CHANGE_WIFI_STATE);
        }
        if (permissionREAD_PHONE_STATE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (permissionRECEIVE_BOOT_COMPLETED != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_BOOT_COMPLETED);
        }
        if (permissionBLUETOOTH_ADMIN != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.BLUETOOTH_ADMIN);
        }
        if (permissionBLUETOOTH != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.BLUETOOTH);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

}