package com.jaydenho.androidtech.test;

/**
 * Created by hedazhao on 2016/8/31.
 */
class UserInfo1 {
    private String mName;

    public UserInfo1(String name) {
        this.mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}

public class TestB {
    public static void main(String args[]) {
        UserInfo1 user1 = new UserInfo1("zs");
        final UserInfo1 user2 = user1;
        UserInfo1 user3 = user1;
        user1.setName("ls");
        user1 = new UserInfo1("wu");
        System.out.println("user2: " + user2.getName() + " & user3: " + user3.getName());
    }
}
