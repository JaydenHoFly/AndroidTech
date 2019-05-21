package com.jaydenho.androidtech.algorithm.common.divideandconquer;

import com.morgoo.helper.Log;

/**
 * 汉诺塔问题。
 * <p>
 * 采用分治的思想。
 * <p>
 * 游戏的目的是将所有的圆盘从塔座A移动到塔座B;塔座C用来防止临时圆盘,游戏的规则如下:
 * <p>
 * 　　　　1、一次只能移动一个圆盘
 * <p>
 * 　　　　2、任何时候都不能将一个较大的圆盘压在较小的圆盘上面.
 * <p>
 * 　　　　3、除了第二条限制,任何塔座的最上面的圆盘都可以移动到其他塔座上.
 * <p>
 * Created by hedazhao on 2019/5/6.
 */
public class TowerOfHanoi {
    private static final String TAG = "TowerOfHanoi";

    /**
     * 将{@code count}数量的圆盘从{@code currentPosition}通过{@code tempPosition}移动到{@code targetPosition}
     *
     * @param count           要移动的圆盘数量，圆盘按从小到大的顺序叠放，小的在大的上面。
     * @param currentPosition 圆盘们的当前位置。
     * @param targetPosition  圆盘们的目标位置。
     * @param tempPosition    圆盘们的临时位置。
     */
    private void move(int count, int currentPosition, int targetPosition, int tempPosition) {
        if (count == 1) {
            moveOnce(count, currentPosition, targetPosition);
        } else {
            move(count - 1, currentPosition, tempPosition, targetPosition);
            moveOnce(count, currentPosition, targetPosition);
            move(count - 1, tempPosition, targetPosition, currentPosition);
        }
    }

    /**
     * 将下标为{@code index}的圆盘从{@code currentPosition}移动到{@code targetPosition}
     *
     * @param index           圆盘的下标。
     * @param currentPosition 圆盘的当前位置。
     * @param targetPosition  圆盘的目标位置。
     */
    private void moveOnce(int index, int currentPosition, int targetPosition) {
        Log.d(TAG, "move plate: " + index + " from position: " + currentPosition + " to position: " + targetPosition);
    }
}
