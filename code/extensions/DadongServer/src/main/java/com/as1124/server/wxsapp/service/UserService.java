package com.as1124.server.wxsapp.service;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.as1124.server.wxsapp.access.WechatPlatformConstants;
import com.as1124.server.wxsapp.database.DatasourceFactory;
import com.as1124.server.wxsapp.database.mapper.UserInfoMapper;
import com.as1124.server.wxsapp.resources.UserInfo;

/**
 * 身份验证服务
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Path("/user")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public class UserService extends AbstractHttpRestService {

	@POST
	@Path("/add")
	@Produces("application/json; charset=UTF-8")
	public Response insertUser(UserInfo user) throws IOException {
		if (user == null || StringUtils.isBlank(user.getOpenid()) || StringUtils.isBlank(user.getUnionid())) {
			return errorResponse(new Exception("新用户信息缺少必要字段"), 1001);
		}
		try (SqlSession session = DatasourceFactory.getDatasource(WechatPlatformConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				List<UserInfo> data = mapper.queryByKey(user);
				if (data != null && !data.isEmpty()) {
					return errorResponse(new Exception("数据库中已经包含该用户信息"), 1002);
				} else {
					mapper.insertUser(user);
					session.commit();
					return successResponse(user);
				}
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		}
	}

	@GET
	@Path("/detail/{userID}")
	@Produces("application/json; charset=UTF-8")
	public Response getUserByID(@PathParam("userID") int userid) throws IOException {
		try (SqlSession session = DatasourceFactory.getDatasource(WechatPlatformConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				return successResponse(mapper.queryByID(userid));
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		}
	}

	@POST
	@Path("/detail")
	@Produces("application/json; charset=UTF-8")
	public Response getUserByKey(UserInfo userInfo) throws IOException {
		try (SqlSession session = DatasourceFactory.getDatasource(WechatPlatformConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				return successResponse(mapper.queryByKey(userInfo));
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		}
	}

	@GET
	@Path("/state/{userID}")
	@Produces("application/json; charset=UTF-8")
	public Response getState(@PathParam("userID") String userID) {
		return Response.status(Status.OK).entity(Boolean.TRUE).build();
	}

	@GET
	@Path("/all")
	@Produces("application/json; charset=UTF-8")
	public Response getAllUsers() throws IOException {
		try (SqlSession session = DatasourceFactory.getDatasource(WechatPlatformConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				return successResponse(mapper.queryAll());
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		}
	}

	@POST
	@Path("/update")
	@Produces("application/json; charset=UTF-8")
	public Response updateUser(UserInfo userInfo) throws IOException {
		try (SqlSession session = DatasourceFactory.getDatasource(WechatPlatformConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				mapper.updateUser(userInfo);
				session.commit();
				return successResponse(userInfo);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		}
	}

	public Response login() {
		return null;
	}

	public Response logOut() {
		return null;
	}

}
