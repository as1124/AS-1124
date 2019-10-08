package com.as1124.server.wxsapp.service;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.ibatis.session.SqlSession;

import com.as1124.server.wxsapp.access.WechatPlatformConstants;
import com.as1124.server.wxsapp.database.DatasourceFactory;

/**
 * 商品信息HTTP服务
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Path("/goodscategory")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
@Produces("application/json; charset=UTF-8")
public class GoodsCategoryService extends AbstractHttpRestService {

	@GET
	@Path("/all")
	public Response allGoodsCategory() {
		try (SqlSession session = DatasourceFactory.getDatasource(WechatPlatformConstants.DB_ENVIRONMENT)
				.openSession(true);) {
			if (session != null) {
				GoodsCategoryService mapper = session.getMapper(GoodsCategoryService.class);
				return successResponse(mapper.allGoodsCategory());
			} else {
				return Response.status(Status.ACCEPTED).build();
			}
		} catch (IOException e) {
			return errorResponse(e, 3001);
		}
	}
}
