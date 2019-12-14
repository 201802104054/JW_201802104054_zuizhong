package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import domain.GraduateProject;
import service.GraduateProjectService;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

/**
 * 将所有方法组织在一个Controller(Servlet)中
 */
@WebServlet("/graduateProject.ctl")
public class GraduateProjectController extends HttpServlet {
    /**
     * POST, http://49.235.39.226:8080/bysj/graduateProject.ctl, 增加毕业项目
     * 增加一个毕业项目对象：将来自前端请求的JSON对象，增加到数据库表中
     * @param request 请求对象
     * @param response 响应对象
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //根据request对象，获得代表参数的JSON字串
        String graduateProject_json = JSONUtil.getJSON(request);
        //将JSON字串解析为GraduateProject对象
        GraduateProject graduateProjectToAdd = JSON.parseObject(graduateProject_json, GraduateProject.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加GraduateProject对象
        try {
            GraduateProjectService.getInstance().add(graduateProjectToAdd);
            message.put("message", "增加成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
            e.printStackTrace();
        }catch(Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    /**
     * DELETE, http://49.235.39.226:8080/bysj/graduateProject.ctl?id=1, 删除id=1的毕业项目
     * 删除一个毕业项目对象：根据来自前端请求的id，删除数据库表中id的对应记录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表中删除对应的毕业项目
        try {
            GraduateProjectService.getInstance().delete(id);
            message.put("message", "删除成功");
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        }catch(Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }


    /**
     * PUT, http://49.235.39.226:8080/bysj/graduateProject.ctl, 修改毕业项目
     *
     * 修改一个毕业项目对象：将来自前端请求的JSON对象，更新数据库表中相同id的记录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String graduateProject_json = JSONUtil.getJSON(request);
        //将JSON字串解析为GraduateProject对象
        GraduateProject graduateProjectToAdd = JSON.parseObject(graduateProject_json, GraduateProject.class);
        System.out.println(graduateProject_json);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改GraduateProject对象对应的记录
        try {
            GraduateProjectService.getInstance().update(graduateProjectToAdd);
            message.put("message", "修改成功");
        }catch (SQLException e){
            e.printStackTrace();
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        }catch(Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    /**
     * GET, http://49.235.39.226:8080/bysj/graduateProject.ctl?id=1, 查询id=1的毕业项目
     * GET, http://49.235.39.226:8080/bysj/graduateProject.ctl, 查询所有的毕业项目
     * 把一个或所有毕业项目对象响应到前端
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //读取参数id
        //从请求中获取id
        String teacherId_str = request.getParameter("teacherId");
        String id_str = request.getParameter("id");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        String graduateProject_JSON = null;
        try {
            //如果id = null, 表示响应所有专业对象，否则响应id指定的专业对象
            if (teacherId_str!=null) {
                int teacherId = Integer.parseInt(teacherId_str);
                responseGraduateProjectsByTeacher(teacherId,response);
            } else if (id_str != null){
                int id = Integer.parseInt(id_str);
                responseGraduateProject(id,response);
            }else {
                responseGraduateProjectsForAll(response);
            }
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
            e.printStackTrace();
            //响应message到前端
            response.getWriter().println(message);
        }catch(Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
            //响应message到前端
            response.getWriter().println(message);
        }
    }
    //响应一个毕业项目对象
    private void responseGraduateProject(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找毕业项目
        GraduateProject graduateProject = GraduateProjectService.getInstance().find(id);
        String graduateProject_json = JSON.toJSONString(graduateProject);

        //响应graduateProject_json到前端
        response.getWriter().println(graduateProject_json);
    }
    //响应所有毕业项目对象
    private void responseGraduateProjectsForAll(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有毕业项目
        Collection<GraduateProject> graduateProjects = GraduateProjectService.getInstance().findAll();
        String graduateProjects_json = JSON.toJSONString(graduateProjects, SerializerFeature.DisableCircularReferenceDetect);
        //响应graduateProjects_json到前端
        response.getWriter().println(graduateProjects_json);
    }
    //根据教师，响应所有毕业项目对象
    private void responseGraduateProjectsByTeacher(int id,HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有对应教师的毕业项目
        Collection<GraduateProject> graduateProjects = GraduateProjectService.getInstance().findByTeacher(id);
        String graduateProjects_json = JSON.toJSONString(graduateProjects, SerializerFeature.DisableCircularReferenceDetect);
        //响应graduateProjects_json到前端
        response.getWriter().println(graduateProjects_json);
    }
}
