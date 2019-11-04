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

import com.as1124.server.wxsapp.access.As1124AppConstants;
import com.as1124.server.wxsapp.database.DatasourceFactory;
import com.as1124.server.wxsapp.database.mapper.GoodsInfoMapper;
import com.as1124.server.wxsapp.resources.GoodsInfo;

/**
 * 商品信息HTTP服务
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Path("/goods")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
@Produces("application/json; charset=UTF-8")
public class GoodsInfoService extends AbstractHttpRestService {

	@GET
	@Path("/topHot")
	public Response queryTopHots() {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				GoodsInfoMapper mapper = session.getMapper(GoodsInfoMapper.class);
				return successResponse(mapper.queryTopHots());
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (IOException e) {
			return errorResponse(e, 2001);
		}
	}

	@POST
	public Response queryGoodsByKey(GoodsInfo goods) {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				GoodsInfoMapper mapper = session.getMapper(GoodsInfoMapper.class);
				return successResponse(mapper.queryGoodsByKey(goods));
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (IOException e) {
			return errorResponse(e, 2001);
		}
	}

	@GET
	@Path("/{goodsno}")
	public Response queryGoodsDetail(@PathParam("goodsno") String goodsno) {
		if (StringUtils.isBlank(goodsno)) {
			return successResponse(null);
		}
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				GoodsInfoMapper mapper = session.getMapper(GoodsInfoMapper.class);
				GoodsInfo goods = new GoodsInfo();
				goods.setGoodsno(goodsno);
				List<GoodsInfo> result = mapper.queryGoodsByKey(goods);
				if (result != null && !result.isEmpty()) {
					return successResponse(result.get(0));
				} else {
					return successResponse(null);
				}
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (IOException e) {
			return errorResponse(e, 2001);
		}
	}

	@GET
	@Path("/all")
	public Response queryAllGoods() {
		try (SqlSession session = DatasourceFactory.getDatasource(As1124AppConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				GoodsInfoMapper mapper = session.getMapper(GoodsInfoMapper.class);
				return successResponse(mapper.allGoods());
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (IOException e) {
			return errorResponse(e, 2001);
		}
	}

}
