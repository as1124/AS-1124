package com.as1124.spring.boot.web;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.as1124.spring.boot.model.Author;
import com.as1124.spring.boot.model.AuthorPersistenceAction;
import com.as1124.spring.boot.properties.As1124BootProperties;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Controller
@RequestMapping(value = "/author")
public class AuthorRestController {

	@Autowired
	private As1124BootProperties bookStore;

	private JpaRepositoryFactory mJpaFactory;

	@Autowired
	private AuthorPersistenceAction authorAction;

	@Autowired
	private ConfigurableWebApplicationContext context;

	@Autowired
	public AuthorRestController(EntityManager entityM) {
		this.mJpaFactory = new JpaRepositoryFactory(entityM);
	}

	@GetMapping(value = "/all")
	public ModelAndView showAll() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("authors", authorAction.findAll());
		mv.addObject("bookStore", bookStore);
		mv.setViewName("authorList");
		return mv;
	}

	@GetMapping(value = "/{uid}")
	public Author showDetail(@PathParam("uid") Integer uid) {
		Optional<Author> result = authorAction.findById(uid);
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}

	@PostMapping(value = "/save")
	public String submitData(Author author) {
		authorAction.save(author);
		return "redirect:/";
	}

	@DeleteMapping(value = "/{uid}")
	public boolean deleteData(@PathParam("uid") Integer uid) {
		try {
			authorAction.deleteById(uid);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
