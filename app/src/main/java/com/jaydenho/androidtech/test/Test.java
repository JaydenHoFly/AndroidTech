package com.jaydenho.androidtech.test;

import java.util.Locale;

/**
 * Created by hedazhao on 2016/8/31.
 */
public class Test {

    public static void main(String args[]) {
        UserInfo user1 = new UserInfo("zs");
        final UserInfo user2 = user1;
        UserInfo user3 = user1;
        user1.setName("ls");
        user1 = new UserInfo("wu");
        System.out.println("user2: " + user2.getName() + " & user3: " + user3.getName());
        System.out.println("leagueOrderId: " + getLeagueOrderId(1164, 110));
        System.out.println("leagueOrderId1: " + getLeagueOrderId1(1164, 110));

        BaseAdapterModel model1 = new BaseAdapterModel(1, null, null);
        BaseAdapterModel model2 = new BaseAdapterModel(1, "", null);
        System.out.println("isTheSameAd(model1, model2): " + isTheSameAd(model1, model2));

    }


    /**
     * * @return 联盟广告订单Id 构造规则：ositionId + leagueId4位，leagueId位数不够前置补0，最后转成负数
     */
    public static String getLeagueOrderId(int positionId, int leagueId) {
        return "-" + String.valueOf(positionId) + String.format(Locale.CHINA, "%04d", leagueId);
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
}
