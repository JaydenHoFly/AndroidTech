package com.jayden.learnbenchmark;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.benchmark.BenchmarkRule;
import androidx.benchmark.BenchmarkState;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
class MyBenchmark {
    @Rule
    public BenchmarkRule benchmarkRule = new BenchmarkRule();

    @Test
    public void myBenchmark() {
        final BenchmarkState state = benchmarkRule.getState();
        while (state.keepRunning()) {
            doSomeWork();
        }
    }
}