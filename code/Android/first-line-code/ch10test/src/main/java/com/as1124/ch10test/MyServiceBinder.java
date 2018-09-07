package com.as1124.ch10test;

import android.app.Service;
import android.os.Binder;

/**
 * 通过Binder可以在Activity中连接Service实例
 *
 * @author as-1124(mailto:as1124huang)
 */
public class MyServiceBinder extends Binder {

    private Service serviceRef;

    public MyServiceBinder(Service service) {
        this.serviceRef = service;
    }

    public Service getServiceRef() {
        return serviceRef;
    }

    public void setServiceRef(Service serviceRef) {
        this.serviceRef = serviceRef;
    }
}
