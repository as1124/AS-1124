package com.as1124.spring.boot;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.as1124.spring.boot.model.Author;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Controller
@RequestMapping(value = "/user")
public class AuthorRestController {

	private IDataAction<Author> userAction;

	@Autowired
	public AuthorRestController(IDataAction<Author> dataAction) {
		this.userAction = dataAction;
	}

	@GetMapping(value = "/all")
	public ModelAndView showAll() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("authors", userAction.findAll());
		mv.setViewName("list");
		return mv;
	}

	@GetMapping(value = "/{udi}")
	public Author showDetail(@PathParam("uid") Integer uid) {
		return userAction.findOne(uid);
	}

	@PostMapping(value = "/save")
	public String submitData(Author user) {
		userAction.saveData(user);
		return "redirect:/";
	}

	@DeleteMapping(value = "/{uid}")
	public boolean deleteData(@PathParam("uid") Integer uid) {
		return userAction.deleteOne(new Author(uid));
	}

}
