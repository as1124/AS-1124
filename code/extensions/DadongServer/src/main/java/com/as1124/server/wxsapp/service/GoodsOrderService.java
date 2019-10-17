package com.as1124.server.wxsapp.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.as1124.server.wxsapp.resources.GoodsOrder;

/**
 * 商品订单HTTP服务
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Path("/order")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public class GoodsOrderService extends AbstractHttpRestService {

	@POST
	@Path("/create")
	public Response createOrder(GoodsOrder orderInfo) {
		return null;
	}

	@GET
	@Path("/detail/{orderid}")
	public Response queryOrderByID(@PathParam("orderid") String orderid) {
		return null;
	}

	@POST
	@Path("/detail")
	public Response queryOrderByKey(GoodsOrder orderInfo) {
		return null;
	}

	@POST
	@Path("/status")
	public Response updateOrderStatus(@FormParam("orderid") String orderid, @FormParam("status") int newStatus) {
		return null;
	}

	@POST
	@Path("/update")
	public Response updateOrder(GoodsOrder orderInfo) {
		return null;
	}

	@DELETE
	@Path("/{orderid}")
	public Response deleteOrder(@PathParam("orderid") String orderid) {
		return null;
	}
}
