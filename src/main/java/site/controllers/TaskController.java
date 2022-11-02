package site.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import site.dao.ListaDAO;
import site.dao.TaskDAO;
import site.entities.Lista;
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

    private TaskDAO daoTask = new TaskDAO();
    private ListaDAO daoLista = new ListaDAO();
    Date data = new Date();
    
    public TaskController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String search = request.getParameter("s"); // parametro s => search
        String l = request.getParameter("l"); // parametro l => lista
        Integer idLista = l != null && l.isBlank() || l == null ? 0 : Integer.parseInt(l); // converte l para inteiro
        
        search = search == null ? "" : search;
        idLista = idLista == null || idLista == 0 ? null : idLista;
        
        // Tarefas pendentes e concluidas
        List<Task> pTasks = daoTask.findByTasks(search, idLista, 0);
        List<Task> cTasks = daoTask.findByTasks(search, idLista, 1, 1);
        
        // Todas as listas e lista atual
        List<Lista> listas = daoLista.findAll();
        Lista listaTask = idLista != null && idLista > 0 ? daoLista.find(idLista) : null;
        
        for(Task ts : pTasks) {
            System.out.println(ts.getlista());
        }
        
        request.setAttribute("pTasks", pTasks);
        request.setAttribute("cTasks", cTasks);
        request.setAttribute("search", search);
        request.setAttribute("listas", listas);
        request.setAttribute("listaTask", listaTask);
        request.getRequestDispatcher("/WEB-INF/task.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        if (action.equals("addTask")) {
            
            // For test purposes
            User user = new User(1, "teste@email.com", "12345678");
            request.getSession().setAttribute("user", user);
            
            String title = request.getParameter("input-title-task");
            String description = request.getParameter("textarea-description");
            
            Task t = new Task(null, title, description, 0, null, null, user, null);
            daoTask.save(t);
            
        } else {

            response.setContentType("text/html; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            Gson json = new Gson();
            ResponseJson res = new ResponseJson();

            if (action.equals("SLTKS")) {

                String search = request.getParameter("input-search-task");
                out.println(search);

            }

            out.println(action);
            out.close();
        }

    }

}
