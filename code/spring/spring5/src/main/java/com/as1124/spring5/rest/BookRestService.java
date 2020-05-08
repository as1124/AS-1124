package com.as1124.spring5.rest;

import com.as1124.spring5.data.jpa.IBookRepository;
import com.as1124.spring5.data.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
@RestController
@RequestMapping(path = "/book", produces = "application/json;charset=utf-8", consumes = "application/json;")
@CrossOrigin(origins = "*")
public class BookRestService {

    private IBookRepository bookAction;

    @Autowired
    public BookRestService(IBookRepository bookDao) {
        this.bookAction = bookDao;
    }

    @GetMapping(path = "/{bookID}")
    public Book queryBook(@PathVariable("bookID") Integer bookID) {
        Optional<Book> book = bookAction.findById(bookID);
        return book.get();
    }

    @GetMapping(path = "/top5")
    public List<Book> queryTop5() {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("isbn").descending());
        return bookAction.findAll(pageRequest).getContent();
    }
}
