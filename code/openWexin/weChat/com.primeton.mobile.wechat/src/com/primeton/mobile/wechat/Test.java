package com.primeton.mobile.wechat;

import java.io.IOException;

import com.primeton.mobile.wechat.exception.WechatExceprion;
import com.primeton.mobile.wechat.model.AccessToken;
import com.primeton.mobile.wechat.model.media.WechatMediaList;

public class Test {

	public static void main(String[] args) throws IOException, WechatExceprion {
		String appID = "wxc70c5d9aab4f6a2b";
		String appSecret = "f3ca23ccf76c301f2158862db65cfdad";
		String[] openid = {"oB1fEuFzGh6e2EMg5Ac5c9xugaRQ", "oB1fEuO7PiqHvU-kExC7N3jbAy40"};
		AccessToken token = CommonOperations.getAccessToken(appID, appSecret);
		WechatMediaList list = MediaOperations.getPermaentMediaList(token.getAccess_token(), "image", 0, 10);
		for(int i=0; i<list.getItem_count(); i++)
			HotlineService.sendImage(token.getAccess_token(), openid[0], list.getItem().get(i).getMedia_id());

	}

}
