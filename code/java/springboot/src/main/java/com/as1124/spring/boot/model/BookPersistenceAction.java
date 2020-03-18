package com.as1124.spring.boot.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookPersistenceAction extends JpaRepository<Book, Integer> {

}
