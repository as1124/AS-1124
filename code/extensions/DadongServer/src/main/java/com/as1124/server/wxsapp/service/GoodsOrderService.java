package com.as1124.server.wxsapp.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * TODO 此处填写 class 信息
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Path("/order")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
public class GoodsOrderService extends AbstractHttpRestService {

}
