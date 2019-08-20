package ssmweb.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ssmweb.entities.Book;
import ssmweb.service.BookService;

//import java.sql.Connection;
//import java.sql.DriverManager;

public class TestBookService {

    static BookService bookservice;
    
    @BeforeClass
    public static void before() {
//    	try {
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/mine?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8","root","123mysql"); System.out.println(conn);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}   
        ApplicationContext ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
        bookservice=ctx.getBean(BookService.class);
        ((AbstractApplicationContext) ctx).close();
    }
    
    @Test
    public void testGetAllBooks() {
        List<Book> books=bookservice.getAllBooks();
        assertNotNull(books);
    }

    @Test
    public void testAdd() {
        Book entity=new Book(0, "Hibernate ���߰�", 78.1, new Date());
        try {
            assertEquals(1, bookservice.add(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteInt() {
        assertEquals(1, bookservice.delete(9));
    }

    @Test
    public void testDeleteStringArray() {
        String[] ids={"7","11","12"};
        assertEquals(3, bookservice.delete(ids));
    }

    @Test
    public void testUpdate() {
        Book entity=new Book(7, "Hibernate �ڶ���", 79.1, new Date());
        try {
            assertEquals(1, bookservice.update(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGetBookById()
    {
        assertNotNull(bookservice.getBookById(1));
    }
    
    @Test
    public void testAddDouble(){
        //��Ϊ������ͬ����ӵڶ�����ʧ�ܣ����ڲ�������
        Book entity1=new Book(0, "Hibernate �ڰ˰�", 78.1, new Date());
        Book entity2=new Book(0, "Hibernate �ڰ˰�", 78.1, new Date());
        assertEquals(2, bookservice.add(entity1, entity2));
    }
}
