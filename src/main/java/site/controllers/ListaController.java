package site.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import site.dao.ListaDAO;
import site.dao.UserDAO;
import site.entities.Lista;
import site.entities.User;
import site.utils.ResponseJson;
import site.utils.ValidateEmail;

@WebServlet("/lists")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class ListaController extends HttpServlet{
    private static final long serialVersionUID = 1L;
    
    @EJB
    private ListaDAO daoLista;
    
    @EJB
    private UserDAO daoUser;
    
    public ListaController() {
        super();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Validate User Logged
        User user = (User) request.getSession().getAttribute("user");
        
        if(user == null) {
            return;
        }
        
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        ResponseJson res = new ResponseJson();

        String action = request.getParameter("action");
        String code = request.getParameter("code");
        Integer idLista = code != null ? Integer.parseInt(code) : null;

        if (action.equals("addLista") || action.equals("editLista")) {

            String title = request.getParameter("input-title-list");
            String description = request.getParameter("textarea-description-list");
            
            if (title == null || title != null && title.isEmpty()) {
                res.setMsg("Título inválido!");
                out.println(res.toJson());
                return;
            }
            
            if (idLista != null && idLista > 0) {
                
                Lista ls = daoLista.find(idLista);
                
                if(ls == null) {
                    res.setMsg("Erro ação inválida!");
                    out.println(res.toJson());
                    return;
                }
                
            }
            
            List<Lista> listas = daoLista.findByTitle(title, user.getId());

            if(listas != null && listas.size() > 0 && listas.get(0).getIdLista() != idLista) {
                res.setMsg("Já existe uma lista com esse título!");
                out.println(res.toJson());
                return;
            }
            
            description = description != null && !description.isEmpty() ? description : null;
            Lista list =  new Lista(idLista, title, description, user);
            
            Lista listRes = daoLista.save(list);

            if(listRes == null) {
                String msg = action.equals("editLista") ? "editar" : "adicionar";
                res.setMsg("Erro ao " + msg + " lista!");
            } else {
                
                String msg = action.equals("editLista") ? "editada" : "adicionada";
                res.setMsg("Lista " + msg + " com sucesso!");
                res.setStatus(1);
            }
            
            out.println(res.toJson());
            return;

        } else if(action.equals("getLista")) {
            
            if(idLista > 0) {
                
                Lista ls = daoLista.findById(idLista, user.getId());
                
                if(ls != null) {
                    res.addRes(ls.getTitle());
                    res.addRes(ls.getDescription());
                    res.setMsg("Lista encontrada com sucesso!");
                    res.setStatus(1);
                } 
                
            } else if(idLista == 0) {
                
            }
            
            out.println(res.toJson());
            return;
            
        }
        else if(action.equals("delLista") && idLista > 0) {
            
            boolean del = false;
            Lista ls = daoLista.findById(idLista, user.getId());
            
            if(ls != null) {
                del = daoLista.remove(ls);
            } 
            
            if(del) {
                res.setMsg("Lista removida com sucesso!");
                res.setStatus(1);
            } else {
                res.setMsg("Erro ao remover a lista!");
            }
            
            out.println(res.toJson());
            return;
            
        } else if(action.equals("shareLista")) {
            
            String checks = request.getParameter("checks");
            String email = request.getParameter("input-share-email");
            
            if(checks == null || checks != null && checks.isBlank()) {
                res.setMsg("Nenhuma lista foi selecionada!");
                out.println(res.toJson());
                return;
            }
            
            if (email == null || email != null && email.isBlank() || email != null && !ValidateEmail.isValidEmailAddress(email)) {
                res.setMsg("E-mail inválido!");
                out.println(res.toJson());
                return;
            } 
            
            email = email.toLowerCase();
            List<User> registers = daoUser.listByEmail(email);
            
            if(registers.size() <= 0) {
                res.setMsg("E-mail não cadastrado no site!");
                out.println(res.toJson());
                return;
            } else if(registers.size() == 1 && (registers.get(0).getEmail()).equals(user.getEmail())) {
                res.setMsg("Não pode ser seu próprio e-mail!");
                out.println(res.toJson());
                return;
            }
            
            String[] listas = checks.split(","); 
            
            if(listas.length > 0) {
                for(String nomes : listas) {
                    out.println(nomes);
                }
            }
            
            
            out.println(res.toJson());
            return;
            
            /*boolean del = false;
            Lista ls = daoLista.findById(idLista, user.getId());
            
            if(ls != null) {
                del = daoLista.remove(ls);
            } 
            
            if(del) {
                res.setMsg("Lista removida com sucesso!");
                res.setStatus(1);
            } else {
                res.setMsg("Erro ao remover a lista!");
            }
            */
            
            
        }
        
        return;
    }
}
