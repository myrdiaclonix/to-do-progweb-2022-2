package site.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import site.dao.TaskDAO;
import site.entities.Task;
import site.entities.User;
import site.utils.ResponseJson;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/tasks")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class TaskController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private TaskDAO dao = new TaskDAO();
    Date data = new Date();
    
    public TaskController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String search = request.getParameter("s");
        
        search = search == null ? "" : search;
        
        List<Task> pTasks = dao.findByTitleStatus(search, 0);
        List<Task> cTasks = dao.findByTitleStatus(search, 1, 1);
        
        request.setAttribute("pTasks", pTasks);
        request.setAttribute("cTasks", cTasks);
        request.setAttribute("search", search);
        request.getRequestDispatcher("/WEB-INF/task.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        Gson json = new Gson();
        ResponseJson res = new ResponseJson();
        
        String action = request.getParameter("action");
        
        if(action.equals("SLTKS")) {
            
            String search = request.getParameter("input-search-task");
            out.println(search);
          
            /*
             * res = new ResponseJson("Usuário Cadastrado com Sucesso!", 1);
            out.println(res.toJson());
            out.close();
             * */
        }
        
        out.println(action);
        out.close();

    }

}
