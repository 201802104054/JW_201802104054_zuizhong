package controller;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout.ctl")
public class LogoutController extends HttpServlet {
    /**
     * GET, http://49.235.39.226:8080/bysj/logout.ctl, 增加职位
     * 进行用户退出操作
     * @param request 请求对象
     * @param response 响应对象
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //移除会话的所有属性
        session.invalidate();
        message.put("message","退出成功");
        response.getWriter().println(message);
    }
}
