package com.jaydenho.androidtech.widget.view.custom.surfaceview.flappybird;

/**
 * Created by hedazhao on 2017/11/17.
 */

public interface IGameComponent {
    void onGameCreate();

    void onGameDestroy();

    void onStatusChanged(@FlappyBird.Status int status);
}
