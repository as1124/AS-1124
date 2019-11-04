package com.as1124.server.wxsapp.service;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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

import com.as1124.server.wxsapp.access.As1124AppConstants;
import com.as1124.server.wxsapp.database.DatasourceFactory;
import com.as1124.server.wxsapp.database.mapper.GoodsOrderMapper;
import com.as1124.server.wxsapp.resources.GoodsInfo;
import com.as1124.server.wxsapp.resources.GoodsOrder;

/**
 * 商品订单HTTP服务
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Path("/order")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public class GoodsOrderService extends AbstractHttpRestService {

	/**
	 * 创建订单
	 * @param orderInfo 订单信息
	 * @param requestid 请求id，用于防止订单重复提交
	 * @return
	 */
	@POST
	@PathParam("/{requestid}")
	@Produces("application/json; charset=UTF-8")
	public Response createOrder(GoodsOrder orderInfo, @PathParam("requestid") String requestid) {
		// 防止订单重复提交
		// 提交了订单后需要更新用户的购物车
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				GoodsOrderMapper mapper = session.getMapper(GoodsOrderMapper.class);
				String orderid = generateOrderNo("DD", As1124AppConstants.WX_APPID, orderInfo.getOpenid());
				orderInfo.setOrderid(orderid);
				mapper.newGoodsOrder(orderInfo);
				session.commit();
				return successResponse(orderInfo);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	@GET
	@Path("/detail/{orderid}")
	@Produces("application/json; charset=UTF-8")
	public Response queryOrderByID(@PathParam("orderid") String orderid) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				GoodsOrderMapper mapper = session.getMapper(GoodsOrderMapper.class);
				return successResponse(mapper.queryOrderByID(orderid));
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	/**
	 * 查询用户的订单
	 * @param openid
	 * @param orderStatus
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@GET
	@Path("/detail")
	public Response queryUserOrders(@QueryParam("openid") String openid, @QueryParam("status") int orderStatus,
			@QueryParam("startDate") String startDate, @QueryParam("endDate") String endDate) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				GoodsOrderMapper mapper = session.getMapper(GoodsOrderMapper.class);
				if (orderStatus > 0) {
					return successResponse(mapper.queryUserOrders(openid, orderStatus));
				} else if (StringUtils.isNotBlank(startDate) || StringUtils.isNotBlank(endDate)) {
					return successResponse(mapper.queryOrdersInDate(startDate, endDate, openid));
				} else {
					return successResponse(new ArrayList<GoodsInfo>());
				}
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	@POST
	@Path("/detail")
	public Response queryOrderByKey(GoodsOrder orderInfo) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				GoodsOrderMapper mapper = session.getMapper(GoodsOrderMapper.class);
				return successResponse(mapper.queryOrders(orderInfo));
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	@POST
	@Path("/status")
	public Response updateOrderStatus(@FormParam("orderid") String orderid, @FormParam("status") int newStatus) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				GoodsOrderMapper mapper = session.getMapper(GoodsOrderMapper.class);
				mapper.updateOrderStatus(orderid, newStatus);
				return successResponse(true);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	@POST
	@Path("/update")
	public Response updateOrder(GoodsOrder orderInfo) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				GoodsOrderMapper mapper = session.getMapper(GoodsOrderMapper.class);
				mapper.updateOrder(orderInfo);
				return successResponse(true);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	@DELETE
	@Path("/{orderid}")
	public Response deleteOrder(@PathParam("orderid") String orderid) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				GoodsOrderMapper mapper = session.getMapper(GoodsOrderMapper.class);
				mapper.deleteOrder(orderid);
				return successResponse(true);
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (Exception e) {
			return errorResponse(e, 3001);
		}
	}

	/**
	 * 生成订单编号
	 * @param prefix
	 * @param wxAppid
	 * @param openid
	 * @return
	 */
	public static String generateOrderNo(String prefix, String wxAppid, String openid) {
		String str = DigestUtils.md5Hex("WXAPPID=" + wxAppid + "#OPENID=" + openid);
		return prefix + str + System.currentTimeMillis();
	}
}
