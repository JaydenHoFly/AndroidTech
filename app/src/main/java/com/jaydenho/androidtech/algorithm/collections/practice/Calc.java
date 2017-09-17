package com.jaydenho.androidtech.algorithm.collections.practice;

import java.util.Stack;

/**
 * Created by Administrator on 2017/9/17.
 */

public class Calc {
    private Stack<Object> mMiddleStack = null;
    private Stack<Object> mLastFixStack = null;

    public Calc() {
        mMiddleStack = new Stack<>();
        mLastFixStack = new Stack<>();
    }

    private boolean isOperator(char c) {
        return c == '*'
                || c == '\\'
                || c == '+'
                || c == '-';
    }

    private void parse(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = getChar(input, i);
            if(isOperator(c)) {
                char k = (char) mMiddleStack.peek();
                mMiddleStack.add(c);
            }
        }
    }

    private char getChar(String input, int pos) {
        return 'a';
    }
}
