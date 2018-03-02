package com.jaydenho.androidtech.intent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.net.URISyntaxException;

/**
 * Created by hedazhao on 2018/3/2.
 */

public class LearnIntent {
    private static final String TAG = "intent.LearnIntent";

    public static void testDeeplink(Context context) {
        String url1 = "vipshop://showGoodsDetail?pid=313967565&goodType=0&tra_from=tra%3Adpoqu5jb%3A%3A%3A%3A";
        String url = "ctrip://wireless/InlandHotel?checkInDate=20170504&checkOutDate=20170505&hotelId=687592&allianceid=288562&sid=960124&sourceid=2504&ouid=Android_Singapore_687592";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    public static void testIntentAndUri() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("jayden")
                .authority("study:8888")
                .appendPath("/book")
                .appendPath("/kindle")
                .appendPath("/english")
                .appendQueryParameter("bn", "little prince");
        Uri uri = uriBuilder.build();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.jaydenho.androidtech");
        String uriString = intent.toUri(Intent.URI_INTENT_SCHEME);
        //uriString:intent://study%3A8888/%2Fbook/%2Fkindle/%2Fenglish?bn=little%20prince#Intent;scheme=jayden;launchFlags=0x10000000;package=com.jaydenho.androidtech;end
        Log.d(TAG, "uriString: " + uriString);
        try {
            Intent intent1 = Intent.parseUri(uriString, Intent.URI_INTENT_SCHEME);
            Log.d(TAG, "intent1.filterEquals(intent): " + intent1.filterEquals(intent));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
