package com.as1124.syx.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.mobile.thirdparty.access.HttpExecuter;

/**
 * SYX调查问卷（腾讯问卷）内容Proxy-Service服务
 *
 * @author huangjw (mailto:as1124huang@gmail.com)
 */
@Path("/sur")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public class SurveyService {

	/**
	 * 针对腾讯问卷通用的方法获取问卷内容
	 * 
	 * @param surveyID
	 * @param hashCode
	 * @param looger
	 * @param field
	 * @return
	 * @throws IOException
	 */
	@GET
	@Path("/get_survey")
	@Produces("applicationi/json;charset=UTF-8")
	public Response getSurveyInfo(@QueryParam("id") String surveyID, @QueryParam("hash") String hashCode,
			@QueryParam("logger") String looger, @QueryParam("_") String field) throws IOException {
		String requestURL = "https://wj.qq.com/sur/get_survey";
		List<NameValuePair> parameters = new ArrayList<>();
		parameters.add(new BasicNameValuePair("id", surveyID));
		parameters.add(new BasicNameValuePair("hash", hashCode));
		parameters.add(new BasicNameValuePair("logger", looger));
		parameters.add(new BasicNameValuePair("_", field));
		requestURL = requestURL + "?" + URLEncodedUtils.format(parameters, "UTF-8");
		CloseableHttpClient httpClient = HttpExecuter.createCustomHttpClient(60 * 1000, 60 * 1000);
		HttpGet getMethod = new HttpGet(requestURL);
		getMethod.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		getMethod.setHeader("Accept-Encoding", "gzip, deflate");
		getMethod.setHeader("Accept-Language", "zh-Hans-CN, zh-Hans; q=0.8, en-GB; q=0.5, en; q=0.3");
		getMethod.setHeader("Cache-Control", "no-cache");
		getMethod.setHeader("Referer", "https://wj.qq.com/s/" + surveyID + "/" + hashCode);
		getMethod.setHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)");
		getMethod.setHeader("X-Requested-With", "XMLHttpRequest");

		HttpResponse response = null;
		try {
			response = httpClient.execute(getMethod);
			HttpEntity entity = response.getEntity();
			byte[] datas = EntityUtils.toByteArray(entity);
			EntityUtils.consume(entity);
			String result = new String(datas, "utf-8");
			return Response.status(Response.Status.OK)
					.header("Referer", "https://wj.qq.com/s/" + surveyID + "/" + hashCode).entity(result).build();
		} finally {
			getMethod.releaseConnection();
			httpClient.close();
		}
	}

}
