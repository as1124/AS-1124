package com.as1124.server.wxsapp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.as1124.server.wxsapp.database.DatasourceFactory;
import com.as1124.server.wxsapp.database.mapper.AppClientMapper;

/**
 * 客户端信息
 * 
 * @author As-1124(mailto:as1124huang@gmail.com)
 *
 */
@Path("/app")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public class ClientService {

	@GET
	@Path("/configuration")
	@Produces("application/json; charset=UTF-8")
	public List<HashMap<String, Object>> getClientSetting(@QueryParam("version") String clientVersion,
			@QueryParam("type") String platform) {
		int clientType = 1;
		if (StringUtils.isBlank(platform)) {
			clientType = 0;
		} else if (platform.indexOf("ndroid") >= 0) {
			clientType = 1;
		} else if (platform.indexOf("iOS") >= 0) {
			clientType = 2;
		}
		if (clientType > 0) {
			try (SqlSession session = DatasourceFactory.getDatasource("").openSession(true);) {
				if (session != null) {
					AppClientMapper mapper = session.getMapper(AppClientMapper.class);
					return mapper.queryAppSetting(clientVersion, clientType);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new ArrayList<HashMap<String, Object>>();
	}

	public static void main(String[] args) {
		new ClientService().getClientSetting("", "Android");
	}
}
