package com.as1124.ch5;

/**
 * @author as-1124(mail:as1124huang@gmail.com)
 */
public class WechatConstants {

    public static String WECHAT_TOKEN = "";

    public static final String DEBUG_APP_KEY = "wxc70c5d9aab4f6a2b";

    public static final String DEBUG_APP_SECRET = "f3ca23ccf76c301f2158862db65cfdad";

    public static final String API_GET_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?appid={0}&secret={1}&grant_type=client_credential";

    public static final String API_MARK_USER = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token={0}";

}
