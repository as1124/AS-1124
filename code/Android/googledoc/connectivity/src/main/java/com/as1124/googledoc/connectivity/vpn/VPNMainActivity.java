package com.as1124.googledoc.connectivity.vpn;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.VpnService;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.widget.Toast;

import com.as1124.googledoc.connectivity.R;

public class VPNMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpn_main);

        findViewById(R.id.but_all_vpn);
        findViewById(R.id.but_add_vpn);
        findViewById(R.id.but_connect_vpn);

        // ask system for permission
        Intent intent = VpnService.prepare(this);
        if (intent == null) {
            Toast.makeText(this, "VPN for app is ready to use", Toast.LENGTH_LONG).show();
        } else {
            startActivityForResult(intent, 124);
            // 操作完成后在系统 VPN 设置中能看到对应APP的VPN选项, 但是不能看到配置信息
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 124 && resultCode == RESULT_OK) {
            Toast.makeText(this, "VPN is ready", Toast.LENGTH_SHORT).show();
        }
    }

    private void getAllVPNSetting() {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void addMyVPN() {
//        VpnService.Builder vpnBuilder = new VpnService.Builder();
//        ParcelFileDescriptor localTunnel = vpnBuilder.addAddress("", 1)
//                .addRoute("", 0)
//                .addDnsServer("").establish();
    }

    private void connectVPN() {

    }
}
