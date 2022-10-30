package site.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import site.dao.ListaDAO;
import site.entities.Lista;
import site.utils.ResponseJson;

@WebServlet("/lists")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class ListaController extends HttpServlet{
    private static final long serialVersionUID = 1L;
    
    private ListaDAO dao = new ListaDAO();
    
    public ListaController() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String search = request.getParameter("s");
        
        search = search == null ? "" : search;
        
        List<Lista> pListas = dao.findByTitle(search);
        List<Lista> cListas = dao.findByTitle(search);
        
        request.setAttribute("pListas", pListas);
        request.setAttribute("cListas", cListas);
        request.setAttribute("search", search);
        request.getRequestDispatcher("/WEB-INF/lista.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        ResponseJson res = new ResponseJson();
        
        String action = request.getParameter("action");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        
        if(title.equals("SLTKS")) {
            
            String search = request.getParameter("input-search-lista");
            out.println(search);
        }
        
        if(title.isEmpty()) {
            res.setMsg("É necessário inserir um Título para Lista!");
            out.println(res.toJson());
            out.close();
            return;
        }
        
        Lista us = new Lista(null, title, description);        
        Lista save = dao.save(us);
        
        if(save == null) {
            res.setMsg("Erro ao Cadastrar Lista!");
            out.println(res.toJson());
            out.close();
            return;
        }
        
        out.println(action);
        out.close();
    }
}
