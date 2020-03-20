package com.as1124.spring.boot.web;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.as1124.spring.boot.model.Author;
import com.as1124.spring.boot.model.AuthorPersistenceAction;
import com.as1124.spring.boot.model.Book;
import com.as1124.spring.boot.model.BookPersistenceAction;
import com.as1124.spring.boot.properties.As1124BootProperties;

@Controller
@RequestMapping(value = "/")
@org.springframework.transaction.annotation.Transactional
public class SpringBootDemoController implements ServletContextAware {

	@Autowired
	private As1124BootProperties bookStore;

	private JpaRepositoryFactory mJpaFactory;

	@Autowired
	private AuthorPersistenceAction authorAction;

	@Autowired
	private ConfigurableWebApplicationContext context;

	private WeakReference<ServletContext> servletContextRef;

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

	@GetMapping(value = "/author", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Author showDetail(@RequestParam("uid") Integer uid) {
		Optional<Author> result = authorAction.findById(uid);
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}

	@PostMapping(value = "/author/save", consumes = "application/json;charset=utf-8")
	public ModelAndView submitData(@RequestBody Author author) {
		ModelAndView mv = new ModelAndView();
		int bookCount = author.getBooks().size();
		BookPersistenceAction bookActions = mJpaFactory.getRepository(BookPersistenceAction.class);
		for (int i = 0; i < bookCount; i++) {
			bookActions.save(author.getBooks().get(i));
		}
		// 很神奇 => 先保存Book, 此时Book中并没有authorId, 保存Author之后book中便有值了
		Author result = authorAction.save(author);
		if (result.getAuthorId() >= 1) {
			mv.addObject("message", "保存 Author 成功 = " + result.getAuthorId());
			mv.setViewName("success");
		} else {
			mv.setViewName("error");
		}
		return mv;
	}

	@DeleteMapping(value = "/author/{uid}")
	@ResponseBody
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
	public @ResponseBody String[] getBeanNames() {
		return context.getBeanDefinitionNames();
	}

	@GetMapping(path = "/filters")
	public @ResponseBody String[] getFilterNames() {
		if (servletContextRef != null && servletContextRef.get() != null) {
			ServletContext servletContext = servletContextRef.get();
			Map<String, ? extends FilterRegistration> filterMaps = servletContext.getFilterRegistrations();
			Iterator<String> its = filterMaps.keySet().iterator();
			while (its.hasNext()) {
				String key = its.next();
				filterMaps.get(key);
			}
			return new String[0];
		} else {
			return new String[0];
		}
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		servletContextRef = new WeakReference<>(servletContext);
	}

}
