package demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Welcome")
public class LoginServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		super.doPost(req, resp);	//405	Method Not Allowed
		
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;utf-8");
		
		String title = "Welcome";
		String name = req.getParameter("username");
		
		PrintWriter out = resp.getWriter();
		out.println(
				"<html>"+
				"<head><title>"+title+"</title></head>"+
				"<body>"+
				"<p>Welcome ! "+name+"</p>"+
				"</body>"+
				"</html>");
		
	}
	
	
	
}
