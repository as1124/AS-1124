interface IAuthorAction{
	List<Author> findByUsername(String username);

	Author findByID(Integer authorId);

	void saveAuthor(Author author)
}