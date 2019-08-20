package ssmweb.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ssmweb.entities.Book;
import ssmweb.service.BookService;

@WebServlet("/BookController.do")
public class BookController extends BaseController {
    private static final long serialVersionUID = 1L;

    BookService bookservice;

    @Override
    public void init() throws ServletException {
        bookservice = CtxUtil.getBean(BookService.class);
    }

    // ͼ���б�Action
    public String ListBook(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("books", bookservice.getAllBooks());
        return "ListBook.jsp";
    }

    // ɾ��ͼ��Action
    public String Delete(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("message", bookservice.delete(id) > 0 ? "ɾ���ɹ���" : "ɾ��ʧ�ܣ�");
        request.setAttribute("books", bookservice.getAllBooks());
        return "ListBook.jsp";
    }

    // ��ɾ��ͼ��Action
    public String Deletes(HttpServletRequest request, HttpServletResponse response) {
        String[] ids = request.getParameterValues("ids");
        if (ids!=null&&ids.length > 0) {
            request.setAttribute("message", bookservice.delete(ids) > 0 ? "ɾ���ɹ���" : "ɾ��ʧ�ܣ�");
        } else {
            request.setAttribute("message", "��ѡ��ɾ���");
        }
        request.setAttribute("books", bookservice.getAllBooks());
        return "ListBook.jsp";
    }

    // ���ͼ��Action
    public String AddBook(HttpServletRequest request, HttpServletResponse response) {
        return "AddBook.jsp";
    }

    // �������ͼ��Action
    public String AddBookPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String title = request.getParameter("title");
            double price = Double.parseDouble(request.getParameter("price"));

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date publishDate = simpleDateFormat.parse(request.getParameter("publishDate"));

            Book entity = new Book(0, title, price, publishDate);
            if (bookservice.add(entity) > 0) {
                request.setAttribute("model", new Book());
                request.setAttribute("message", "��ӳɹ���");
            } else {
                request.setAttribute("model", entity);
                request.setAttribute("message", "���ʧ�ܣ�");
            }
        } catch (Exception exp) {
            request.setAttribute("message", exp.getMessage());
            exp.printStackTrace();
        }
        return "AddBook.jsp";
    }
    
        //�༭ͼ��Action
        public String EditBook(HttpServletRequest request, HttpServletResponse response) {
            int id = Integer.parseInt(request.getParameter("id"));
            Book model=bookservice.getBookById(id);
            request.setAttribute("model", model);
            return "EditBook.jsp";
        }

        // ����༭ͼ��Action
        public String EditBookPost(HttpServletRequest request, HttpServletResponse response) {
            try {
                int id=Integer.parseInt(request.getParameter("id"));
                
                String title = request.getParameter("title");
                double price = Double.parseDouble(request.getParameter("price"));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date publishDate = simpleDateFormat.parse(request.getParameter("publishDate"));

                Book entity = new Book(id, title, price, publishDate);
                request.setAttribute("message", bookservice.update(entity) > 0 ? "���³ɹ���" : "����ʧ�ܣ�");
                request.setAttribute("model", entity);
            } catch (Exception exp) {
                request.setAttribute("message", exp.getMessage());
                exp.printStackTrace();
            }
            return "EditBook.jsp";
        }
}