package com.example.broadcastapp;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.provider.Telephony;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.broadcastapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> mData=new ArrayList<String>();
    private ArrayAdapter<String> mAdapter;


    private MySMSReceiver mReceiver;

    private final int RC_SMS_RECEIVED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mData);
        binding.listview.setAdapter(mAdapter);

        mReceiver = new MySMSReceiver();

        mReceiver.setOnSmsReceived(new MySMSReceiver.OnSmsReceived() {
            @Override
            public void onReceived(String p,String m) {
                mData.add(p+" : "+m);
                mAdapter.notifyDataSetChanged();
            }
        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS}, RC_SMS_RECEIVED);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mReceiver, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == RC_SMS_RECEIVED) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission granted
            } else {
                new AlertDialog.Builder(this).setTitle("Permission!")
                        .setMessage("RECEIVE_SMS permission is required to receive SMS.\nPress OK to grant the permission.")
                        .setPositiveButton("OK", ((dialog, which) -> ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.RECEIVE_SMS}, RC_SMS_RECEIVED)))
                        .setNegativeButton("Cancel", null)
                        .create().show();
            }
        }
    }
}
