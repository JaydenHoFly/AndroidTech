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
    }

    /**
     * * @return 联盟广告订单Id 构造规则：ositionId + leagueId4位，leagueId位数不够前置补0，最后转成负数
     */
    public static String getLeagueOrderId(int positionId, int leagueId) {
        return "-" + String.valueOf(positionId) + String.format(Locale.CHINA, "%04d", leagueId);
    }

    public static String getLeagueOrderId1(int positionId,int leagueId) {
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
}
