@Repository
class JdbcAuthorActionImpl implements IAuthorAction{

	@Autowired
	JdbcTemplate jdbcTemplate;

	List<Author> findByUsername(String username){
		return jdbcTemplate.query("select * from author s where s.user_name like ?", { resultSet, rowNum ->
			new Author(authorId:resultSet.getInt("id"), username:resultSet.getString("user_name"),
			password:resultSet.getString("password"), address:resultSet.getString("address"))
		} as RowMapper, "%"+username+"%");
	}

	Author findByID(Integer authorId) {
		return new Author( authorId:124, username:"Groovy Test", password:"123456", address:"傻狗")
	}

	void saveAuthor(Author author) {
		jdbcTemplate.update("insert into author(user_name,password) values(?,?)", author.username, author.password)
	}
}