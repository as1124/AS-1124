package com.as1124.spring.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInitializer;
import org.springframework.web.client.RestTemplate;

import com.as1124.spring.rest.pojo.AuthorXMLPojo;
import com.as1124.spring.web.model.UserInfo;

/**
 * Spring REST Client API 使用示例
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class SpringRestTemplateClient {

	private static final String HOST = "http://localhost:8080/springstudy/rest";

	public static void main(String[] args) {
		getJsonUser();
		getXmlUser();
	}

	@SuppressWarnings("unchecked")
	private static void getJsonUser() {
		RestTemplate restTemp = new RestTemplate();
		List<ClientHttpRequestInitializer> requestInitor = new ArrayList<ClientHttpRequestInitializer>();
		requestInitor.add(createRequestInitializer());
		restTemp.setClientHttpRequestInitializers(requestInitor);
		List<UserInfo> result = new ArrayList<>();
		result = restTemp.getForObject(HOST + "/user/{uid}", result.getClass(), 22);
		System.out.println(result.size());
	}

	private static void getXmlUser() {
		RestTemplate restTemp = new RestTemplate();
		List<ClientHttpRequestInitializer> requestInitor = new ArrayList<ClientHttpRequestInitializer>();
		requestInitor.add(createRequestInitializer());
		restTemp.setClientHttpRequestInitializers(requestInitor);
		AuthorXMLPojo result = restTemp.getForObject(HOST + "/userxml/one", AuthorXMLPojo.class);
		System.out.println(result.getAuthorName());
	}

	private static ClientHttpRequestInitializer createRequestInitializer() {
		return (request) -> {
			request.getHeaders().setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
		};
	}
}
