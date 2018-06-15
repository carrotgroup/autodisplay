package com.adtv.homescreen;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.adtv.R;
import com.adtv.Utility.DataManager;
import com.adtv.Utility.Utility;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HomeActivity extends Activity {

    private ImageView mImageView;
    private VideoView mVideoView;

    private int mMixedUrlListIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        playContent();
    }

    private void initView() {
        mImageView = findViewById(R.id.imageView);
        mVideoView = findViewById(R.id.videoView);
    }

    private void showImage(String url) {
        isShowImage(true);
        Glide.with(this).load(url).into(mImageView);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                incrementIndexAndPlayNext();
            }
        }, 10000);
    }

    private void isShowImage(boolean isShowImage) {
        mImageView.setVisibility(isShowImage ? View.VISIBLE : View.GONE);
        mVideoView.setVisibility(isShowImage ? View.GONE : View.VISIBLE);
    }

    private void playVideo(String url) {
        isShowImage(false);

        //Creating MediaController
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoView);

        //specify the location of media file
        Uri uri = Uri.parse(url);

        //Setting MediaController and URI, then starting the videoView
        mVideoView.setMediaController(mediaController);
        mVideoView.setVideoURI(uri);
        mVideoView.requestFocus();
        mVideoView.start();

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                incrementIndexAndPlayNext();
            }
        });
    }

    private void incrementIndexAndPlayNext() {
        mMixedUrlListIndex++;
        playContent();
    }

    private void playContent() {

        ArrayList<String> mixedUrlList = DataManager.getInstance().getUrlList();

        if (mixedUrlList != null && mixedUrlList.size() > mMixedUrlListIndex) {
            String url = Utility.modifyDropboxUrl(mixedUrlList.get(mMixedUrlListIndex));
            refractorUrlAndPlayContent(url);
        } else {
            mMixedUrlListIndex = 0;
            playContent();
        }
    }

    private void refractorUrlAndPlayContent(String url) {

        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }

        if (url.contains("jpg") || url.contains("jpeg") || url.contains("png")) {
            showImage(url);
        } else {
            playVideo(url);
        }
    }
}
