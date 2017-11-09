package com.jaydenho.androidtech.kotlin.learnkotlin

import java.util.concurrent.locks.Lock

/**
 * Created by hedazhao on 2017/11/3.
 */
class Inline {
    val int : Int
        inline get() = 1
    fun test() {
        lambda(true, { false })
        inline(true, { false })
        var l: Lock
//        lock(l, { return })
    }


    fun lambda(a: Boolean, b: () -> Boolean): Boolean {
        return a && b()
    }

    inline fun inline(a: Boolean, b: () -> Boolean): Boolean {
        return a && b()
    }

    inline fun <T> lock(lock: Lock, body: () -> T): T {
        lock.lock()
        try {
            return body()
        } finally {
            lock.unlock()
        }
    }

    fun <T> otherCheck(body: ()-> T){

    }

}