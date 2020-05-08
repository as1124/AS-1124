package com.as1124.spring5.data.jpa;

import com.as1124.spring5.data.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthorRepository extends JpaRepository<Author, Integer> {
}
