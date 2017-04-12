package com.example.yang3.buckeyesafety;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapsFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {

    private Activity mActivity;
    private Context mContext;
    private GoogleMap map;
    MapView mapView;


    public MapsFragment() {
        int stop = 0;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = getActivity();
        mContext = getContext();

        Button button = (Button) mActivity.findViewById(R.id.map_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you safe?")
                        .setPositiveButton("Yes, add more time (requires PIN)", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Calls to PIN popup
                            }
                        })
                        .setNegativeButton("No (emergency)", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Calls to Alert Manager
                            }
                        });
                builder.create();
                builder.show();
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);


        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


        return rootView;
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        // Sets the map type to be "hybrid"
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Add a marker in Sydney and move the camera
        LatLng loc = new LatLng(40.0142, -83.0309);
        LatLng loc2 = new LatLng(40.01,-83.03);


        Location location1 = new Location("");
        location1.setLatitude(40.0142);
        location1.setLongitude(-83.0309);

        Location location2 = new Location("");
        location2.setLatitude(40.01);
        location2.setLongitude(-83.03);

        float distInMeters = location1.distanceTo(location2);
        float walkSpeed = 9;
        //estimatedDriveTimeInMinutes = distInMeters / walkSpeed;

        PolylineOptions options = new PolylineOptions();
        options.color( Color.parseColor( "#CC0000FF" ) );
        options.width( 5 );
        options.visible( true );

        options.add(loc);
        options.add(loc2);

        googleMap.addPolyline( options );


        googleMap.addMarker(new MarkerOptions()
                .position(loc)
                .title("OhioStateMarker"));

        googleMap.addMarker(new MarkerOptions()
                .position(loc2)
                .title("HouseMarker"));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(20));

    }


}
