package es.maxtuni.youtupi;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by kktuax on 23/09/15.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWebView();
    }

    private void initWebView(){
        WebView webview = (WebView) findViewById(R.id.webView);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webview.getSettings().setJavaScriptEnabled(true);
        loadUrlInWebView();
    }

    private void loadUrlInWebView(){
        WebView webview = (WebView) findViewById(R.id.webView);
        webview.loadUrl("http://" + getHostFromPreference() + ":" + getPortFromPreference(8080));
    }

    private String getHostFromPreference(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getString("server_ip", null);
    }

    private Integer getPortFromPreference(Integer defaultValue){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        try{
            return Integer.parseInt(prefs.getString("server_port", defaultValue.toString()));
        }catch (NumberFormatException e){
            return defaultValue;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                startSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
