package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import domain.GraduateProjectType;
import service.GraduateProjectTypeService;
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
@WebServlet("/graduateProjectType.ctl")
public class GraduateProjectTypeController extends HttpServlet {
    /**
     * POST, http://49.235.39.226:8080/bysj/graduateProjectType.ctl, 增加毕业项目类型
     * 增加一个毕业项目类型对象：将来自前端请求的JSON对象，增加到数据库表中
     * @param request 请求对象
     * @param response 响应对象
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //根据request对象，获得代表参数的JSON字串
        String graduateProjectType_json = JSONUtil.getJSON(request);
        //将JSON字串解析为GraduateProjectType对象
        GraduateProjectType graduateProjectTypeToAdd = JSON.parseObject(graduateProjectType_json, GraduateProjectType.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加GraduateProjectType对象
        try {
            GraduateProjectTypeService.getInstance().add(graduateProjectTypeToAdd);
            message.put("message", "增加成功");
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
     * DELETE, http://49.235.39.226:8080/bysj/graduateProjectType.ctl?id=1, 删除id=1的毕业项目类型
     * 删除一个毕业项目类型对象：根据来自前端请求的id，删除数据库表中id的对应记录
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

        //到数据库表中删除对应的毕业项目类型
        try {
            GraduateProjectTypeService.getInstance().delete(id);
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
     * PUT, http://49.235.39.226:8080/bysj/graduateProjectType.ctl, 修改毕业项目类型
     *
     * 修改一个毕业项目类型对象：将来自前端请求的JSON对象，更新数据库表中相同id的记录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String graduateProjectType_json = JSONUtil.getJSON(request);
        //将JSON字串解析为GraduateProjectType对象
        GraduateProjectType graduateProjectTypeToAdd = JSON.parseObject(graduateProjectType_json, GraduateProjectType.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改GraduateProjectType对象对应的记录
        try {
            GraduateProjectTypeService.getInstance().update(graduateProjectTypeToAdd);
            message.put("message", "修改成功");
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
     * GET, http://49.235.39.226:8080/bysj/graduateProjectType.ctl?id=1, 查询id=1的毕业项目类型
     * GET, http://49.235.39.226:8080/bysj/graduateProjectType.ctl, 查询所有的毕业项目类型
     * 把一个或所有毕业项目类型对象响应到前端
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //读取参数id
        String id_str = request.getParameter("id");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有毕业项目类型对象，否则响应id指定的毕业项目类型对象
            if (id_str == null) {
                responseGraduateProjectTypes(response);
            } else {
                int id = Integer.parseInt(id_str);
                responseGraduateProjectType(id, response);
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
    //响应一个毕业项目类型对象
    private void responseGraduateProjectType(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找毕业项目类型
        GraduateProjectType graduateProjectType = GraduateProjectTypeService.getInstance().find(id);
        String graduateProjectType_json = JSON.toJSONString(graduateProjectType);

        //响应graduateProjectType_json到前端
        response.getWriter().println(graduateProjectType_json);
    }
    //响应所有毕业项目类型对象
    private void responseGraduateProjectTypes(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有毕业项目类型
        Collection<GraduateProjectType> graduateProjectTypes = GraduateProjectTypeService.getInstance().findAll();
        String graduateProjectTypes_json = JSON.toJSONString(graduateProjectTypes, SerializerFeature.DisableCircularReferenceDetect);

        //响应graduateProjectTypes_json到前端
        response.getWriter().println(graduateProjectTypes_json);
    }
}
