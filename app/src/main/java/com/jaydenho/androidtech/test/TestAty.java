package com.jaydenho.androidtech.test;

import android.app.Activity;
import android.app.Person;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jayden.testcompile2.Calling;
import com.jaydenho.androidtech.AndroidApplicationLike;
import com.jaydenho.androidtech.R;
import com.jaydenho.androidtech.annotation.AnnotationInfo;
import com.jaydenho.androidtech.annotation.DBInterpreter;
import com.jaydenho.androidtech.intent.LearnIntent;
import com.jaydenho.androidtech.logger.LearnLogger;
import com.jaydenho.androidtech.storage.LearnFileStorage;
import com.jaydenho.androidtech.thread.Test;
import com.jaydenho.androidtech.util.CommonUtil;
import com.jaydenho.androidtech.util.FileUtils;
import com.jaydenho.androidtech.util.LocationProvider;
import com.jaydenho.androidtech.widget.view.dialog.LoadingDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xunlei.applyprocessor.Jayden;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hedazhao on 2016/7/26.
 */
public class TestAty extends FragmentActivity {

    private static final String TAG = TestAty.class.getSimpleName();
    private ImageView mImageLoaderTestIv = null;
    private BrowserExchangeDialogPPW mPPW = null;
    private TextView mNameTV = null;

    private boolean mRestore = false;

    private TextView dateTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            setDisplayCutoutMode(getWindow(), WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER);
        }
        com.xunlei.applyprocessor.Jayden jayden = new Jayden();
        setContentView(R.layout.aty_test);
        LearnLogger.learn();
//        Test.test();
//        Toast.makeText(this, "toast", Toast.LENGTH_SHORT).show();
//        mNameTV = (TextView) findViewById(R.id.tv_name);
//        String userAgent = System.getProperty("http.agent");
//        Log.d(TAG, "userAgent: " + userAgent);
//        String userAgent2 = getUserAgent();
//        Log.d(TAG, "userAgent2: " + userAgent2);
//       /*
//        String name = "一二三四五六七";
//        String testStr = "你好，测试。saf,.;1；2：3？4！";
//     *//*   String regex = "[\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b]";
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(testStr);
//        if (m.find()) {
//            Log.d(TAG,"group0: " + m.group(0));
//        }*//*
//        mNameTV.setText(cutTextEllipseEnd(name, 5));
//        mNameTV.setText(Html.fromHtml("&quot;你好&quot;"));

//        String[] strArray = testStr.split("[\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b]");
//        String[] strArray = testStr.split("[\\u3002|\\uff1f|\\uff01|\\uff0c|\\u3001|\\uff1b|\\uff1a|\\u201c|\\u201d|\\u2018|\\u2019|\\uff08|\\uff09|\\u300a|\\u300b|\\u3008|\\u3009|\\u3010|\\u3011|\\u300e|\\u300f|\\u300c|\\u300d|\\ufe43|\\ufe44|\\u3014|\\u3015|\\u2026|\\u2014|\\uff5e|\\ufe4f|\\uffe5]");
//        for (String str : strArray) {
//            Log.d(TAG, " str: " + str);
//        }

//        List<Integer> list = new ArrayList<>();
//        list.add(2);
//        list.add(1);
//        Log.d(TAG, "list.contains(1): " + list.contains(1));
//        list.remove(Integer.valueOf(1));
//        list.remove(list.indexOf(2));
//        Log.d(TAG, Arrays.toString(list.toArray()));
//
//        mImageLoaderTestIv = (ImageView) findViewById(R.id.test_image_loader_iv);
//        *//*ImageLoader.getInstance().displayImage("http://pic34.nipic.com/20131021/11569127_170602617166_2.jpg", mImageLoaderTestIv, getSujectIconDisplayOptions());
//        ImageLoader.getInstance().loadImage("http://pic60.nipic.com/file/20150303/12216461_110913232000_2.jpg", getSujectIconDisplayOptions(), new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String s, View view) {
//
//            }
//
//            @Override
//            public void onLoadingFailed(String s, View view, FailReason failReason) {
//
//            }
//
//            @Override
//            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//
//            }
//
//            @Override
//            public void onLoadingCancelled(String s, View view) {
//
//            }
//        });*//*
//        String str = "我是原始值";
//        checkString(str);
//        Log.d(TAG, "checkString: " + str);
//
//        testProbability();
//
//        mHandler.sendEmptyMessageDelayed(1, 2000);
//
//        a();
//
//        Log.d(TAG, "onCreate");
//        if (savedInstanceState != null) {
//            mRestore = savedInstanceState.getBoolean("restore", mRestore);
//        }
//
//        Log.d(TAG, "branch: " + getPhoneBrand() + " model: " + Build.MODEL + " devide: " + Build.DEVICE + " product" + Build.PRODUCT);
//
//        IdentityHashMap<String, String> map = new IdentityHashMap<>();
//        map.put("show", "1323");
//        map.put("show", "1323");
//        map.put("show", "1324");
//
//        List<String> orderIds = new ArrayList<>();
//        orderIds.add("11377");
//        orderIds.add("11374");
//        orderIds.add("11378");
//        orderIds.add("11371");
//        testSortString(orderIds);*/
//
//        mImageLoaderTestIv = (ImageView) findViewById(R.id.test_image_loader_iv);
//        ImageLoader.getInstance().displayImage("http://pic34.nipic.com/20131021/11569127_170602617166_2.jpg", mImageLoaderTestIv, getSujectIconDisplayOptions());
//        initPPW();
//
//        startLocation();
//        jump();

  /*      new Thread(new Runnable() {
            @Override
            public void run() {
                writeParcel2File();
            }
        }).start();

        Log.d(TAG, "getLeagueOrderId(1137, 114): " + getLeagueOrderId(1137, 114));

        testStringFormat();

        testListFormat();*/

//        printThread();

//        time();
//
//        testIntent("ctrip://wireless/InlandHotel?checkInDate=20170504&checkOutDate=20170505&hotelId=687592&allianceid=288562&sid=960124&sourceid=2504&ouid=Android_Singapore_687592");
//        testIntent("tbopen://m.taobao.com/tbopen/index.html?action=ali.open.nav&module=h5&bootImage=0&source=alimama&h5Url=http%3A%2F%2Fmo.m.taobao.com%2Fpage_201708301727368&appkey=24570756&visa=f9a9733e34b4e74f&packageName=&backURL=");
//        testIntent("thunder://QUFodHRwOi8vZGwwMS44MHMuaW06OTIwLzE1MDQvW+eBq+W9seW/jeiAheWJp+WcuueJiF3kuInml6XmnIjlspvkuIrnmoTliqjnianpqprkubEvW+eBq+W9seW/jeiAheWJp+WcuueJiF3kuInml6XmnIjlspvkuIrnmoTliqjnianpqprkubFfYmQubXA0Wlo=");
//
//        ADIntentUtils.isAcceptedScheme("taobao://item.taobao.com/item.htm?spm=a1z10.3-c.w4002-9705227192.14.6nS5Xy&id=525291527981&c=359c9d34a4c185dfa2338711e8ac3d82&did=ANDROID-09309950e8e545dda88578b6465298ef&taskid=4T5@S-0918100421-RMP-0918-r5J5tvfYJc9NDg11111f16&s=getui");
//
//        MyNotification.notifyTaskNotification(this, "", "", "", 323);
//
//        dateTV = findViewById(R.id.tv_date);
    /*    long time = 1513670339000L;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dateTV.setText(sdf.format(new Date(time)));*/
//        dateTV.setText(getString(R.string.test_translate, 23));
//        dateTV.setText(getString(R.string.test_multi, 1, 2));
//        testEditTextCount();
////        testProgressBar();
//
//        startService(new Intent(this, OppoNotiService.class));
//
////        LearnIntent.testDeeplink(this);
//
//        LearnIntent.testIntentAndUri();
//
//        Log.d(TAG, "getExternalFilesDir(null) == null: " + (getExternalFilesDir(null) == null));
//
//        AnnotationInfo info = new AnnotationInfo();
//        String createTableSql = new DBInterpreter().createTableSql(info.getClass());
//        Log.d(TAG, "createTableSql: " + createTableSql);
//
//        testSamsung();
//
////        jump();
//
//        Calling call = new Calling();
//        call.call();
//
//        Log.d(TAG, "startAlarm1.");
////        new LearnAlarmManager().startAlarm(this);
//
////        jump();
//        testMkdirs();
//
//        LearnFileStorage.printPath(this);
//        LearnFileStorage.traverse(getCacheDir().getParentFile());
//
//        testBackslash();
//        testFakeUnicode();
    }

    /**
     * android P 设置刘海屏显示模式
     * @param window
     * @param mode LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT 默认非全屏使用刘海，全屏不使用刘海
     *             LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES 此flag下不进行应用窗口的限制，窗口可以显示到刘海区域
     *             LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER 此flag下应用窗口一直被限制在刘海区域以下
     */
    public static void setDisplayCutoutMode(Window window, int mode){
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.layoutInDisplayCutoutMode = mode;
            window.setAttributes(lp);
        }
    }

    /**
     * 反斜杠。
     */
    private void testBackslash(){
        String str = "\\uD83D\\uDE0E下载得现金【手机迅雷福利版】\\n \\uD83D\\uDCB0 现在加入送最高【18元】\\n [Packet] 领取链接：";
        Log.d(TAG,"testTran-str=" + str);
        String regex = "\\\\(.*?)(?=[^a-z|^0-9])";
        Matcher matcher = Pattern.compile(regex).matcher(str);
        //无法做到将"\\uD83D"换成"\uD83D", 因为"\\"中，第一个反斜杠是转义符号，第二个反斜杠是真正的反斜杠，但是代码run起来后，就无法把这两个反斜杠换成一个转义符号。
        //在代码中操作字符串，和在txt编辑器中操作字符串是不同的，不同点就在于在txt编辑器中能两个反斜杠都是真正的反斜杠，是可以操作的。
        //testFakeUnicode方法 可以把\\uD83D换成\uD83D.
        String str1 = str.replaceAll(regex,"\\$1");
        Log.d(TAG,"testTran-str1=" + str1+"|str="+str);
    }

    private void testFakeUnicode(){
        String source = "\\uD83D\\uDE0E下载得现金【手机迅雷福利版】\\n \\uD83D\\uDCB0 现在加入送最高【18元】\\n [Packet] 领取链接：";
        if (source.contains("\\u")) {
            StringBuffer buf = new StringBuffer();
            Matcher m = Pattern.compile("\\\\u([0-9A-Fa-f]{4})").matcher(source);
            while (m.find()) {
                try {
                    int cp = Integer.parseInt(m.group(1), 16);
                    m.appendReplacement(buf, "");
                    buf.appendCodePoint(cp);
                } catch (NumberFormatException e) {
                }
            }
            m.appendTail(buf);
            String result = buf.toString();
            Log.d(TAG,"testFakeUnicode-result="+result);
        }
    }

    private void testMkdirs() {
//        File file = new File(Environment.getExternalStorageDirectory(), "xiaoyusan");
        File file = new File(getCacheDir(), "xiaoyusan");
        boolean result;
        if (!file.exists()) {
            result = file.mkdirs();
        } else {
            result = true;
        }
        Log.d(TAG, "testMkdirs--result=" + result + "|path=" + file.getAbsolutePath());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Intent intent = new Intent(this,TestAty2.class);
                startActivity(intent);

                Intent intent1 = new Intent(this,TestAty3.class);
                startActivity(intent1);
//                startTestAty();
//                if (webview != null) {
//                    webview.onPause();
//                }
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                startTestAtyClearTop();
//                if (webview != null) {
//                    webview.onResume();
//                }
//                Intent intent = new Intent(this,TestAty2.class);
//                startActivityForResult(intent,100);
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void testApkInstall() {
        File apkFile = new File("/storage/emulated/0/Android/obb/com.xunlei.downloadprovider/bf43547e9199a54951624fdb78517894.apk");
        if (apkFile.exists()) {
            Log.d(TAG, "testApkInstall. start install.");
            ApkHelper.installApk(this, apkFile);
        }
    }

    /**
     * 拦截超链接
     *
     * @param tv
     */
    private void interceptHyperLink(TextView tv) {
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tv.getText();
        if (text instanceof Spannable) {
            int end = text.length();
            Spannable spannable = (Spannable) tv.getText();
            URLSpan[] urlSpans = spannable.getSpans(0, end, URLSpan.class);
            if (urlSpans.length == 0) {
                return;
            }

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
            // 循环遍历并拦截 所有https://开头的链接
            for (URLSpan uri : urlSpans) {
                String url = uri.getURL();
                CustomUrlSpan customUrlSpan = new CustomUrlSpan(this, url);
                spannableStringBuilder.setSpan(customUrlSpan, spannable.getSpanStart(uri),
                        spannable.getSpanEnd(uri), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            tv.setText(spannableStringBuilder);
        }
    }

    public class CustomUrlSpan extends ClickableSpan {

        private Context context;
        private String url;

        public CustomUrlSpan(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        @Override
        public void onClick(View widget) {
            // 在这里可以做任何自己想要的处理
            Log.d(TAG, "jump url: " + url);
        }
    }

    public class NoUnderlineSpan extends UnderlineSpan {

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }
    }

    private void removeHyperLinkUnderline(TextView tv) {
        CharSequence text = tv.getText();
        if (text instanceof Spannable) {
            Log.i("test", "true");
            Spannable spannable = (Spannable) tv.getText();
            NoUnderlineSpan noUnderlineSpan = new NoUnderlineSpan();
            spannable.setSpan(noUnderlineSpan, 0, text.length(), Spanned.SPAN_MARK_MARK);
        }
    }

    public interface IFilter {
        boolean filter(ApplicationInfo ai, PackageInfo info);
    }

    public void testSamsung() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ReportAPPPackageInfo[] installedAPPsPackageInfo = getPreInstalledAPPsPackageInfo(3, TestAty.this);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ReportAPPPackageInfo[] installedAPPsPackageInfo = getSelfInstalledAPPsPackageInfo(4, TestAty.this);
                try {
                    Thread.sleep(1000);
                    getSelfInstalledAPPsPackageInfo(5, TestAty.this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        getPreInstalledAPPsPackageInfo(1, this);
        getSelfInstalledAPPsPackageInfo(2, this);
    }

    public static ReportAPPPackageInfo[] getSelfInstalledAPPsPackageInfo(int i, Context context) {
        return getInstalledAPPsPackageInfo(i, context, new IFilter() {
            @Override
            public boolean filter(ApplicationInfo ai, PackageInfo info) {
                return !isSystemApp(ai) && !isAndroidSystemApp(info);
            }
        });
    }

    public static ReportAPPPackageInfo[] getPreInstalledAPPsPackageInfo(int i, Context context) {
        return getInstalledAPPsPackageInfo(i, context, new IFilter() {
            @Override
            public boolean filter(ApplicationInfo ai, PackageInfo info) {
                return isSystemApp(ai) && !isAndroidSystemApp(info);
            }
        });
    }

    public static boolean isAndroidSystemApp(PackageInfo info) {
        String regex = "^\\bcom.android.*$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(info.packageName);
        return matcher.matches();
    }

    public static boolean isSystemApp(@NonNull ApplicationInfo ai) {
        return (ai.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM
                || (ai.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
    }

    public static ReportAPPPackageInfo[] getInstalledAPPsPackageInfo(int i, Context context, IFilter filter) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> originalList = pm.getInstalledPackages(0);
        Log.d(TAG, "getInstalledAPPsPackageInfo list: " + originalList.hashCode() + " i: " + i);
        //https://bugly.qq.com/v2/crash-reporting/crashes/900015673/18689023?pid=1
        //三星手机上，遍历已安装列表的时候，会报java.util.ConcurrentModificationException异常，应该是三星手机上的pm.getInstalledPackages()方法返回的List是同一个对象，然后系统还会往这个List中增删元素，所以将originalList拷贝一份，再遍历。
        List<PackageInfo> list;
        if (CommonUtil.changeTypeByDays(true, new Boolean[]{true, false}))
            list = new ArrayList<>(originalList);
        else
            list = originalList;
        ReportAPPPackageInfo spiTmp[] = new ReportAPPPackageInfo[list.size()];
        Iterator<PackageInfo> it = list.iterator();
        int idx = 0;
        while (it.hasNext()) {
            PackageInfo info = it.next();
            try {
                ApplicationInfo ai = pm.getApplicationInfo(info.packageName, 0);
                if (filter.filter(ai, info)) {
                    spiTmp[idx] = new ReportAPPPackageInfo();
                    spiTmp[idx].packageName = info.packageName;
                    spiTmp[idx].displayName = pm
                            .getApplicationLabel(info.applicationInfo).toString();
                    spiTmp[idx].installer = pm.getInstallerPackageName(info.packageName);
                    spiTmp[idx].appInfo = ai;
                    spiTmp[idx].versionCode = info.versionCode;
                    spiTmp[idx].version = info.versionName;
                    spiTmp[idx].firstInstalled = info.firstInstallTime;
                    spiTmp[idx].lastUpdated = info.lastUpdateTime;
                    spiTmp[idx].uid = info.applicationInfo.uid;
                    spiTmp[idx].dataDir = info.applicationInfo.dataDir;
                    spiTmp[idx].targetsdk = ai.targetSdkVersion;
                    spiTmp[idx].systemAPP = (ai.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM;
                    spiTmp[idx].updateSystemAPP = (ai.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
                    spiTmp[idx].flags = ai.flags;
                    idx++;
                }
            } catch (PackageManager.NameNotFoundException exp) {
                Log.d(TAG, "exception: " + exp.getLocalizedMessage());
            }
        }
        // Reminder: the copying is necessary because we are filtering away the
        // system apps.
        ReportAPPPackageInfo spi[] = new ReportAPPPackageInfo[idx];
        System.arraycopy(spiTmp, 0, spi, 0, idx);
        return spi;
    }

    private LoadingDialog d;

    private void testProgressBar() {
        d = new LoadingDialog(this);
        d.setCancelable(false);
    }

    private void startTestAty() {
        Intent intent = new Intent(this, TestAty.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(intent);
    }

    private void startTestAtyClearTop() {
        Intent intent = new Intent(this, TestAty.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void testEditTextCount() {
        final int count = getResources().getInteger(R.integer.feedback_content_count);
        final EditText et = findViewById(R.id.et_count);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "text.length: " + s.toString().length() + "/" + count);
                dateTV.setText(s.toString());
                Pattern p = Pattern.compile("abc://\\S*");
                Linkify.addLinks(dateTV, p, "abc");
                interceptHyperLink(dateTV);
                removeHyperLinkUnderline(dateTV);
            }
        });
    }

    private void testIntent(String url) {
        Intent intent;
        try {
            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            intent.setComponent(null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                intent.setSelector(null);
            }
            ComponentName cn = intent.resolveActivity(getPackageManager());
            Log.d(TAG, "cn: " + (cn == null ? "null" : cn.getShortClassName()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    private SpannableString emoji(String emojiStr) {
        Log.d(TAG, "emojiStr: " + emojiStr);
        SpannableString ss = new SpannableString(emojiStr);
        Pattern pattern = Pattern.compile("\\uD83D\\uDE1A", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            String key = matcher.group();
            Log.d(TAG, "key: " + key);
            ImageSpan imageSpan = new ImageSpan(this, R.drawable.third_qq_circle);
            ss.setSpan(imageSpan, matcher.start(), matcher.end(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    private void time() {
        Calendar c = Calendar.getInstance();
        //没有设置的字段会采用当前时间
        c.set(Calendar.HOUR_OF_DAY, 13);
        c.set(Calendar.MINUTE, 10);
        c.set(Calendar.SECOND, 0);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date(System.currentTimeMillis()));
        Log.d(TAG, "c.getTime: " + c.getTimeInMillis() + " c2.getTime: " + c2.getTimeInMillis());
        Log.d(TAG, "c.after(c2): " + (c.after(c2)));
    }

    private static String getUserAgent() {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(AndroidApplicationLike.getApplicationInstance());
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private void printThread() {
        Map<Thread, StackTraceElement[]> stacks = Thread.getAllStackTraces();
        Set<Thread> set = stacks.keySet();
        for (Thread key : set) {
            StackTraceElement[] stackTraceElements = stacks.get(key);
            Log.d(TAG, "---- print thread: " + key.getName() + " start ----");
            for (StackTraceElement st : stackTraceElements) {
                Log.d(TAG, "StackTraceElement: " + st.toString());
            }
            Log.d(TAG, "---- print thread: " + key.getName() + " end ----");
        }
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
        for (String str : strs) {

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

    private WebView webview;

    private void jump() {
        webview = (WebView) findViewById(R.id.wv);
        webview.setVisibility(View.VISIBLE);
        //        String url = "https://m.maila88.com/mailaIndex?mailaAppKey=MDLwQB58MLrXyAWXiRzzH&mailaOs=android";
//        String url = "https://cas.sh.cn.criteo.com/delivery/r/afr.php?did=59097ccea1ee61ec88f5c69594ef1f00&z=123&u=%7CswkfdbcQtkvui12846RboqJLwRhNPVl34PioXE6yyZw%3D%7C&c1=64huUomFIxsbbfC6BUo1bAIUwNPLnARfF4Ndf6RWTRNKshGo9uOJFa4y7YpWro_E7A97gold_uS11r4nF0bccBvIrKjJhZ4AqRzgtbI5nEF55zoPt8_RpxWuuhJSjLsERRFRmY0_PuZSHbtQSTS4J_tvOsae3ab_VV3HbU0hEjZZY07sx_ywh_fNb9fS9n-XBA2TvhvMY52La7qe1grEZPiwNN1jxaYPQDIGqyNrWVHG5gCAiivf4X65dP7YoGLCxxJZ47IzAuavjHD_yOtuvHYg52IgvrElK_iuQ7pInlVdwka_OU9OM7MFVumvlkeh";
//        String url = "http://dl.xunlei.com/";
//        String url = "http://cat.sh.cn.criteo.com/delivery/ckn.php?cppv=1&cpp=7dHKgHx5TEp6eHVYMVR1U3E2ZlpFMlZWTnpiWDROTml5UmpqWmYwYmZXOFFqektDM3FJb2hZZ2EwMVd6d1lGN1A2dDlzWTNYTmZSV0VObGNUbytjdkJ3dm41U1pZa2FXY2tzazFNZ1lLNzFKVnlwTnJYeWtmWkZhRitJUUFQelhMSy9YWlpHc3pwek5paEp3M05HMGJ3TFhXemlYemRtWEpLN0FHZUcrTDNYZzNmdjFXUTAwU3FINTJrc1B5dTZxWGx5a1J2WGplM3B3eit6OGZlSGhEQnZkWk1SQnBTU1loaWk4UFI0d0R4eWpqdjVrVHhTejRLT2EzSUxWdU1Ia1BGUk9kTVAxTGpJZGpPYXVYRlZrS2h1WkVPdC81YkUzRFRGU2xUWlNUMUxkRndnVU03ODljZGlRZ20vU2dQbE00VGJpZFlMVGNHeFl3MG4xdkk5cGdNa2NMOFRtdDNhZS9qWTFNTFJ0c2Q2dk1lVGNINGNsYTlwam1tWDhvM0VQOHZjK0svNkZ1TlRpRTZ5Ui9FREMra05LVWUwMjhjcUhod0FDZ0RFanpRajdSaDF3dDhuclhCMnVaSlc2YWphanp0TUxvOEN5bE10TURnV3lLK2xkWWNUTHF2MXNob1V6TnJZQ0FETlNObGkyVXc0ND18&maxdest=ctrip%3A%2F%2Fwireless%2FInlandHotel%3FcheckInDate%3D20170504%26checkOutDate%3D20170505%26hotelId%3D687592%26allianceid%3D288562%26sid%3D960124%26sourceid%3D2504%26ouid%3DAndroid_Singapore_687592";
//        String url = "https://m.ctrip.com/webapp/hotel/hoteldetail/687592/checkin-1-7.html?allianceid=288562&sid=964106&sourceid=2504&sepopup=12";
//        String url = "http://blog.csdn.net/jiangwei0910410003/article/details/16859039";
        String url = "http://m.toutiaoimg.cn/i6602523869741842948/";

        adIntentUtils = new ADIntentUtils(this);
        webview.setWebViewClient(new MyWebViewClient());
        webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Log.d(TAG, "downloadUrl: " + url);
            }
        });
        WebSettings ws = webview.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setDatabaseEnabled(true);
        String databasePath = getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        ws.setDatabasePath(databasePath);
        ws.setLoadWithOverviewMode(true);
        // 提高渲染的优先级
        ws.setRenderPriority(WebSettings.RenderPriority.HIGH);// 把图片加载放在最后来加载渲染
        ws.setBlockNetworkImage(false); // 对 网络图片 加载的设置
        ws.setAppCacheEnabled(true);
        String cachePath = getApplicationContext().getCacheDir().getPath();
        ws.setAppCachePath(cachePath);
        ws.setDefaultTextEncodingName("GBK");// 不设置系统默认“latin”，给个gbk
        // 不支持页面缩放
        ws.setSupportZoom(false);
        ws.setBuiltInZoomControls(false);
        ws.setDisplayZoomControls(false);
        //这配置不能删，主要是为了解决手机设置超大字体后，页面显示会错乱，所以强制webview在展示页面时别使用大字体
        ws.setTextSize(WebSettings.TextSize.NORMAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {//设置H5视频可以自动播放
            ws.setMediaPlaybackRequiresUserGesture(false);
        }
        webview.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.d(TAG, "onPageStarted url: " + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d(TAG, "onPageFinished url: " + url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
            //            return adIntentUtils.shouldOverrideUrlLoadingByApp(view, url) || super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Log.d(TAG, "onReceivedError");
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Log.d(TAG, "onReceivedError");
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            Log.d(TAG, "onReceivedSslError");
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            Log.d(TAG, "onReceivedHttpError");
        }
    }

    private void testSortString(List<String> orderIds) {
        Collections.sort(orderIds);
        Log.d(TAG, Arrays.toString(orderIds.toArray()));
    }

    private LocationProvider mLocationProvider = null;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mLocationProvider != null) {
            mLocationProvider.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 获取一次定位信息,用户广告模块
     */
    private void startLocation() {
        mLocationProvider = new LocationProvider(this);
        mLocationProvider.setSimpleLocationListener(new LocationProvider.SimpleLocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "onLocationChanged1");
            }

            @Override
            public void onLocationFail(int errorCode, String errorMsg) {
                Log.d(TAG, "onLocationFail1");
            }
        });
        mLocationProvider.start(true);

        mLocationProvider = new LocationProvider(this);
        mLocationProvider.setSimpleLocationListener(new LocationProvider.SimpleLocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "onLocationChanged2");
            }

            @Override
            public void onLocationFail(int errorCode, String errorMsg) {
                Log.d(TAG, "onLocationFail2");
            }
        });
        mLocationProvider.start(true);
    }

    private void destroyLocation() {
        if (mLocationProvider != null) {
            mLocationProvider.destroy();
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
        stopService(new Intent(this, OppoNotiService.class));
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
        if (requestCode == 100 && resultCode == 1) {
            finish();
        }
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
//        if (!a) {
//            showPPW(mNameTV);
//        }
//        a = true;
    }

    private void initPPW() {
        mImageLoaderTestIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPPW(mNameTV);
                testApkInstall();
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
