package com.jaydenho.androidtech.test;

import android.graphics.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hedazhao on 2016/8/31.
 */
public class Test {

    public static void main(String args[]) {
        BaseAdapterModel model1 = new BaseAdapterModel(1, null, null);
        BaseAdapterModel model2 = new BaseAdapterModel(1, "", null);
        System.out.println("isTheSameAd(model1, model2): " + isTheSameAd(model1, model2));

        Test t = new Test();
        t.testThreadException();

        Father f = t.new Son();
        Son s = t.new Son();
        Son1 f1 = t.new Son1();
        f.doSomething(new HashMap());
        s.doSomething(new HashMap());
        f1.doSomething(new HashMap());

        List<Long> list1 = new ArrayList<>();
        list1.add(20L);
        list1.add(30L);
        list1.add(20L);
        List<Long> list2 = new ArrayList<>(new HashSet(list1));
        System.out.println("no duplicate list.size: " + list2.size());

        int result = (int) (1073741825 + 0.4f);
        System.out.println("(int) (1073741825 + 0.4f) " + result);
        System.out.println("(int) (65537 + 0.4f) " + (int) (65537 + 0.4f));
        System.out.println("(int) (65533 + 0.4f) " + (int) (65533 + 0.4f));

        isAndroidSystemApp("com.android.xxx");
        isAndroidSystemApp("com.moto.android.xxx");
        isAndroidSystemApp("net.com.android.xxx");

        testSplit1();
        testApk("jinritoutiao");
        testApk("jinritoutiao.apk");
        testApk("");

        isBlank("" +
                "");
        isBlank(" \"");

        System.out.println("getLeagueOrderId(1137, 114): " + getLeagueOrderId(1137, 114));

        testListInert();

        testYu();
    }

    private static void testYu() {
        System.out.println("3 % 2: " +   3 % 2);
        System.out.println("3 % 2.0: " +   3 % 2.0);

    }
    private  static void testListInert() {
        List<String> list = new ArrayList<>();
        list.add(0,"1");
        list.add(1,"2");
    }
    private static boolean isBlank(String text) {
        return text == null || text.trim().length() == 0;
    }

    private static void testApk(String appDownloadName) {
        if (!appDownloadName.endsWith(".apk")) {
            appDownloadName += ".apk";
        }
        System.out.println("appDownloadName: " + appDownloadName);
    }

    private static void testReportByGroup(int size) {
        System.out.println("==========test==========");
        int[] infos = new int[size];
        for (int i = 0; i < size; i++) {
            infos[i] = i;
        }
        reportByGroup(true, infos);
    }

    private static void testSplit1() {
        String alliance = "100:1";
        String[] allianceArgs = alliance.split(":");
        alliance = allianceArgs.length != 0 ? allianceArgs[0] : "101";
        String styleId = "0";
        styleId = alliance.length() > 1 ? allianceArgs[1] : styleId;
        System.out.println("alliance: " + alliance + " styleId: " + styleId);
    }

    public static boolean isAndroidSystemApp(String info) {
        String regex = "^\\bcom.android.*$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(info);
        boolean result = matcher.matches();
        System.out.println("info is android system app: " + result);
        return result;
    }

    protected static void reportByGroup(boolean isSystemApps, int[] infos) {
        int maxInfosIndex = Math.min(infos.length, 500) - 1;
        int nextInfosGroupFirstIndex = 0;//下一组infos中的第一个info的下标(在总infos中的下标)
        int groupSize;
        int[] infosGroup;
        String reportJsonString;
        while (nextInfosGroupFirstIndex <= maxInfosIndex) {//当下一组中的第一个元素下标等于最大下标时,说明还有一个元素需要上报
            System.out.println("---------test-group---------");
            groupSize = Math.min(maxInfosIndex - nextInfosGroupFirstIndex + 1, 30);
            infosGroup = new int[groupSize];
            System.arraycopy(infos, nextInfosGroupFirstIndex, infosGroup, 0, groupSize);
            System.out.println("reportJsonString : " + Arrays.toString(infosGroup));
            nextInfosGroupFirstIndex += groupSize;
        }
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


    /**
     * * @return 联盟广告订单Id 构造规则：ositionId + leagueId4位，leagueId位数不够前置补0，最后转成负数
     */
    public static String getLeagueOrderId(int positionId, int leagueId) {
        return "-" + String.valueOf(positionId) + String.format(Locale.ENGLISH, "%04d", leagueId);
    }

    public static String getLeagueOrderId1(int positionId, int leagueId) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(positionId));
        sb.append(String.format(Locale.CHINA, "%04d", leagueId));
        long orderIdLong = 0;
        try {
            orderIdLong = Long.parseLong(sb.toString()) * -1;
        } catch (NumberFormatException e) {
        }
        return String.valueOf(orderIdLong);
    }

    public static boolean isTheSameAd(BaseAdapterModel firstDatum, BaseAdapterModel secondDatum) {
        if (firstDatum == secondDatum) {
            return true;
        }
        if (firstDatum == null || secondDatum == null) {
            return false;
        }
        if (firstDatum.getADSourceType() != secondDatum.getADSourceType()) {
            return false;
        }
        if (firstDatum.getTitle() == null && secondDatum.getTitle() == null) {//都等于null,说明是同一个广告
            return true;
        }
        if (firstDatum.getTitle() != null && secondDatum.getTitle() != null) {//都不等于null,就可以用equals判断,不会有空指针异常
            if (firstDatum.getTitle().equals(secondDatum.getTitle())) {//相同,说明是同一个广告
                return true;
            }
        }
        if (firstDatum.getIconUrl() == null && secondDatum.getIconUrl() == null) {
            return true;
        }
        if (firstDatum.getIconUrl() != null && secondDatum.getIconUrl() != null) {
            if (firstDatum.getIconUrl().equals(secondDatum.getIconUrl())) {
                return true;
            }
        }
        return false;
    }

    public class Father {
        public void doSomething(Map map) {
            System.out.print("by father");
        }
    }

    public class Son extends Father {
        @Override
        public void doSomething(Map map) {
            super.doSomething(map);
        }

        public void doSomething(HashMap map) {
            System.out.print("by son");
        }
    }

    public class Father1 {
        public void doSomething(HashMap map) {
            System.out.print("1 by father");
        }
    }

    public class Son1 extends Father1 {
        @Override
        public void doSomething(HashMap map) {
            super.doSomething(map);
        }

        public void doSomething(Map map) {
            System.out.println("1 by son");
        }
    }
}
