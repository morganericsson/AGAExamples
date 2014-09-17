package com.example.speed;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.swedspot.automotiveapi.*;
import android.swedspot.automotiveapi.unit.*;
import android.swedspot.automotiveapi.*;
import android.swedspot.scs.data.*;
import android.widget.TextView;


import com.swedspot.vil.policy.*;
import com.swedspot.vil.distraction.*;

public class Speedometer extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedometer);

        // Get a reference to the text field
        final TextView ds = (TextView)findViewById(R.id.displaySpeed);

        // Fire off an async task. Networking and similar should not (cannot) happen on the UI thread
        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... objects) {
                // Access to Automotive API
                AutomotiveFactory.createAutomotiveManagerInstance(
                        new AutomotiveCertificate(new byte[0]),
                        new AutomotiveListener() { // Listener that observes the Signals
                            @Override
                            public void receive(final AutomotiveSignal automotiveSignal) {
                                ds.post(new Runnable() { // Post the result back to the View/UI thread
                                    public void run() {
                                        ds.setText(String.format("%.1f km/h", ((SCSFloat) automotiveSignal.getData()).getFloatValue()));
                                    }
                                });
                            }

                            @Override
                            public void timeout(int i) {}

                            @Override
                            public void notAllowed(int i) {}
                        },
                        new DriverDistractionListener() {       // Observe driver distraction level
                            @Override
                            public void levelChanged(final DriverDistractionLevel driverDistractionLevel) {
                                ds.post(new Runnable() { // Post the result back to the View/UI thread
                                    public void run() {
                                        ds.setTextSize(driverDistractionLevel.getLevel()*10.0F + 12.0F);
                                    }
                                });
                            }
                        }
                ).register(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED); // Register for the speed signal
                return null;
            }
        }.execute(); // And go!
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.speedometer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}