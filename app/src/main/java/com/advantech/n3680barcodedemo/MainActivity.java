package com.advantech.n3680barcodedemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String TAG = "BarCodeTest";
    private static final String SERVICE_PACKAGE_NAME = "com.advantech.n3680barcode";
    private static final String SERVICE_CLASS_NAME = "com.advantech.n3680barcode.RunN3680BarcodeService";
    private static final String ACTION_START_SERVICE = "com.advantech.n3680barcode.START_SERVICE";
    private static final String ACTION_TRANSFER_DATA = "com.advantech.n3680barcode.TRANSFER_DATA";
    private static final String ACTION_TURNON_BEEP = "com.advantech.n3680barcode.TURNON_BEEP";
    private static final String ACTION_TURNOFF_BEEP = "com.advantech.n3680barcode.TURNOFF_BEEP";

    private TextView textView;

    BarCodeDataBroadcastReceiver barCodeDataBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_TRANSFER_DATA);
        barCodeDataBroadcastReceiver = new BarCodeDataBroadcastReceiver();
        registerReceiver(barCodeDataBroadcastReceiver,filter);


        Intent intent = new Intent(ACTION_START_SERVICE);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setComponent(new ComponentName(SERVICE_PACKAGE_NAME,SERVICE_CLASS_NAME));
        }
        sendBroadcast(intent);

        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                textView.setText("");
                return true;
            }
        });

        Button turnOnButton = findViewById(R.id.turnon_beep);
        turnOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTION_TURNON_BEEP);
                intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(intent);
            }
        });

        Button turnOffButton = findViewById(R.id.turnoff_beep);
        turnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTION_TURNOFF_BEEP);
                intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                sendBroadcast(intent);
            }
        });

        Log.d(TAG,"onCreate");
    }

    private  class BarCodeDataBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String barcodeData = intent.getStringExtra("barcodeData");
            if(barcodeData != null){
                textView.append(barcodeData + "\n");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(barCodeDataBroadcastReceiver);
        Log.d(TAG,"onDestroy");
    }
}
