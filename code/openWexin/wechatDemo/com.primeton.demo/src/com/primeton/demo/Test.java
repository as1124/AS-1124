package com.primeton.demo;

import com.alibaba.fastjson.JSONObject;

public class Test {

	String json1 = "{'name':'微服务','sub_button':[{'type':'click','name':'微绑定','key':'primeton_bind'},{'type':'click','name':'账户信息','key':'primeton_userinfo'},{'type':'view','name':'关于我们','url':'http://www.primeton.com'}]}";
	String json2 = "{'name':'菜单','sub_button':[{'type':'view','name':'搜索','url':'http://www.baidu.com'},{'type':'view','name':'视频','url':'http://v.qq.com'},{'type':'click','name':'赞一下','key':'support'}]}";
	String json3 = "{'name':'89&10','sub_button':[{'type':'location_select','name':'位置',key:'send_location'},{'type':'media_id','name':'素材','media_id':'_1Bkhbh4VeP4qhoiN3fILWfe9VZ0EB6oCzRxkMzYJfc'},{'type':'pic_photo_or_album','name':'发图','key':'send_photo'}{'type':'view_limited','name':'新闻','media_id':'_1Bkhbh4VeP4qhoiN3fILcyce975caGASLDiykfnDRw'}]}";
}
