package com.example.speed;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.swedspot.automotive.AutomotiveBroadcast;
import android.swedspot.automotive.AutomotiveManager;
import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.swedspot.automotiveapi.AutomotiveListener;


public class Speedometer extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speedometer);

        final TextView ds = (TextView)findViewById(R.id.displaySpeed);

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... objects) {
                final AutomotiveManager manager = (AutomotiveManager) getApplicationContext().getSystemService(Context.AUTOMOTIVE_SERVICE);

                manager.setListener(new AutomotiveListener() {
                    @Override
                    public void timeout(final int signalId) {
                        // Request did time out and no data was received
                        Log.d("speed", "timeout " + signalId);
                    }

                    @Override
                    public void receive(final AutomotiveSignal automotiveSignal) {
                        // Incoming signal
                        switch (automotiveSignal.getSignalId()) {
                            case AutomotiveSignalId.FMS_WHEEL_BASED_SPEED:
                                ds.post(new Runnable() { // Post the result back to the View/UI thread
                                    public void run() {
                                        ds.setText(String.format("%.1f km/h", ((SCSFloat) automotiveSignal.getData()).getFloatValue()));
                                    }
                                });
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void notAllowed(final int signalId) {
                        // Policy does not allow for this operation
                        Log.d("speed", "not allowed " + signalId);
                    }
                });

                manager.register(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED);
                manager.requestValue(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED);

                final BroadcastReceiver receiver = new BroadcastReceiver() {

                    @Override
                    public void onReceive(Context context, Intent intent) {
                        final int level = intent.getIntExtra(AutomotiveBroadcast.EXTRA_DRIVER_DISTRACTOIN_LEVEL, 5);
                        ds.post(new Runnable() { // Post the result back to the View/UI thread
                            public void run() {
                                ds.setTextSize(level*10.0F + 12.0F);
                            }
                        });
                    }
                };

                final IntentFilter intentFilter = new IntentFilter(AutomotiveBroadcast.ACTION_DRIVER_DISTRACTION_LEVEL_CHANGED);
                final Intent currentValue = getApplicationContext().registerReceiver(receiver, intentFilter);
                if (currentValue != null) {
                    final int level = currentValue.getIntExtra(AutomotiveBroadcast.EXTRA_DRIVER_DISTRACTOIN_LEVEL, 5);
                    ds.post(new Runnable() { // Post the result back to the View/UI thread
                        public void run() {
                            ds.setTextSize(level*10.0F + 12.0F);
                        }
                    });
                }

                return null;
            }
        }.execute();
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
