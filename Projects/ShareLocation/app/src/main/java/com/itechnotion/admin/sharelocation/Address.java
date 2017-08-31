package com.itechnotion.admin.sharelocation;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;

public class Address extends AppCompatActivity implements GooglePlayServicesClient.ConnectionCallbacks,GooglePlayServicesClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private LinearLayout linearLayout1, linearLayout2;
    private FloatingActionButton fabEdit;
    private FloatingActionButton fabShare;
    private Button btnDone;

    private TextView tvAddresssrcL2;
    private TextView tvCitysrcL2;
    private Intent mIntentService;
    private PendingIntent mPendingIntent;
    private LocationClient locationclient;
    private String TAG;
    private TextView tvStatesrcL2,tvCountrysrcL2,tvZipCodesrcL2;
    private EditText tvAddresssrcL1,tvCitysrcL1,tvStatesrcL1,tvCountrysrcL1,tvZipCodesrcL1;
    private LocationRequest mLocationRequest;
    private Bundle connectionBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        fabEdit = (FloatingActionButton) findViewById(R.id.fab_edit);
        fabShare = (FloatingActionButton) findViewById(R.id.fab_share);
        btnDone = (Button) findViewById(R.id.btn_done);
        tvAddresssrcL2 = (TextView) findViewById(R.id.tv_addresssrc2_address);
        tvCitysrcL2 = (TextView) findViewById(R.id.tv_citysrc2_address);
        tvStatesrcL2 = (TextView)findViewById(R.id.tv_statesrc2_address);
        tvCountrysrcL2 = (TextView)findViewById(R.id.tv_countrysrc2_address);
        tvZipCodesrcL2 = (TextView)findViewById(R.id.tv_zipsrc2_address);
        /////////////////////////////////////////////////////////////////////
        tvAddresssrcL1 = (EditText) findViewById(R.id.tv_addresssrc_address);
        tvCitysrcL1 = (EditText) findViewById(R.id.tv_citysrc_address);
        tvStatesrcL1 = (EditText)findViewById(R.id.tv_statesrc_address);
        tvCountrysrcL1 = (EditText)findViewById(R.id.tv_countrysrc_address);
        tvZipCodesrcL1 = (EditText)findViewById(R.id.tv_zipsrc_address);


        tvAddresssrcL2.setText("Siddhi Vinayak Tower");
        tvCitysrcL2.setText("Ahmedabad");
        tvCountrysrcL2.setText("India");
        tvStatesrcL2.setText("Gujarat");
        tvZipCodesrcL2.setText("380051");


        mIntentService = new Intent(this,LocationService.class);
        mPendingIntent = PendingIntent.getService(this, 1, mIntentService, 0);

        int resp = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if(resp == ConnectionResult.SUCCESS){
            //Toast.makeText(this, "Google Play Service " + resp, Toast.LENGTH_LONG).show();
            //locationclient = new LocationClient(this,this,this);

            locationclient = new LocationClient(this, new GooglePlayServicesClient.ConnectionCallbacks() {
                @Override
                public void onDisconnected() {
                    //Do some stuff here
                    Toast.makeText(Address.this, "Location Is DisConnected ", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onConnected(Bundle arg0) {
                    if(locationclient.getLastLocation() != null) {
                        //Do some other stuff here
                        Toast.makeText(Address.this, "Location Is Connected "+locationclient, Toast.LENGTH_LONG).show();
                    }
                }
            }, new GooglePlayServicesClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(ConnectionResult arg0) {
                    //Do other stuff here
                    Toast.makeText(Address.this, "Location Is not Connected "+locationclient, Toast.LENGTH_LONG).show();
                }
            });




            locationclient.connect();
            LocationRequest request = LocationRequest.create();
            request.setNumUpdates(1);
            //onConnected(connectionBundle);
            //locationclient.requestLocationUpdates(request, this);

            if (locationclient.isConnectionFailedListenerRegistered(this))
            {
                Toast.makeText(this, "Google Play  " + resp, Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this, "Google Play Service Error " + resp, Toast.LENGTH_LONG).show();
        }
        setLinearLayout2();
        Log.e("Loc",locationclient.toString());
        if(locationclient!=null && locationclient.isConnected()){
            Location loc =locationclient.getLastLocation();
            Log.i(TAG, "Last Known Location :" + loc.getLatitude() + "," + loc.getLongitude());
            //txtLastKnownLoc.setText(loc.getLatitude() + "," + loc.getLongitude());
            tvAddresssrcL2.setText((int) loc.getLatitude());
            Toast.makeText(Address.this,"Hello",Toast.LENGTH_LONG).show();
            tvCitysrcL2.setText((int) loc.getLongitude());
        }




        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAddress();
                setLinearLayout1();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doneAddress();
                setLinearLayout2();
            }
        });
    }


    public void setLinearLayout2() {
        linearLayout1.setVisibility(View.GONE);
        linearLayout2.setVisibility(View.VISIBLE);
        fabEdit.setVisibility(View.VISIBLE);
        fabShare.setVisibility(View.VISIBLE);
        linearLayout1.setVisibility(View.GONE);
    }

    public void setLinearLayout1() {
        linearLayout2.setVisibility(View.GONE);
        fabEdit.setVisibility(View.GONE);
        fabShare.setVisibility(View.GONE);
        linearLayout1.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onStart() {
        super.onStart();
        locationclient.connect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "onConnected");
        connectionBundle=connectionHint;
        Toast.makeText(Address.this,"Connection Status : Connected",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDisconnected() {
        Log.i(TAG, "onDisconnected");
        //txtConnectionStatus.setText("Connection Status : Disconnected");
        Toast.makeText(Address.this,"Connection Status : Disconnected",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "onConnectionFailed");
       // txtConnectionStatus.setText("Connection Status : Fail");
        Toast.makeText(Address.this,"Connection Status : Fail",Toast.LENGTH_LONG).show();


    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.i(TAG, "Location Request :" + location.getLatitude() + "," + location.getLongitude());
            //txtLocationRequest.setText(location.getLatitude() + "," + location.getLongitude());
            Toast.makeText(Address.this, location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_LONG).show();
        }
    }

    public void doneAddress()
    {
        tvCitysrcL2.setText(tvCitysrcL1.getText());
        tvAddresssrcL2.setText(tvAddresssrcL1.getText());
        tvCountrysrcL2.setText(tvCountrysrcL1.getText());
        tvStatesrcL2.setText(tvStatesrcL1.getText());
        tvZipCodesrcL2.setText(tvZipCodesrcL1.getText());
    }

    public void editAddress()
    {
        tvCitysrcL1.setText(tvCitysrcL2.getText());
        tvAddresssrcL1.setText(tvAddresssrcL2.getText());
        tvCountrysrcL1.setText(tvCountrysrcL2.getText());
        tvStatesrcL1.setText(tvStatesrcL2.getText());
        tvZipCodesrcL1.setText(tvZipCodesrcL2.getText());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //if(locationclient!=null)
        //locationclient.disconnect();
    }





}
