package com.example.yang3.buckeyesafety;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.R.attr.value;

;

public class SetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        WebView view = (WebView) findViewById(R.id.setup_webview);
        view.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("http://exitme")) {
                    Intent myIntent = new Intent(SetupActivity.this, MainActivity.class);
                    myIntent.putExtra("key", value); //Optional parameters
                    SetupActivity.this.startActivity(myIntent);
                    finish();  // close activity
                }
                else
                    view.loadUrl(url);

                return true;
            }
        });
        WebSettings webSettings = view.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String html = "<html>\n" +
                "\t<head>\n" +
                "\t</head>\n" +
                "\t<body>\n" +
                "\t\t<h1>\n" +
                "\t\t\tWelcome to BuckeyeSaftey!\n" +
                "\t\t</h1>\n" +
                "\t\t<h3>\n" +
                "\t\t\tThis app will be your buddy whenever you need to walk alone! There's two different ways to stay protected: <br />\n" +
                "1. Hold a button on the main screen and if you let go before you find your destination, emergency contacts will be notified. <br />\n" +
                "2.  Swipe left at any time and emergency contacts will be notified. <br />\n" +
                "\t\t</h3>\n" +
                "\t\t<div>\n" +
                "\t\t\t<form id=\"settings\" action=\"#\">\n" +
                "\t\t\t\t<input id=\"phone\"></input>\n" +
                "\t\t\t\t<input id=\"email\"></input>\n" +
                "<a href=\"http://exitme\" />close</a>" +
                "\t\t\t</form>\n" +
                "\t\t</div>\n" +
                "\t</body>\n" +
                "</html>";
        view.loadData(html, "text/html", null);

    }
}