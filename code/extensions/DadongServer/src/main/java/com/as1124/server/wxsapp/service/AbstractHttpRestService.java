package com.as1124.server.wxsapp.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;

/**
 * 基础Http-REST服务封装
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public abstract class AbstractHttpRestService {

	@Context
	protected HttpRequest httpRequest;

	@Context
	protected HttpResponse httpResponse;

	public Map<String, Object> buildDataMap(String msg, int errcode) {
		HashMap<String, Object> dataMap = new HashMap<>();
		if (errcode != 0) {
			dataMap.put("errcode", errcode);
			dataMap.put("errmsg", msg);
		} else {
			dataMap.put("errcode", 0);
			dataMap.put("errmsg", "");
		}
		return dataMap;
	}

	/**
	 * 接口成功返回
	 * @param resultData 结果集
	 * @return
	 */
	public Response successResponse(Object resultData) {
		Map<String, Object> resultMap = buildDataMap("", 0);
		resultMap.put("result", resultData);
		return Response.status(Status.OK).encoding("UTF-8").entity(resultMap).build();
	}

	/**
	 * 异常或错误时返回
	 * @param ex
	 * @param errcode
	 * @return
	 */
	public Response errorResponse(Exception ex, int errcode) {
		return Response.status(Status.OK).encoding("UTF-8").entity(buildDataMap(ex.getMessage(), errcode)).build();
	}

	/**
	 * @return Returns the {@link httpRequest}.
	 */
	public HttpRequest getHttpRequest() {
		return httpRequest;
	}

	/**
	 * @param httpRequest The {@link httpRequest} to set.
	 */
	protected void setHttpRequest(HttpRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	/**
	 * @return Returns the {@link httpResponse}.
	 */
	public HttpResponse getHttpResponse() {
		return httpResponse;
	}

	/**
	 * @param httpResponse The {@link httpResponse} to set.
	 */
	protected void setHttpResponse(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

}
