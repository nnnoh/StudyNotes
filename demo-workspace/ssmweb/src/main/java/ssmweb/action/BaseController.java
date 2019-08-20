package ssmweb.action;

import java.io.IOException;
import java.lang.reflect.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet����
 * �Զ������������
 */
public class BaseController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        // ���Ҫִ�еķ�����
        String act = request.getParameter("act");

        // ����û�û���ṩ������
        if (act == null || act.equals("")) {
            // Ĭ�Ϸ���
            act = "execute";
        }

        // ���ݷ�������÷�����Ϣ��÷�����Ϣ
        Method method;
        try {
            // �ڶ����л��������Ϣ,�������л�÷���ͨ�������������������
            method = this.getClass().getMethod(act, HttpServletRequest.class, HttpServletResponse.class);
            // ���÷���,�ڵ�ǰ�����е��ã����ݲ���request��response,��÷��ؽ��
            String targetUri = method.invoke(this, request, response) + "";
            // ������ص�url����redirect��ʼ�������ض���
            if (targetUri.startsWith("redirect:")) {
                response.sendRedirect(targetUri.substring(9, targetUri.length()));
            } else {
                // ת��
                request.getRequestDispatcher(targetUri).forward(request, response);
            }
        } catch (Exception e) {
            response.sendError(400, e.getMessage());
            e.printStackTrace();
        }
    }

    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(400, "��ʹ�ò���actָ����Ҫ���ʵķ���");
    }
}