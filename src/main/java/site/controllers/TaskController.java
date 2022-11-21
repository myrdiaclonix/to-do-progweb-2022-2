package site.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import site.dao.ListaDAO;
import site.dao.TagDAO;
import site.dao.TaskDAO;
import site.entities.Lista;
import site.entities.Tag;
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

    @EJB
    private TaskDAO daoTask;

    @EJB
    private ListaDAO daoLista;
    
    @EJB
    private TagDAO daoTag;
    Date date = new Date();
    
    public TaskController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Validate User Logged
        User user = (User) request.getSession().getAttribute("user");
        
        if(user == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        System.out.println(user.getEmail());
        
        // User associated tags.
        List<Tag> userTags = daoTag.userTags((User) request.getSession().getAttribute("user"));
        request.getSession().setAttribute("userTags", userTags);
        
        String search = request.getParameter("s"); // parametro s => search
        String l = request.getParameter("l"); // parametro l => lista
        Integer idLista = l != null && l.isBlank() || l == null ? 0 : Integer.parseInt(l); // converte l para inteiro
        
        search = search == null ? "" : search;
        idLista = idLista == null || idLista == 0 ? null : idLista;
        
        // Tarefas pendentes e concluidas
        List<Task> pTasks = daoTask.findByTasks(user.getId(), search, idLista, 0);
        List<Task> cTasks = daoTask.findByTasks(user.getId(), search, idLista, 1, 1);
        
        // Todas as listas e lista atual
        List<Lista> listas = daoLista.findAll(user.getId());
        Lista listaTask = idLista != null && idLista > 0 ? daoLista.findById(idLista, user.getId()) : null;
        
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

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Validate User Logged
        User user = (User) request.getSession().getAttribute("user");
        
        if(user == null) {
            return;
        }
        
        PrintWriter out = response.getWriter();
        ResponseJson res = new ResponseJson();

        String action = request.getParameter("action");

        if (action.equals("addTask")) {

            String title = request.getParameter("input-title-task");
            String description = request.getParameter("textarea-description");
            String dateLimit = request.getParameter("input-date-limit");

            Integer listId = Integer.parseInt(request.getParameter("task-list-option"));

            boolean isOk = true;
            if (title.isEmpty()) {
                res.setMsg("Título inválido!");
                isOk = false;
                out.println(res.toJson());
                return;
            }
            if (description.isEmpty()) {
                res.setMsg("Descrição da tarefa inválida!");
                isOk = false;
                out.println(res.toJson());
                return;
            }
            if (dateLimit.isEmpty()) {
                res.setMsg("Data inválida!");
                isOk = false;
                out.println(res.toJson());
                return;
            }

            if (isOk) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime localDate = LocalDateTime.parse(dateLimit, formatter);
                Timestamp date = Timestamp.valueOf(localDate);
                Task t = new Task(null, title, description, 0, date, null,
                        (User) request.getSession().getAttribute("user"),
                        daoLista.find(listId));
                daoTask.save(t);
            }

            res.setMsg("Tarefa adiciona com sucesso!");
            res.setStatus(1);

            out.println(res.toJson());
            return;

        } else {
            if (action.equals("SLTKS")) {
                String search = request.getParameter("input-search-task");
                out.println(search);
            }
            out.println(action);
            return;
        }
    }
}
