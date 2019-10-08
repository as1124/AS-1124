package com.as1124.server.wxsapp.database.mapper;

import java.util.HashMap;
import java.util.List;

public interface AppClientMapper {

	public List<HashMap<String, Object>> queryAppSetting(String clientVersion, int clientType);
}
