package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.User;
import service.UserService;
import util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login.ctl")
public class LoginController extends HttpServlet {
    /**
     * POST, http://49.235.39.226:8080/bysj/login.ctl, 增加职位
     * 进行用户登录操作
     * @param request 请求对象
     * @param response 响应对象
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //根据request对象，获得代表参数的JSON字串
        String login_json = JSONUtil.getJSON(request);
        //将JSON字串解析为User对象
        User user= JSON.parseObject(login_json,User.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //进行登录操作
            User loggedUser = UserService.getInstance().login(user);
            //如果用以表示验证数据库的数据是否正确的loggedUser指向不为空，则验证成功
            if (loggedUser != null){
                //让message添加信息"message","登陆成功"
                message.put("message","登陆成功");
                //创建会话
                HttpSession session = request.getSession();
                //若十分钟没有任何操作，使session失效
                session.setMaxInactiveInterval(10 * 60);
                //设置session属性，属性名叫currentUser，值为loggedUser指向的对象
                session.setAttribute("currentUser",loggedUser);
                //向前端输出信息
                //获取账号的user对象的teacher字段并向前端输出
                String teacher_json = JSON.toJSONString(loggedUser.getTeacher());
                response.getWriter().println(teacher_json);
                return;
            }else {
                message.put("message","账号或密码错误，请重试");
                response.getWriter().println(message);
            }
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        }catch(Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
        }
    }
}
