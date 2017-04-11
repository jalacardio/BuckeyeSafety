package com.example.yang3.buckeyesafety;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by yang3 on 4/9/2017.
 */
public class AlertManager {
    SharedPreferences shared;
    String number;
    String email;
    Context c;
    public AlertManager(Context c){
        shared = PreferenceManager.getDefaultSharedPreferences(c);
        this.c = c;
    }
    public void getNumber(){
        number = shared.getString("phone", "x");
    }
    public void getEmail(){
        email = shared.getString("email", "x");
    }
    public void callToNumber(){
        String numb = "tel:" + number;
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(numb));
        try{
            c.startActivity(callIntent);
        }catch (SecurityException e){
            System.out.print("Phone call not granted!");
        }

    }
    public void sendEmail(){
        String[] emails = {email};
        String subject = "Emergency!!!";
        String message = "Your friend is currently in danger! Try to reach out to him!";

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, emails);
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);

        // need this to prompts email client only
        email.setType("message/rfc822");

        c.startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }
    public void alert(){
        getNumber();
        getEmail();
        callToNumber();
        sendEmail();
    }
}
