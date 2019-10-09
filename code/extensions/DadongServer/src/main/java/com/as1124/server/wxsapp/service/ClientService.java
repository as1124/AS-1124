package com.as1124.server.wxsapp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.as1124.server.wxsapp.access.WechatPlatformConstants;
import com.as1124.server.wxsapp.database.DatasourceFactory;
import com.as1124.server.wxsapp.database.mapper.AppClientMapper;

/**
 * 客户端配置信息HTTP服务
 * 
 * @author As-1124(mailto:as1124huang@gmail.com)
 *
 */
@Path("/app")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public class ClientService extends AbstractHttpRestService {

	@GET
	@Path("/setting")
	@Produces("application/json; charset=UTF-8")
	public Response getClientSetting(@QueryParam("version") String clientVersion, @QueryParam("type") String platform) {
		int clientType = 0;
		if (StringUtils.isBlank(platform)) {
			clientType = 0;
		} else if (platform.indexOf("ndroid") >= 0) {
			clientType = 1;
		} else if (platform.indexOf("iOS") >= 0) {
			clientType = 2;
		} else if (platform.matches("[0-9]")) {
			clientType = Integer.parseInt(platform);
		}
		if (clientType > 0) {
			try (SqlSession session = DatasourceFactory.getDatasource(WechatPlatformConstants.DB_ENVIRONMENT)
					.openSession(true);) {
				if (session != null) {
					AppClientMapper mapper = session.getMapper(AppClientMapper.class);
					return successResponse(mapper.queryAppSetting(clientType, clientVersion));
				} else {
					return Response.status(Status.ACCEPTED).build();
				}
			} catch (IOException e) {
				return errorResponse(e, 2001);
			}
		} else {
			return successResponse(new ArrayList<HashMap<String, Object>>());
		}
	}

}
