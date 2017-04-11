package com.example.yang3.buckeyesafety;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HandsOnFragment extends Fragment {

    private Activity mActivity;
    private Context mContext;
    private boolean started = false;
    private int fails = 0;
    private Handler handler = new Handler();
    private Runnable runOrange = new Runnable() {
        @Override
        public void run() {
            LinearLayout layout = (LinearLayout) mActivity.findViewById(R.id.handson_layout);
            layout.setBackgroundColor(Color.rgb(255, 165, 0));
            handler.postDelayed(runRed, 1000);
        }
    };
    private Runnable runRed = new Runnable() {
        @Override
        public void run() {
            LinearLayout layout = (LinearLayout) mActivity.findViewById(R.id.handson_layout);
            layout.setBackgroundColor(Color.RED);
        }
    };
    private Runnable runDialog = new Runnable() {
        @Override
        public void run() {
            Log.d("HandsOn", "Auth Started");
            createEmergencyDialog();
        }
    };
    private Runnable runEmergency = new Runnable() {
        @Override
        public void run() {
            Log.d("HandsOn", "Emergency Started");
            AlertManager am = new AlertManager(mActivity);
            am.alert();
        }
    };
    private AlertDialog dialog;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hands_on, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = getActivity();
        mContext = getContext();

        Button button = (Button) mActivity.findViewById(R.id.handson_btn);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!started) {
                        Log.d("HandsOn", "Pressed Start");
                        started = true;
                        LinearLayout layout = (LinearLayout) mActivity.findViewById(R.id.handson_layout);
                        layout.setBackgroundColor(Color.BLUE);
                    } else {
                        Log.d("HandsOn", "Pressed Again");
                        LinearLayout layout = (LinearLayout) mActivity.findViewById(R.id.handson_layout);
                        layout.setBackgroundColor(Color.BLUE);
                        handler.removeCallbacks(runDialog);
                        handler.removeCallbacks(runOrange);
                        handler.removeCallbacks(runRed);
                    }
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (started) {
                        Log.d("HandsOn", "Pressed Stop");
                        LinearLayout layout = (LinearLayout) mActivity.findViewById(R.id.handson_layout);
                        layout.setBackgroundColor(Color.YELLOW);
                        handler.postDelayed(runDialog, 5000);
                        handler.postDelayed(runOrange, 3000);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void removeCallbacks() {
        if (mActivity != null) {
            Log.d("HandsOn", "Navigated to EmergencyTab");
            handler.removeCallbacks(runOrange);
            handler.removeCallbacks(runRed);
            handler.removeCallbacks(runDialog);
            handler.removeCallbacks(runEmergency);
            LinearLayout layout = (LinearLayout) mActivity.findViewById(R.id.handson_layout);
            layout.setBackgroundColor(Color.WHITE);
        }
    }

    private void createEmergencyDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView lbl = new TextView(mContext);
        lbl.setText("Enter Passcode");
        final EditText txt = new EditText(mContext);
        layout.addView(lbl);
        layout.addView(txt);
        builder.setView(layout);
        builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String text = txt.getText().toString();
                    if ("1".equals(text)) {
                        // pass
                        fails = 0;
                        Log.d("HandsOn", "Auth Passed");
                        Toast.makeText(mContext, "Check Password Passed", Toast.LENGTH_SHORT).show();
                        handler.removeCallbacks(runEmergency);
                        dialog.dismiss();
                        createSafeDialog();
                    } else if (fails < 2){
                        // fail
                        fails++;
                        Log.d("HandsOn", "Auth Bad Password");
                        Toast.makeText(mContext, "Check Password Failed", Toast.LENGTH_SHORT).show();
                        txt.setText("");
                    } else {
                        fails = 0;
                        Log.d("HandsOn", "Auth Failed");
                        LinearLayout layout = (LinearLayout) mActivity.findViewById(R.id.handson_layout);
                        layout.setBackgroundColor(Color.WHITE);
                        started = false;
                        handler.removeCallbacks(runEmergency);
                        handler.post(runEmergency);
                    }
                }
        });
        handler.postDelayed(runEmergency, 10000);
    }

    private void createSafeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LinearLayout layout = new LinearLayout(mContext);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        TextView lbl = new TextView(mContext);
        lbl.setText("Have you reached your destination?");
        layout.addView(lbl);
        builder.setView(layout);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LinearLayout layout = (LinearLayout) mActivity.findViewById(R.id.handson_layout);
                layout.setBackgroundColor(Color.WHITE);
                started = false;
                handler.removeCallbacks(runEmergency);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LinearLayout layout = (LinearLayout) mActivity.findViewById(R.id.handson_layout);
                layout.setBackgroundColor(Color.YELLOW);
                handler.removeCallbacks(runEmergency);
                handler.postDelayed(runDialog, 5000);
                handler.postDelayed(runOrange, 3000);
            }
        });
        dialog = builder.create();
        dialog.show();
        handler.postDelayed(runEmergency, 10000);
    }
}
