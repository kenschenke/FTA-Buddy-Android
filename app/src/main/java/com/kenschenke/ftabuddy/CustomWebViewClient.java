package com.kenschenke.ftabuddy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

// https://stackoverflow.com/a/38484061
public class CustomWebViewClient extends WebViewClient {
    private Context ctx;
    public CustomWebViewClient(Context context) {
        super();
        ctx = context;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        final Uri uri = Uri.parse(url);
        return handleUri(uri);
    }

    @androidx.annotation.RequiresApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        final Uri uri = request.getUrl();
        return handleUri(uri);
    }

    private boolean handleUri(final Uri uri) {
        final String scheme = uri.getScheme();
        if (!(scheme.equals("http") || scheme.equals("https"))) {
            // Returning false means that you are going to load this url in the webView itself
            return false;
        } else {
            // Returning true means that you need to handle what to do with the url
            // e.g. open web page in a Browser
            ctx.startActivity(new Intent(Intent.ACTION_VIEW, uri));
            return true;
        }
    }
}
