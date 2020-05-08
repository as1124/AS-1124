package com.as1124.spring5.rest;

import com.as1124.spring5.data.jpa.IAuthorRepository;
import com.as1124.spring5.data.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
@RestController
@RequestMapping(path = "/author", produces = "application/json;charset=utf-8", consumes = "application/json;")
@CrossOrigin(origins = "*")
public class AuthorRestService {

    private IAuthorRepository authAction;

    public AuthorRestService(IAuthorRepository authAction) {
        this.authAction = authAction;
    }

    @GetMapping(path = "/{authorID}")
    public Author queryAuthor(@PathVariable("authorID") Integer authorID) {
        Optional<Author> result = authAction.findById(authorID);
        if (result.get() != null) {
            System.out.println(result.get());
        }
        return result.get();
    }
}
