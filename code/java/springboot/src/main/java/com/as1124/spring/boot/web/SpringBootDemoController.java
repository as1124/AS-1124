package com.as1124.spring.boot.web;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.as1124.spring.boot.model.Author;
import com.as1124.spring.boot.model.AuthorPersistenceAction;
import com.as1124.spring.boot.model.Book;
import com.as1124.spring.boot.model.BookPersistenceAction;
import com.as1124.spring.boot.properties.As1124BootProperties;

@Controller
@RequestMapping(value = "/")
public class SpringBootDemoController {

	@Autowired
	private As1124BootProperties bookStore;

	private JpaRepositoryFactory mJpaFactory;

	@Autowired
	private AuthorPersistenceAction authorAction;

	@Autowired
	private ConfigurableWebApplicationContext context;

	@Autowired
	public SpringBootDemoController(EntityManager entityM) {
		this.mJpaFactory = new JpaRepositoryFactory(entityM);
	}

	@GetMapping(value = "/author/all")
	public ModelAndView showAll() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("authors", authorAction.findAll());
		mv.addObject("bookStore", bookStore);
		mv.setViewName("authorList");
		return mv;
	}

	@GetMapping(value = "/author/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Author showDetail(@PathVariable("uid") Integer uid) {
		Optional<Author> result = authorAction.findById(uid);
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}

	@PostMapping(value = "/author/save")
	public String submitData(@RequestBody Author author) {
		authorAction.save(author);
		return "redirect:/";
	}

	@DeleteMapping(value = "/author/{uid}")
	public boolean deleteData(@PathVariable("uid") Integer uid) {
		try {
			authorAction.deleteById(uid);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@GetMapping(path = "/book/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Book finBook(@PathVariable("id") Integer bookID) {
		BookPersistenceAction bookActions = mJpaFactory.getRepository(BookPersistenceAction.class);
		Optional<Book> result = bookActions.findById(bookID);
		if (result.isPresent()) {
			return result.get();
		} else {
			return new Book(bookID, "未查询到结果");
		}
	}

	@GetMapping(path = "/beans", produces = MediaType.APPLICATION_JSON_VALUE)
	public String[] getBeanNames() {
		return context.getBeanDefinitionNames();
	}

}
