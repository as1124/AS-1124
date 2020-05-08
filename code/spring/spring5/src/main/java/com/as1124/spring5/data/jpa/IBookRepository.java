package com.as1124.spring5.data.jpa;

import com.as1124.spring5.data.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepository extends JpaRepository<Book, Integer> {
}
