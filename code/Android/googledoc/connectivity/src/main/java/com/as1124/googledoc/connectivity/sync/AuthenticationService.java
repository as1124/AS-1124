package com.as1124.googledoc.connectivity.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


/**
 * Android Sync Adapter Framework需要使用Service配合使用
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class AuthenticationService extends Service {

    // Instance field that stores the authenticator object
    private Authenticator mAuthenticator;

    public AuthenticationService() {
    }

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new Authenticator(this);
    }

    /**
     * When the system binds to this Service to make the RPC call
     *
     * @param intent
     * @return the authenticator's IBinder
     */
    @Override
    public IBinder onBind(Intent intent) {
        String action = intent.getAction();

        return mAuthenticator.getIBinder();
    }
}
