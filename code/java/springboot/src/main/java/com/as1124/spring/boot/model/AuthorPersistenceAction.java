package com.as1124.spring.boot.model;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.as1124.spring.boot.IDataAction;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
//@Component("authorAction")
@Repository
public class AuthorPersistenceAction implements IDataAction<Author> {

	@Override
	public Author findOne(Integer uid) {
		return null;
	}

	@Override
	public List<Author> findAll() {
		return IDataAction.super.findAll();
	}

	@Override
	public int countData() {
		return IDataAction.super.countData();
	}

	@Override
	public boolean saveData(Author data) {
		return IDataAction.super.saveData(data);
	}

	@Override
	public boolean deleteOne(Author data) {
		return IDataAction.super.deleteOne(data);
	}
}
