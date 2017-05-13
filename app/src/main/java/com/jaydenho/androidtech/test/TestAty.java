package com.jaydenho.androidtech.test;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jaydenho.androidtech.R;
import com.jaydenho.androidtech.util.CommonUtil;
import com.jaydenho.androidtech.util.FileUtils;
import com.jaydenho.androidtech.util.LocationUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Set;

/**
 * Created by hedazhao on 2016/7/26.
 */
public class TestAty extends AppCompatActivity {

    private static final String TAG = TestAty.class.getSimpleName();
    private ImageView mImageLoaderTestIv = null;
    private BrowserExchangeDialogPPW mPPW = null;
    private TextView mNameTV = null;

    private boolean mRestore = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_test);

       /* mNameTV = (TextView) findViewById(R.id.tv_name);
        String name = "一二三四五六七";
        String testStr = "你好，测试。saf,.;1；2：3？4！";
     *//*   String regex = "[\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(testStr);
        if (m.find()) {
            Log.d(TAG,"group0: " + m.group(0));
        }*//*
//        mNameTV.setText(cutTextEllipseEnd(name, 5));
        mNameTV.setText(Html.fromHtml("&quot;你好&quot;"));

//        String[] strArray = testStr.split("[\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b]");
        String[] strArray = testStr.split("[\\u3002|\\uff1f|\\uff01|\\uff0c|\\u3001|\\uff1b|\\uff1a|\\u201c|\\u201d|\\u2018|\\u2019|\\uff08|\\uff09|\\u300a|\\u300b|\\u3008|\\u3009|\\u3010|\\u3011|\\u300e|\\u300f|\\u300c|\\u300d|\\ufe43|\\ufe44|\\u3014|\\u3015|\\u2026|\\u2014|\\uff5e|\\ufe4f|\\uffe5]");
        for (String str : strArray) {
            Log.d(TAG, " str: " + str);
        }

        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(1);
        Log.d(TAG, "list.contains(1): " + list.contains(1));
        list.remove(Integer.valueOf(1));
        list.remove(list.indexOf(2));
        Log.d(TAG, Arrays.toString(list.toArray()));

        mImageLoaderTestIv = (ImageView) findViewById(R.id.test_image_loader_iv);
        *//*ImageLoader.getInstance().displayImage("http://pic34.nipic.com/20131021/11569127_170602617166_2.jpg", mImageLoaderTestIv, getSujectIconDisplayOptions());
        ImageLoader.getInstance().loadImage("http://pic60.nipic.com/file/20150303/12216461_110913232000_2.jpg", getSujectIconDisplayOptions(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });*//*
        String str = "我是原始值";
        checkString(str);
        Log.d(TAG, "checkString: " + str);

        testProbability();

        initPPW();

        mHandler.sendEmptyMessageDelayed(1, 2000);

        a();

        Log.d(TAG, "onCreate");
        if (savedInstanceState != null) {
            mRestore = savedInstanceState.getBoolean("restore", mRestore);
        }

        Log.d(TAG, "branch: " + getPhoneBrand() + " model: " + Build.MODEL + " devide: " + Build.DEVICE + " product" + Build.PRODUCT);

        IdentityHashMap<String, String> map = new IdentityHashMap<>();
        map.put("show", "1323");
        map.put("show", "1323");
        map.put("show", "1324");

        startLocation();

        List<String> orderIds = new ArrayList<>();
        orderIds.add("11377");
        orderIds.add("11374");
        orderIds.add("11378");
        orderIds.add("11371");
        testSortString(orderIds);*/

        jump();

  /*      new Thread(new Runnable() {
            @Override
            public void run() {
                writeParcel2File();
            }
        }).start();

        Log.d(TAG, "getLeagueOrderId(1137, 114): " + getLeagueOrderId(1137, 114));

        testStringFormat();

        testListFormat();*/

    }

    class dns extends Observable implements Observer {

        @Override
        public void update(Observable o, Object arg) {

        }
    }

    private void testListFormat() {
        List<String> list = new ArrayList<>();
        list.add("nihao");
        Object o = list;
        if (o instanceof List) {
            List<Integer> list1 = (List<Integer>) o;
            try {
                for (Integer i : list1) {
                    Log.d(TAG, "i: " + i);
                }
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

    private void testArrayIterator() {
        String[] strs = null;
        for(String str : strs) {

        }
    }
    private void testStringFormat() {
        Log.e(TAG, String.format("test format %d-%s-%s", 12, "21", 12));
        Log.e(TAG, String.format("test format %d-%s-%s", 12, "21", null));
        Log.e(TAG, String.format("test format %d-%s-%s", 12, "21", null) + null);
    }

    /**
     * 存储序列化对象
     */
    private void writeParcel2File() {
        UserInfo u1 = new UserInfo("李四");
        UserInfo u2 = new UserInfo("张三");
        List<UserInfo> us = new ArrayList<>();
        us.add(u1);
        us.add(u2);
        UserInfo[] ua = new UserInfo[2];
        ua[0] = u1;
        ua[1] = u2;
        String path1 = CommonUtil.BASE_PATH + File.separator + "parcelable" + File.separator + "u.txt";
        String path2 = CommonUtil.BASE_PATH + File.separator + "parcelable" + File.separator + "us.txt";
        String path3 = CommonUtil.BASE_PATH + File.separator + "parcelable" + File.separator + "ua.txt";

        try {
            UserInfo uc1 = FileUtils.readFile(path1, UserInfo.class);
            List usc = FileUtils.readFile(path2, List.class);
            UserInfo[] uac = FileUtils.readFile(path3, UserInfo[].class);

            Log.d(TAG, "u1c: " + uc1.toString());
            Log.d(TAG, "usc: " + Arrays.toString(usc.toArray()));
            Log.d(TAG, "uac: " + Arrays.toString(uac));

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        try {
            FileUtils.writeFile(path1, u1, false);
            FileUtils.writeFile(path2, us, false);
            FileUtils.writeFile(path3, ua, false);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * * @return 联盟广告订单Id 构造规则：ositionId + leagueId4位，leagueId位数不够前置补0，最后转成负数
     */
    public static String getLeagueOrderId(int positionId, int leagueId) {
        return "-" + String.valueOf(positionId) + String.format(Locale.ENGLISH, "%04d", leagueId);
    }

    private ADIntentUtils adIntentUtils = null;

    private void jump() {
        WebView webview = (WebView) findViewById(R.id.wv);
//        String url = "https://m.maila88.com/mailaIndex?mailaAppKey=MDLwQB58MLrXyAWXiRzzH&mailaOs=android";
//        String url = "https://cas.sh.cn.criteo.com/delivery/r/afr.php?did=59097ccea1ee61ec88f5c69594ef1f00&z=123&u=%7CswkfdbcQtkvui12846RboqJLwRhNPVl34PioXE6yyZw%3D%7C&c1=64huUomFIxsbbfC6BUo1bAIUwNPLnARfF4Ndf6RWTRNKshGo9uOJFa4y7YpWro_E7A97gold_uS11r4nF0bccBvIrKjJhZ4AqRzgtbI5nEF55zoPt8_RpxWuuhJSjLsERRFRmY0_PuZSHbtQSTS4J_tvOsae3ab_VV3HbU0hEjZZY07sx_ywh_fNb9fS9n-XBA2TvhvMY52La7qe1grEZPiwNN1jxaYPQDIGqyNrWVHG5gCAiivf4X65dP7YoGLCxxJZ47IzAuavjHD_yOtuvHYg52IgvrElK_iuQ7pInlVdwka_OU9OM7MFVumvlkeh";
//        String url = "http://dl.xunlei.com/";
//        String url = "http://cat.sh.cn.criteo.com/delivery/ckn.php?cppv=1&cpp=7dHKgHx5TEp6eHVYMVR1U3E2ZlpFMlZWTnpiWDROTml5UmpqWmYwYmZXOFFqektDM3FJb2hZZ2EwMVd6d1lGN1A2dDlzWTNYTmZSV0VObGNUbytjdkJ3dm41U1pZa2FXY2tzazFNZ1lLNzFKVnlwTnJYeWtmWkZhRitJUUFQelhMSy9YWlpHc3pwek5paEp3M05HMGJ3TFhXemlYemRtWEpLN0FHZUcrTDNYZzNmdjFXUTAwU3FINTJrc1B5dTZxWGx5a1J2WGplM3B3eit6OGZlSGhEQnZkWk1SQnBTU1loaWk4UFI0d0R4eWpqdjVrVHhTejRLT2EzSUxWdU1Ia1BGUk9kTVAxTGpJZGpPYXVYRlZrS2h1WkVPdC81YkUzRFRGU2xUWlNUMUxkRndnVU03ODljZGlRZ20vU2dQbE00VGJpZFlMVGNHeFl3MG4xdkk5cGdNa2NMOFRtdDNhZS9qWTFNTFJ0c2Q2dk1lVGNINGNsYTlwam1tWDhvM0VQOHZjK0svNkZ1TlRpRTZ5Ui9FREMra05LVWUwMjhjcUhod0FDZ0RFanpRajdSaDF3dDhuclhCMnVaSlc2YWphanp0TUxvOEN5bE10TURnV3lLK2xkWWNUTHF2MXNob1V6TnJZQ0FETlNObGkyVXc0ND18&maxdest=ctrip%3A%2F%2Fwireless%2FInlandHotel%3FcheckInDate%3D20170504%26checkOutDate%3D20170505%26hotelId%3D687592%26allianceid%3D288562%26sid%3D960124%26sourceid%3D2504%26ouid%3DAndroid_Singapore_687592";
        String url = "https://m.ctrip.com/webapp/hotel/hoteldetail/687592/checkin-1-7.html?allianceid=288562&sid=964106&sourceid=2504&sepopup=12";
//        String url = "http://blog.csdn.net/jiangwei0910410003/article/details/16859039";

        adIntentUtils = new ADIntentUtils(this);
        webview.setWebViewClient(new MyWebViewClient());
        webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Log.d(TAG, "downloadUrl: " + url);
            }
        });
        WebSettings wSet = webview.getSettings();
        wSet.setJavaScriptEnabled(true);
        webview.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d(TAG,"onPageStarted url: " + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d(TAG,"onPageFinished url: " + url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(!url.startsWith("http")){
                return true;
            }
            return false;
//            return adIntentUtils.shouldOverrideUrlLoadingByApp(view, url) || super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.d(TAG,"onReceivedError");
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.d(TAG,"onReceivedError");
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            Log.d(TAG,"onReceivedSslError");
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            Log.d(TAG,"onReceivedHttpError");
        }
    }

    private void testSortString(List<String> orderIds) {
        Collections.sort(orderIds);
        Log.d(TAG, Arrays.toString(orderIds.toArray()));
    }

    private LocationUtil mLocationUtil = null;

    /**
     * 获取一次定位信息,用户广告模块
     */
    private void startLocation() {
        mLocationUtil = new LocationUtil(this);
        mLocationUtil.start();
    }

    private void destroyLocation() {
        if (mLocationUtil != null) {
            mLocationUtil.destroy();
        }
    }

    public static String getPhoneBrand() {
        return Build.BRAND;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        destroyLocation();
    }

    public void testThreadException() {
        try {
            occurThreadException();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
    }

    public void occurThreadException() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                throw new ArrayIndexOutOfBoundsException("thread fault");
            }
        }).start();
    }

    public static void a() {
        String jsonStr = "{\"a\":\"png\\/jpg\"}";
        String jsonStr2 = "{\"a\":\"png/jpg\"}";
        String result = "";
        String result2 = "";
        try {
            JSONObject json = new JSONObject(jsonStr);
            JSONObject json2 = new JSONObject(jsonStr2);
            result = json.optString("a");
            result2 = json2.optString("a");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "result: " + result + " result2: " + result2);
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Intent intent = new Intent(TestAty.this, TestListViewAty.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra("handler",new Handler());
//            startActivityForResult(intent, 1);
//            finish();
//            startActivity(intent);
            return true;
        }
    });

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putBoolean("restore", mRestore);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
        mRestore = savedInstanceState.getBoolean("restore", mRestore);
    }

    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.right_in_anim, R.anim.left_out_anim);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.left_in_anim, R.anim.right_out_anim);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult resultCode: " + resultCode);
    }

    public static String cutTextEllipseEnd(String text, int maxLength) {
        if (text.length() >= maxLength + 1) {
            text = text.substring(0, maxLength) + "...";
        }
        return text;
    }

    boolean a;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
     /*   if (!a) {
            showPPW(mNameTV);
        }
        a = true;*/
    }

    private void initPPW() {
        mImageLoaderTestIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPPW(mNameTV);
            }
        });
        mPPW = new BrowserExchangeDialogPPW(this);
        mPPW.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(TestAty.this, 1);
            }
        });
        mPPW.setContentText("nihaoafdafsfs");
    }

    private void showPPW(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int anchorX = location[0];
        int anchorY = location[1];
        View ppwContentView = mPPW.getContentView();
        ppwContentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Log.d(TAG, "mppw width : " + ppwContentView.getWidth() + " height: " + ppwContentView.getHeight() + " measureWidth: " + ppwContentView.getMeasuredWidth() + " measureHeight: " + ppwContentView.getMeasuredHeight());
//        mPPW.showAtLocation(v, Gravity.NO_GRAVITY, location[0], location[1] - ppwContentView.getMeasuredHeight());
//        mPPW.showAsDropDown(mImageLoaderTestIv, 0, -(mImageLoaderTestIv.getHeight() + mPPW.getHeight()));
        backgroundAlpha(this, 0.4f);
        mPPW.showAtLocation(v, Gravity.CENTER, 0, 0);
    }

    public void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     *
     * @return window显示的左上角的xOff, yOff坐标
     */
   /* private static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = ScreenUtils.getScreenHeight(anchorView.getContext());
        final int screenWidth = ScreenUtils.getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }*/
    private void testProbability() {
        Map<String, Integer> testResult = new HashMap<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String resultStyle = getProbability();
            Integer resultTimes = testResult.get(resultStyle);
            testResult.put(resultStyle, (resultTimes == null ? 0 : resultTimes) + 1);
        }
        for (Map.Entry<String, Integer> entry : testResult.entrySet()) {
            Log.d(TAG, "[result] key: " + entry.getKey() + " value: " + entry.getValue());
        }
        Log.d(TAG, "[result] spend time(millisSecond): " + (System.currentTimeMillis() - start));
    }

    public void checkString(String str) {
        str = "我改变了";
    }

    public static DisplayImageOptions getSujectIconDisplayOptions() {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.cacheInMemory(true); // 设置是否开启内存缓存
        builder.cacheOnDisk(true);
        return builder.build();
    }

    private String getProbability() {
        Map<String, Integer> probabilityMap = new HashMap<>();
        String defaultStyle = "1";
        Set<String> styles = new HashSet<>();
        styles.add("1");
        styles.add("2");
        styles.add("3");
        styles.add("4");
        styles.add("5");
        styles.add("6");
        styles.add("7");
        styles.add("8");
        styles.add("9");
        styles.add("10");
        styles.add("11");
        int styleSize = styles.size();
        int average = 100 / styleSize;
        int remain = 100 - average * styleSize;
        for (String style : styles) {
            int probability = average;
            if (style.equals(defaultStyle)) {
                probability += remain;
            }
            probabilityMap.put(style, probability);
        }
        for (Map.Entry<String, Integer> entry : probabilityMap.entrySet()) {
            Log.d(TAG, "key: " + entry.getKey() + " value: " + entry.getValue());
        }
        String style = getResultFromProbability(probabilityMap, defaultStyle);
        Log.d(TAG, "style: " + style + "\n====================");
        return style;
    }

    public static <T> T getResultFromProbability(Map<T, Integer> probability, T defaultType) {
        // examine
        int total = 0;
        for (T ad_type : probability.keySet()) {
            total += probability.get(ad_type);
        }
        if (total != 100) {
            return defaultType;
        }

        // sort
        T sort[] = (T[]) new Object[probability.keySet().size()];
        List<T> tmpList = new ArrayList<>();
        tmpList.addAll(probability.keySet());

        int tag = 0;
        while (tag < sort.length) {
            T tmpMin = tmpList.get(0);
            int count = tmpList.size();
            for (int i = 1; i < count; i++) {
                T type = tmpList.get(i);
                if (probability.get(type) < probability.get(tmpMin)) {
                    tmpMin = type;
                }
            }

            tmpList.remove(tmpMin);
            sort[tag] = tmpMin;
            tag++;
        }

        // get result
        Random random = new Random();
        int resultPorbability = random.nextInt(100) + 1;

        int[] gap = new int[sort.length];
        for (int i = 0; i < sort.length; i++) {
            gap[i] = probability.get(sort[i]);

            int gapIndex = i - 1;
            while (gapIndex >= 0) {
                gap[i] += probability.get(sort[gapIndex]);
                gapIndex--;
            }
        }

        for (int i = 0; i < gap.length; i++) {
            if (i == 0) {
                if (resultPorbability <= gap[0])
                    return sort[0];
            } else if (i == sort.length - 1) {
                if (resultPorbability > gap[i - 1])
                    return sort[sort.length - 1];
            } else if (resultPorbability > gap[i - 1] && resultPorbability <= gap[i]) {
                return sort[i];
            }
        }
        return defaultType;
    }
}
