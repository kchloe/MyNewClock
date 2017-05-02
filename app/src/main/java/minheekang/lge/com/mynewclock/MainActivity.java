package minheekang.lge.com.mynewclock;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    String PREF_KEY_HOME="home_url";
    String defaultHome="https://m.search.naver.com/search.naver?sm=mtb_hty.top&where=m&query=%EC%84%9C%EC%9A%B8+%EB%82%A0%EC%94%A8";
    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mPref = PreferenceManager.getDefaultSharedPreferences(this);

        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);

        webView.setWebChromeClient(new WebBrowserClient());
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(getHomeUrl());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                webView.loadUrl(getHomeUrl());
                break;
            case R.id.menu_set:
                settingHome();
                break;
            default:
                break;
        }
        return true;
    }

    final class WebBrowserClient extends WebChromeClient {
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            result.confirm();
            return true;
        }
    }

    public void settingHome() {
        if (getHomeUrl().equals(webView.getUrl())) {
            Toast.makeText(this, "There is no change.", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences.Editor editor = mPref.edit();
            editor.putString(PREF_KEY_HOME, webView.getUrl());
            editor.commit();

            Toast.makeText(this, "Home is changed!", Toast.LENGTH_SHORT).show();
        }

    }

    private String getHomeUrl() {
        return mPref.getString(PREF_KEY_HOME, defaultHome);
    }

}
