package com.as1124.background_tasks.aidl;

/**
 * Declare any non-default types here with import statements
 *
 * @author as-1124(mailtoas1124huang@gmail.com)
 */
interface IRemoteService {

    /**
     * Request the process ID of this service, to do evil things with it.
     *
     * @return
     */
    int getRemotePID();

    /**
     * Demonstrates some basic types that you can use as parameters and return
     * values in AIDL
     * @param anInt
     * @param aLong
     * @param aBoolean
     * @param aFloat
     * @param aDouble
     * @param str
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String str);
}
