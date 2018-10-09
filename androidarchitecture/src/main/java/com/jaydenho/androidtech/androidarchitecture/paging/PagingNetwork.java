package com.jaydenho.androidtech.androidarchitecture.paging;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hedazhao on 2018/10/9.
 */
public class PagingNetwork {
    public static int index;

    public static List<Concert> request(int size) {
        List<Concert> concerts = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Concert concert = new Concert();
            long number = index++;
            concert.setId(number);
            concert.setText("pageing-" + number);
            concerts.add(concert);
        }
        return concerts;
    }
}
