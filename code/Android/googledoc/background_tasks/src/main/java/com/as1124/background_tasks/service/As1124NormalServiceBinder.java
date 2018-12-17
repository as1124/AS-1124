package com.as1124.background_tasks.service;

import android.os.Binder;

class As1124NormalServiceBinder extends Binder {

    private As1124NormalService mService;

    public As1124NormalServiceBinder(As1124NormalService service) {
        this.mService = service;
    }

    public As1124NormalService getService() {
        return mService;
    }
}
