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
import site.dao.ListaSharedDAO;
import site.dao.TagDAO;
import site.dao.TaskDAO;
import site.entities.Lista;
import site.entities.ListaShared;
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
    private ListaSharedDAO daoListaShared;
    
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
        List<ListaShared> usersC = daoListaShared.findByLista(idLista);
        List<ListaShared> listaShared = daoListaShared.findByUser(user.getId());
        
        for(ListaShared us : usersC) {
            System.out.println(us.getIdListaShared());
        }
        
        request.setAttribute("pTasks", pTasks);
        request.setAttribute("cTasks", cTasks);
        request.setAttribute("search", search);
        request.setAttribute("listas", listas);
        request.setAttribute("listaShared", listaShared);
        request.setAttribute("listaTask", listaTask);
        request.setAttribute("usersC", usersC);
        request.setAttribute("user", user);
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
        String id = request.getParameter("id");
        Integer taskId = id != null && id.length() > 0 && Integer.parseInt(id) > 0 ? Integer.parseInt(id) : null;

        if (action.equals("addTask") || action.equals("editTask"))
        {
            String title = request.getParameter("input-title-task");
            String description = request.getParameter("textarea-description");
            String dateLimit = request.getParameter("input-date-limit");

            Integer listId = Integer.parseInt(request.getParameter("task-list-option"));
            Lista listaTask = daoLista.findById(listId, user.getId());
            boolean isOk = true;
            
            if (title.isEmpty())
            {
                res.setMsg("Título inválido!");
                isOk = false;
                out.println(res.toJson());
                return;
            }
            
            if (description.isEmpty())
            {
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
            
            if(listaTask == null) {
                res.setMsg("Lista inválida!");
                isOk = false;
                out.println(res.toJson());
                return;
            } else {
                
                if(listaTask.getUser().getId() != user.getId()) {
                    user = listaTask.getUser();
                }
                
            }
            
            if (isOk) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime localDate = LocalDateTime.parse(dateLimit, formatter);
                Timestamp date = Timestamp.valueOf(localDate);
                Task t = new Task(taskId, title, description, 0, date, null, user, listaTask);
                daoTask.save(t);
                
                String msg = action.equals("editTask") ? "Tarefa atualizada com sucesso!" : "Tarefa adicionada com sucesso!";
                
                res.setMsg(msg);
                res.setStatus(1);
            }
            out.println(res.toJson());
            return;
        }  
        else if (action.equals("getTask"))
        {
            Task ts = daoTask.find(taskId);
            if (ts != null)
            {
                res.addRes(ts.getTitle());
                if (ts.getDtLimit() != null)
                {
                    //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    res.addRes(ts.getDtLimit().toString());
                }
                else
                {
                    res.addRes(null);
                }
                res.addRes(ts.getDescription());
                Lista ls = ts.getlista();
                if (ls != null)
                {
                    res.addRes(ls.getIdLista().toString());
                }
                res.setMsg("Tarefa encontrada.");
                res.setStatus(1);
            }
            out.println(res.toJson());
            return;
        } 
        else if (action.equals("removeTask"))
        {
            boolean del = false;
            Task t = daoTask.find(taskId);
            if (t != null) {
                del = daoTask.remove(t);
            }
            if(del) {
                res.setMsg("Tarefa removida com sucesso.");
                res.setStatus(1);
            } else {
                res.setMsg("Erro ao remover a tarefa.");
            }
            out.println(res.toJson());
            return;
        }
        else if (action.equals("changeStatus"))
        {
            
            Integer status = Integer.parseInt(request.getParameter("status"));
            Task t = daoTask.find(taskId);
            
            if(status == 1) {
                Timestamp date = new Timestamp(new Date().getTime());
                t.setDtComplete(date);
            } else {
                t.setDtComplete(null);
            }
            
            t.setStatus(status);

            daoTask.save(t);
            
            res.setMsg("Tarefa atualizada com sucesso!");
            res.setStatus(1);
            
            out.println(res.toJson());
            return;
        }
    }
}
