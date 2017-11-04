package com.as1124.syx.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * 身份验证服务
 *
 * @author huangjw (mailto:as1124huang@gmail.com)
 */
@Path("/userState")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public class LoginService {

	@GET
	@Path("/{userID}")
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
}
