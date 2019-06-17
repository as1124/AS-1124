package com.as1124.ch4;

import java.util.concurrent.Callable;
import java.lang.Runnable;

/**
 * 和{@linkplain Runnable}相比, Callable执行可以提供返回结果以及可以抛出异常.
 * {@link Callable#call()}方法在异步线程中执行
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "[Callable]线程返回的结果";
    }
}
