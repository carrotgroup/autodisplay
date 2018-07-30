package com.adtv.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.adtv.R;
import com.adtv.Utility.DataManager;
import com.adtv.Utility.PreferenceConstants;
import com.adtv.Utility.Utility;
import com.adtv.homescreen.HomeActivity;
import com.adtv.splashscreen.model.GetSharedLinkApiResponse;

import java.util.List;

public class SplashActivity extends Activity implements SplashScreenApiResponseListener, View.OnClickListener {

    private String mAccessToken = "";//"qPWe4rHzi4AAAAAAAAAAB-GU8ty1sQZtY1jsZNBp2OoW2c31HohRJUmRN6OTXymg";

    private ImageView mSplashScreenImageView;
    private EditText mAccessTokenEditText;
    private Button mAccessTokenButton;
    private ProgressBar mSplashScreenProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        if (PreferenceManager.getDefaultSharedPreferences(this).contains(PreferenceConstants.KEY_ACCESS_TOKEN)) {
            mAccessTokenEditText.setVisibility(View.GONE);
            mAccessTokenEditText.setText(PreferenceManager.getDefaultSharedPreferences(this).getString(PreferenceConstants.KEY_ACCESS_TOKEN, ""));
            mAccessTokenButton.setVisibility(View.GONE);
            mSplashScreenProgressBar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAccessTokenButton.performClick();
                }
            },25*1000);
        }
    }

    private void fetchDropboxSharedLinks(String accessToken) {
        mAccessTokenButton.setVisibility(View.GONE);
        mSplashScreenProgressBar.setVisibility(View.VISIBLE);
        new FetchSharedLinkApiListener(this).callFetchSharedLinksApi("Bearer " + accessToken);
    }

    private void initView() {
        mSplashScreenImageView = findViewById(R.id.splash_screen_image_view);
        mSplashScreenProgressBar = findViewById(R.id.splash_screen_progress_bar);
        mAccessTokenEditText = findViewById(R.id.access_token_edit_text);
        mAccessTokenButton = findViewById(R.id.submit_access_token_button);
        mAccessTokenEditText.requestFocus();

        mAccessTokenEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Utility.hideSoftKeypad(mAccessTokenEditText);
            }
        });
        mAccessTokenButton.setOnClickListener(this);
    }

    @Override
    public void onResponse(GetSharedLinkApiResponse iGetSharedLinkApiResponse) {
        mSplashScreenProgressBar.setVisibility(View.INVISIBLE);
        if (iGetSharedLinkApiResponse != null && iGetSharedLinkApiResponse.getLinks().size() > 0) {
            finish();
            fillUrlList(iGetSharedLinkApiResponse);
            openHomeScreen();
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString(PreferenceConstants.KEY_ACCESS_TOKEN, mAccessToken).apply();
        }
    }

    private void openHomeScreen() {
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onError(String message) {
        if (!TextUtils.isEmpty(message)) {
            if (message.contains("Bad")) {
                Utility.showToast("Kindly check your access token");
            } else {
                Utility.showToast(message);
            }
        }
        mSplashScreenProgressBar.setVisibility(View.GONE);
        mAccessTokenButton.setVisibility(View.VISIBLE);
        mAccessTokenEditText.requestFocus();
    }

    private void fillUrlList(GetSharedLinkApiResponse iGetSharedLinkApiResponse) {
        List<String> urlList = DataManager.getInstance().getUrlList();
        for (int i = 0; i < iGetSharedLinkApiResponse.getLinks().size(); i++) {
            urlList.add(iGetSharedLinkApiResponse.getLinks().get(i).getUrl());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit_access_token_button:
                if (!TextUtils.isEmpty(mAccessTokenEditText.getText().toString())) {
                    mAccessToken = mAccessTokenEditText.getText().toString();
                } else {
                    Utility.showToast("Please enter access token");
                    mAccessTokenEditText.requestFocus();
                    return;
                }
                fetchDropboxSharedLinks(mAccessToken);
                break;
        }
    }
}
