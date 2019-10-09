package com.feedreader.myapplication.tools;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

public class TwitterResultReceiver extends BroadcastReceiver {

    Intent returnIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TweetUploadService.UPLOAD_SUCCESS.equals(intent.getAction())) {
            // success
            Activity thisActivity = (Activity) context;

            //returnIntent = new Intent(context, WebViewActivity.class);
            //context.startActivity(returnIntent);

            thisActivity.onBackPressed();
        }
    }
}
