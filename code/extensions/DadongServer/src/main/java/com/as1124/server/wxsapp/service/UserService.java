package com.as1124.server.wxsapp.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

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
	@Path("/all")
	@Produces("application/json; charset=UTF-8")
	public List<UserInfo> getAllUsers() throws IOException {
		try (SqlSession session = DatasourceFactory.getDatasource("").openSession(true);) {
			UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
			if (mapper != null) {
				return mapper.queryAll();
			} else {
				return new ArrayList<UserInfo>();
			}
		}
	}

	@GET
	@Path("/{userID}")
	@Produces("application/json; charset=UTF-8")
	public UserInfo getUserByID(@PathParam("userID") int userid) throws IOException {
		try (SqlSession session = DatasourceFactory.getDatasource("").openSession(true);) {
			UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
			if (mapper != null) {
				return mapper.queryByID(userid);
			} else {
				return null;
			}
		}
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	public UserInfo getUserDetail(@QueryParam("openid") String openid, @QueryParam("telephone") String phoneno)
			throws IOException {
		try (SqlSession session = DatasourceFactory.getDatasource("").openSession(true);) {
			UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
			if (mapper != null) {
				if (StringUtils.isNotBlank(openid)) {
					mapper.queryByOpenid(openid);
				} else if (StringUtils.isNotBlank(phoneno)) {
					mapper.queryByPhoneNo(phoneno);
				}
			}
		}
		return null;
	}

	@POST
	@Produces("application/json; charset=UTF-8")
	public void insertUser(UserInfo user) throws IOException {
		try (SqlSession session = DatasourceFactory.getDatasource("").openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				mapper.insertUser(user);
				session.commit();
				System.out.println("新用户id==" + user.getUserid());
			}
		}
	}

}
