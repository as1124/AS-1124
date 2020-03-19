
// 设置Groovy运行依赖的注解 => 通常只有当Goovy无法推断出依赖时才显示添加
@Grab("mysql:mysql-connector-java:8.0.18")
@Grab("org.springframework.boot:spring-boot-starter-thymeleaf:2.2.0.RELEASE")

@Controller
@RequestMapping("/author")
class AuthorGroovyController{

	@Autowired
	IAuthorAction authorAction

	@GetMapping("/{name}")
	def showDetail(@PathVariable("name")String name) {
		println("input name is " + name)
		ModelAndView result = new ModelAndView();
		List<Author> authors = authorAction.findByUsername("H")
		if(authors) {
			result.addObject("authors", authors)
		}
		result.viewName = "authorList"
		return result;
	}

	@PostMapping("/save")
	def saveAuthor(Author author) {
		authorAction.saveAuthor(author)
	}
}