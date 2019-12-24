package com.as1124.wxsapp.access;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.as1124.wxsapp.sign.service.AbstractHttpRestService;

/**
 * 后端管理服务
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
@Path("/server")
public class ServerSettingService extends AbstractHttpRestService {

	@GET
	public Response reloadServerConfig() {
		try {
			SignAppConstants.reloadFile();
			return successResponse(Boolean.TRUE);
		} catch (Exception ex) {
			return errorResponse(ex, 922);
		}
	}
}
