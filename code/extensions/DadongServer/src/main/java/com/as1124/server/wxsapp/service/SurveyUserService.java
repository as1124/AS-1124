package com.as1124.server.wxsapp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.mobile.thirdparty.access.HttpExecuter;

/**
 * SYX调查问卷（腾讯问卷）请求用户信息Proxy-Service
 *
 * @author huangjw (mailto:as1124huang@gmail.com)
 */
@Path("/survey")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public class SurveyUserService {

	@GET
	@Path("/user_info")
	@Produces("applicationi/json;charset=UTF-8")
	public Response getUserInfo(@QueryParam("_") String timestamp, @HeaderParam("Referer") String refer,
			@Context HttpRequest request) throws IOException {
		String requestURL = "https://wj.qq.com/survey/user_info";
		List<NameValuePair> parameters = new ArrayList<>();
		parameters.add(new BasicNameValuePair("_", timestamp));
		requestURL = requestURL + "?" + URLEncodedUtils.format(parameters, "UTF-8");
		CloseableHttpClient httpClient = HttpExecuter.createCustomHttpClient(60 * 1000, 60 * 1000);
		HttpGet getMethod = new HttpGet(requestURL);
		getMethod.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		getMethod.setHeader("Accept-Encoding", "gzip, deflate, br");
		getMethod.setHeader("Accept-Language", "zh-CN,zh; q=0.9");
		getMethod.setHeader("Cache-Control", "no-cache");
		getMethod.setHeader("Referer", refer);
		getMethod.setHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)");
		getMethod.setHeader("X-Requested-With", "XMLHttpRequest");

		HttpResponse response = null;
		try {
			response = httpClient.execute(getMethod);
			HttpEntity entity = response.getEntity();
			byte[] datas = EntityUtils.toByteArray(entity);
			EntityUtils.consume(entity);
			String result = new String(datas, "utf-8");
			return Response.status(Response.Status.OK).entity(result).build();
		} finally {
			getMethod.releaseConnection();
			httpClient.close();
		}
	}

}
