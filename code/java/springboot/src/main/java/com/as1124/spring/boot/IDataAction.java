package com.as1124.spring.boot;

import java.util.ArrayList;
import java.util.List;

/**
 * 持久化数据操作抽象接口
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface IDataAction<T> {// extends JpaRepository<T, Integer> {

	public T findOne(Integer uid);

	public default List<T> findAll() {
		return new ArrayList<>();
	}

	public default int countData() {
		return -1;
	}

	public default boolean saveData(T data) {
		return false;
	}

	public default boolean deleteOne(T data) {
		return false;
	}

}
