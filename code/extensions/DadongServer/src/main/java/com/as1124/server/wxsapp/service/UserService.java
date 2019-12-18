package com.as1124.server.wxsapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.as1124.api.wechat.sapp.SappBaseService;
import com.as1124.server.wxsapp.access.As1124AppConstants;
import com.as1124.server.wxsapp.database.DatasourceFactory;
import com.as1124.server.wxsapp.database.mapper.GoodsInfoMapper;
import com.as1124.server.wxsapp.database.mapper.UserAddressMapper;
import com.as1124.server.wxsapp.database.mapper.UserInfoMapper;
import com.as1124.server.wxsapp.resources.GoodsInfo;
import com.as1124.server.wxsapp.resources.UserExpressAddress;
import com.as1124.server.wxsapp.resources.UserInfo;

/**
 * 用户相关信息HTTP服务
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Path("/user")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
@Produces("application/json; charset=UTF-8")
public class UserService extends AbstractHttpRestService {

	/**
	 * 用户初次登录时插入
	 * 
	 * @param user
	 * @return 包含用户插入后id的对象实体, 一个
	 */
	@POST
	public Response insertUser(UserInfo user) {
		if (user == null || StringUtils.isBlank(user.getOpenid()) || StringUtils.isBlank(user.getAppid())) {
			Exception ex = new Exception(DadongMessages.getString(DadongMessages.USER_1001));
			return errorResponse(ex, Integer.valueOf(DadongMessages.USER_1001));
		}
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				List<UserInfo> data = mapper.queryByKey(user);
				if (data == null || data.isEmpty()) {
					mapper.insertUser(user);
					session.commit();
					return successResponse(user);
				} else {
					// 用户已经存在
					Exception ex = new Exception(DadongMessages.getString(DadongMessages.USER_1002));
					return errorResponse(ex, Integer.valueOf(DadongMessages.USER_1002));
				}
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, Integer.valueOf(DadongMessages.SQL_ERROR));
		}
	}

	/**
	 * 根据数据库id号查询用户，精确查询
	 * 
	 * @param userid
	 * @return 一个结果
	 */
	@GET
	@Path("/detail/{userID}")
	public Response getUserByID(@PathParam("userID") int userid) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				UserAddressMapper addressMapper = session.getMapper(UserAddressMapper.class);
				UserInfo userInfo = mapper.queryByID(userid);
				if (userInfo != null) {
					userInfo.setExpressAddress(addressMapper.queryExpressAddress(userInfo.getOpenid(), -1));
				}
				return successResponse(userInfo);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, Integer.valueOf(DadongMessages.SQL_ERROR));
		}
	}

	/**
	 * 关键词模糊查询
	 * 
	 * @param userInfo
	 * @return 数组
	 */
	@POST
	@Path("/detail")
	public Response getUserByKey(UserInfo userInfo) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				UserAddressMapper addressMapper = session.getMapper(UserAddressMapper.class);
				List<UserInfo> users = mapper.queryByKey(userInfo);
				for (UserInfo one : users) {
					one.setExpressAddress(addressMapper.queryExpressAddress(one.getOpenid(), -1));
				}
				return successResponse(users);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, Integer.valueOf(DadongMessages.SQL_ERROR));
		}
	}

	/**
	 * 所有已注册用户
	 * 
	 * @return 数组
	 */
	@GET
	@Path("/all")
	public Response getAllUsers() {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				return successResponse(mapper.queryAll());
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, Integer.valueOf(DadongMessages.SQL_ERROR));
		}
	}

	/**
	 * 更新用户信息
	 * 
	 * @param userInfo
	 * @return true-成功
	 */
	@POST
	@Path("/update")
	public Response updateUser(UserInfo userInfo) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				mapper.updateUser(userInfo);
				session.commit();
				return successResponse(true);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, Integer.valueOf(DadongMessages.SQL_ERROR));
		}
	}

	/************************************
	 * 购物车HTTP Service
	 **********************************************/

	/**
	 * 查询用户购物车信息
	 * 
	 * @param openid
	 * @return
	 */
	@GET
	@Path("/goodscar/{openid}")
	public Response getGoodsCar(@PathParam("openid") String openid) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				GoodsInfoMapper goodsMapper = session.getMapper(GoodsInfoMapper.class);
				String result = mapper.queryGoodsCar(openid);
				JSONArray goodsArray = JSON.parseArray(result);
				for (int i = 0; i < goodsArray.size(); i++) {
					// 根据商品编号填充商品详情
					JSONObject one = goodsArray.getJSONObject(i);
					GoodsInfo goodsDetail = goodsMapper.queryGoodsByID(one.getString("goodsno"));
					one.putAll((JSONObject) JSONObject.toJSON(goodsDetail));
				}
				return successResponse(goodsArray);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, Integer.valueOf(DadongMessages.SQL_ERROR));
		}
	}

	/**
	 * 保存购物车数据
	 * <p>
	 * <ul>
	 * <li>openid 用户公众号下openid
	 * <li>goodsCar 购物车JSON数据
	 * </ul>
	 * </p>
	 * 
	 * @param formData
	 * @return
	 */
	@POST
	@Path("/goodscar")
	public Response saveGoodsCar(Map<String, ?> formData) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				String openid = String.valueOf(formData.get("openid"));
				Object obj = formData.get("goodsCar");
				JSONArray newGoodsCar = new JSONArray();
				if (obj != null) {
					List<?> goodsArray = (List<?>) obj;
					for (int i = 0; i < goodsArray.size(); i++) {
						JSONObject data = new JSONObject();
						Map<?, ?> detail = (Map<?, ?>) goodsArray.get(i);
						data.put("goodsno", detail.get("goodsno"));
						data.put("goodsNum", detail.get("goodsNum"));
						data.put("goodsPrice", detail.get("price"));
						data.put("selectedSubtype", detail.get("selectedSubtype"));
						newGoodsCar.add(data);
					}
				}
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				boolean result = mapper.updateGoodsCar(openid, newGoodsCar.toJSONString());
				session.commit();
				return successResponse(result);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, Integer.valueOf(DadongMessages.SQL_ERROR));
		}
	}

	/**
	 * 保存收货地址信息
	 * 
	 * @param addressInfo
	 * @return
	 */
	@POST
	@Path("/address")
	public Response insertExpressAddress(UserExpressAddress addressInfo) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserAddressMapper mapper = session.getMapper(UserAddressMapper.class);
				mapper.insertExpressAddress(addressInfo);
				session.commit();
				return successResponse(addressInfo);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	/**
	 * 查询用户的收货地址
	 * 
	 * @param openid
	 * @param addressID
	 * @return
	 */
	@GET
	@Path("/address/{openid}")
	public Response queryExpressAddress(@PathParam("openid") String openid, @QueryParam("id") int addressID) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserAddressMapper mapper = session.getMapper(UserAddressMapper.class);
				return successResponse(mapper.queryExpressAddress(openid, addressID));
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	@POST
	@Path("/address/update")
	public Response updateExpressAddress(UserExpressAddress addressInfo) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserAddressMapper mapper = session.getMapper(UserAddressMapper.class);
				boolean result = mapper.updateExpressAddress(addressInfo);
				session.commit();
				return successResponse(result);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	@DELETE
	@Path("/address")
	public Response deleteExpressAddress(UserExpressAddress addressInfo) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserAddressMapper mapper = session.getMapper(UserAddressMapper.class);
				boolean result = mapper.deleteExpressAddress(addressInfo.getOpenid(), addressInfo.getAddressid());
				return successResponse(result);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	/**
	 * 小程序授权登录
	 * 
	 * @param authCode
	 * @return
	 */
	@GET
	@Path("/loginsapp/{authCode}")
	@Produces("application/json; charset=UTF-8")
	public Response login(@PathParam("authCode") String authCode) {
		JSONObject sessionInfo = SappBaseService.authCode2Session(As1124AppConstants.WX_APPID, As1124AppConstants.WX_SECRET, authCode);
		int errcode = 0;
		if (sessionInfo.containsKey("errcode")) {
			errcode = sessionInfo.getIntValue("errcode");
		}
		if (errcode == 0) {
			UserInfo user = new UserInfo();
			user.setOpenid(sessionInfo.getString("openid"));
			if (sessionInfo.containsKey("unionid")) {
				user.setUnionid(sessionInfo.getString("unionid"));
			}
			try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
					.openSession(true)) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				List<UserInfo> result = mapper.queryByKey(user);
				if (result == null || result.isEmpty()) {
					mapper.insertUser(user);
				}
			} catch (IOException e) {
				// 插入用户失败
			}
		}
		return successResponse(sessionInfo);
	}

	public Response decryptUserData(Map<String, ?> formData, String openid) {
		// ATTENTION 未完成
		Object encryptData = formData.get("encryptData");
		Object sessionKey = formData.get("sessionKey");
		Object iv = formData.get("iv");
		if (encryptData != null && sessionKey != null && iv != null) {
			new Thread(() -> {
				String jsonData = SappBaseService.decryptUserInfo(encryptData.toString(), sessionKey.toString(),
						iv.toString());
				JSON.parseObject(jsonData);
			}).start();
			return successResponse(true);
		} else {
			return successResponse(false);
		}
	}
}
