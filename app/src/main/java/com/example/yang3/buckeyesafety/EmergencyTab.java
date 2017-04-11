package com.example.yang3.buckeyesafety;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by yang3 on 3/7/2017.
 */
public class EmergencyTab extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmentemergency, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageButton button = (ImageButton) getActivity().findViewById(R.id.alertButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              Toast toast = Toast.makeText(this, "I'm clicked", Toast.LENGTH_LONG);
//              toast.show();
                AlertManager am = new AlertManager(getContext());
                am.alert();
            }
        });
    }
}
