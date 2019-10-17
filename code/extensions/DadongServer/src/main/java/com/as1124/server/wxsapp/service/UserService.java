package com.as1124.server.wxsapp.service;

import java.util.List;
import java.util.Map;

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
import com.as1124.server.wxsapp.database.mapper.UserAddressMapper;
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
	public Response insertUser(UserInfo user) {
		if (user == null || StringUtils.isBlank(user.getOpenid()) || StringUtils.isBlank(user.getAppid())) {
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
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	@GET
	@Path("/detail/{userID}")
	@Produces("application/json; charset=UTF-8")
	public Response getUserByID(@PathParam("userID") int userid) {
		try (SqlSession session = DatasourceFactory.getDatasource(WechatPlatformConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				UserAddressMapper addressMapper = session.getMapper(UserAddressMapper.class);
				UserInfo userInfo = mapper.queryByID(userid);
				if (StringUtils.isNoneBlank(userInfo.getOpenid())) {
					userInfo.setExpressAddress(addressMapper.queryExpressAddress(userInfo.getOpenid()));
				}
				return successResponse(userInfo);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	@POST
	@Path("/detail")
	@Produces("application/json; charset=UTF-8")
	public Response getUserByKey(UserInfo userInfo) {
		try (SqlSession session = DatasourceFactory.getDatasource(WechatPlatformConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				UserAddressMapper addressMapper = session.getMapper(UserAddressMapper.class);
				List<UserInfo> users = mapper.queryByKey(userInfo);
				for (UserInfo one : users) {
					if (StringUtils.isNoneBlank(one.getOpenid())) {
						one.setExpressAddress(addressMapper.queryExpressAddress(one.getOpenid()));
					}
				}
				return successResponse(users);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	@GET
	@Path("/all")
	@Produces("application/json; charset=UTF-8")
	public Response getAllUsers() {
		try (SqlSession session = DatasourceFactory.getDatasource(WechatPlatformConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				return successResponse(mapper.queryAll());
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	@POST
	@Path("/update")
	@Produces("application/json; charset=UTF-8")
	public Response updateUser(UserInfo userInfo) {
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
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	@POST
	@Path("/goodscar")
	@Produces("application/json; charset=UTF-8")
	public Response saveGoodsCar(Map<String, ?> formData) {
		try (SqlSession session = DatasourceFactory.getDatasource(WechatPlatformConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				String openid = String.valueOf(formData.get("openid"));
				String goodscar = formData.get("goodscar").toString();
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				boolean result = mapper.updateGoodsCar(openid, goodscar);
				session.commit();
				return successResponse(result);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	public Response insertExpressAddress() {
		return null;
	}

	public Response updateExpressAddress() {
		return null;
	}

	public Response queryExpressAddress() {
		return null;
	}

	public Response deleteExpressAddress() {
		return null;
	}

	public Response login() {
		return null;
	}

	public Response logOut() {
		return null;
	}

}
