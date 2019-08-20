package ssmweb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ssmweb.entities.Book;

public interface BookDAO {
	
	public List<Book> getAllBooks();
	
	public Book getBookById(@Param("id")int id);
	
	public int add(Book entity);
	
	public int delete(int id);
	
	public int update(Book entity);
}
