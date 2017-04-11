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
                "\t\t<style type=\"text/css\">\n" +
                "\t\t\t#close {\n" +
                "\t\t\t\tdisplay: block;\n" +
                "\t\t\t\tsize: 25px;\n" +
                "\t\t\t\tline-height: 50px;\n" +
                "\t\t\t\twidth: 350px;\n" +
                "\t\t\t\tbackground-color: red;\n" +
                "\t\t\t\tmargin: auto auto;\n" +
                "\t\t\t\ttext-align: center;\n" +
                "\t\t\t\tcolor: #fff;\n" +
                "\t\t\t\ttext-decoration: none;\n" +
                "\t\t\t\tfont-weight: bold;\n" +
                "\n" +
                "\t\t\t}\n" +
                "\t\t\t#brutus {\n" +
                "\t\t\t\tposition: relative;\n" +
                "\t\t\t\tdisplay: block;\n" +
                "\t\t\t\tright: -35px;\n" +
                "\t\t\t}\n" +
                "\t\t</style>\n" +
                "\t</head>\n" +
                "\t<body>\n" +
                "\t\t<h1>\n" +
                "\t\t\tWelcome to BuckeyeSaftey!\n" +
                "\t\t</h1>\n" +
                "\t\t<div id=\"paraWrapper\">\n" +
                "\t\t\t<h3>\n" +
                "\t\t\t\tThis app will be your buddy whenever you need to walk alone! There's three different ways to stay protected: <br />\n" +
                "\t\t\t\t<ol>\n" +
                "\t\t\t\t\t<li>Hold a button on the main screen and if you let go before you find your destination, emergency contacts will be notified.</li>\n" +
                "\t\t\t\t\t<li>Enter estination address and get an ETA. App will prompt you after ETA minutes to see if you're home. </li>\n" +
                "\t\t\t\t\t<li>Swipe left at any time and emergency contacts will be notified. </li>\n" +
                "\t\t\t\t</ol>\n" +
                "\t\t\t</h3>\n" +
                "\t\t</div>\n" +
                "\t\t<div>\n" +
                "\t\t\t<a id=\"close\" href=\"http://exitme\" />Got it!</a>\n" +
                "\t\t</div>\n" +
                "\t\t<img src=\"http://content.sportslogos.net/logos/33/791/full/4914_ohio_state_buckeyes-mascot-2003.png\" id=\"brutus\" />\n" +
                "\t</body>\n" +
                "</html> ";
        view.loadData(html, "text/html", null);

    }
}