package com.as1124.googledoc.connectivity.vpn;

import android.content.Intent;
import android.net.VpnService;
import android.os.IBinder;

/**
 * 配置VPN需要有自己的Service. Declare the service, the system can automatically start and stop
 * my app's VPN service when need.
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124VPNService extends VpnService {

    public As1124VPNService() {
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
