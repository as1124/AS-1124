package com.as1124.spring.web.controller.model;

import java.util.List;

/**
 * 
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface ISpittleRepository {

	public List<Spittle> findSpittles(long max, int count);
}
