package com.as1124.spring.boot;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.as1124.spring.boot.model.Author;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@RestController
@RequestMapping(value = "/user", produces = "appliction/json;charset=utf-8")

//@Controller
//@RequestMapping(value = "/user")
public class AuthorRestController {

	private IDataAction<Author> userAction;

	public AuthorRestController(IDataAction<Author> dataAction) {
		this.userAction = dataAction;
	}

	//	@GetMapping(value = "/all")
	//	public ModelAndView showAll(ModelAndView mv) {
	//		mv.addObject("authors", userAction.findAll());
	//		mv.setViewName("list");
	//		return mv;
	//	}

	@GetMapping(value = "/all")
	public List<Author> showAll() {
		return userAction.findAll();
	}

	@GetMapping(value = "/{udi}")
	public Author showDetail(@PathParam("uid") Integer uid) {
		return userAction.findOne(uid);
	}

	@PostMapping(value = "/save")
	public void submitData(Author user) {
		userAction.saveData(user);
	}

	@DeleteMapping(value = "/{uid}")
	public boolean deleteData(@PathParam("uid") Integer uid) {
		return userAction.deleteOne(new Author(uid));
	}

}
