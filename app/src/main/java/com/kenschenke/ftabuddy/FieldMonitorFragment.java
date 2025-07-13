package com.kenschenke.ftabuddy;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FieldMonitorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FieldMonitorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FieldMonitorFragment extends Fragment {
    private EditText editTextUrl;
    private WebView webView;

    public FieldMonitorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FieldMonitorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FieldMonitorFragment newInstance(String param1, String param2) {
        FieldMonitorFragment fragment = new FieldMonitorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_field_monitor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextUrl = view.findViewById(R.id.editTextUrl);
        editTextUrl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                hideKeyboard();
                loadUrl();
                return true;
            }
        });

        AppCompatImageButton buttonRefresh = view.findViewById(R.id.buttonRefresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                loadUrl();
            }
        });

        webView = view.findViewById(R.id.webView);
        webView.setWebViewClient(new myWebClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        SharedPreferences prefs = getActivity().getSharedPreferences("com.kenschenke.ftabuddy", Context.MODE_PRIVATE);
        String url = prefs.getString("FieldMonitor", "");
        webView.loadUrl(url.isEmpty() ? getString(R.string.url_welcome) : url);
    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            if (url.equals(getString(R.string.url_welcome))) {
                editTextUrl.setText(R.string.title_welcome_page);
            } else if (url.equals(getString(R.string.url_error_page))) {
                editTextUrl.setText(R.string.title_error_page);
            } else {
                editTextUrl.setText(url);
            }
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            webView.loadUrl(getString(R.string.url_error_page));
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void hideKeyboard() {
        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        editTextUrl.clearFocus();
    }

    private void loadUrl() {
        String url = editTextUrl.getText().toString();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://".concat(url);
        }

        SharedPreferences prefs = getActivity().getSharedPreferences("com.kenschenke.ftabuddy", Context.MODE_PRIVATE);
        prefs.edit().putString("FieldMonitor", url).apply();

        webView.loadUrl(url);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
