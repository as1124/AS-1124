package com.as1124.server.wxsapp.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.as1124.server.wxsapp.resources.UserInfo;

/**
 * 身份验证服务
 *
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
@Path("/user")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public class UserService {

	@GET
	@Path("/state/{userID}")
	@Produces("application/json; charset=UTF-8")
	public Response getState(@PathParam("userID") String userID) {
		return Response.status(Status.OK).entity(Boolean.TRUE).build();
	}

	public Response login() {
		return null;
	}

	public Response logOut() {
		return null;
	}
	
	@GET
	@Path("/{userID}")
	@Produces("application/json; charset=UTF-8")
	public UserInfo getUserDetail(@PathParam("userID")String userid) {
		UserInfo info = new UserInfo();
		info.setId(123);
		info.setWechat("huangj");
		info.setWechatName("黄家伟");
		
		return info;
	}
	
}
