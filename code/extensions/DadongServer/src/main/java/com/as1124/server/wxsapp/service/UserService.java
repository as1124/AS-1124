package com.as1124.server.wxsapp.service;

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

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.as1124.api.wechat.sapp.SAPPService;
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
public class UserService extends AbstractHttpRestService {

	@POST
	@Produces("application/json; charset=UTF-8")
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
				if (data != null && !data.isEmpty()) {
					Exception ex = new Exception(DadongMessages.getString(DadongMessages.USER_1002));
					return errorResponse(ex, Integer.valueOf(DadongMessages.USER_1002));
				} else {
					mapper.insertUser(user);
					session.commit();
					return successResponse(user);
				}
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, Integer.valueOf(DadongMessages.SQL_ERROR));
		}
	}

	@GET
	@Path("/detail/{userID}")
	@Produces("application/json; charset=UTF-8")
	public Response getUserByID(@PathParam("userID") int userid) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				UserAddressMapper addressMapper = session.getMapper(UserAddressMapper.class);
				UserInfo userInfo = mapper.queryByID(userid);
				if (StringUtils.isNoneBlank(userInfo.getOpenid())) {
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

	@POST
	@Path("/detail")
	@Produces("application/json; charset=UTF-8")
	public Response getUserByKey(UserInfo userInfo) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				UserAddressMapper addressMapper = session.getMapper(UserAddressMapper.class);
				List<UserInfo> users = mapper.queryByKey(userInfo);
				for (UserInfo one : users) {
					if (StringUtils.isNoneBlank(one.getOpenid())) {
						one.setExpressAddress(addressMapper.queryExpressAddress(one.getOpenid(), -1));
					}
				}
				return successResponse(users);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, Integer.valueOf(DadongMessages.SQL_ERROR));
		}
	}

	@GET
	@Path("/all")
	@Produces("application/json; charset=UTF-8")
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

	@POST
	@Path("/update")
	@Produces("application/json; charset=UTF-8")
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

	@GET
	@Path("/goodscar/{openid}")
	@Produces("application/json; charset=UTF-8")
	public Response getGoodsCar(@PathParam("openid") String openid) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserInfoMapper mapper = session.getMapper(UserInfoMapper.class);
				GoodsInfoMapper goodsMapper = session.getMapper(GoodsInfoMapper.class);
				String result = mapper.queryGoodsCar(openid);
				JSONArray goodsArray = JSONObject.parseArray(result);
				for (int i = 0; i < goodsArray.size(); i++) {
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

	@POST
	@Path("/goodscar")
	@Produces("application/json; charset=UTF-8")
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

	@POST
	@Path("/address")
	@Produces("application/json; charset=UTF-8")
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

	@GET
	@Path("/address/{openid}")
	@Produces("application/json; charset=UTF-8")
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
	@Produces("application/json; charset=UTF-8")
	public Response updateExpressAddress(UserExpressAddress addressInfo) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserAddressMapper mapper = session.getMapper(UserAddressMapper.class);
				mapper.updateExpressAddress(addressInfo);
				session.commit();
				return successResponse(true);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	@DELETE
	@Path("/address")
	@Produces("application/json; charset=UTF-8")
	public Response deleteExpressAddress(UserExpressAddress addressInfo) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				UserAddressMapper mapper = session.getMapper(UserAddressMapper.class);
				mapper.deleteExpressAddress(addressInfo.getOpenid(), addressInfo.getAddressid());
				return successResponse(true);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	@GET
	@Path("/loginsapp/{authCode}")
	@Produces("application/json; charset=UTF-8")
	public Response login(@PathParam("authCode") String authCode) {
		JSONObject sessionInfo = SAPPService.authCode2Session(As1124AppConstants.WX_APPID, As1124AppConstants.WX_SECRET,
				authCode);
		return successResponse(sessionInfo);
	}

	/**
	 * 解密微信信息数据
	 * 
	 * @return
	 */
	@POST
	@Path("/decrypt")
	@Produces("application/json; charset=UTF-8")
	public Response decryptData(Map<String, Object> dataMap) {
		// 校验签名
		Object signature = dataMap.get("signature");
		Object rawData = dataMap.get("rawData");
		String sig1 = DigestUtils.sha1Hex(rawData.toString() + "7z84c+IsyiKdPkZUBQui8Q==");

		// 解密数据

		Object iv = dataMap.get("iv");
		Object encryptedData = dataMap.get("signature");
		return successResponse(signature);
	}

	public Response logOut() {
		return null;
	}

	public static void main(String[] args) {
		String authCode = "011SHJd31S4jkQ1ShGh31vIQd31SHJda";
		JSONObject sessionInfo = SAPPService.authCode2Session(As1124AppConstants.WX_APPID, As1124AppConstants.WX_SECRET,
				authCode);
		System.out.println("sss");
	}

}
